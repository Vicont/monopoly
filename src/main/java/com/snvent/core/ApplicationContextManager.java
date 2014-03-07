package com.snvent.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Manager which stores application context
 *
 * @author vicont
 */
@Component
public class ApplicationContextManager implements ApplicationContextAware {

    /**
     * Application context
     */
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        context = ctx;
    }

    /**
     * Retrieve application context
     *
     * @return Context
     */
    public static ApplicationContext getContext() {
        return context;
    }

}
