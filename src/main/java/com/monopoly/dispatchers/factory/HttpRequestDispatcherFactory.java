package com.monopoly.dispatchers.factory;

import com.monopoly.dispatchers.HttpRequestDispatcher;
import com.monopoly.dispatchers.definition.HttpCommandExecutionDefinition;
import com.monopoly.http.dispatcher.factory.AbstractHttpDispatcherFactory;
import com.monopoly.http.dispatcher.HttpDispatcher;
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

    private final Map<String,HttpCommandExecutionDefinition> definitions = new HashMap<String, HttpCommandExecutionDefinition>();

    @Override
    public void init() {

    }

    @Override
    public HttpDispatcher getDispatcher() {
        HttpRequestDispatcher dispatcher = applicationContext.getBean(HttpRequestDispatcher.class);
        dispatcher.setDefinitions(definitions);
        return dispatcher;
    }

}
