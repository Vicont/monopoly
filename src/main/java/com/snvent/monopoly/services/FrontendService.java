package com.snvent.monopoly.services;

import com.snvent.core.service.SubscribedService;
import com.snvent.monopoly.services.frontend.UserSessionStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Frontend service
 *
 * @author vicont
 */
public class FrontendService extends SubscribedService {

    /**
     * Storage for logged users
     */
    private UserSessionStorage userSessionStorage = new UserSessionStorage();

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(FrontendService.class);

    @Override
    public void doOperation() {

    }

}
