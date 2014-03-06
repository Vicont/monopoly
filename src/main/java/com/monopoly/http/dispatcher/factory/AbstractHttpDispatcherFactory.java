package com.monopoly.http.dispatcher.factory;

import com.monopoly.http.dispatcher.factory.exception.HttpDispatcherFactoryInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Abstract HTTP dispatcher factory
 *
 * @author vicont
 */
abstract public class AbstractHttpDispatcherFactory implements HttpDispatcherFactory, ApplicationContextAware, BeanFactoryPostProcessor {

    /**
     * Spring application context
     */
    protected ApplicationContext applicationContext;

    /**
     * Spring bean factory
     */
    protected ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        try {
            this.init();
        } catch (HttpDispatcherFactoryInitializationException e) {
            Logger log = LoggerFactory.getLogger(this.getClass());
            log.error("Dispatcher factory initialization error: ", e);
        }
    }

}
