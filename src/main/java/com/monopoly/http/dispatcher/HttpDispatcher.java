package com.monopoly.http.dispatcher;

import com.monopoly.http.HttpServerRequest;
import com.monopoly.http.HttpServerResponse;

/**
 * HTTP request dispatcher
 *
 * @author vicont
 */
public interface HttpDispatcher {

    public void process(HttpServerRequest request, HttpServerResponse response);

}
