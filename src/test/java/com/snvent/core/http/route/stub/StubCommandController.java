package com.snvent.core.http.route.stub;

import com.snvent.core.http.annotation.CommandAction;
import com.snvent.core.http.annotation.controller.CommandController;
import com.snvent.core.http.controller.AbstractHttpCommandController;

/**
 * Class description
 *
 * @author vicont
 */
@CommandController
public class StubCommandController extends AbstractHttpCommandController {

    @CommandAction("StubCommand")
    public void stubCommandMethod() {
    }

}
