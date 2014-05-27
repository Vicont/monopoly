package com.snvent.monopoly.controllers.request;

import com.snvent.core.http.controller.AbstractHttpController;

/**
 * Debug controller
 *
 * @author vicont
 */
public class DebugController extends AbstractHttpController {

    public void index() {
        response.write("You've requested DEBUG");
        response.end();
    }

}
