package com.monopoly.service;

import com.monopoly.IApplication;

/**
 * Interface for application service
 *
 * @author vicont
 */
public interface IService {

    /**
     * Set current application
     *
     * @param application Game application
     */
    public void setApplication(IApplication application);

    /**
     * Activate service and initialize resources
     */
    public void activate();

    /**
     * Shutdown service and clear all used resources
     */
    public void shutdown();

}
