package com.snvent.monopoly.controllers.command;

import com.snvent.core.http.annotation.After;
import com.snvent.core.http.annotation.Before;
import com.snvent.core.http.annotation.CommandAction;
import com.snvent.core.http.annotation.controller.CommandController;
import com.snvent.monopoly.commands.incoming.Debug;
import com.snvent.core.http.controller.AbstractHttpCommandController;

/**
 * Debug controller
 *
 * @author vicont
 */
@CommandController
public class DebugController extends AbstractHttpCommandController {

    @Before
    public void sayHello() {
        //response.write("Hello\n");
    }

    @CommandAction("Debug")
    public Object index() {
        Debug requestCommand = (Debug) this.getRequestCommand();
        System.out.println(requestCommand.toString());
        return new DebugController();
    }

    @After
    public void sayGoodBye() {
        //throw new RuntimeException("Good bye");
    }

}
