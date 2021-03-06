package com.snvent.core.http.dispatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snvent.core.http.dispatcher.definition.HttpCommandExecutionDefinition;
import com.snvent.core.http.controller.HttpCommandController;
import com.snvent.core.http.dispatcher.exception.InvocationCommandException;
import io.netty.handler.codec.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
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
        Object requestCommand;
        Object responseCommand;

        try {
            requestCommand = this.parseCommand(definition.getStructure());
        } catch (Exception e) {
            this.createErrorResponse("Error while decoding request: " + e);
            return;
        }

        HttpCommandController controller = applicationContext.getBean(definition.getControllerName(), HttpCommandController.class);
        controller.setRequest(this.request);
        controller.setResponse(this.response);
        controller.setRequestCommand(requestCommand);

        Method actionMethod = definition.getActionMethod();
        Method beforeMethod = definition.getBeforeMethod();
        Method afterMethod = definition.getAfterMethod();

        try {
            if (beforeMethod != null) {
                beforeMethod.invoke(controller);
            }

            responseCommand = actionMethod.invoke(controller);

            if (afterMethod != null) {
                afterMethod.invoke(controller);
            }
        } catch (Exception e) {
            this.createErrorResponse(e);
            return;
        }

        this.createResponse(responseCommand);
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
     * Create success response
     *
     * @param data Response data
     */
    private void createResponse(Object data) {
        ObjectMapper mapper = new ObjectMapper();
        SuccessOutgoingCommand command = new SuccessOutgoingCommand();
        command.setData(data);

        if (!this.response.isSent()) {
            String output;

            try {
                try {
                    output = mapper.writeValueAsString(command);
                } catch (Exception e) {
                    this.createErrorResponse("Error while encoding response: " + e.getMessage());
                    return;
                }
                this.response.setHeader(HttpHeaders.Names.CONTENT_TYPE, "application/json");
                this.response.end(output);
                this.log("RESPONSE", output);
            } catch (Exception e) {
                this.log("ERROR", "Error while sending response: " + e.getMessage());
            }
        } else {
            this.log("ERROR", "Response has already sent");
        }
    }

    /**
     * Create error response caused by exception
     *
     * @param e Exception
     */
    private void createErrorResponse(Throwable e) {
        String message = e + ": ";

        if (e.getMessage() != null) {
            message += e.getMessage();
        } else if (e instanceof InvocationTargetException) {
            Throwable te = ((InvocationTargetException) e).getTargetException();
            if (te instanceof InvocationCommandException) {
                this.createErrorResponse(te.getMessage(), ((InvocationCommandException) te).getCode());
            } else if (te.getMessage() != null) {
                message += te.getMessage();
            } else {
                message += te.toString() + Arrays.toString(te.getStackTrace());
            }
        } else {
            message += e.toString() + Arrays.toString(e.getStackTrace());
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
        this.sendErrorCommand(command);
    }

    /**
     * Create error response with message and error code
     *
     * @param message Message
     * @param code Error code
     */
    private void createErrorResponse(String message, int code) {
        ErrorOutgoingCommand command = new ErrorOutgoingCommand();
        command.setError(message);
        command.setCode(code);
        this.sendErrorCommand(command);
    }

    /**
     * Encode and send error command to client
     *
     * @param command Error command
     */
    private void sendErrorCommand(ErrorOutgoingCommand command) {
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
            this.log("ERROR", command.getError());
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
     * Error code
     */
    private int code;

    /**
     * Result flag (always false)
     *
     * @return Result flag
     */
    public boolean getSuccess() {
        return false;
    }

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
     * Retrieve error code
     *
     * @return Error code
     */
    public int getCode() {
        return this.code;
    }

    /**
     * Set error code
     *
     * @param code Error code
     */
    public void setCode(int code) {
        this.code = code;
    }

}

/**
 * Command structure for success response
 */
class SuccessOutgoingCommand {

    /**
     * Command data
     */
    private Object data;

    /**
     * Result flag (always true)
     *
     * @return Result flag
     */
    public boolean getSuccess() {
        return true;
    }

    /**
     * Retrieve command data
     *
     * @return Data object
     */
    public Object getData() {
        return data;
    }

    /**
     * Set command data
     *
     * @param data Data object
     */
    public void setData(Object data) {
        this.data = data;
    }

}
