package com.monopoly.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;

/**
 * Handler for accepted http connection
 *
 * @author vicont
 */
public class HttpConnectionHandler extends SimpleChannelInboundHandler<Object> {

    /**
     * HTTP exchange container
     */
    private final HttpExchange exchange = new HttpExchange();

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        exchange.setContext(ctx);

        if (msg instanceof HttpRequest) {
            exchange.handleHttpRequest((HttpRequest) msg);
        }

        if (msg instanceof HttpContent) {
            exchange.handleHttpContent((HttpContent) msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
