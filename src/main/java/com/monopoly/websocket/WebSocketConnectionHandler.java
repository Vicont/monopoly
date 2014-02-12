package com.monopoly.websocket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler for WebSocket connections
 *
 * @author vicont
 */
public class WebSocketConnectionHandler implements AuthorizationListener {

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(WebSocketConnectionHandler.class);

    @Override
    public boolean isAuthorized(HandshakeData data) {
        return true;
    }

    @OnConnect
    public void onConnect(SocketIOClient client) {
        log.info("New client connected!");
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        log.info("client disconnected!");
    }

    @OnMessage
    public void onMessageReceived(SocketIOClient client, String data, AckRequest ackRequest) {
        log.info("message received: " + data);
        client.sendMessage(data);
    }

}
