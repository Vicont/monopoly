package com.snvent.core.http.dispatcher.factory;

import com.snvent.core.AbstractTest;
import com.snvent.core.http.dispatcher.factory.exception.HttpDispatcherFactoryInitializationException;
import com.snvent.core.http.dispatcher.factory.exception.InvalidCommandStructureException;
import com.snvent.core.http.dispatcher.factory.exception.InvalidControllerException;
import com.snvent.core.http.dispatcher.factory.storage.CommandControllerNamesStorage;
import com.snvent.core.http.dispatcher.factory.storage.IncomingCommandNamesStorage;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Class description
 *
 * @author vicont
 */
public class HttpCommandDispatcherFactoryTest extends AbstractTest {

    @Test(expected = InvalidCommandStructureException.class)
    public void testInitWithNonExistsStructure() throws HttpDispatcherFactoryInitializationException {
        HttpCommandDispatcherFactory factory = new HttpCommandDispatcherFactory();

        Map<String, String> commandNames = new HashMap<String, String>();
        commandNames.put("StubCommand", "com.snvent.core.http.stub.NonExistsStubCommand");
        IncomingCommandNamesStorage incomingCommandNamesStorage = mock(IncomingCommandNamesStorage.class);
        when(incomingCommandNamesStorage.getAll()).thenReturn(commandNames);

        factory.setCommandNamesStorage(incomingCommandNamesStorage);
        factory.init();
    }

    @Test(expected = InvalidCommandStructureException.class)
    public void testInitWithDuplicateCommandName() throws HttpDispatcherFactoryInitializationException {
        HttpCommandDispatcherFactory factory = new HttpCommandDispatcherFactory();

        Map<String, String> commandNames = new HashMap<String, String>();
        commandNames.put("StubCommand1", "com.snvent.core.http.stub.StubCommand");
        commandNames.put("StubCommand2", "com.example.StubCommand");
        IncomingCommandNamesStorage incomingCommandNamesStorage = mock(IncomingCommandNamesStorage.class);
        when(incomingCommandNamesStorage.getAll()).thenReturn(commandNames);

        factory.setCommandNamesStorage(incomingCommandNamesStorage);
        factory.init();
    }

    @Test(expected = InvalidCommandStructureException.class)
    public void testInitWithCommandWithoutStructure() throws HttpDispatcherFactoryInitializationException {
        HttpCommandDispatcherFactory factory = new HttpCommandDispatcherFactory();

        Map<String, String> commandNames = new HashMap<String, String>();
        IncomingCommandNamesStorage incomingCommandNamesStorage = mock(IncomingCommandNamesStorage.class);
        when(incomingCommandNamesStorage.getAll()).thenReturn(commandNames);

        Map<String, String> controllerNames = new HashMap<String, String>();
        controllerNames.put("StubController", "com.snvent.core.http.stub.StubCommandController");
        CommandControllerNamesStorage commandControllerNamesStorage = mock(CommandControllerNamesStorage.class);
        when(commandControllerNamesStorage.getAll()).thenReturn(controllerNames);

        factory.setApplicationContext(this.applicationContext);
        factory.setCommandNamesStorage(incomingCommandNamesStorage);
        factory.setControllerNamesStorage(commandControllerNamesStorage);
        factory.init();
    }

    @Test(expected = InvalidControllerException.class)
    public void testInitWithNonExistsController() throws HttpDispatcherFactoryInitializationException {
        HttpCommandDispatcherFactory factory = new HttpCommandDispatcherFactory();

        Map<String, String> commandNames = new HashMap<String, String>();
        commandNames.put("StubCommand", "com.snvent.core.http.stub.StubCommand");
        IncomingCommandNamesStorage incomingCommandNamesStorage = mock(IncomingCommandNamesStorage.class);
        when(incomingCommandNamesStorage.getAll()).thenReturn(commandNames);

        Map<String, String> controllerNames = new HashMap<String, String>();
        controllerNames.put("StubController", "com.snvent.core.http.stub.NonExistsController");
        CommandControllerNamesStorage commandControllerNamesStorage = mock(CommandControllerNamesStorage.class);
        when(commandControllerNamesStorage.getAll()).thenReturn(controllerNames);

        factory.setApplicationContext(this.applicationContext);
        factory.setCommandNamesStorage(incomingCommandNamesStorage);
        factory.setControllerNamesStorage(commandControllerNamesStorage);
        factory.init();
    }

