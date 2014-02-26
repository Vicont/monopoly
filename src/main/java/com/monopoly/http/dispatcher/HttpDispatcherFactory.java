package com.monopoly.http.dispatcher;

import com.monopoly.http.HttpServerRequest;
import com.monopoly.http.dispatcher.exception.HttpDispatcherNotFoundException;
import com.monopoly.http.dispatcher.exception.InvalidHttpDispatcherException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Factory that picks dispatcher for HTTP request
 *
 * @author vicont
 */
public class HttpDispatcherFactory {

    /**
     * Route list
     */
    private List<ConstructedRoute> routes;

    /**
     * Constructor
     *
     * @param routes Route list
     */
    public HttpDispatcherFactory(Set<Route> routes) {
        this.routes = new ArrayList<ConstructedRoute>();
        for (Route route : routes) {
            this.routes.add(new ConstructedRoute(route));
        }
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

        for (ConstructedRoute constructedRoute : this.routes) {
            if (constructedRoute.matches(uri)) {
                Class dispatcherClass = constructedRoute.getDispatcher();

                Map<String, String> params = constructedRoute.findParams(uri);
                for (String name : params.keySet()) {
                    request.setParam(name, params.get(name));
                }

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
