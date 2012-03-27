package com.epam.mobile.driver.probationproject.exampleproject.tests.protoinstances;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface History {
	// for direct annotation:
	// String value() default "";
	int number() default 0;
	String author() default "default_username";
	String date() default "default_password";
}
