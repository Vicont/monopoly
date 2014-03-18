package com.snvent.core.http.dispatcher.factory.storage;

import com.snvent.core.http.annotation.command.IncomingCommand;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Storage for names of incoming commands
 *
 * @author vicont
 */
@Component
public class IncomingCommandNamesStorage implements BeanFactoryPostProcessor {

    /**
     * Command names
     */
    protected final Map<String, String> names = new HashMap<String, String>();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanNames;

        beanNames = beanFactory.getBeanNamesForAnnotation(IncomingCommand.class);
        for (String structureBeanName : beanNames) {
            String className = beanFactory.getBeanDefinition(structureBeanName).getBeanClassName();
            names.put(structureBeanName, className);
        }
    }

    public Map<String, String> getAll() {
        return this.names;
    }

}
