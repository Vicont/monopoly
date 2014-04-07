package com.snvent.core.service;

import com.snvent.core.message.Address;
import com.snvent.core.message.MessageSystem;
import com.snvent.core.message.Subscriber;

/**
 * Abstract subscribed service
 *
 * @author vicont
 */
abstract public class SubscribedService extends AbstractService implements Subscriber, Runnable {

    /**
     * Address in message system
     */
    protected Address address;

    /**
     * Message system
     */
    protected MessageSystem messageSystem;

    /**
     * Set message system
     *
     * @param ms Message system
     */
    public void setMessageSystem(MessageSystem ms) {
        this.messageSystem = ms;
    }

    /**
     * Retrieve message system
     *
     * @return Message system
     */
    public MessageSystem getMessageSystem() {
        return this.messageSystem;
    }

    /**
     * Initialize message system
     */
    protected void initMessageSystem() {
        this.address = new Address();
        this.messageSystem.addSubscriber(this);
    }

    @Override
    public Address getAddress() {
        return this.address;
    }

}
