package com.monopoly.http.dispatcher.factory;

import com.monopoly.http.dispatcher.HttpDispatcher;
import com.monopoly.http.dispatcher.factory.exception.HttpDispatcherFactoryInitializationException;

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
