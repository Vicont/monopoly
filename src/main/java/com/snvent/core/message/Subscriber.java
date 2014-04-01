package com.snvent.core.message;

/**
 * Subscriber in message system
 *
 * @author vicont
 */
public interface Subscriber {

    /**
     * Retrieve subscriber's address in message system
     *
     * @return Address
     */
    Address getAddress();

}
