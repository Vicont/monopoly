package com.monopoly.controllers.command;

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

    @CommandAction("debug")
    public void index() {
        response.write("You've requested URI: " + request.getUri() + "\n");
        response.end();
    }

}
