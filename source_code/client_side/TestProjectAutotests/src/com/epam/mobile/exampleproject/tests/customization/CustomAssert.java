package com.epam.mobile.exampleproject.tests.customization;

import org.junit.Assert;

import com.epam.mobile.exampleproject.tests.architecture.design.screens.specification.AppModel;
import com.epam.mobile.testing.utils.ScriptExecutor;

// There are many different ways to create CustomAssert.
// One way is to extend from existing Assert class with overriding or 
// extending functionality;
// Second way: create separated Assert class with custom assertion
// using or not default jUnit asserts.
public class CustomAssert{
	
	private static String VALUE_NOT_FOUND = "The element value is not found.";
	private static String EMPTY_STRING = "";
	
	public static void assertModalEquals(AppModel expected, AppModel actual){
		
	}
	
	public static boolean assertEquals(String expected, String actual){
		
		return false;
	}

	public static void assertNotEmpty(String object){
		Assert.assertNotNull(object);
		Assert.assertNotSame(object, EMPTY_STRING);
		Assert.assertNotSame(object, VALUE_NOT_FOUND);
	}

	public static void assertNotEmpty(String[] objects){
		for (String object : objects) {
			assertNotEmpty(object);
		}		
	}
	
	public static boolean assertEquals(String[] expected, String[] actual){
		
		return false;
	}
	
	public static void main(String[] args) {
		ScriptExecutor.getInstance().run("ls -al");	
	}
}
