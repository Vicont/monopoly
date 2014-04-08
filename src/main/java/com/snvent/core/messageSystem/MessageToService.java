package com.snvent.core.messageSystem;

import com.snvent.core.service.Service;

/**
 * Message to service
 *
 * @author vicont
 */
abstract public class MessageToService<T extends Service> extends Message {

    /**
     * Recipient class
     */
    protected Class<T> recipientClass;

    /**
     * Constructor
     *
     * @param from Sender address
     * @param to Recipient address
     */
    public MessageToService(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void execute(Subscriber subscriber) {
        if (this.recipientClass == null) {
            String message = String.format("Recipient class for message <%s> is not specified.", this.getClass().getSimpleName());
            throw new MessageSystemException(message);
        }

        if (!subscriber.getClass().isAssignableFrom(this.recipientClass)) {
            String message = String.format("Invalid subscriber for executing message <%s> for service. Expected: <%s>, found: <%s>.",
                    this.getClass().getSimpleName(),
                    this.recipientClass.getSimpleName(),
                    subscriber.getClass().getSimpleName());

            throw new MessageSystemException(message);
        }

        T service = this.recipientClass.cast(subscriber);
        this.execute(service);
    }

    /**
     * Execute messageSystem for service
     *
     * @param service Service
     */
    protected abstract void execute(T service);

}
