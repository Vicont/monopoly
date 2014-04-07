package com.snvent.core.http.controller;

/**
 * Abstract HTTP command controller
 *
 * @author vicont
 */
abstract public class AbstractHttpCommandController extends AbstractHttpController implements HttpCommandController {

    /**
     * Request command
     */
    protected Object requestCommand;

    @Override
    public void setRequestCommand(Object command) {
        this.requestCommand = command;
    }

    @Override
    public Object getRequestCommand() {
        return this.requestCommand;
    }

}