    @Test(expected = InvalidControllerException.class)
    public void testInitWithDuplicateControllerForCommand() throws HttpDispatcherFactoryInitializationException {
        HttpCommandDispatcherFactory factory = new HttpCommandDispatcherFactory();

        Map<String, String> commandNames = new HashMap<String, String>();
        commandNames.put("StubCommand", "com.snvent.core.http.stub.StubCommand");
        IncomingCommandNamesStorage incomingCommandNamesStorage = mock(IncomingCommandNamesStorage.class);
        when(incomingCommandNamesStorage.getAll()).thenReturn(commandNames);

        Map<String, String> controllerNames = new HashMap<String, String>();
        controllerNames.put("StubController", "com.snvent.core.http.stub.StubCommandController");
        controllerNames.put("AnotherOneStubController", "com.snvent.core.http.stub.AnotherOneStubController");
        CommandControllerNamesStorage commandControllerNamesStorage = mock(CommandControllerNamesStorage.class);
        when(commandControllerNamesStorage.getAll()).thenReturn(controllerNames);

        factory.setApplicationContext(this.applicationContext);
        factory.setCommandNamesStorage(incomingCommandNamesStorage);
        factory.setControllerNamesStorage(commandControllerNamesStorage);
        factory.init();
    }

    @Test(expected = InvalidControllerException.class)
    public void testInitWithDuplicateBeforeMethodInController() throws HttpDispatcherFactoryInitializationException {
        HttpCommandDispatcherFactory factory = new HttpCommandDispatcherFactory();

        Map<String, String> commandNames = new HashMap<String, String>();
        commandNames.put("StubCommand", "com.snvent.core.http.stub.StubCommand");
        IncomingCommandNamesStorage incomingCommandNamesStorage = mock(IncomingCommandNamesStorage.class);
        when(incomingCommandNamesStorage.getAll()).thenReturn(commandNames);

        Map<String, String> controllerNames = new HashMap<String, String>();
        controllerNames.put("StubController", "com.snvent.core.http.stub.StubCommandControllerWithDuplicateBeforeMethod");
        CommandControllerNamesStorage commandControllerNamesStorage = mock(CommandControllerNamesStorage.class);
        when(commandControllerNamesStorage.getAll()).thenReturn(controllerNames);

        factory.setApplicationContext(this.applicationContext);
        factory.setCommandNamesStorage(incomingCommandNamesStorage);
        factory.setControllerNamesStorage(commandControllerNamesStorage);
        factory.init();
    }

    @Test(expected = InvalidControllerException.class)
    public void testInitWithDuplicateAfterMethodInController() throws HttpDispatcherFactoryInitializationException {
        HttpCommandDispatcherFactory factory = new HttpCommandDispatcherFactory();

        Map<String, String> commandNames = new HashMap<String, String>();
        commandNames.put("StubCommand", "com.snvent.core.http.stub.StubCommand");
        IncomingCommandNamesStorage incomingCommandNamesStorage = mock(IncomingCommandNamesStorage.class);
        when(incomingCommandNamesStorage.getAll()).thenReturn(commandNames);

        Map<String, String> controllerNames = new HashMap<String, String>();
        controllerNames.put("StubController", "com.snvent.core.http.stub.StubCommandControllerWithDuplicateAfterMethod");
        CommandControllerNamesStorage commandControllerNamesStorage = mock(CommandControllerNamesStorage.class);
        when(commandControllerNamesStorage.getAll()).thenReturn(controllerNames);

        factory.setApplicationContext(this.applicationContext);
        factory.setCommandNamesStorage(incomingCommandNamesStorage);
        factory.setControllerNamesStorage(commandControllerNamesStorage);
        factory.init();
    }

    @Test
    public void testSuccessInit() throws HttpDispatcherFactoryInitializationException {
        HttpCommandDispatcherFactory factory = new HttpCommandDispatcherFactory();

        Map<String, String> commandNames = new HashMap<String, String>();
        commandNames.put("StubCommand", "com.snvent.core.http.stub.StubCommand");
        IncomingCommandNamesStorage incomingCommandNamesStorage = mock(IncomingCommandNamesStorage.class);
        when(incomingCommandNamesStorage.getAll()).thenReturn(commandNames);

        Map<String, String> controllerNames = new HashMap<String, String>();
        controllerNames.put("StubController", "com.snvent.core.http.stub.StubCommandController");
        CommandControllerNamesStorage commandControllerNamesStorage = mock(CommandControllerNamesStorage.class);
        when(commandControllerNamesStorage.getAll()).thenReturn(controllerNames);

        factory.setApplicationContext(this.applicationContext);
        factory.setCommandNamesStorage(incomingCommandNamesStorage);
        factory.setControllerNamesStorage(commandControllerNamesStorage);
        factory.init();
    }

}
