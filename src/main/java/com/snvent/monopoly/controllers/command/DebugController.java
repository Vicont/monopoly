package com.snvent.monopoly.controllers.command;

import com.snvent.core.http.annotation.After;
import com.snvent.core.http.annotation.Before;
import com.snvent.core.http.annotation.CommandAction;
import com.snvent.core.http.annotation.controller.CommandController;
import com.snvent.core.http.controller.AbstractHttpCommandController;
import com.snvent.core.message.Address;
import com.snvent.core.message.Message;
import com.snvent.core.message.MessageSystem;
import com.snvent.core.service.DatabaseService;
import com.snvent.monopoly.commands.incoming.Debug;
import com.snvent.monopoly.messages.GetUserByLoginAndPasswordMessage;

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
        MessageSystem ms = this.frontendService.getMessageSystem();
        Address recipient = ms.getAddressLocator().getAddress(DatabaseService.class);
        Message message = new GetUserByLoginAndPasswordMessage(this.frontendService.getAddress(), recipient, "user1@example.com", "123");
        ms.sendMessage(message);

        Debug requestCommand = (Debug) this.getRequestCommand();
        System.out.println(requestCommand.toString());
        return new DebugController();
    }

    @After
    public void sayGoodBye() {
        //throw new RuntimeException("Good bye");
    }

}
