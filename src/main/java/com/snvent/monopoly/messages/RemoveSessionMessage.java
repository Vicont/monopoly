package com.snvent.monopoly.messages;

import com.snvent.core.messageSystem.Address;
import com.snvent.core.messageSystem.Message;
import com.snvent.core.messageSystem.MessageToService;
import com.snvent.monopoly.models.UserSessionDAO;
import com.snvent.monopoly.services.DatabaseService;

/**
 * RemoveSessionMessage
 *
 * @author vicont
 */
public class RemoveSessionMessage extends MessageToService<DatabaseService> {

    private Integer userId;

    public RemoveSessionMessage(Address from, Address to, Integer userId) {
        super(from, to);
        this.userId = userId;
        this.recipientClass = DatabaseService.class;
    }

    @Override
    protected void execute(DatabaseService service) {
        UserSessionDAO dao = service.getDaoFactory().getDAO(UserSessionDAO.class);
        dao.removeByUserId(userId);

        Message message = new SetUserWithSessionToRemove(getTo(), getFrom(), userId);
        service.getMessageSystem().sendMessage(message);
    }

}
