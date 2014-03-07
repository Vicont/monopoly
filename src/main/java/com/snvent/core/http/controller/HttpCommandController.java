package com.snvent.core.http.controller;

/**
 * HTTP command controller
 *
 * @author vicont
 */
public interface HttpCommandController extends HttpController {

    /**
     * Set request command data
     *
     * @param command Data
     */
    void setRequestCommand(Object command);

    /**
     * Retrieve request command
     *
     * @return Command
     */
    Object getRequestCommand();

}
