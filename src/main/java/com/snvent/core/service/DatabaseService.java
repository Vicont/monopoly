package com.snvent.core.service;

import com.snvent.monopoly.models.DAOFactory;
import com.snvent.monopoly.models.User;
import com.snvent.monopoly.models.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Database service
 *
 * @author vicont
 */
public class DatabaseService extends SubscribedService {

    /**
     * Default service tick size
     */
    private static final int TICK_SIZE = 10;

    /**
     * Service thread
     */
    private Thread thread;

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(DatabaseService.class);

    @Override
    public void activate() {
        this.initMessageSystem();
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void shutdown() {
        thread.interrupt();
    }

    @Override
    public void run() {
        UserDAO dao = DAOFactory.getInstance().getUserDAO();
        User user = dao.getById(2);
        log.info("User loaded: " + user.getNick());

        while (!thread.isInterrupted()) {
            try {
                this.messageSystem.executeForSubscriber(this);
                Thread.sleep(TICK_SIZE);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}
