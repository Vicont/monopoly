package com.monopoly.http.dispatcher;

import com.monopoly.http.HttpServerRequest;
import com.monopoly.http.HttpServerResponse;
import com.monopoly.http.dispatcher.exception.HttpDispatcherNotFoundException;
import com.monopoly.http.dispatcher.exception.InvalidHttpDispatcherException;
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
     * @throws HttpDispatcherNotFoundException
     * @throws InvalidHttpDispatcherException
     */
    public void route (HttpServerRequest request, HttpServerResponse response) throws HttpDispatcherNotFoundException, InvalidHttpDispatcherException {
        String uri = request.getUri();

        for (ConstructedRoute constructedRoute : this.routes) {
            if (constructedRoute.matches(uri)) {
                Map<String, String> params = constructedRoute.findParams(uri);
                Class<HttpDispatcherFactory> dispatcherFactoryClass = constructedRoute.getDispatcherFactoryClass();

                HttpDispatcherFactory factory = applicationContext.getBean(dispatcherFactoryClass);
                HttpDispatcher dispatcher = factory.getDispatcher(params);
                dispatcher.process(request, response);
                return;
            }
        }

        throw new HttpDispatcherNotFoundException("Dispatcher for request \"" + uri + "\" is not found");
    }

}
