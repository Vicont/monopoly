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
    void start();

    /**
     * Stop application
     */
    void stop();

    /**
     * Returns server settings
     *
     * @return Settings object
     */
    ServerSettings getSettings();

}
