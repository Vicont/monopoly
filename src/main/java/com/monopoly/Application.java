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

    public void start() {
        log.info("Application is starting...");
    }

}
