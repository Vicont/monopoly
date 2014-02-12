package com.monopoly.http;

import io.netty.handler.codec.http.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * HttpServerRequestTest
 *
 * @author vicont
 */
public class HttpServerRequestTest {

    private HttpServerRequest request;

    @Before
    public void createRequest() {
        HttpRequest nettyReq = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "http://example.com/");
        HttpHeaders.setHost(nettyReq, "example.org");
        request = new HttpServerRequest(nettyReq);
    }

    @Test
    public void testUri() {
        assertEquals("http://example.com/", request.getUri());
    }

    @Test
    public void testMethod() {
        assertEquals(HttpMethod.GET, request.getMethod());
    }

    @Test
    public void testProtocolVersion() {
        assertEquals(HttpVersion.HTTP_1_1, request.getProtocolVersion());
    }

    @Test
    public void testHost() {
        assertEquals("example.org", request.getHost());
    }

    @Test
    public void testParams() {
        assertNull(request.getParam("param"));

        request.setParam("param", "value");
        assertEquals("value", request.getParam("param"));
        assertEquals("value", request.getParams().get("param"));
    }

    @Test
    public void testCookies() {
        assertNull(request.getCookie("key1"));

        Cookie cookie = new DefaultCookie("key1", "value1");
        request.setCookie("key", cookie);
        assertEquals(cookie, request.getCookie("key"));
        assertEquals(cookie, request.getCookies().get("key"));
    }

    @Test
    public void testBody() {
        assertNull(request.getBody());

        request.setBody("some content");
        assertEquals("some content", request.getBody());
    }

    @Test
    public void testHeaders() {
        HttpHeaders headers = request.getHeaders();
        assertNotNull(headers);
    }

}
