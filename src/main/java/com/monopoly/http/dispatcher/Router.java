package com.monopoly.http.dispatcher;

import com.monopoly.http.HttpServerRequest;
import com.monopoly.http.dispatcher.exception.HttpDispatcherNotFoundException;
import com.monopoly.http.dispatcher.exception.InvalidHttpDispatcherException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Factory that picks dispatcher for HTTP request
 *
 * @author vicont
 */
public class Router {

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
                HttpDispatcher dispatcher;
                Class dispatcherClass = constructedRoute.getDispatcher();

                try {
                    dispatcher = (HttpDispatcher) dispatcherClass.newInstance();
                } catch (Exception e) {
                    String message = "Dispatcher for request \"" + uri + "\" with class [" + dispatcherClass.getName() + "] is invalid";
                    throw new InvalidHttpDispatcherException(message);
                }

                Map<String, String> params = constructedRoute.findParams(uri);
                dispatcher.setParams(params);
                return dispatcher;
            }
        }

        throw new HttpDispatcherNotFoundException("Dispatcher for request \"" + uri + "\" is not found");
    }

}
