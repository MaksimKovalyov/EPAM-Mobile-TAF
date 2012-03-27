package com.epam.mobile.testing.utils;

import org.junit.Test;

public class CustomAssertUnitTests {

	// positive scenarios
	@Test
	public void isAssertNotEmptyStringValid(){
		String notEmptyStringObject = "String";
		
		CustomAssert.assertNotEmpty(notEmptyStringObject);

		// It should pass without Exception.
	}

	@Test
	public void isAssertNotEmptyStringArrayValid(){
		String[] notEmptyStringArray = new String[]{"String1", "String2"};
		
		CustomAssert.assertNotEmpty(notEmptyStringArray);

		// It should pass without Exception.
	}
	
	// negative scenarios
	@Test(expected=AssertionError.class)
	public void isAssertNotEmptyNullStringValid(){
		String nullString = null;
		
		CustomAssert.assertNotEmpty(nullString);

		// It should pass with throwing Exception.
	}

	@Test(expected=AssertionError.class)
	public void isAssertNotEmptyEmptyStringValid(){
		String emptyString = "";
		
		CustomAssert.assertNotEmpty(emptyString);
		
		// It should pass with throwing Exception.
	}

	@Test(expected=AssertionError.class)
	public void isAssertNotEmptyNullStringArrayFullValid(){
		String nullString = null;
		String[] nullStringArrayFull = new String[]{nullString, nullString};
		
		CustomAssert.assertNotEmpty(nullStringArrayFull);
		
		// It should pass with throwing Exception.
	}

	@Test(expected=AssertionError.class)
	public void isAssertNotEmptyValidEmptyStringArrayFull(){
		String emptyString = "";
		String[] emptyStringArrayFull = new String[]{emptyString, emptyString};
		
		CustomAssert.assertNotEmpty(emptyStringArrayFull);
		
		// It should pass with throwing Exception.
	}

	@Test(expected=AssertionError.class)
	public void isAssertNotEmptyValidNullStringArrayMixed(){
		String nullString = null;
		String[] nullStringArrayMixed = new String[]{"String", nullString};
		
		CustomAssert.assertNotEmpty(nullStringArrayMixed);
		
		// It should pass with throwing Exception.
	}

	@Test(expected=AssertionError.class)
	public void isAssertNotEmptyEmptyStringArrayMixedValid(){
		String emptyString = "";
		String[] emptyStringArrayMixed = new String[]{"String", emptyString};
		
		CustomAssert.assertNotEmpty(emptyStringArrayMixed);
		
		// It should pass with throwing Exception.
	}

	@Test(expected=AssertionError.class)
	public void isAssertNotEmptyNullStringArrayMixedReverseValid(){
		String nullString = null;
		String[] nullStringArrayMixedReverse = new String[]{nullString, "String"};
		
		CustomAssert.assertNotEmpty(nullStringArrayMixedReverse);
		
		// It should pass with throwing Exception.
	}

	@Test(expected=AssertionError.class)
	public void isAssertNotEmptyEmptyStringArrayMixedReverseValid(){
		String emptyString = "";
		String[] emptyStringArrayMixedReverse = new String[]{emptyString, "String"};
		
		CustomAssert.assertNotEmpty(emptyStringArrayMixedReverse);
		
		// It should pass with throwing Exception.
	}

	
}
