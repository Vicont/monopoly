package com.snvent.core.http.route;

import com.snvent.core.http.HttpServerRequest;
import com.snvent.core.http.HttpServerResponse;
import com.snvent.core.http.route.exception.InvalidDispatcherFactoryException;
import com.snvent.core.http.route.exception.RouteException;
import com.snvent.core.http.route.exception.RouteNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * RouterTest
 *
 * @author vicont
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/beans/beans.xml"})
public class RouterTest implements ApplicationContextAware {

    protected ApplicationContext applicationContext;

    @Override
    public final void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

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
        when(request.getUri()).thenReturn("/myCommand/StubCommand");
        when(request.getBody()).thenReturn("{}");
        HttpServerResponse response = mock(HttpServerResponse.class);

        this.router.route(request, response);
    }

}
