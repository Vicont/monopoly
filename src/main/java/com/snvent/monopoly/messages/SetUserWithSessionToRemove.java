package com.snvent.monopoly.messages;

import com.snvent.core.messageSystem.Address;
import com.snvent.core.messageSystem.MessageToService;
import com.snvent.monopoly.services.FrontendService;

/**
 * SetUserByLoginMessage
 *
 * @author vicont
 */
public class SetUserWithSessionToRemove extends MessageToService<FrontendService> {

    private Integer userId;

    public SetUserWithSessionToRemove(Address from, Address to, Integer userId) {
        super(from, to);
        this.userId = userId;
        this.recipientClass = FrontendService.class;
    }

    @Override
    protected void execute(FrontendService service) {
        service.setUserWithSessionToRemove(this.userId);
    }

}
