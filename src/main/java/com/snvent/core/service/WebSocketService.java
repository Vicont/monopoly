package com.snvent.core.service;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.snvent.core.websocket.WebSocketConnectionHandler;
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
    protected int maxBossThreads = 1;

    /**
     * Maximum number of worker threads
     */
    protected int maxWorkerThreads = Runtime.getRuntime().availableProcessors();

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
     * Set maximum number of boss threads
     *
     * @param maxBossThreads Max threads
     */
    public void setMaxBossThreads(int maxBossThreads) {
        this.maxBossThreads = maxBossThreads;
    }

    /**
     * Set maximum number of worker threads
     *
     * @param maxWorkerThreads Max threads
     */
    public void setMaxWorkerThreads(int maxWorkerThreads) {
        this.maxWorkerThreads = maxWorkerThreads;
    }

    @Override
    public void activate() {
        WebSocketConnectionHandler handler = new WebSocketConnectionHandler();

        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);
        config.setBossThreads(maxBossThreads);
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
