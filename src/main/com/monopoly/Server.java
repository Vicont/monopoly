package com.monopoly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Game server
 *
 * @author vicont
 */
public class Server {

    private final static Logger log = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {
        log.info("Server is starting...");
        IApplication app = new Application();
        app.start();
        log.info("Server started");
    }

}
