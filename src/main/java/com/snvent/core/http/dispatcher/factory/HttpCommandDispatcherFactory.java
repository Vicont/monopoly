package com.snvent.core.http.dispatcher.factory;

import com.snvent.core.http.annotation.After;
import com.snvent.core.http.annotation.Before;
import com.snvent.core.http.annotation.CommandAction;
import com.snvent.core.http.dispatcher.HttpCommandDispatcher;
import com.snvent.core.http.dispatcher.HttpDispatcher;
import com.snvent.core.http.dispatcher.definition.HttpCommandExecutionDefinition;
import com.snvent.core.http.dispatcher.factory.exception.HttpDispatcherFactoryInitializationException;
import com.snvent.core.http.dispatcher.factory.storage.CommandControllerNamesStorage;
import com.snvent.core.http.dispatcher.factory.storage.IncomingCommandNamesStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    /**
     * Storage for names of incoming commands
     */
    @Autowired
    protected IncomingCommandNamesStorage commandNamesStorage;

    /**
     * Storage for controller names
     */
    @Autowired
    protected CommandControllerNamesStorage controllerNamesStorage;

    @PostConstruct
    @Override
    public void init() throws HttpDispatcherFactoryInitializationException {
        Map<String, Class> structures = this.findCommandStructures();
        Map<String, String> names = this.controllerNamesStorage.getAll();

        for (String controllerName : names.keySet()) {
            String className = names.get(controllerName);
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
        Map<String, String> names = this.commandNamesStorage.getAll();

        for (String structureBeanName : names.keySet()) {
            String className = names.get(structureBeanName);
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
