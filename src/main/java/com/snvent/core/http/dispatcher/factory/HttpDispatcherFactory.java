package com.snvent.core.http.dispatcher.factory;

import com.snvent.core.http.dispatcher.HttpDispatcher;
import com.snvent.core.http.dispatcher.factory.exception.HttpDispatcherFactoryInitializationException;

/**
 * Interface for HTTP dispatcher factory
 *
 * @author vicont
 */
public interface HttpDispatcherFactory {

    /**
     * Initialize factory
     */
    void init() throws HttpDispatcherFactoryInitializationException;

    /**
     * Retrieve dispatcher
     *
     * @return Dispatcher
     */
    HttpDispatcher getDispatcher();

}
