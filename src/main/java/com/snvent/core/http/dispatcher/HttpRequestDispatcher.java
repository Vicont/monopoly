package com.snvent.core.http.dispatcher;

import com.snvent.core.http.dispatcher.definition.HttpCommandExecutionDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Dispatcher for HTTP commands
 *
 * @author vicont
 */
@Component
@Scope("prototype")
public class HttpRequestDispatcher extends AbstractHttpDispatcher {

    /**
     * Available controllers
     */
    private Map<String, HttpCommandExecutionDefinition> controllers;

    public void setDefinitions(Map<String, HttpCommandExecutionDefinition> controllers) {
        this.controllers = controllers;
    }

    @Override
    public void process() {
        response.write("You've requested URI: " + request.getUri() + "\n");
        response.write("Controller: " + this.params.get("controllerName") + "\n");
        response.write("Action: " + this.params.get("actionName") + "\n");
        response.end();
    }

}
