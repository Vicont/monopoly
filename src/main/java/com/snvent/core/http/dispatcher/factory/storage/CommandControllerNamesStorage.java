package com.snvent.core.http.dispatcher.factory.storage;

import com.snvent.core.http.annotation.controller.CommandController;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Storage for controller names
 *
 * @author vicont
 */
@Component
public class CommandControllerNamesStorage implements BeanFactoryPostProcessor {

    /**
     * Command names
     */
    protected final Map<String, String> names = new HashMap<String, String>();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanNames;

        beanNames = beanFactory.getBeanNamesForAnnotation(CommandController.class);
        for (String structureBeanName : beanNames) {
            String className = beanFactory.getBeanDefinition(structureBeanName).getBeanClassName();
            names.put(structureBeanName, className);
        }
    }

    public Map<String, String> getAll() {
        return this.names;
    }

}
