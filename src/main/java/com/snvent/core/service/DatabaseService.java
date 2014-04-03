package com.snvent.core.service;

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
