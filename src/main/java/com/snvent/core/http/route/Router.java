package com.snvent.core.http.route;

import com.snvent.core.http.HttpServerRequest;
import com.snvent.core.http.HttpServerResponse;
import com.snvent.core.http.dispatcher.HttpDispatcher;
import com.snvent.core.http.dispatcher.factory.HttpDispatcherFactory;
import com.snvent.core.http.route.exception.InvalidDispatcherFactoryException;
import com.snvent.core.http.route.exception.RouteException;
import com.snvent.core.http.route.exception.RouteNotFoundException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Factory that picks dispatcher for HTTP request
 *
 * @author vicont
 */
public class Router implements ApplicationContextAware {

    /**
     * Spring application context
     */
    private ApplicationContext applicationContext;

    /**
     * Route list
     */
    private List<ConstructedRoute> routes;

    /**
     * Constructor
     *
     * @param routes Route list
     */
    public Router(List<Route> routes) {
        this.routes = new ArrayList<ConstructedRoute>();
        for (Route route : routes) {
            this.routes.add(new ConstructedRoute(route));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Route HTTP request
     *
     * @param request HTTP request
     * @throws RouteException
     */
    public void route (HttpServerRequest request, HttpServerResponse response) throws RouteException {
        String uri = request.getUri();

        for (ConstructedRoute constructedRoute : this.routes) {
            if (constructedRoute.matches(uri)) {
                Map<String, String> params = constructedRoute.findParams(uri);
                Class<? extends HttpDispatcherFactory> dispatcherFactoryClass = constructedRoute.getDispatcherFactoryClass();

                try {
                    HttpDispatcherFactory factory = applicationContext.getBean(dispatcherFactoryClass.getName(), dispatcherFactoryClass);

                    HttpDispatcher dispatcher = factory.getDispatcher();
                    dispatcher.setParams(params);
                    dispatcher.setRequest(request);
                    dispatcher.setResponse(response);
                    dispatcher.process();
                    return;
                } catch (BeansException e) {
                    throw new InvalidDispatcherFactoryException("Component with class \"" + dispatcherFactoryClass + "\" is not found");
                }
            }
        }

        throw new RouteNotFoundException("Route for request \"" + uri + "\" is not found");
    }

}
