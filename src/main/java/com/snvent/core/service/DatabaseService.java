package com.snvent.core.service;

import com.snvent.core.model.DAOFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Database service
 *
 * @author vicont
 */
public class DatabaseService extends SubscribedService {

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(DatabaseService.class);

    /**
     * DAO factory
     */
    @Autowired
    private DAOFactory daoFactory;

    /**
     * Retrieve DAO factory
     *
     * @return DAO factory
     */
    public DAOFactory getDaoFactory() {
        return this.daoFactory;
    }

    @Override
    public void doOperation() {

    }

}
