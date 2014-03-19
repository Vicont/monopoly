package com.snvent.core.http.stub;

import com.snvent.core.http.dispatcher.AbstractHttpDispatcher;
import com.snvent.core.http.dispatcher.HttpDispatcher;
import com.snvent.core.http.dispatcher.factory.HttpDispatcherFactory;
import com.snvent.core.http.dispatcher.factory.exception.InvalidCommandStructureException;
import com.snvent.core.http.dispatcher.factory.exception.InvalidControllerException;
import org.springframework.stereotype.Component;

/**
 * Class description
 *
 * @author vicont
 */
@Component
public class StubHttpDispatcherFactory implements HttpDispatcherFactory {

    @Override
    public void init() throws InvalidControllerException, InvalidCommandStructureException {
    }

    @Override
    public HttpDispatcher getDispatcher() {
        return new AbstractHttpDispatcher() {
            @Override
            public void process() {}
        };
    }

}
