package com.snvent.core.http.route;

import com.snvent.core.http.dispatcher.factory.HttpDispatcherFactory;

/**
 * Route
 *
 * @author vicont
 */
public class Route {

    /**
     * URI pattern
     */
    private String pattern;

    /**
     * Dispatcher factory class which should handles request
     */
    private Class<? extends HttpDispatcherFactory> factoryClass;

    /**
     * Set pattern
     *
     * @param pattern URI pattern
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Retrieve pattern
     *
     * @return Pattern
     */
    public String getPattern() {
        return this.pattern;
    }

    /**
     * Set dispatcher factory class
     *
     * @param factoryClass Factory class
     */
    public void setDispatcherFactoryClass(Class<? extends HttpDispatcherFactory> factoryClass) {
        this.factoryClass = factoryClass;
    }

    /**
     * Retrieve dispatcher factory class
     *
     * @return Factory class
     */
    public Class<? extends HttpDispatcherFactory> getDispatcherFactoryClass() {
        return this.factoryClass;
    }

}
