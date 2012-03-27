package com.epam.mobile.driver.probationproject.exampleproject.tests.protoinstances;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.epam.mobile.extensions.report.TAFLoggerFactory;
import com.epam.mobile.testing.utils.ScriptExecutor;
import com.epam.mobile.testing.utils.Scripts;

public class TestSet_NetworkCheck extends TestSetTexture{	
	
	private final static int ping_timeout = 1000;
	
	// override
	@BeforeClass
	public static void setUpAllTests(){
		logger = TAFLoggerFactory.getLogger(TestSet_NetworkCheck.class);
		
		beforeAllTests();
		ScriptExecutor.getInstance().run(Scripts.Install_iPhoneApp_Into_Simulator);		
	}	
	
	// Test fails if timeout is reached.
	// Test pass for the first 'pass' response.
    @Test(timeout=2*test_timeout)
	public void isNetworkServicesWork(){
		logger.info("Short Description: Test checks that Server sends responses.");
		logger.info("                   Driver should connect to Server and get"
				+ "{value:pass} in response.");
		
		Assert.assertTrue(isServerResponsible());
    }
    
    public boolean isServerResponsible(){
    	boolean result = true;
    	
    	try {
    		while (!driver.isServerAlive()) {
    			logger.info(" Attempt to connect... timeout=" + ping_timeout);
    			Thread.sleep(ping_timeout);
    		}
		} catch (Exception e) {
			result = false;
			logger.error(e.toString());
		}
		
		return result;
    }		
}