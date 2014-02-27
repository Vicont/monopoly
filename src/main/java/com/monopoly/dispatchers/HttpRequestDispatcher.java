package com.monopoly.dispatchers;

import com.monopoly.http.HttpServerRequest;
import com.monopoly.http.HttpServerResponse;
import com.monopoly.http.dispatcher.HttpDispatcher;

import java.util.Map;

/**
 * Dispatcher for HTTP commands
 *
 * @author vicont
 */
public class HttpRequestDispatcher implements HttpDispatcher {

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

        response.write("You've requested URI: " + request.getUri() + "\n");
        response.write("Controller: " + this.params.get("controllerName") + "\n");
        response.write("Action: " + this.params.get("actionName") + "\n");
        response.end();
    }

}
