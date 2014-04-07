package com.snvent.core.http.controller;

import com.snvent.core.service.FrontendService;
import org.springframework.beans.factory.annotation.Autowired;

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

    /**
     * Frontend service
     */
    @Autowired
    protected FrontendService frontendService;

    @Override
    public void setRequestCommand(Object command) {
        this.requestCommand = command;
    }

    @Override
    public Object getRequestCommand() {
        return this.requestCommand;
    }

}
