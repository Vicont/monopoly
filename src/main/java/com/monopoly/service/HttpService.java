package com.monopoly.service;

import com.monopoly.http.HttpConnectionInitializer;
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
     * Set maximum number of worker threads
     *
     * @param maxWorkerThreads Max threads
     */
    public void setMaxWorkerThreads(byte maxWorkerThreads) {
        this.maxWorkerThreads = maxWorkerThreads;
    }

    @Override
    public void activate() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(MAX_BOSS_THREADS);
        EventLoopGroup workerGroup = new NioEventLoopGroup(maxWorkerThreads);
        log.info("threads: " + maxWorkerThreads);

        try {
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new HttpConnectionInitializer())
                .childOption(ChannelOption.SO_KEEPALIVE, true);

            bootstrap.bind(host, port).sync();
            log.info("HTTP server started on " + host + ":" + port);
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
