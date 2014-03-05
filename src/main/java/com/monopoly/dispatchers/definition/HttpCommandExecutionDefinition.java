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
     * Controller method
     */
    private Method method;

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
     * Retrieve method object
     *
     * @return Method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Set method object
     *
     * @param method Method
     */
    public void setMethod(Method method) {
        this.method = method;
    }

}
