package com.monopoly.dispatchers.factory;

import com.monopoly.annotations.CommandAction;
import com.monopoly.annotations.controller.CommandController;
import com.monopoly.dispatchers.HttpCommandDispatcher;
import com.monopoly.http.dispatcher.AbstractHttpDispatcherFactory;
import com.monopoly.http.dispatcher.HttpDispatcher;
import com.monopoly.http.dispatcher.exception.InvalidHttpDispatcherException;
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
public class HttpCommandDispatcherFactory extends AbstractHttpDispatcherFactory {

    private final Map<String, String> controllers = new HashMap<String, String>();

    @Override
    public void init() {
        String[] names = this.beanFactory.getBeanNamesForAnnotation(CommandController.class);

        for (String controllerName : names) {
            String className = this.beanFactory.getBeanDefinition(controllerName).getBeanClassName();
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
    public HttpDispatcher getDispatcher(Map<String, String> params) throws InvalidHttpDispatcherException {
        HttpDispatcher dispatcher = applicationContext.getBean(HttpCommandDispatcher.class);
        dispatcher.setParams(params);
        dispatcher.setControllers(controllers);
        return dispatcher;
    }

}
