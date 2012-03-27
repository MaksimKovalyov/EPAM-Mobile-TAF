package com.epam.mobile.driver.probationproject.exampleproject.tests.protoinstances;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface TestFixture {
	// for direct annotation:
	// String value() default "";
	String username() default "default_username";
	String password() default "default_password";
}
