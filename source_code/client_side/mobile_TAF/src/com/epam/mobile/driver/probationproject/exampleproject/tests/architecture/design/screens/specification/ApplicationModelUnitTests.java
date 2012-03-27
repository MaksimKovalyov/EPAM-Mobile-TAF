package com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.design.screens.specification;

import org.junit.Assert;
import org.junit.Test;

public class ApplicationModelUnitTests {

	@Test
	public void isLoginScreenModelValid(){
		String[] expecteds = new String[]{"1", "2"};
		String[] actuals = ApplicationModel.LoginScreen().getScreen();
		Assert.assertArrayEquals(expecteds, actuals);
	}
}
