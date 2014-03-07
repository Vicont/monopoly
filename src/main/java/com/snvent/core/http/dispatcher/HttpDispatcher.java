package com.snvent.core.http.dispatcher;

import com.snvent.core.http.HttpServerRequest;
import com.snvent.core.http.HttpServerResponse;

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
     */
    void process();

}
