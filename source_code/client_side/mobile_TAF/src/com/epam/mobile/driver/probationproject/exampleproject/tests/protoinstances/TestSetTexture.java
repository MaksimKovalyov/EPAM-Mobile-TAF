package com.epam.mobile.driver.probationproject.exampleproject.tests.protoinstances;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

import com.epam.mobile.driver.core.NativeDriver;
import com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.configuration.Configurator;
import com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.foundation.NativeDriverContext;
import com.epam.mobile.testing.utils.UIMap;

public class TestSetTexture {

	protected String expected;
	protected String actual;
	
	protected String username;
	protected String password;

	protected final static int test_timeout = 60000;	
	protected static Logger logger;
	protected static NativeDriver driver;
	
	/* 
	   Analogy of Aspect-Driven Programming Languages Feature:
	     here, we rebuild the ordinary test case method and
	     can run some code before and after TC method starts.
	   
	     So we make possible the dynamic annotation usage for set up fields.	     
	     For example: 
	     @Test
	     @TestFixture(username="value")
	*/
	@Rule
	public MethodRule watchman = new TestWatchman() {
		@Override
		public void starting(FrameworkMethod method) {
			try {
				TestFixture tf = method.getAnnotation(TestFixture.class);
				process(tf);				
			} catch (Exception e) {
				logger.error(e.toString());
			}
			
			logger.info("-----------------------------------------------------------");
			logger.info("Test " + method.getName() + "being run...");
			
			super.starting(method);			
		}

		@Override
		public void succeeded(FrameworkMethod method) {
			logger.info("Test status: [ PASS ]");
			
			super.succeeded(method);
		}

		@Override
		public void failed(Throwable e, FrameworkMethod method) {
			logger.info("Test status: [ FAIL ]");
			logger.info("Error: " + e.getMessage() + " " + e.getStackTrace());

			super.failed(e, method);	
		}
		
		private void process(TestFixture tf){
			if (tf != null) {
				username = tf.username();
				password = tf.password();
			}
		}
	};
	
	@BeforeClass
	protected static void beforeAllTests(){		
		NativeDriverContext.initInstance(NativeDriverContext.APP_iOS, Configurator.getInstance().getServerURL());
	}
	
	@AfterClass
	protected static void afterAllTests(){
		NativeDriverContext.getInstance().close();
	}
	
	@Before
	protected void beforeEachTest(){
	}
	
	@After
	protected void afterEachTest(){
		NativeDriverContext.getInstance().getDriver().touch(UIMap.HomeScreen.segment_logout);
		NativeDriverContext.getInstance().getDriver().touch(UIMap.AlertDialog.button_yes);
	}
}