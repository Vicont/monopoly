package com.monopoly.service;

import com.monopoly.Application;

/**
 * Interface for application service
 *
 * @author vicont
 */
public interface Service {

    /**
     * Set current application
     *
     * @param application Game application
     */
    public void setApplication(Application application);

    /**
     * Activate service and initialize resources
     */
    public void activate();

    /**
     * Shutdown service and clear all used resources
     */
    public void shutdown();

}
