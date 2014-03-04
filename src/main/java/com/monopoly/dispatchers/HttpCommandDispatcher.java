package com.monopoly.dispatchers;

import com.monopoly.dispatchers.definition.HttpCommandExecutionDefinition;
import com.monopoly.http.HttpServerRequest;
import com.monopoly.http.HttpServerResponse;
import com.monopoly.http.controller.HttpController;
import com.monopoly.http.dispatcher.HttpDispatcher;
import com.monopoly.http.dispatcher.exception.HttpDispatcherNotFoundException;
import com.monopoly.http.dispatcher.exception.InvalidHttpDispatcherException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
public class HttpCommandDispatcher implements HttpDispatcher, ApplicationContextAware {

    /**
     * Additional params
     */
    private Map<String, String> params;

    /**
     * Command execution definitions
     */
    private Map<String, HttpCommandExecutionDefinition> definitions;

    /**
     * HTTP request
     */
    private HttpServerRequest request;

    /**
     * HTTP response
     */
    private HttpServerResponse response;

    /**
     * Spring application context
     */
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public void setDefinitions(Map<String, HttpCommandExecutionDefinition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public void process(HttpServerRequest request, HttpServerResponse response) throws HttpDispatcherNotFoundException, InvalidHttpDispatcherException {
        this.request = request;
        this.response = response;

        if (!params.containsKey("commandName")) {
            throw new InvalidHttpDispatcherException("Parameter \"commandName\" is not found");
        }

        String commandName = params.get("commandName");
        if (!definitions.containsKey(commandName)) {
            throw new InvalidHttpDispatcherException("Command with name \"" + commandName + "\" is not found");
        }

        HttpCommandExecutionDefinition definition = definitions.get(commandName);
        HttpController controller = applicationContext.getBean(definition.getControllerName(), HttpController.class);
        controller.setRequest(this.request);
        controller.setResponse(this.response);

        try {
            Method method = definition.getMethod();
            method.invoke(controller);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
