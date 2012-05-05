package com.epam.mobile.exampleproject.tests.protoinstances;

import org.junit.Test;
import org.junit.Assert;

import com.epam.mobile.exampleproject.tests.architecture.design.screens.FirstLoginScreen;
import com.epam.mobile.exampleproject.tests.architecture.design.screens.specification.AppModel;
import com.epam.mobile.exampleproject.tests.javaspice.TestFixture;

public class TestCase_OrdinaryCase extends TestCaseTexture{
	
	@Test
	@TestFixture(username="Username_ProtoCase", 
				 password="Password_ProtoCase")
	public void isLogin_PositiveScenario(){
		expected = AppModel.HomeScreen.getScreenTitle();
		actual   = FirstLoginScreen.tapLoginButton().
								login(username, password).
								getScreenTitle();
		
		Assert.assertEquals(expected, actual);
	}
}
