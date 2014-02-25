package com.monopoly.http;

import com.monopoly.ApplicationContextManager;
import com.monopoly.http.dispatcher.HttpDispatcher;
import com.monopoly.http.dispatcher.HttpDispatcherFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static io.netty.handler.codec.http.HttpResponseStatus.*;

/**
 * This class encapsulates a HTTP request received and a response to be generated in one exchange
 *
 * @author vicont
 */
public class HttpExchange {

    /**
     * Netty channel context
     */
    private ChannelHandlerContext context;

    /**
     * HTTP request
     */
    private HttpServerRequest request;

    /**
     * HTTP response
     */
    private HttpServerResponse response;

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(HttpExchange.class);

    /**
     * Set netty channel context
     *
     * @param ctx Context object
     */
    public void setContext(ChannelHandlerContext ctx) {
        this.context = ctx;
    }

    /**
     * Handle HTTP request message
     *
     * @param req Request message
     */
    public void handleHttpRequest(HttpRequest req) {
        this.checkDecoderResult(req);
        this.request = new HttpServerRequest(req);

        if (is100ContinueExpected(req)) {
            response = new HttpServerResponse(this.context, CONTINUE, false);
            response.end();
        }
    }

    /**
     * Handle HTTP content message
     *
     * @param httpContent Content message
     */
    public void handleHttpContent(HttpContent httpContent) {
        this.checkDecoderResult(httpContent);

        ByteBuf content = httpContent.content();

        if (content.isReadable()) {
            String body = content.toString(CharsetUtil.UTF_8);
            this.request.setBody(body);
        }

        if (httpContent instanceof LastHttpContent) {
            boolean isGoodRequest = httpContent.getDecoderResult().isSuccess();
            HttpResponseStatus status = (isGoodRequest) ? OK : BAD_REQUEST;

            response = new HttpServerResponse(this.context, status, this.request.isKeepAlive());

            if (isGoodRequest) {
                try {
                    HttpDispatcherFactory factory = ApplicationContextManager.getContext().getBean("httpDispatcherFactory", HttpDispatcherFactory.class);
                    HttpDispatcher dispatcher = factory.getDispatcher(this.request);
                    dispatcher.process(this.request, this.response);
                } catch (Exception e) {
                    response.setStatus(INTERNAL_SERVER_ERROR);
                    response.write(e.toString() + "\n");
                }

                for (Map.Entry<String, Cookie> entry : this.request.getCookies().entrySet()) {
                    response.setCookie(entry.getValue());
                }
            }

            response.end();
        }
    }

    /**
     * Check the result of decoding this message
     *
     * @param o Message
     */
    private void checkDecoderResult(HttpObject o) {
        DecoderResult result = o.getDecoderResult();
        if (!result.isSuccess()) {
            log.error("Http decoder failure: ", result.cause());
        }
    }

}
