package com.snvent.core.http.controller;

import com.snvent.core.http.HttpServerRequest;
import com.snvent.core.http.HttpServerResponse;

/**
 * HTTP Controller
 *
 * @author vicont
 */
public interface HttpController {

    void setRequest(HttpServerRequest request);

    void setResponse(HttpServerResponse response);

}
