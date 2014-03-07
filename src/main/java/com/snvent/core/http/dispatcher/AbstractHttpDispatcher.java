package com.snvent.core.http.dispatcher;

import com.snvent.core.http.HttpServerRequest;
import com.snvent.core.http.HttpServerResponse;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * Abstract HTTP dispatcher
 *
 * @author vicont
 */
abstract public class AbstractHttpDispatcher implements HttpDispatcher, ApplicationContextAware {

    /**
     * Additional params
     */
    protected Map<String, String> params;

    /**
     * HTTP request
     */
    protected HttpServerRequest request;

    /**
     * HTTP response
     */
    protected HttpServerResponse response;

    /**
     * Spring application context
     */
    protected ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setRequest(HttpServerRequest request) {
        this.request = request;
    }

    @Override
    public void setResponse(HttpServerResponse response) {
        this.response = response;
    }

    @Override
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

}
