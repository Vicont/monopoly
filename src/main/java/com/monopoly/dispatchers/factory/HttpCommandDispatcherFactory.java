package com.monopoly.dispatchers.factory;

import com.monopoly.annotations.After;
import com.monopoly.annotations.Before;
import com.monopoly.annotations.CommandAction;
import com.monopoly.annotations.controller.CommandController;
import com.monopoly.dispatchers.HttpCommandDispatcher;
import com.monopoly.dispatchers.definition.HttpCommandExecutionDefinition;
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

    /**
     * Command execution definitions
     */
    private final Map<String, HttpCommandExecutionDefinition> definitions = new HashMap<String, HttpCommandExecutionDefinition>();

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
            Method beforeMethod = null;
            Method afterMethod = null;

            for (Method method : methods) {
                if (method.isAnnotationPresent(Before.class)) {
                    if (beforeMethod != null) {
                        throw new RuntimeException("@Before method has already registered for \"" + className + "\"");
                    }
                    beforeMethod = method;
                }
                if (method.isAnnotationPresent(After.class)) {
                    if (afterMethod != null) {
                        throw new RuntimeException("@After method has already registered for \"" + className + "\"");
                    }
                    afterMethod = method;
                }
            }

            for (Method method : methods) {
                if (method.isAnnotationPresent(CommandAction.class)) {
                    CommandAction action = method.getAnnotation(CommandAction.class);
                    String commandName = action.value();

                    if (definitions.containsKey(commandName)) {
                        throw new RuntimeException("Controller for command \"" + commandName + "\" has already registered");
                    }

                    HttpCommandExecutionDefinition definition = new HttpCommandExecutionDefinition();
                    definition.setControllerName(controllerName);
                    definition.setActionMethod(method);
                    definition.setBeforeMethod(beforeMethod);
                    definition.setAfterMethod(afterMethod);

                    definitions.put(commandName, definition);
                }
            }
        }
    }

    @Override
    public HttpDispatcher getDispatcher() throws InvalidHttpDispatcherException {
        HttpCommandDispatcher dispatcher = applicationContext.getBean(HttpCommandDispatcher.class);
        dispatcher.setDefinitions(definitions);
        return dispatcher;
    }

}
