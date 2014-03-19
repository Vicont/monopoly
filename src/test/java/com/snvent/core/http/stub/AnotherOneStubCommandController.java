package com.snvent.core.http.stub;

import com.snvent.core.http.annotation.CommandAction;
import com.snvent.core.http.controller.AbstractHttpCommandController;

/**
 * Class description
 *
 * @author vicont
 */
public class AnotherOneStubCommandController extends AbstractHttpCommandController {

    @CommandAction("StubCommand")
    public void stubCommandMethod() {
    }

}
