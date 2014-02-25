package com.monopoly.dispatchers;

import com.monopoly.http.HttpServerRequest;
import com.monopoly.http.HttpServerResponse;
import com.monopoly.http.dispatcher.HttpDispatcher;

/**
 * Dispatcher for HTTP commands
 *
 * @author vicont
 */
public class HttpCommandDispatcher implements HttpDispatcher {

    /**
     * HTTP request
     */
    private HttpServerRequest request;

    /**
     * HTTP response
     */
    private HttpServerResponse response;

    @Override
    public void process(HttpServerRequest request, HttpServerResponse response) {
        this.request = request;
        this.response = response;

        response.end("You've requested URI: " + request.getUri() + "\n");
    }

}
