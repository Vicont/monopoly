package com.monopoly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Game application
 *
 * @author vicont
 */
public class Application implements IApplication {

    private final static Logger log = LoggerFactory.getLogger(Application.class);

    private Application() {
    }

    public static void main(String[] args) {
        log.info("Application is starting...");
        IApplication app = new Application();
        app.start();
        log.info("Application started");
    }

    @Override
    public void start() {

    }

}
