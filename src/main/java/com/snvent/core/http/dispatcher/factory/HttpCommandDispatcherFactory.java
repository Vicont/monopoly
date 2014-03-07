package com.snvent.core.http.dispatcher.factory;

import com.snvent.core.http.annotation.After;
import com.snvent.core.http.annotation.Before;
import com.snvent.core.http.annotation.CommandAction;
import com.snvent.core.http.annotation.command.IncomingCommand;
import com.snvent.core.http.annotation.controller.CommandController;
import com.snvent.core.http.dispatcher.HttpCommandDispatcher;
import com.snvent.core.http.dispatcher.definition.HttpCommandExecutionDefinition;
import com.snvent.core.http.dispatcher.HttpDispatcher;
import com.snvent.core.http.dispatcher.factory.exception.HttpDispatcherFactoryInitializationException;
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
    public void init() throws HttpDispatcherFactoryInitializationException {
        String[] names = this.beanFactory.getBeanNamesForAnnotation(CommandController.class);
        Map<String, Class> structures = this.findCommandStructures();

        for (String controllerName : names) {
            String className = this.beanFactory.getBeanDefinition(controllerName).getBeanClassName();
            Class controllerClass;

            try {
                controllerClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new HttpDispatcherFactoryInitializationException("Controller with name \"" + className + "\" is not found");
            }

            Method[] methods = controllerClass.getMethods();
            Method beforeMethod = null;
            Method afterMethod = null;

            for (Method method : methods) {
                if (method.isAnnotationPresent(Before.class)) {
                    if (beforeMethod != null) {
                        throw new HttpDispatcherFactoryInitializationException("@Before method has already registered for \"" + className + "\"");
                    }
                    beforeMethod = method;
                }
                if (method.isAnnotationPresent(After.class)) {
                    if (afterMethod != null) {
                        throw new HttpDispatcherFactoryInitializationException("@After method has already registered for \"" + className + "\"");
                    }
                    afterMethod = method;
                }
            }

            for (Method method : methods) {
                if (method.isAnnotationPresent(CommandAction.class)) {
                    CommandAction action = method.getAnnotation(CommandAction.class);
                    String commandName = action.value();

                    if (definitions.containsKey(commandName)) {
                        throw new HttpDispatcherFactoryInitializationException("Controller for command \"" + commandName + "\" has already registered");
                    }

                    if (!structures.containsKey(commandName)) {
                        throw new HttpDispatcherFactoryInitializationException("Structure for command \"" + commandName + "\" is not found");
                    }

                    HttpCommandExecutionDefinition definition = new HttpCommandExecutionDefinition();
                    definition.setStructure(structures.get(commandName));
                    definition.setControllerName(controllerName);
                    definition.setActionMethod(method);
                    definition.setBeforeMethod(beforeMethod);
                    definition.setAfterMethod(afterMethod);

                    definitions.put(commandName, definition);
                }
            }
        }
    }

    /**
     * Find command structures
     *
     * @return Structures map
     * @throws HttpDispatcherFactoryInitializationException
     */
    private Map<String, Class> findCommandStructures() throws HttpDispatcherFactoryInitializationException {
        Map<String, Class> commands = new HashMap<String, Class>();
        String[] names = this.beanFactory.getBeanNamesForAnnotation(IncomingCommand.class);

        for (String structureBeanName : names) {
            String className = this.beanFactory.getBeanDefinition(structureBeanName).getBeanClassName();
            Class commandStructure;

            try {
                commandStructure = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new HttpDispatcherFactoryInitializationException("Structure with name \"" + className + "\" is not found");
            }

            String commandName = commandStructure.getSimpleName();

            if (commands.containsKey(commandName)) {
                throw new HttpDispatcherFactoryInitializationException("Structure for command \"" + commandName + "\" has already registered");
            }

            commands.put(commandName, commandStructure);
        }

        return commands;
    }

    @Override
    public HttpDispatcher getDispatcher() {
        HttpCommandDispatcher dispatcher = applicationContext.getBean(HttpCommandDispatcher.class);
        dispatcher.setDefinitions(definitions);
        return dispatcher;
    }

}
