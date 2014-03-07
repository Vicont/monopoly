package com.snvent.core.service;

import com.snvent.core.Application;

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
    void setApplication(Application application);

    /**
     * Activate service and initialize resources
     */
    void activate();

    /**
     * Shutdown service and clear all used resources
     */
    void shutdown();

}
