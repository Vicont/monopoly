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

    private HttpRequest nettyReq;

    private HttpServerRequest request;

    @Before
    public void createRequest() {
        nettyReq = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "http://example.com/location?param=value");
        request = new HttpServerRequest(nettyReq);
    }

    @Test
    public void testUri() {
        assertEquals("http://example.com/location?param=value", request.getUri());
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
        HttpHeaders.setHost(nettyReq, "example.org");
        assertEquals("example.org", request.getHost());
    }

    @Test
    public void testIsKeepAlive() {
        HttpHeaders.setKeepAlive(nettyReq, true);
        assertTrue(request.isKeepAlive());
    }

    @Test
    public void testParams() {
        assertNull(request.getParam("param2"));
        assertEquals("value", request.getParam("param"));

        request.setParam("param2", "value2");
        assertEquals("value2", request.getParam("param2"));
        assertEquals("value2", request.getParams().get("param2"));

        //request.setBody("param3=value3");
        //assertEquals("value3", request.getParam("param3"));
    }

    @Test
    public void testCookies() {
        assertNull(request.getCookie("key1"));
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
