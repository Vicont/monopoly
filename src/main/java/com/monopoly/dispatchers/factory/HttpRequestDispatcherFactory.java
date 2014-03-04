package com.monopoly.dispatchers.factory;

import com.monopoly.dispatchers.HttpRequestDispatcher;
import com.monopoly.http.dispatcher.AbstractHttpDispatcherFactory;
import com.monopoly.http.dispatcher.HttpDispatcher;
import com.monopoly.http.dispatcher.exception.InvalidHttpDispatcherException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Dispatcher factory
 *
 * @author vicont
 */
@Component
public class HttpRequestDispatcherFactory extends AbstractHttpDispatcherFactory {

    private final Map<String, String> controllers = new HashMap<String, String>();

    @Override
    public void init() {

    }

    @Override
    public HttpDispatcher getDispatcher(Map<String, String> params) throws InvalidHttpDispatcherException {
        HttpDispatcher dispatcher = applicationContext.getBean(HttpRequestDispatcher.class);
        dispatcher.setParams(params);
        dispatcher.setControllers(controllers);
        return dispatcher;
    }

}
