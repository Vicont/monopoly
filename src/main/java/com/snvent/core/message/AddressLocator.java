package com.snvent.core.message;

import java.util.HashMap;
import java.util.Map;

/**
 * Address locator for message system
 *
 * @author vicont
 */
public class AddressLocator {

    /**
     * Addresses map
     */
    private Map<Class<?>, Address> addresses = new HashMap<>();

    /**
     * Register new subscriber with its address
     *
     * @param subscriber Subscriber
     */
    public void registerAddress(Subscriber subscriber) {
        addresses.put(subscriber.getClass(), subscriber.getAddress());
    }

    /**
     * Retrieve address by subscriber's class
     *
     * @param subscriberClass Subscriber's class
     * @return Address
     */
    public Address getAddress(Class<?> subscriberClass) {
        return addresses.get(subscriberClass);
    }

}
