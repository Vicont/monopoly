package com.snvent.monopoly.messages;

import com.snvent.core.message.Address;
import com.snvent.core.message.Message;
import com.snvent.core.message.MessageToService;
import com.snvent.monopoly.models.UserSession;
import com.snvent.monopoly.models.UserSessionDAO;
import com.snvent.monopoly.services.DatabaseService;

/**
 * CreateSessionMessage
 *
 * @author vicont
 */
public class CreateSessionMessage extends MessageToService<DatabaseService> {

    private Integer userId;

    public CreateSessionMessage(Address from, Address to, Integer userId) {
        super(from, to);
        this.userId = userId;
        this.recipientClass = DatabaseService.class;
    }

    @Override
    protected void execute(DatabaseService service) {
        UserSessionDAO dao = service.getDaoFactory().getDAO(UserSessionDAO.class);
        dao.removeByUserId(userId);

        UserSession userSession = new UserSession();
        userSession.setUserId(userId);
        userSession.setStartDate((int) (System.currentTimeMillis() / 1000));
        dao.add(userSession);

        Message message = new SetSessionByUserId(getTo(), getFrom(), userSession);
        service.getMessageSystem().sendMessage(message);
    }

}
