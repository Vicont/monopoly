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
     * Set HTTP request object
     *
     * @param request HTTP request
     */
    void setRequest(HttpServerRequest request);

    /**
     * Set HTTP response object
     *
     * @param response HTTP response
     */
    void setResponse(HttpServerResponse response);

    /**
     * Set additional params
     *
     * @param params Params map
     */
    void setParams(Map<String, String> params);

    /**
     * Dispatch request
     *
     * @throws HttpDispatcherNotFoundException
     * @throws InvalidHttpDispatcherException
     */
    void process() throws HttpDispatcherNotFoundException, InvalidHttpDispatcherException;

}
