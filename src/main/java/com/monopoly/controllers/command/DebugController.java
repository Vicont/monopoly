package com.monopoly.controllers.command;

import com.monopoly.annotations.After;
import com.monopoly.annotations.Before;
import com.monopoly.annotations.CommandAction;
import com.monopoly.annotations.controller.CommandController;
import com.monopoly.commands.incoming.Debug;
import com.monopoly.http.controller.AbstractHttpCommandController;

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
