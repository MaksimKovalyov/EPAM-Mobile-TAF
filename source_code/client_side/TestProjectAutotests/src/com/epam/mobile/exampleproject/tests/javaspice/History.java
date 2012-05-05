package com.epam.mobile.exampleproject.tests.javaspice;

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
	String number() default "default_number";
	String author() default "default_username";
	String date() default "default_password";
}
