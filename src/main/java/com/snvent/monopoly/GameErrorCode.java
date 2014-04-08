package com.snvent.monopoly;

import com.snvent.core.error.ErrorCode;

/**
 * Error codes
 *
 * @author vicont
 */
public enum GameErrorCode implements ErrorCode {

    INVALID_LOGIN_PARAMETERS    (100),
    LOGIN_FAILED                (101);

    /**
     * Error code
     */
    private int code;

    /**
     * Constructor
     *
     * @param code Error code
     */
    GameErrorCode(int code) {
        this.code = code;
    }

    /**
     * Retrieve error code
     *
     * @return Error code
     */
    @Override
    public int getCode() {
        return this.code;
    }

}
