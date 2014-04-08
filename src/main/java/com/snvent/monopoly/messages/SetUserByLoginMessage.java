package com.snvent.monopoly.messages;

import com.snvent.core.messageSystem.Address;
import com.snvent.core.messageSystem.MessageToService;
import com.snvent.monopoly.services.FrontendService;
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
        service.setUserByLogin(user.getLogin(), user);
    }

}
