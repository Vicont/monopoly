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
     * Default server host
     */
    protected static final String DEFAULT_HOST = "localhost";

    /**
     * Default server port
     */
    protected static final int DEFAULT_PORT = 80;

    /**
     * Server host (use default if it isn't defined)
     */
    protected String host = HttpService.DEFAULT_HOST;

    /**
     * Server port (use default if it isn't defined)
     */
    protected int port = HttpService.DEFAULT_PORT;

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

    @Override
    public void activate() {
        byte maxThreads = 4;
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(maxThreads);

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
