package com.monopoly.dispatchers;

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
     * Available controllers
     */
    private Map<String, String> controllers;

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
    public void setControllers(Map<String, String> controllers) {
        this.controllers = controllers;
    }

    @Override
    public void process(HttpServerRequest request, HttpServerResponse response) throws HttpDispatcherNotFoundException, InvalidHttpDispatcherException {
        this.request = request;
        this.response = response;

        if (!params.containsKey("commandName")) {
            throw new InvalidHttpDispatcherException("Parameter \"commandName\" is not found");
        }

        String commandName = params.get("commandName");
        if (!controllers.containsKey(commandName)) {
            throw new InvalidHttpDispatcherException("Command with name \"" + commandName + "\" is not found");
        }

        String controllerName = controllers.get(commandName);
        HttpController controller = applicationContext.getBean(controllerName, HttpController.class);
        controller.setRequest(this.request);
        controller.setResponse(this.response);

        Class c = controller.getClass();
        try {
            Method method = c.getMethod("index");
            method.invoke(controller);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
