package com.monopoly.http.dispatcher;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * ConstructedRouteTest
 *
 * @author vicont
 */
public class ConstructedRouteTest {

    @Test
    public void testMatchesWithOptionalParameter() {
        Route route = new Route();
        route.setPattern("/:param1?");
        ConstructedRoute constructedRoute = new ConstructedRoute(route);

        assertTrue(constructedRoute.matches("/"));
        assertTrue(constructedRoute.matches("/foo"));
        assertTrue(constructedRoute.matches("/foo/bar"));
        assertTrue(constructedRoute.matches("/foo/bar?param=value"));
    }

    @Test
    public void testMatchesWithOneParameter() {
        Route route = new Route();
        route.setPattern("/someUri/:param1");
        ConstructedRoute constructedRoute = new ConstructedRoute(route);

        assertTrue(constructedRoute.matches("/someUri/someCommand"));
        assertTrue(constructedRoute.matches("/someUri/someCommand?param=value"));
        assertTrue(constructedRoute.matches("/someUri/someCommand/noise/?param=value"));
        assertFalse(constructedRoute.matches("/uri/"));
        assertFalse(constructedRoute.matches("/?param=value"));
    }

    @Test
    public void testMatchesWithTwoParameters() {
        Route route = new Route();
        route.setPattern("/:param1/:param2");
        ConstructedRoute constructedRoute = new ConstructedRoute(route);

        assertTrue(constructedRoute.matches("/foo/bar"));
        assertTrue(constructedRoute.matches("/foo/bar?param=value"));
        assertTrue(constructedRoute.matches("/foo/bar/baz/?param=value"));
        assertFalse(constructedRoute.matches("/foo/"));
        assertFalse(constructedRoute.matches("/?param=value"));
    }

    @Test
    public void testFindParamsWithOneParameter() {
        Route route = new Route();
        route.setPattern("/someUri/:param1");
        ConstructedRoute constructedRoute = new ConstructedRoute(route);
        Map<String, String> foundParams = constructedRoute.findParams("/someUri/foo");

        Map<String, String> params = new HashMap<String, String>();
        assertNotEquals(params, foundParams);

        params.put("param1", "foo");
        assertEquals(params, foundParams);

        params.put("param2", "bar");
        assertNotEquals(params, foundParams);
    }

    @Test
    public void testFindParamsWithTwoParameters() {
        Route route = new Route();
        route.setPattern("/:param1/:param2");
        ConstructedRoute constructedRoute = new ConstructedRoute(route);
        Map<String, String> foundParams = constructedRoute.findParams("/foo/bar");

        Map<String, String> params = new HashMap<String, String>();
        assertNotEquals(params, foundParams);

        params.put("param1", "foo");
        assertNotEquals(params, foundParams);

        params.put("param2", "bar");
        assertEquals(params, foundParams);

        params.put("param2", "baz");
        assertNotEquals(params, foundParams);
    }

}
