package com.monopoly.controllers.request;

import com.monopoly.annotations.CommandAction;
import com.monopoly.annotations.controller.CommandController;
import org.springframework.beans.factory.BeanNameAware;

/**
 * Debug controller
 *
 * @author vicont
 */
@CommandController
public class DebugController implements BeanNameAware {

    @Override
    public void setBeanName(String name) {
        System.out.println("BEAN NAME: " + name);
    }

    @CommandAction("debug1")
    public void index() {

    }

}
