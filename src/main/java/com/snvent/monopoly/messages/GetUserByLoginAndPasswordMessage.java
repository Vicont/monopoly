package com.snvent.monopoly.messages;

import com.snvent.core.message.Address;
import com.snvent.core.message.Message;
import com.snvent.core.message.MessageToService;
import com.snvent.core.service.DatabaseService;
import com.snvent.monopoly.models.DAOFactory;
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
        UserDAO dao = DAOFactory.getInstance().getUserDAO();
        User user = dao.getByLoginAndPassword(login, password);

        Message message = new SetUserByLoginMessage(getTo(), getFrom(), user);
        service.getMessageSystem().sendMessage(message);
    }

}
