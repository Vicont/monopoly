package com.monopoly.http.dispatcher;

import com.monopoly.http.dispatcher.exception.InvalidHttpDispatcherException;

/**
 * Interface for HTTP dispatcher factory
 *
 * @author vicont
 */
public interface HttpDispatcherFactory {

    /**
     * Initialize factory
     */
    void init();

    /**
     * Retrieve dispatcher
     *
     * @return Dispatcher
     * @throws InvalidHttpDispatcherException
     */
    HttpDispatcher getDispatcher() throws InvalidHttpDispatcherException;

}
