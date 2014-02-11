package com.monopoly.http;

import io.netty.handler.codec.http.*;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP server request
 *
 * @author vicont
 */
public class HttpServerRequest {

    private HttpRequest req;

    private String host;

    private Map<String, Cookie> cookies = new HashMap<String, Cookie>();

    private Map<String, String> params = new HashMap<String, String>();

    private String body;

    public HttpServerRequest(HttpRequest req) {
        this.req = req;
        this.host = HttpHeaders.getHost(req, "unknown");
    }

    public HttpVersion getProtocolVersion() {
        return this.req.getProtocolVersion();
    }

    public HttpMethod getMethod() {
        return req.getMethod();
    }

    public String getHost() {
        return this.host;
    }

    public String getUri() {
        return req.getUri();
    }

    public HttpHeaders getHeaders() {
        return req.headers();
    }

    public void setParam(String name, String value) {
        this.params.put(name, value);
    }

    public String getParam(String name) {
        return this.params.get(name);
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public void setCookie(String name, Cookie cookie) {
        this.cookies.put(name, cookie);
    }

    public Cookie getCookie(String name) {
        return this.cookies.get(name);
    }

    public Map<String, Cookie> getCookies() {
        return this.cookies;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }

}
