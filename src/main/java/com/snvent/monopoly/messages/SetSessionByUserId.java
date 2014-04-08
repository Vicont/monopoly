package com.snvent.monopoly.messages;

import com.snvent.core.message.Address;
import com.snvent.core.message.MessageToService;
import com.snvent.monopoly.models.UserSession;
import com.snvent.monopoly.services.FrontendService;

/**
 * SetUserByLoginMessage
 *
 * @author vicont
 */
public class SetSessionByUserId extends MessageToService<FrontendService> {

    private UserSession session;

    public SetSessionByUserId(Address from, Address to, UserSession session) {
        super(from, to);
        this.session = session;
        this.recipientClass = FrontendService.class;
    }

    @Override
    protected void execute(FrontendService service) {
        service.setSessionByUserId(session.getUserId(), session);
    }

}
