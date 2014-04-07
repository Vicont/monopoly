package com.snvent.core.message;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Message system
 *
 * @author vicont
 */
@Component
public class MessageSystem {

    /**
     * Mailboxes map
     */
    private Map<Address, ConcurrentLinkedQueue<Message>> mailboxes = new HashMap<>();

    /**
     * Address locator
     */
    private AddressLocator addressLocator = new AddressLocator();

    /**
     * Returns address locator
     *
     * @return Address locator
     */
    public AddressLocator getAddressLocator() {
        return this.addressLocator;
    }

    /**
     * Add new subscriber to message system
     *
     * @param subscriber Subscriber
     */
    public void addSubscriber(Subscriber subscriber) {
        ConcurrentLinkedQueue<Message> mailbox = new ConcurrentLinkedQueue<>();
        addressLocator.registerAddress(subscriber);
        mailboxes.put(subscriber.getAddress(), mailbox);
    }

    /**
     * Send message
     *
     * @param message Message
     */
    public void sendMessage(Message message) {
        Address recipient = message.getTo();
        Queue<Message> recipientMailbox = mailboxes.get(recipient);

        if (recipientMailbox == null) {
            throw new MessageSystemException("Mailbox for address <" + recipient + "> is not found");
        }

        recipientMailbox.add(message);
    }

    /**
     * Execute all new messages for specified subscriber (and remove them)
     *
     * @param subscriber Subscriber
     */
    public void executeForSubscriber(Subscriber subscriber) {
        Address address = subscriber.getAddress();
        Queue<Message> mailbox = mailboxes.get(address);

        if (mailbox == null) {
            throw new MessageSystemException("Mailbox for address <" + address + "> is not found");
        }

        while (!mailbox.isEmpty()) {
            Message message = mailbox.poll();
            message.execute(subscriber);
        }
    }

}
