package com.epam.mobile.driver.probationproject.exampleproject.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.epam.mobile.testing.utils.ScriptExecutor;
import com.epam.mobile.testing.utils.Scripts;

/* 
 * Test Set for network check. - in progress.
 * 
 * Add ScriptExecutor (direct, ssh(From Windows To Mac to be able run script) modes)
 * - install app;
 * - kill process;
 * - run script;
 * Add simple check on Server Start
 * Add pretestmethod check on actual interactive connection with server 
 * (if app crash reinstall app and start it)
 *
 */
public class WorkingNetworkServicesTest extends TestTexture{

	/*
    // private static String PROXY_RPC_ADDRESS_KEY = "proxy_rpc_address";
	
	// test helpers
	private static StubHost    host     = null;
	// private static XPathReader reader   = null;
	
	// credentials
	private static String      username = "";
	private static String      password = "";

	// MarketBoard and related connection urls
	private static URL         url1     = null;
	
	// other parameters
//	private static String      request  = "";
	private static String      response = "";
*/
	
/*	private final static String noHostResponse = 
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
		"<methodResponse><params><param><value><array><data/>" +
		"</array></value></param></params></methodResponse>";
*/	
	
	@BeforeClass
	public static void setUpAllTests(){
		setUpTests();
		//driver.setServerHost("http://10.143.24.243:37265/");
		ScriptExecutor.getInstance().run(Scripts.Install_iPhoneApp_Into_Simulator);
		
/*		
        Properties properties = new Properties();
		try {
		    properties.load(new FileInputStream("tests_settings.properties"));
		} catch (IOException e) {
			properties.put(PROXY_RPC_ADDRESS_KEY, "http://10.6.125.121:80/");
		}
*/
		
		// start data collecting:
		//Report.getInstance().setPath("start_test_output.txt");		
		//Report.getInstance().start();
/*
		// init test helpers
		host    = new StubHost(0);
				
		// set up credentials:
		// usernames
		username           = "";
		// passwords
		password           = "";
		// MD5 passwords
		password           = host.getMD5Password(password);

		// set up and related connection urls:
		try {			
//			url1             = new URL(properties.getProperty(PROXY_RPC_ADDRESS_KEY));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		// log basic parameters
		logger.info("XML-request URL: {} is set up", url1.toString());
		logger.info("username: {}  is set up", username);
		logger.info("MD5: {} password is set up", password);
		*/
	}
	
	@Before
	public void setUpEachTest(){
		setUpTest();
	}
	
	// Test fails if timeout is reached.
	// Test pass for the first 'pass' response.
    @Test(timeout=80000)
	public void isNetworkServicesWork(){
		logger.info("Short Description: Test checks that "
				+ "Server sends responses.");
		logger.info("                   Driver should connect to Server and get"
				+ "{value:pass} in response.");
		//driver.setServerHost("http://10.143.24.243:37265/");
		Assert.assertTrue(isServerResponsible());
    }
    
    public boolean isServerResponsible(){
    	boolean result = true;
    	try {
        	int timeout = 1000;
    		while (!driver.isServerAlive()) {
    			System.out.println("[LOG] Attempt to connect... timeout=" + timeout);
    			Thread.sleep(timeout);
    		}
		} catch (Exception e) {
			result = false;
			// log error to report
			System.out.println(e.toString());
		}
		
		return result;
    }
	
	@After
	public void tearDownEachTest() {
		tearDownTest();
	}
	
	@AfterClass
	public static void tearDownAllTests() {
		tearDownTests();
	}
	
	public void cleanUpRegistrations(){
//		if (unregisterHosts()) 
//			System.out.println("unregisterHosts is pass.");
//		else
//			System.out.println("unregisterHosts is fail.");
	}

/*
	public boolean unregisterHost(String request, String uuid){
		boolean result = true;
		
		return result;
	}
*/
	
/*	public boolean unregisterHosts(){
		boolean result = true;
			
		return result;
	}
*/	
	
	// method waits and returns one non-empty host response 
	public String getPatientWebServiceResponse(String user, String pass){
//		boolean startWebService = false;
//		use isHostFullResponse.
		isHostFullResponse();
		
		return response;
	}
	
	private boolean isHostFullResponse(){
		return (response.contains("<token>") && 
				(response.contains("<device>")));
	}
}