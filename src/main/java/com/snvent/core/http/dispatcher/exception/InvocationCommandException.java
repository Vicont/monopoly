package com.snvent.core.http.dispatcher.exception;

import com.snvent.core.error.ErrorCode;

/**
 * InvocationCommandException
 *
 * @author vicont
 */
public class InvocationCommandException extends RuntimeException {

    /**
     * Error code
     */
    private ErrorCode code;

    /**
     * Constructor
     *
     * @param message Error message
     * @param code Error code
     */
    public InvocationCommandException(String message, ErrorCode code) {
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
        return this.code.getCode();
    }

}
