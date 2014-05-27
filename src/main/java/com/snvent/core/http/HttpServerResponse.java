package com.snvent.core.http;

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

    /**
     * Decorated Netty response object
     */
    private FullHttpResponse res;

    /**
     * Netty context linking channel with handler
     */
    private ChannelHandlerContext ctx;

    /**
     * Output buffer
     */
    private ByteBuf output;

    /**
     * True if the connection can remain open and thus 'kept alive'
     */
    private boolean isKeepAlive;

    /**
     * True if response contains content for sending
     */
    private boolean hasContent = false;

    /**
     * True if response has already sent
     */
    private boolean isSent = false;

    /**
     * Response charset
     */
    private Charset charset = CharsetUtil.UTF_8;

    /**
     * Constructor
     *
     * @param ctx Netty channel handler context
     * @param status Response status
     * @param isKeepAlive Is connection keep-alive
     */
    public HttpServerResponse(ChannelHandlerContext ctx, HttpResponseStatus status, boolean isKeepAlive) {
        this.ctx = ctx;
        this.isKeepAlive = isKeepAlive;
        this.output = this.ctx.alloc().buffer(0);
        this.res = new DefaultFullHttpResponse(HTTP_1_1, status, this.output);
    }

    /**
     * Set response charset
     *
     * @param charset Charset
     */
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    /**
     * Retrieve response charset
     *
     * @return Charset
     */
    public Charset getCharset() {
        return this.charset;
    }

    /**
     * Set response HTTP status
     *
     * @param status HTTP status
     */
    public void setStatus(HttpResponseStatus status) {
        this.res.setStatus(status);
    }

    /**
     * Retrieve response HTTP status
     *
     * @return HTTP status
     */
    public HttpResponseStatus getStatus() {
        return this.res.getStatus();
    }

    /**
     * True if response has already sent
     *
     * @return Is sent
     */
    public boolean isSent() {
        return this.isSent;
    }

    /**
     * Write content
     *
     * @param content Content
     */
    public void write(String content) {
        if (this.isSent) {
            throw new HttpServerException("HTTP response is already sent");
        }

        this.hasContent = true;
        this.output.writeBytes(content.getBytes(this.charset));
    }

    /**
     * Set cookie object
     *
     * @param cookie Cookie object
     */
    public void setCookie(Cookie cookie) {
        this.res.headers().add(SET_COOKIE, ServerCookieEncoder.encode(cookie));
    }

    /**
     * Set response header
     *
     * @param name Header name
     * @param value Header value
     */
    public void setHeader(String name, Object value) {
        this.res.headers().set(name, value);
    }

    /**
     * Stop writing response and send it to client
     */
    public void end() {
        if (this.isSent) {
            throw new HttpServerException("HTTP response is already sent");
        }

        if (this.hasContent) {
            this.res.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");

            if (this.isKeepAlive) {
                this.res.headers().set(CONTENT_LENGTH, this.res.content().readableBytes());
                this.res.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            }
        }

        ChannelFuture f = ctx.write(this.res);
        this.isSent = true;

        if (!this.isKeepAlive) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * Write last portion of content and send response
     *
     * @param content Content
     */
    public void end(String content) {
        this.write(content);
        this.end();
    }

}
