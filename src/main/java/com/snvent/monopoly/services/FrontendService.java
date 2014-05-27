package com.snvent.monopoly.services;

import com.snvent.core.model.DAOFactory;
import com.snvent.core.service.SubscribedService;
import com.snvent.monopoly.models.User;
import com.snvent.monopoly.models.UserDAO;
import com.snvent.monopoly.models.UserSession;
import com.snvent.monopoly.models.UserSessionDAO;
import com.snvent.monopoly.services.frontend.UserStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

    /**
     * DAO factory
     */
    @Autowired
    private DAOFactory daoFactory;

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

    /**
     * Retrieve user by login and password
     *
     * @param login User login
     * @param password User password
     * @return User
     */
    public User getUserByLoginAndPassword(String login, String password) {
        UserDAO dao = this.daoFactory.getDAO(UserDAO.class);
        return dao.getByLoginAndPassword(login, password);
    }

    /**
     * Remove session for user
     *
     * @param user User
     */
    public void removeUserSession(User user) {
        UserSessionDAO dao = this.daoFactory.getDAO(UserSessionDAO.class);
        dao.removeByUserId(user.getId());
    }

    /**
     * Create new session for user
     *
     * @param user User
     * @return UserSession
     */
    public UserSession createUserSession(User user) {
        UserSessionDAO dao = this.daoFactory.getDAO(UserSessionDAO.class);

        UserSession userSession = new UserSession();
        userSession.setUserId(user.getId());
        userSession.setStartDate((int) (System.currentTimeMillis() / 1000));
        dao.add(userSession);

        return userSession;
    }

    @Override
    public void doOperation() {

    }

}
