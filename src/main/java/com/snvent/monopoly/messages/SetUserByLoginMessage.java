package com.snvent.monopoly.messages;

import com.snvent.core.message.Address;
import com.snvent.core.message.MessageToService;
import com.snvent.core.service.FrontendService;
import com.snvent.monopoly.models.User;

/**
 * SetUserByLoginMessage
 *
 * @author vicont
 */
public class SetUserByLoginMessage extends MessageToService<FrontendService> {

    private User user;

    public SetUserByLoginMessage(Address from, Address to, User user) {
        super(from, to);
        this.user = user;
        this.recipientClass = FrontendService.class;
    }

    @Override
    protected void execute(FrontendService service) {
        System.out.println("User found: " + user.getNick());
    }

}
