package com.snvent.core.http.route;

import com.snvent.core.AbstractTest;
import com.snvent.core.http.HttpServerRequest;
import com.snvent.core.http.HttpServerResponse;
import com.snvent.core.http.route.exception.InvalidDispatcherFactoryException;
import com.snvent.core.http.route.exception.RouteException;
import com.snvent.core.http.route.exception.RouteNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * RouterTest
 *
 * @author vicont
 */
public class RouterTest extends AbstractTest {

    /**
     * Router
     */
    private Router router;

    @Before
    public void setUp() {
        this.router = this.applicationContext.getBean(Router.class);
    }

    @Test(expected = RouteNotFoundException.class)
    public void testRouteRequestWithUnroutableUri() throws RouteException {
        HttpServerRequest request = mock(HttpServerRequest.class);
        when(request.getUri()).thenReturn("/foo");
        HttpServerResponse response = mock(HttpServerResponse.class);

        this.router.route(request, response);
    }

    @Test(expected = InvalidDispatcherFactoryException.class)
    public void testRouteWithInvalidDispatcher() throws RouteException {
        HttpServerRequest request = mock(HttpServerRequest.class);
        when(request.getUri()).thenReturn("/someUri/baz");
        HttpServerResponse response = mock(HttpServerResponse.class);

        this.router.route(request, response);
    }

    @Test
    public void testRouteValidRequest() throws RouteException {
        HttpServerRequest request = mock(HttpServerRequest.class);
        when(request.getUri()).thenReturn("/any/location");
        HttpServerResponse response = mock(HttpServerResponse.class);

        this.router.route(request, response);
    }

}
