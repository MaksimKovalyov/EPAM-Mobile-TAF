package com.epam.mobile.exampleproject.tests.javaspice;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExpectedModel {
	//String value() default "";
	String[] modelParts() default "";
	String methodName() default "";
	
}
