package com.snvent.core.messageSystem;

/**
 * Abstract message
 *
 * @author vicont
 */
abstract public class Message {

    /**
     * Sender address
     */
    protected Address from;

    /**
     * Recipient address
     */
    protected Address to;

    /**
     * Constructor
     *
     * @param from Sender address
     * @param to Recipient address
     */
    public Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Retrieve message sender address
     *
     * @return Sender address
     */
    public Address getFrom() {
        return this.from;
    }

    /**
     * Retrieve message recipient address
     *
     * @return Recipient address
     */
    public Address getTo() {
        return this.to;
    }

    /**
     * Execute message for subscriber
     *
     * @param subscriber Subscriber
     */
    abstract public void execute(Subscriber subscriber);

}
