package com.epam.mobile.driver.probationproject.exampleproject.tests.protoinstances;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.design.screens.FirstLoginScreen;
import com.epam.mobile.extensions.report.TAFLoggerFactory;
import com.epam.mobile.testing.utils.AppModel;
import com.epam.mobile.testing.utils.ScriptExecutor;
import com.epam.mobile.testing.utils.Scripts;

public class TestSet_OrdinaryCase extends TestSetTexture{
	
	// override
	@BeforeClass
	protected static void setUpAllTests(){
		logger = TAFLoggerFactory.getLogger(TestSet_OrdinaryCase.class);
		
		beforeAllTests();
		ScriptExecutor.getInstance().run(Scripts.Install_iPhoneApp_Into_Simulator);
	}
	
	//@Ignore
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
	
	//@Ignore
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