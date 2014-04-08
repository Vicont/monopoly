package com.snvent.monopoly.services;

import com.snvent.core.message.Address;
import com.snvent.core.message.Message;
import com.snvent.core.message.MessageSystem;
import com.snvent.core.service.SubscribedService;
import com.snvent.monopoly.messages.CreateSessionMessage;
import com.snvent.monopoly.messages.GetUserByLoginAndPasswordMessage;
import com.snvent.monopoly.models.User;
import com.snvent.monopoly.models.UserSession;
import com.snvent.monopoly.services.frontend.UserStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Frontend service
 *
 * @author vicont
 */
public class FrontendService extends SubscribedService {

    /**
     * Storage for logged users
     */
    private UserStorage userStorage = new UserStorage();

    private Map<String, User> mapLoginToUser = new ConcurrentHashMap<>();

    private Map<Integer, UserSession> mapUserIdToSession = new ConcurrentHashMap<>();

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(FrontendService.class);

    /**
     * Retrieve storage for logged users
     *
     * @return User storage
     */
    public UserStorage getUserStorage() {
        return this.userStorage;
    }

    public User getUserByLoginAndPassword(String login, String password) {
        this.mapLoginToUser.remove(login);

        MessageSystem ms = this.getMessageSystem();
        Address recipient = ms.getAddressLocator().getAddress(DatabaseService.class);
        Message message = new GetUserByLoginAndPasswordMessage(this.getAddress(), recipient, login, password);
        ms.sendMessage(message);

        while (!mapLoginToUser.containsKey(login)) {
            try {
                Thread.sleep(TICK_SIZE / 10);
            } catch (InterruptedException e) {
                break;
            }
        }

        return mapLoginToUser.get(login);
    }

    public void setUserByLogin(String login, User user) {
        this.mapLoginToUser.put(login, user);
    }

    public UserSession createUserSession(User user) {
        this.mapUserIdToSession.remove(user.getId());

        MessageSystem ms = this.getMessageSystem();
        Address recipient = ms.getAddressLocator().getAddress(DatabaseService.class);
        Message message = new CreateSessionMessage(this.getAddress(), recipient, user.getId());
        ms.sendMessage(message);

        while (!mapUserIdToSession.containsKey(user.getId())) {
            try {
                Thread.sleep(TICK_SIZE / 10);
            } catch (InterruptedException e) {
                break;
            }
        }

        return mapUserIdToSession.get(user.getId());
    }

    public void setSessionByUserId(Integer userId, UserSession userSession) {
        this.mapUserIdToSession.put(userId, userSession);
    }

    @Override
    public void doOperation() {

    }

}
