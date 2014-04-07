package com.snvent.monopoly.controllers;

import com.snvent.core.http.controller.AbstractHttpCommandController;
import com.snvent.monopoly.services.FrontendService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract HTTP command controller
 *
 * @author vicont
 */
abstract public class MonopolyHttpCommandController extends AbstractHttpCommandController {

    /**
     * Frontend service
     */
    @Autowired
    protected FrontendService frontendService;

}
