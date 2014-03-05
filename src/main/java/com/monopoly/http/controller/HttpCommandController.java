package com.monopoly.http.controller;

/**
 * HTTP command controller
 *
 * @author vicont
 */
public interface HttpCommandController extends HttpController {

    /**
     * Set command data
     *
     * @param command Data
     */
    void setCommand(Object command);

}
