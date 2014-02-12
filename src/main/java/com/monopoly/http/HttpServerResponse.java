package com.monopoly.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * HTTP server response
 *
 * @author vicont
 */
public class HttpServerResponse {

    private FullHttpResponse res;

    private ChannelHandlerContext ctx;

    private ByteBuf output;

    private boolean isKeepAlive;

    private boolean hasContent = false;

    private Charset charset = CharsetUtil.UTF_8;

    public HttpServerResponse(ChannelHandlerContext ctx, HttpResponseStatus status, boolean isKeepAlive) {
        this.ctx = ctx;
        this.isKeepAlive = isKeepAlive;
        this.output = this.ctx.alloc().buffer(0);
        this.res = new DefaultFullHttpResponse(HTTP_1_1, status, this.output);
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setStatus(HttpResponseStatus status) {
        this.res.setStatus(status);
    }

    public HttpResponseStatus getStatus() {
        return this.res.getStatus();
    }

    public void write(String content) {
        this.hasContent = true;
        this.output.writeBytes(content.getBytes(this.charset));
    }

    public void setCookie(Cookie cookie) {
        this.res.headers().add(SET_COOKIE, ServerCookieEncoder.encode(cookie));
    }

    public void end() {
        if (this.hasContent) {
            this.res.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");

            if (this.isKeepAlive) {
                this.res.headers().set(CONTENT_LENGTH, this.res.content().readableBytes());
                this.res.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            }
        }

        ChannelFuture f = ctx.write(this.res);
        f.addListener(ChannelFutureListener.CLOSE);
    }

    public void end(String content) {
        this.write(content);
        this.end();
    }

}
