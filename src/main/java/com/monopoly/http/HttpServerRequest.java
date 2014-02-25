package com.monopoly.http;

import io.netty.handler.codec.http.*;

import java.util.*;

import static io.netty.handler.codec.http.HttpHeaders.Names.COOKIE;

/**
 * HTTP server request
 *
 * @author vicont
 */
public class HttpServerRequest {

    /**
     * Decorated Netty request object
     */
    private HttpRequest req;

    /**
     * Cookies map
     */
    private Map<String, Cookie> cookies = new HashMap<String, Cookie>();

    /**
     * Parameters map (includes query and body parameters)
     */
    private Map<String, String> params = new HashMap<String, String>();

    /**
     * Body content
     */
    private String body;

    /**
     * Constructor
     *
     * @param req Netty request
     */
    public HttpServerRequest(HttpRequest req) {
        this.req = req;

        this.decodeParams(req.getUri(), true);
        this.decodeCookies();
    }

    /**
     * Retrieve protocol version
     *
     * @return Protocol version
     */
    public HttpVersion getProtocolVersion() {
        return this.req.getProtocolVersion();
    }

    /**
     * Retrieve HTTP method
     *
     * @return Method object
     */
    public HttpMethod getMethod() {
        return req.getMethod();
    }

    /**
     * Retrieve requested URI
     *
     * @return URI
     */
    public String getUri() {
        return req.getUri();
    }

    /**
     * Retrieve the headers object
     *
     * @return Headers object
     */
    public HttpHeaders getHeaders() {
        return req.headers();
    }

    /**
     * Retrieve requested host
     *
     * @return Host
     */
    public String getHost() {
        return HttpHeaders.getHost(req, "unknown");
    }

    /**
     * Returns true if the connection can remain open and thus 'kept alive'
     *
     * @return Is keep alive
     */
    public boolean isKeepAlive() {
        return HttpHeaders.isKeepAlive(req);
    }

    /**
     * Set parameter
     *
     * @param name Param name
     * @param value Param value
     */
    public void setParam(String name, String value) {
        this.params.put(name, value);
    }

    /**
     * Retrieve parameter value by name
     *
     * @param name Param name
     * @return Param value
     */
    public String getParam(String name) {
        return this.params.get(name);
    }

    /**
     * Retrieve map with all request parameters
     *
     * @return Parameters map
     */
    public Map<String, String> getParams() {
        return this.params;
    }

    /**
     * Retrieve cookie object by name
     *
     * @param name Cookie name
     * @return Cookie object
     */
    public Cookie getCookie(String name) {
        return this.cookies.get(name);
    }

    /**
     * Retrieve map with all cookies
     *
     * @return Cookies map
     */
    public Map<String, Cookie> getCookies() {
        return this.cookies;
    }

    /**
     * Set body content
     *
     * @param body Body content
     * @throws UnsupportedOperationException
     */
    public void setBody(String body) throws UnsupportedOperationException {
        if (this.body == null) {
            this.body = body;
            this.decodeParams(body, false);
        } else {
            throw new UnsupportedOperationException("Request body have already decoded");
        }
    }

    /**
     * Retrieve body content
     *
     * @return Body content
     */
    public String getBody() {
        return this.body;
    }

    /**
     * Decode parameters from string and store them
     *
     * @param data Uri or another parameters string
     * @param hasPath True if data contains path
     */
    private void decodeParams(String data, boolean hasPath) {
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(data, hasPath);
        Map<String, List<String>> params = queryStringDecoder.parameters();

        if (!params.isEmpty()) {
            for (Map.Entry<String, List<String>> param: params.entrySet()) {
                String key = param.getKey();
                List<String> values = param.getValue();
                for (String value : values) {
                    this.setParam(key, value);
                }
            }
        }
    }

    /**
     * Decode cookies from request headers and store them
     */
    private void decodeCookies() {
        String cookieString = this.getHeaders().get(COOKIE);
        if (cookieString != null) {
            Set<Cookie> cookieSet = CookieDecoder.decode(cookieString);

            for (Cookie cookie : cookieSet) {
                this.cookies.put(cookie.getName(), cookie);
            }
        }
    }

}
