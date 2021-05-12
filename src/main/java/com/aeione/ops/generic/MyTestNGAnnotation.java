package com.aeione.ops.generic;

import org.testng.ITestListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * A Custom TestNG Annotation – MyTestNGAnnotation — to customize TestNG Test
 * execution
 * created on 04-04-2021
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(METHOD)
public @interface MyTestNGAnnotation
{

    String name() ;

}