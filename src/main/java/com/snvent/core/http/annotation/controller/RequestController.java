package com.snvent.core.http.annotation.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for request controllers
 *
 * @author vicont
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
@Scope("prototype")
public @interface RequestController {
}
