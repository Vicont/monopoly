package com.snvent.core;

/**
 * Application exception
 *
 * @author vicont
 */
public class ApplicationException extends RuntimeException {

    /**
     * Constructor
     *
     * @param message Exception message
     */
    public ApplicationException(String message) {
        super(message);
    }

}
