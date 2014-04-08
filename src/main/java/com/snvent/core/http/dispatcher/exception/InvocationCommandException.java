package com.snvent.core.http.dispatcher.exception;

/**
 * InvocationCommandException
 *
 * @author vicont
 */
public class InvocationCommandException extends RuntimeException {

    /**
     * Error code
     */
    private int code;

    /**
     * Constructor
     *
     * @param message Error message
     * @param code Error code
     */
    public InvocationCommandException(String message, int code) {
        super(message);
        this.code = code;
    }

    /**
     * Constructor
     *
     * @param message Error message
     */
    public InvocationCommandException(String message) {
        super(message);
    }

    /**
     * Retrieve error code
     *
     * @return Error code
     */
    public int getCode() {
        return this.code;
    }

}
