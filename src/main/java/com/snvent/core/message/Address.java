package com.snvent.core.message;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Address in message system
 *
 * @author vicont
 */
public class Address {

    /**
     * Code generator
     */
    private static AtomicInteger generator = new AtomicInteger();

    /**
     * Address code
     */
    private int code;

    /**
     * Constructor
     */
    public Address() {
        this.code = generator.incrementAndGet();
    }

    /**
     * Returns hash code
     *
     * @return Hash code
     */
    public int hashCode() {
        return this.code;
    }

}
