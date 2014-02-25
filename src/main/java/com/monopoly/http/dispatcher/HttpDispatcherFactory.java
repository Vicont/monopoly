package com.monopoly.http.dispatcher;

import com.monopoly.http.HttpServerRequest;
import com.monopoly.http.dispatcher.exception.HttpDispatcherNotFoundException;
import com.monopoly.http.dispatcher.exception.InvalidHttpDispatcherException;

import java.util.Set;

/**
 * Factory that picks dispatcher for HTTP request
 *
 * @author vicont
 */
public class HttpDispatcherFactory {

    /**
     * Routes list
     */
    private Set<Route> routes;

    /**
     * Set available routes
     *
     * @param routes Routes
     */
    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
    }

    /**
     * Retrieve dispatcher which should handles request
     *
     * @param request HTTP request
     * @return Dispatcher
     * @throws HttpDispatcherNotFoundException
     * @throws InvalidHttpDispatcherException
     */
    public HttpDispatcher getDispatcher(HttpServerRequest request) throws HttpDispatcherNotFoundException, InvalidHttpDispatcherException {
        String uri = request.getUri();

        for (Route route : this.routes) {
            String pattern = route.getPattern();

            if (uri.equals(pattern)) {
                Class dispatcherClass = route.getDispatcher();
                try {
                    return (HttpDispatcher) dispatcherClass.newInstance();
                } catch (Exception e) {
                    String message = "Dispatcher for request \"" + uri + "\" with class [" + dispatcherClass.getName() + "] is invalid";
                    throw new InvalidHttpDispatcherException(message);
                }
            }
        }

        throw new HttpDispatcherNotFoundException("Dispatcher for request \"" + uri + "\" is not found");
    }

}
