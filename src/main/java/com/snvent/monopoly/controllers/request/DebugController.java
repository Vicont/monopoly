package com.snvent.monopoly.controllers.request;

import com.snvent.core.http.annotation.controller.CommandController;
import com.snvent.core.http.controller.AbstractHttpController;

/**
 * Debug controller
 *
 * @author vicont
 */
@CommandController
public class DebugController extends AbstractHttpController {

    //@CommandAction("debug1")
    public void index() {
        response.write("You've requested DEBUG1");
        response.end();
    }

}
