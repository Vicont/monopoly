package com.monopoly.dispatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monopoly.dispatchers.definition.HttpCommandExecutionDefinition;
import com.monopoly.http.controller.HttpCommandController;
import com.monopoly.http.dispatcher.AbstractHttpDispatcher;
import io.netty.handler.codec.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Dispatcher for HTTP commands
 *
 * @author vicont
 */
@Component
@Scope("prototype")
public class HttpCommandDispatcher extends AbstractHttpDispatcher {

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger("http-protocol-command");

    /**
     * Command execution definitions
     */
    private Map<String, HttpCommandExecutionDefinition> definitions;

    /**
     * Set execution definitions
     *
     * @param definitions Definitions map
     */
    public void setDefinitions(Map<String, HttpCommandExecutionDefinition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public void process() {
        this.log("REQUEST", this.request.getBody());

        if (!params.containsKey("commandName")) {
            this.createErrorResponse("Parameter \"commandName\" is not found");
            return;
        }

        String commandName = params.get("commandName");
        if (!definitions.containsKey(commandName)) {
            this.createErrorResponse("Command with name \"" + commandName + "\" is not found");
            return;
        }

        HttpCommandExecutionDefinition definition = definitions.get(commandName);
        Object command;

        try {
            command = this.parseCommand(definition.getStructure());
        } catch (Exception e) {
            this.createErrorResponse(e);
            return;
        }

        HttpCommandController controller = applicationContext.getBean(definition.getControllerName(), HttpCommandController.class);
        controller.setRequest(this.request);
        controller.setResponse(this.response);
        controller.setCommand(command);

        Method actionMethod = definition.getActionMethod();
        Method beforeMethod = definition.getBeforeMethod();
        Method afterMethod = definition.getAfterMethod();

        try {
            if (beforeMethod != null) {
                beforeMethod.invoke(controller);
            }

            actionMethod.invoke(controller);

            if (afterMethod != null) {
                afterMethod.invoke(controller);
            }
        } catch (Exception e) {
            this.createErrorResponse(e);
        }
    }

    /**
     * Parse command from request
     *
     * @param structure Command structure
     * @return Command
     * @throws Exception
     */
    private Object parseCommand(Class structure) throws Exception {
        String body = this.request.getBody();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(body, structure);
    }

    /**
     * Create error response caused by exception
     *
     * @param e Exception
     */
    private void createErrorResponse(Throwable e) {
        String message = String.valueOf(e) + ": ";

        if (e.getMessage() != null) {
            message += e.getMessage();
        } else {
            if (e instanceof InvocationTargetException) {
                Throwable te = ((InvocationTargetException) e).getTargetException();
                message += te.getMessage();
            }
        }

        this.createErrorResponse(message);
    }

    /**
     * Create error response with message
     *
     * @param message Message
     */
    private void createErrorResponse(String message) {
        ErrorOutgoingCommand command = new ErrorOutgoingCommand();
        command.setError(message);
        ObjectMapper mapper = new ObjectMapper();

        if (!this.response.isSent()) {
            String output;

            try {
                output = mapper.writeValueAsString(command);
                this.response.setHeader(HttpHeaders.Names.CONTENT_TYPE, "application/json");
            } catch (Exception e) {
                output = e.getMessage();
            }

            this.log("ERROR RESPONSE", output);
            this.response.end(output);
        } else {
            this.log("ERROR", message);
        }
    }

    /**
     * Log protocol
     *
     * @param marker Marker
     * @param message Message
     */
    private void log(String marker, String message) {
        String fixMarker = String.format("%1$-9s", marker).substring(0, 9);
        log.info(fixMarker + " [" + Integer.toHexString(hashCode()) + "]: " + message);
    }

}

/**
 * Command structure for error response
 */
class ErrorOutgoingCommand {

    /**
     * Error message
     */
    private String error = "";

    /**
     * Retrieve error message
     *
     * @return Message
     */
    public String getError() {
        return error;
    }

    /**
     * Set error message
     *
     * @param error Message
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Result flag (always false)
     *
     * @return Result flag
     */
    public boolean getSuccess() {
        return false;
    }

}
