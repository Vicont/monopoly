package com.snvent.core.service;

import com.snvent.core.Application;

/**
 * Abstract application service
 *
 * @author vicont
 */
abstract public class AbstractService implements Service {

    /**
     * Game application
     */
    protected Application application;

    @Override
    public void setApplication(Application application) {
        this.application = application;
    }

    @Override
    abstract public void activate();

    @Override
    abstract public void shutdown();

}
