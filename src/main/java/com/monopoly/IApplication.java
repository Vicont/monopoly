package com.monopoly;

/**
 * Game application interface
 *
 * @author vicont
 */
public interface IApplication {

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
