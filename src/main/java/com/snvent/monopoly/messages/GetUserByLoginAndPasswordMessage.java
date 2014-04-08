package com.snvent.monopoly.messages;

import com.snvent.core.messageSystem.Address;
import com.snvent.core.messageSystem.Message;
import com.snvent.core.messageSystem.MessageToService;
import com.snvent.monopoly.services.DatabaseService;
import com.snvent.monopoly.models.User;
import com.snvent.monopoly.models.UserDAO;

/**
 * GetUserByLoginAndPasswordMessage
 *
 * @author vicont
 */
public class GetUserByLoginAndPasswordMessage extends MessageToService<DatabaseService> {

    private String login;

    private String password;

    public GetUserByLoginAndPasswordMessage(Address from, Address to, String login, String password) {
        super(from, to);
        this.login = login;
        this.password = password;
        this.recipientClass = DatabaseService.class;
    }

    @Override
    protected void execute(DatabaseService service) {
        UserDAO dao = service.getDaoFactory().getDAO(UserDAO.class);
        User user = dao.getByLoginAndPassword(login, password);

        Message message = new SetUserByLoginMessage(getTo(), getFrom(), user);
        service.getMessageSystem().sendMessage(message);
    }

}
