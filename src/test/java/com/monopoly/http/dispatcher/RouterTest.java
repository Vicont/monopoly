package com.monopoly.http.dispatcher;

import com.monopoly.dispatchers.HttpCommandDispatcher;
import com.monopoly.http.HttpServerRequest;
import com.monopoly.http.dispatcher.exception.HttpDispatcherNotFoundException;
import com.monopoly.http.dispatcher.exception.InvalidHttpDispatcherException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * RouterTest
 *
 * @author vicont
 */
public class RouterTest {

    /**
     * router
     */
    private Router router;

    @Before
    public void setUp() {
        Route route;
        List<Route> routes = new ArrayList<Route>();

        route = new Route();
        route.setPattern("/someUri/:foo");
        route.setDispatcher(HttpDispatcher.class);
        routes.add(route);

        route = new Route();
        route.setPattern("/:foo/:bar");
        route.setDispatcher(HttpCommandDispatcher.class);
        routes.add(route);

        this.router = new Router(routes);
    }

    @Test(expected = HttpDispatcherNotFoundException.class)
    public void testGetDispatcherWithUnroutableUri() throws HttpDispatcherNotFoundException, InvalidHttpDispatcherException {
        HttpServerRequest request = mock(HttpServerRequest.class);
        when(request.getUri()).thenReturn("/foo");
        this.router.getDispatcher(request);
    }

    @Test(expected = InvalidHttpDispatcherException.class)
    public void testGetInvalidDispatcher() throws HttpDispatcherNotFoundException, InvalidHttpDispatcherException {
        HttpServerRequest request = mock(HttpServerRequest.class);
        when(request.getUri()).thenReturn("/someUri/baz");
        this.router.getDispatcher(request);
    }

    @Test
    public void testGetValidDispatcher() throws HttpDispatcherNotFoundException, InvalidHttpDispatcherException {
        HttpServerRequest request = mock(HttpServerRequest.class);
        when(request.getUri()).thenReturn("/foo/bar");

        HttpDispatcher dispatcher = this.router.getDispatcher(request);
        assertEquals(HttpCommandDispatcher.class, dispatcher.getClass());
    }

}
