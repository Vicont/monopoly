package com.monopoly.service;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.monopoly.websocket.WebSocketConnectionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebSocket service
 *
 * @author vicont
 */
public class WebSocketService extends AbstractService {

    /**
     * Maximum number of boss threads
     */
    protected static final byte MAX_BOSS_THREADS = 1;

    /**
     * Maximum number of worker threads
     */
    protected byte maxWorkerThreads = 1;

    /**
     * Server host
     */
    protected String host = "localhost";

    /**
     * Server port
     */
    protected int port = 81;

    /**
     * Socket.io server
     */
    protected SocketIOServer server;

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);

    /**
     * Set HTTP-server host
     *
     * @param host Hostname
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Set HTTP-server port
     *
     * @param port Port number
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Set maximum number of worker threads
     *
     * @param maxWorkerThreads Max threads
     */
    public void setMaxWorkerThreads(byte maxWorkerThreads) {
        this.maxWorkerThreads = maxWorkerThreads;
    }

    @Override
    public void activate() {
        WebSocketConnectionHandler handler = new WebSocketConnectionHandler();

        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);
        config.setBossThreads(MAX_BOSS_THREADS);
        config.setWorkerThreads(maxWorkerThreads);
        config.setAuthorizationListener(handler);

        server = new SocketIOServer(config);
        server.addListeners(handler);
        server.start();
    }

    @Override
    public void shutdown() {
        server.stop();
    }

}
