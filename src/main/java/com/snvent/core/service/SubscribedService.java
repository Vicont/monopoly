package com.snvent.core.service;

import com.snvent.core.messageSystem.Address;
import com.snvent.core.messageSystem.MessageSystem;
import com.snvent.core.messageSystem.Subscriber;

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
     * Default service tick size
     */
    protected static final int TICK_SIZE = 10;

    /**
     * Service thread
     */
    protected Thread thread;

    @Override
    public void activate() {
        this.address = new Address();
        this.messageSystem.addSubscriber(this);

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void shutdown() {
        thread.interrupt();
    }

    @Override
    public void run() {
        while (!thread.isInterrupted()) {
            try {
                long startDate = System.currentTimeMillis();
                this.messageSystem.executeForSubscriber(this);
                this.doOperation();
                long endDate = System.currentTimeMillis();

                long deltaTime = endDate - startDate;
                double load = (deltaTime / TICK_SIZE);

                if (load < 1) {
                    Thread.sleep(TICK_SIZE - deltaTime);
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public abstract void doOperation();

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

    @Override
    public Address getAddress() {
        return this.address;
    }

}
