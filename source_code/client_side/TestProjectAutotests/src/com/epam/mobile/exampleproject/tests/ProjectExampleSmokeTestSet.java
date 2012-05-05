package com.epam.mobile.exampleproject.tests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.epam.mobile.exampleproject.tests.architecture.design.screens.ScreenFactory;
import com.epam.mobile.exampleproject.tests.architecture.design.screens.specification.AppModel;
import com.epam.mobile.exampleproject.tests.javaspice.TestFixture;
import com.epam.mobile.extensions.report.TAFLoggerFactory;

/*
 * ExampleProject - Smoke tests Test Set.
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
		actuals = ScreenFactory.getInstance().getLoginScreen().
								              login(username, password).
								              getScreenModel();

		// checks:
		// home screen labels:
		Assert.assertArrayEquals(expecteds, actuals);
	}
}