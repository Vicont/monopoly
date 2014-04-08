package com.snvent.monopoly.commands.outgoing;

/**
 * Session created
 *
 * @author vicont
 */
public class SessionCreated {

    public String sessionId;

    public SessionCreated(String sid) {
        this.sessionId = sid;
    }

}
