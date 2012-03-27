package com.epam.mobile.driver.probationproject.exampleproject.tests.protoinstances;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

import com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.configuration.Configurator;
import com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.foundation.NativeDriverContext;

public class TestCaseTexture {

	protected String expected;
	protected String actual;
	
	protected String username;
	protected String password;

	/* 
	   Analog of Aspect-Driven Programming Languages Feature:
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
				System.out.println("[LOG]" + e.toString());
			}
			
			super.starting(method);			
		}
		
		private void process(TestFixture tf){
			if (tf != null) {
				username = tf.username();
				password = tf.password();
			}
		}
	};
	
	@BeforeClass
	public static void beforeAllTests(){
		NativeDriverContext.initInstance(NativeDriverContext.APP_iOS, 
										 Configurator.getInstance().getServerURL());
	}
}
