package com.monopoly.http.dispatcher;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Abstract HTTP dispatcher factory
 *
 * @author vicont
 */
abstract public class AbstractHttpDispatcherFactory implements HttpDispatcherFactory, ApplicationContextAware {

    /**
     * Spring application context
     */
    protected ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
