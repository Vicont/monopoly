package com.monopoly.http.dispatcher;

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
    private Class<HttpDispatcherFactory> factoryClass;

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
    public void setDispatcherFactoryClass(Class<HttpDispatcherFactory> factoryClass) {
        this.factoryClass = factoryClass;
    }

    /**
     * Retrieve dispatcher factory class
     *
     * @return Factory class
     */
    public Class<HttpDispatcherFactory> getDispatcherFactoryClass() {
        return this.factoryClass;
    }

}
