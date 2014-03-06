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
        response.write("Hello\n");
    }

    @CommandAction("Debug")
    public void index() {
        Debug command = (Debug) this.command;
        System.out.println(command.toString());
        response.write("You've requested URI: " + request.getUri() + "\n");
        response.end();
    }

    @After
    public void sayGoodBye() {
        throw new RuntimeException("zzz");
        //System.out.println("Good bye");
    }

}
