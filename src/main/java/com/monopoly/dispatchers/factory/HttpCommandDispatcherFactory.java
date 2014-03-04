package com.monopoly.dispatchers.factory;

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

            for (Method method : methods) {
                if (method.isAnnotationPresent(CommandAction.class)) {
                    CommandAction action = method.getAnnotation(CommandAction.class);
                    String commandName = action.value();

                    if (definitions.containsKey(commandName)) {
                        throw new RuntimeException("Controller for command \"" + commandName + "\" already registered");
                    }

                    HttpCommandExecutionDefinition definition = new HttpCommandExecutionDefinition();
                    definition.setControllerName(controllerName);
                    definition.setMethod(method);

                    definitions.put(commandName, definition);
                }
            }
        }
    }

    @Override
    public HttpDispatcher getDispatcher(Map<String, String> params) throws InvalidHttpDispatcherException {
        HttpDispatcher dispatcher = applicationContext.getBean(HttpCommandDispatcher.class);
        dispatcher.setParams(params);
        dispatcher.setDefinitions(definitions);
        return dispatcher;
    }

}
