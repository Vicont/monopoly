package com.monopoly.http.dispatcher;

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
    }

}
