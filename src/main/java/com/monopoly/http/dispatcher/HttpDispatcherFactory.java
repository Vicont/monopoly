package com.monopoly.http.dispatcher;

import com.monopoly.http.dispatcher.exception.InvalidHttpDispatcherException;

import java.util.Map;

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
     * @param params Params from request
     * @return Dispatcher
     * @throws InvalidHttpDispatcherException
     */
    HttpDispatcher getDispatcher(Map<String, String> params) throws InvalidHttpDispatcherException;

}
