package com.monopoly.http;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;

/**
 * This class encapsulates a HTTP request received and a response to be generated in one exchange
 *
 * @author vicont
 */
public class HttpExchange {

    private ChannelHandlerContext context;

    private HttpServerRequest request;

    private HttpServerResponse response;

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(HttpExchange.class);

    public void setContext(ChannelHandlerContext ctx) {
        this.context = ctx;
    }

    public void handleHttpRequest(HttpRequest req) {
        this.checkDecoderResult(req);

        this.request = new HttpServerRequest(req);

        Set<Cookie> cookies = this.decodeCookies();
        for (Cookie cookie : cookies) {
            this.request.setCookie(cookie.getName(), cookie);
        }

        Map<String, List<String>> params = this.decodeParams(req.getUri());
        this.applyRequestParams(params);

        if (is100ContinueExpected(req)) {
            response = new HttpServerResponse(this.context, CONTINUE, false);
            response.end();
        }
    }

    public void handleHttpContent(HttpContent httpContent) {
        this.checkDecoderResult(httpContent);

        ByteBuf content = httpContent.content();

        if (content.isReadable()) {
            String body = content.toString(CharsetUtil.UTF_8);
            this.request.setBody(body);

            Map<String, List<String>> params = this.decodeParams(body);
            this.applyRequestParams(params);
        }

        if (httpContent instanceof LastHttpContent) {
            HttpResponseStatus status = (httpContent.getDecoderResult().isSuccess()) ? OK : BAD_REQUEST;
            response = new HttpServerResponse(this.context, status, this.request.isKeepAlive());

            response.write("Hello, world!");

            for (Map.Entry<String, Cookie> entry : this.request.getCookies().entrySet()) {
                response.setCookie(entry.getValue());
            }

            response.end();
        }
    }

    private void checkDecoderResult(HttpObject o) {
        DecoderResult result = o.getDecoderResult();
        if (!result.isSuccess()) {
            log.error("Http decoder failure: ", result.cause());
        }
    }

    private Set<Cookie> decodeCookies() {
        Set<Cookie> cookieSet;

        String cookieString = this.request.getHeaders().get(COOKIE);
        if (cookieString != null) {
            cookieSet = CookieDecoder.decode(cookieString);
        } else {
            cookieSet = new HashSet<Cookie>();
        }

        return cookieSet;
    }

    private Map<String, List<String>> decodeParams(String data) {
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(data);
        return queryStringDecoder.parameters();
    }

    private void applyRequestParams(Map<String, List<String>> params) {
        if (!params.isEmpty()) {
            for (Map.Entry<String, List<String>> param: params.entrySet()) {
                String key = param.getKey();
                List<String> values = param.getValue();
                for (String value : values) {
                    this.request.setParam(key, value);
                }
            }
        }
    }

}
