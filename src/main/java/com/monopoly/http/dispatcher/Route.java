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
     * Dispatcher class which should handles request
     */
    private Class dispatcher;

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
     * Set dispatcher class
     *
     * @param dispatcher Dispatcher class
     */
    public void setDispatcher(Class dispatcher) {
        this.dispatcher = dispatcher;
    }

    /**
     * Retrieve dispatcher class
     *
     * @return Dispatcher class
     */
    public Class getDispatcher() {
        return this.dispatcher;
    }

}
