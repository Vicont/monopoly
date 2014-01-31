package com.monopoly.config;

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

    @Key("cpu.cores")
    Byte cpuCores();

}
