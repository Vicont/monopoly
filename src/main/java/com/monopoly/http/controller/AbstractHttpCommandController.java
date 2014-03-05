package com.monopoly.http.controller;

/**
 * Abstract HTTP command controller
 *
 * @author vicont
 */
abstract public class AbstractHttpCommandController extends AbstractHttpController implements HttpCommandController {

    /**
     * Command data
     */
    protected Object command;

    @Override
    public void setCommand(Object command) {
        this.command = command;
    }

}
