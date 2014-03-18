package com.snvent.core.http.dispatcher.factory;

import com.snvent.core.http.dispatcher.HttpDispatcher;
import com.snvent.core.http.dispatcher.HttpRequestDispatcher;
import com.snvent.core.http.dispatcher.definition.HttpCommandExecutionDefinition;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Dispatcher factory
 *
 * @author vicont
 */
@Component
public class HttpRequestDispatcherFactory extends AbstractHttpDispatcherFactory {

    private final Map<String, HttpCommandExecutionDefinition> definitions = new HashMap<String, HttpCommandExecutionDefinition>();

    @PostConstruct
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
