package com.monopoly.dispatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monopoly.dispatchers.definition.HttpCommandExecutionDefinition;
import com.monopoly.http.controller.HttpCommandController;
import com.monopoly.http.dispatcher.AbstractHttpDispatcher;
import com.monopoly.http.dispatcher.exception.HttpDispatcherNotFoundException;
import com.monopoly.http.dispatcher.exception.InvalidHttpDispatcherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
    private static final Logger log = LoggerFactory.getLogger(HttpCommandDispatcher.class);

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
    public void process() throws HttpDispatcherNotFoundException, InvalidHttpDispatcherException {
        if (!params.containsKey("commandName")) {
            throw new InvalidHttpDispatcherException("Parameter \"commandName\" is not found");
        }

        String commandName = params.get("commandName");
        if (!definitions.containsKey(commandName)) {
            throw new InvalidHttpDispatcherException("Command with name \"" + commandName + "\" is not found");
        }

        HttpCommandExecutionDefinition definition = definitions.get(commandName);
        Object command = this.parseCommand(definition.getStructure());

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
            e.printStackTrace();
        }
    }

    private Object parseCommand(Class structure) {
        String body = this.request.getBody();
        ObjectMapper mapper = new ObjectMapper();
        Object command = null;

        try {
            command = mapper.readValue(body, structure);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return command;
    }

}
