package com.snvent.core.http.stub;

import com.snvent.core.http.annotation.After;
import com.snvent.core.http.controller.AbstractHttpCommandController;

/**
 * Class description
 *
 * @author vicont
 */
public class StubCommandControllerWithDuplicateAfterMethod extends AbstractHttpCommandController {

    @After
    public void firstAfter() {
    }

    @After
    public void secondAfter() {
    }

}
