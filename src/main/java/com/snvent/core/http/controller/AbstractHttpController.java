package com.snvent.core.http.controller;

import com.snvent.core.http.HttpServerRequest;
import com.snvent.core.http.HttpServerResponse;

/**
 * Abstract HTTP controller
 *
 * @author vicont
 */
abstract public class AbstractHttpController implements HttpController {

    /**
     * Request object
     */
    protected HttpServerRequest request;

    /**
     * Response object
     */
    protected HttpServerResponse response;


    @Override
    public void setRequest(HttpServerRequest request) {
        this.request = request;
    }

    @Override
    public void setResponse(HttpServerResponse response) {
        this.response = response;
    }

}
