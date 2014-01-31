package com.monopoly;

/**
 * Application interface
 *
 * @author vicont
 */
public interface Application {

    /**
     * Start application
     */
    public void start();

    /**
     * Stop application
     */
    public void stop();

    /**
     * Returns server settings
     *
     * @return Settings object
     */
    public ServerSettings getSettings();

}
