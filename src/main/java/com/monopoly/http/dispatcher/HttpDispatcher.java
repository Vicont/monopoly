package com.monopoly.http.dispatcher;

import com.monopoly.http.HttpServerRequest;
import com.monopoly.http.HttpServerResponse;
import com.monopoly.http.dispatcher.exception.HttpDispatcherNotFoundException;
import com.monopoly.http.dispatcher.exception.InvalidHttpDispatcherException;

import java.util.Map;

/**
 * HTTP request dispatcher
 *
 * @author vicont
 */
public interface HttpDispatcher {

    /**
     * Set additional params
     *
     * @param params Params map
     */
    void setParams(Map<String, String> params);

    /**
     * Set available controllers
     *
     * @param controllers Controllers map
     */
    void setControllers(Map<String, String> controllers);

    /**
     * Dispatch request
     *
     * @param request Request object
     * @param response Response object
     */
    void process(HttpServerRequest request, HttpServerResponse response) throws HttpDispatcherNotFoundException, InvalidHttpDispatcherException;

}
