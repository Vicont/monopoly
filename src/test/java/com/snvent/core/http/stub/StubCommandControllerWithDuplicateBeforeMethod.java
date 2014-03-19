package com.snvent.core.http.stub;

import com.snvent.core.http.annotation.Before;
import com.snvent.core.http.controller.AbstractHttpCommandController;

/**
 * Class description
 *
 * @author vicont
 */
public class StubCommandControllerWithDuplicateBeforeMethod extends AbstractHttpCommandController {

    @Before
    public void firstBefore() {
    }

    @Before
    public void secondBefore() {
    }

}
