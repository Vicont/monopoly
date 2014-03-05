package com.monopoly.dispatchers.definition;

import java.lang.reflect.Method;

/**
 * Definition indicating how to execute command
 *
 * @author vicont
 */
public class HttpCommandExecutionDefinition {

    /**
     * Controller name
     */
    private String controllerName;

    /**
     * Action method
     */
    private Method actionMethod;

    /**
     * Method that executes before action
     */
    private Method beforeMethod;

    /**
     * Method that executes after action
     */
    private Method afterMethod;

    /**
     * Retrieve controller name
     *
     * @return Controller name
     */
    public String getControllerName() {
        return controllerName;
    }

    /**
     * Set controller name
     *
     * @param name Controller name
     */
    public void setControllerName(String name) {
        this.controllerName = name;
    }

    /**
     * Retrieve action method
     *
     * @return Method
     */
    public Method getActionMethod() {
        return actionMethod;
    }

    /**
     * Set action method
     *
     * @param actionMethod Method
     */
    public void setActionMethod(Method actionMethod) {
        this.actionMethod = actionMethod;
    }

    /**
     * Retrieve method that executes before action
     *
     * @return Method
     */
    public Method getBeforeMethod() {
        return beforeMethod;
    }

    /**
     * Set method that executes before action
     *
     * @param beforeMethod Method
     */
    public void setBeforeMethod(Method beforeMethod) {
        this.beforeMethod = beforeMethod;
    }

    /**
     * Retrieve method that executes after action
     *
     * @return Method
     */
    public Method getAfterMethod() {
        return afterMethod;
    }

    /**
     * Set method that executes after action
     *
     * @param afterMethod Method
     */
    public void setAfterMethod(Method afterMethod) {
        this.afterMethod = afterMethod;
    }

}
