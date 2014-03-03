package com.monopoly.dispatchers.factory;

import com.monopoly.annotations.CommandAction;
import com.monopoly.annotations.controller.CommandController;
import com.monopoly.http.dispatcher.AbstractHttpDispatcherFactory;
import com.monopoly.http.dispatcher.HttpDispatcher;
import com.monopoly.http.dispatcher.exception.InvalidHttpDispatcherException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Dispatcher factory
 *
 * @author vicont
 */
@Component
@Scope("prototype")
public class HttpCommandDispatcherFactory extends AbstractHttpDispatcherFactory implements BeanFactoryPostProcessor {

    private final Map<String, String> controllers = new HashMap<String, String>();

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] names = beanFactory.getBeanNamesForAnnotation(CommandController.class);

        for (String controllerName : names) {
            String className = beanFactory.getBeanDefinition(controllerName).getBeanClassName();
            Class controllerClass;

            try {
                controllerClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Controller class \"" + className + "\" is not found");
            }

            Method[] methods = controllerClass.getMethods();

            for (Method method : methods) {
                if (method.isAnnotationPresent(CommandAction.class)) {
                    CommandAction action = method.getAnnotation(CommandAction.class);
                    String commandName = action.value();

                    if (controllers.containsKey(commandName)) {
                        throw new RuntimeException("Controller for command \"" + commandName + "\" already registered");
                    }
                    controllers.put(commandName, controllerName);
                }
            }
        }
    }

    @Override
    public void init() {

    }

    /**
     * Retrieve dispatcher
     *
     * @return Dispatcher
     * @throws com.monopoly.http.dispatcher.exception.InvalidHttpDispatcherException
     */
    public HttpDispatcher getDispatcher(Map<String, String> params) throws InvalidHttpDispatcherException {
        if (!params.containsKey("commandName")) {
            throw new InvalidHttpDispatcherException("Parameter \"commandName\" is not found");
        }

        String commandName = params.get("commandName");
        if (!controllers.containsKey(commandName)) {
            throw new InvalidHttpDispatcherException("Command with name \"" + commandName + "\" is not found");
        }

        String controllerName = controllers.get(commandName);
        return applicationContext.getBean(controllerName, HttpDispatcher.class);
    }

}
