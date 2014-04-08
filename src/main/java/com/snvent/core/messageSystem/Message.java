package com.snvent.core.messageSystem;

/**
 * Abstract messageSystem
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
     * Retrieve messageSystem sender address
     *
     * @return Sender address
     */
    public Address getFrom() {
        return this.from;
    }

    /**
     * Retrieve messageSystem recipient address
     *
     * @return Recipient address
     */
    public Address getTo() {
        return this.to;
    }

    /**
     * Execute messageSystem for subscriber
     *
     * @param subscriber Subscriber
     */
    abstract public void execute(Subscriber subscriber);

}
