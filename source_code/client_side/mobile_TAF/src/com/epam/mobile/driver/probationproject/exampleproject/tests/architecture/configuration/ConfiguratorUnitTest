package com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.configuration;

import org.junit.Assert;
import org.junit.Test;

public class ConfiguratorUnitTests {

	public String expected;
	public String actual;
	
	@Test
	public void isReadServerURL(){
		expected = "http://localhost:37265/";
		actual = Configurator.getInstance().getServerURL();
		
		Assert.assertEquals(expected, actual);
	}
	
}
