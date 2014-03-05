package com.monopoly.controllers.request;

import com.monopoly.annotations.CommandAction;
import com.monopoly.annotations.controller.CommandController;
import com.monopoly.http.controller.AbstractHttpController;

/**
 * Debug controller
 *
 * @author vicont
 */
@CommandController
public class DebugController extends AbstractHttpController {

    @CommandAction("debug1")
    public void index() {
        response.write("You've requested DEBUG1");
        response.end();
    }

}
