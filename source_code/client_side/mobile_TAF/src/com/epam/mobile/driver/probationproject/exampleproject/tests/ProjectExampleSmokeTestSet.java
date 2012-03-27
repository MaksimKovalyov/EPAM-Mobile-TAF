package com.epam.mobile.driver.probationproject.exampleproject.tests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.design.screens.LoginScreen;
import com.epam.mobile.driver.probationproject.exampleproject.tests.protoinstances.TestFixture;
import com.epam.mobile.extensions.report.TAFLoggerFactory;
import com.epam.mobile.testing.utils.AppModel;


/*
 * MarketBoard - Smoke tests Test Set.
 * 
 * Issues: please, see the TAFReview.txt document.
 * 
 */
public class ProjectExampleSmokeTestSet extends ProjectExampleTexture {

	@BeforeClass
	public static void beforeAll(){
		logger = TAFLoggerFactory.getLogger(ProjectExampleSmokeTestSet.class);
		
		setUpAllTests();
	}
		
	@Ignore
	@Test(timeout = test_timeout)
	@TestFixture(username=test_desktop_user,
				 password=test_desktop_pass)
	public void isAuthenticateLogin() {
		// working body of the test
		expecteds = AppModel.HomeScreen.getScreenModel();
		actuals = new LoginScreen().login(username, password).
									getScreenModel();

		// checks:
		// home screen labels:
		Assert.assertArrayEquals(expecteds, actuals);
	}
}