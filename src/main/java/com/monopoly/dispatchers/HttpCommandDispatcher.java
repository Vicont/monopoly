package com.monopoly.dispatchers;

import com.monopoly.ApplicationContextManager;
import com.monopoly.annotations.controller.CommandController;
import com.monopoly.http.HttpServerRequest;
import com.monopoly.http.HttpServerResponse;
import com.monopoly.http.dispatcher.HttpDispatcher;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * Dispatcher for HTTP commands
 *
 * @author vicont
 */
public class HttpCommandDispatcher implements HttpDispatcher {

    /**
     * Additional params
     */
    private Map<String, String> params;

    /**
     * HTTP request
     */
    private HttpServerRequest request;

    /**
     * HTTP response
     */
    private HttpServerResponse response;

    @Override
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public void process(HttpServerRequest request, HttpServerResponse response) {
        this.request = request;
        this.response = response;

        ApplicationContext context = ApplicationContextManager.getContext();
        Map<String, Object> controllers = context.getBeansWithAnnotation(CommandController.class);
        System.out.println(controllers.size());

        response.write("You've requested URI: " + request.getUri() + "\n");
        response.write("Command: " + this.params.get("commandName") + "\n");
        response.end();
    }

}
