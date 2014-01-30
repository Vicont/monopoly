package com.monopoly.service;

import com.monopoly.IApplication;

/**
 * Abstract application service
 *
 * @author vicont
 */
abstract public class AbstractService implements IService {

    protected IApplication application;

    @Override
    public void setApplication(IApplication application) {
        this.application = application;
    }

    @Override
    abstract public void activate();

    @Override
    abstract public void shutdown();

}
