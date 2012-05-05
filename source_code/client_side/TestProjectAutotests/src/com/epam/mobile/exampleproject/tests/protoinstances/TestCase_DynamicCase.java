package com.epam.mobile.exampleproject.tests.protoinstances;

import org.junit.Assert;
import org.junit.Test;

import com.epam.mobile.exampleproject.tests.javaspice.TestFixture;

public class TestCase_DynamicCase extends TestCaseTexture{

	@Test
	@TestFixture(username="Username_ProtoCase", 
				 password="Password_ProtoCase")
	public void isAnotherSomethingCouldBeChoosen(){
		// steps:
		/*SomeScreen screen = FirstLoginScreen.tapLoginButton().
											login(username, password).
											tapSomeIcon().
											tapSomeOption().
											tapSomething();
		
		
		// get and check expected:
		// expected is the first portfolio label:
		expected = screen.getSomethingName();
		CustomAssert.assertNotEmpty(expected);
		
		// get actual:
		// actual is the second portfolio label:
		actual = screen.tapHomeButton().
								tapMyLists().
								tapAnotherSomething().
								getSomethingName();
		*/
		// check:
		// portfolio labels should be different:
		Assert.assertNotSame(expected, actual);
	}	
}