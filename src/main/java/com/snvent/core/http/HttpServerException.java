package com.snvent.core.http;

/**
 * HTTP-server exception
 *
 * @author vicont
 */
public class HttpServerException extends RuntimeException {

    /**
     * Constructor
     *
     * @param message Exception message
     */
    public HttpServerException(String message) {
        super(message);
    }

}
