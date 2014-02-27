package com.monopoly.http.dispatcher;

import com.monopoly.http.HttpServerRequest;
import com.monopoly.http.HttpServerResponse;

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
    public void setParams(Map<String, String> params);

    /**
     * Dispatch request
     *
     * @param request Request object
     * @param response Response object
     */
    public void process(HttpServerRequest request, HttpServerResponse response);

}
