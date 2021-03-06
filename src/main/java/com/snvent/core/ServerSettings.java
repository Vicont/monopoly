package com.snvent.core;

import com.snvent.core.config.BaseConfig;
import org.aeonbits.owner.ConfigFactory;

/**
 * Server settings instance
 *
 * @author vicont
 */
public class ServerSettings {

    /**
     * Base server settings
     */
    public BaseConfig base;

    /**
     * Constructor
     */
    public ServerSettings() {
        base = ConfigFactory.create(BaseConfig.class);
    }

}
