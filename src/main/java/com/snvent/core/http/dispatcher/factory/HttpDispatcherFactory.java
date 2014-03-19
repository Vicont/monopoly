package com.snvent.core.http.dispatcher.factory;

import com.snvent.core.http.dispatcher.HttpDispatcher;
import com.snvent.core.http.dispatcher.factory.exception.InvalidCommandStructureException;
import com.snvent.core.http.dispatcher.factory.exception.InvalidControllerException;

/**
 * Interface for HTTP dispatcher factory
 *
 * @author vicont
 */
public interface HttpDispatcherFactory {

    /**
     * Initialize factory
     *
     * throws InvalidControllerException, InvalidCommandStructureException
     */
    void init() throws InvalidControllerException, InvalidCommandStructureException;

    /**
     * Retrieve dispatcher
     *
     * @return Dispatcher
     */
    HttpDispatcher getDispatcher();

}
