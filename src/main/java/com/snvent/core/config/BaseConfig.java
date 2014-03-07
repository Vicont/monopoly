package com.snvent.core.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

/**
 * Base server config
 *
 * @author vicont
 */
@Sources({"classpath:config/base.properties"})
public interface BaseConfig extends Config {

    String environment();

}
