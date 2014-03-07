package com.snvent.core.service;

import com.snvent.core.http.HttpConnectionInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTTP-service
 *
 * @author vicont
 */
public class HttpService extends AbstractService {

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
    protected int port = 80;

    /**
     * Netty bootstrap
     */
    protected ServerBootstrap bootstrap;

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(HttpService.class);

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
        EventLoopGroup bossGroup = new NioEventLoopGroup(maxBossThreads);
        EventLoopGroup workerGroup = new NioEventLoopGroup(maxWorkerThreads);

        try {
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new HttpConnectionInitializer())
                .childOption(ChannelOption.SO_KEEPALIVE, true);

            bootstrap.bind(host, port).sync();
            log.info("HTTP server started at " + host + ":" + port);
        } catch (InterruptedException e) {
            log.error("An interrupted exception thrown: ", e);
        }
    }

    @Override
    public void shutdown() {
        bootstrap.childGroup().shutdownGracefully();
        bootstrap.group().shutdownGracefully();
    }

}
