package com.monopoly.http.controller;

import com.monopoly.http.HttpServerRequest;
import com.monopoly.http.HttpServerResponse;

/**
 * HTTP Controller
 *
 * @author vicont
 */
public interface HttpController {

    void setRequest(HttpServerRequest request);

    void setResponse(HttpServerResponse response);

}
