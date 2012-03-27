package com.epam.mobile.driver.probationproject.exampleproject.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.mobile.driver.core.NativeDriver;
import com.epam.mobile.driver.core.iPhoneNativeDriver;
import com.epam.mobile.driver.utils.DBManager;
import com.epam.mobile.driver.utils.Generator;
import com.epam.mobile.driver.utils.StubHost;
import com.epam.mobile.driver.utils.XPathReader;

// in progress.

public class SITexture {
	// test helpers
	public  static NativeDriver      driver  = null;
	public  static StubHost          host    = null;
	public  static StubHost          client  = null;
	public  static XPathReader       reader  = null;
	public  static DBManager         db      = null;
	public  static String            db_name = "";
	public  static ArrayList<String> garbage = null;
	
	// credentials
	protected static String username             = "";
	protected static String password             = "";
	protected static String test_desktop_user    = "";
	protected static String test_desktop_pass    = "";
	protected static String test_user            = "";
	protected static String test_pass            = "";
	
	// extra credential options
	protected static String device    = "";
	protected static String token     = "";

	// MB and related connection urls
	protected static URL    urlLogin             = null;
	protected static URL    urlGetSomething      = null;
	protected static String actionLogin          = null;
	protected static String actionSomething      = null;
	
	// other parameters
	protected static String somethingID = "";
	protected static String ip          = "";
	protected static int    limit       = 0;
	protected static int    size        = 0;
	protected static int    sizeVersion = 0;
	protected static String request     = "";
	protected static String response    = "";
	protected static int    step        = 0;

	protected static String faultCode   = "";
	protected static String faultString = "";
	
	protected static int passTest = 0;
	protected static int failTest = 0;
	
	protected static int pass = 0;
	protected static int fail = 0;
	protected static int failAgain = 0;

	protected final static Logger logger = LoggerFactory.getLogger(SITexture.class);

/* add properties file
 * for example:
	protected static String PROXY_RPC_ADDRESS_KEY = "proxy_rpc_address";
*/
	
	@Rule
	public MethodRule watchman = new TestWatchman() {
		@Override
		public void starting(FrameworkMethod method) {
			super.starting(method);
			
			logger.info("-----------------------------------------------------------");
			logger.info("Test {} being run...", method.getName());			
		}

		@Override
		public void succeeded(FrameworkMethod method) {
			super.succeeded(method);
			
			logger.info("Test status: [ PASS ]");
		}

		@Override
		public void failed(Throwable e, FrameworkMethod method) {			
			super.failed(e, method);
			
			logger.info("Test status: [ FAIL ]");
			logger.info("Error: {} {} ", e.getMessage(), e.getStackTrace());
		}
	};

	public static void setUpTests(){
		// start data collecting:
		//Report.getInstance().setPath("ProxyConnection_test_output.txt");		
		//Report.getInstance().start();

		// init test helpers
		host      = new StubHost(0);
		client    = new StubHost(0);
		driver    = new iPhoneNativeDriver();

/*		Example for db support:
 *      
        db_name = "concrete_db_name";
		Properties properties = new Properties();
		try {
		    properties.load(new FileInputStream("tests_settings_local.properties"));
		} catch (IOException e) {
			Assert.fail(e.getLocalizedMessage());
		}
		db = new DBManager(db_name, properties.getProperty(
				DB_USER_NAME_KEY, "root"), properties.getProperty(
				DB_USER_PASSWORD_KEY, ""));
*/				
		// set up credentials:
		// usernames
		username           = "user";
		test_desktop_user  = "test_user";
		test_user          = "iUser";
		// passwords
		password           = "pass";
		test_desktop_pass  = "test_user_pass";
		test_pass          = "pass_no_pain_more_less";
		
		// set up extra credential options:
		device           = "AAAAAAAA-AAAA-AAAA-AAAA-AAAAAAAAAAAA";
		token            = "";
		
		// set up web services:
		actionLogin      = "http://mysite.com/SomeService/Login"; 
		actionSomething  = "http://mysite.com/SomeAnotherService/GetSomething";
		somethingID      = "11223344";
		
		// set up MarketBoard and related connection urls:
		try {
			urlLogin        = new URL("http://site.com/project//SomeService.svc");
			urlGetSomething = new URL("http://site.com/project//SomeAnotherService.svc"); 

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		// log basic parameters
//		logger.info("XML-request URL: {} is set up", url1.toString());
//		logger.info("username: {}  is set up", username);
//		logger.info("MD5: {} password is set up", password);
	}
	
	
	public void setUpTest(){
//		garbage = new ArrayList<String>();
	}
	
	public void tearDownTest() {
//	    cleanUpRegistrations();
//		garbage = null;
		//Report.getInstance().writeTest("All tests: " + (passTest+failTest) + 
		//		" fail: " + failTest + " pass: " + passTest);
//		Report.getInstance().end();
	}
	
	public static void tearDownTests() {
		host = null;
		reader = null;
		db = null;
		
		System.gc();
		//Report.getInstance().writeTest("All tests: " + (passTest+failTest) + 
		//		" fail: " + failTest + " pass: " + passTest);
		//Report.getInstance().end();
	}

	
	public void cleanUpRegistrations(){
		if (unregisterHosts()) 
			System.out.println("unregisterHosts is pass.");
		else
			System.out.println("unregisterHosts is fail.");
	}
	
	public String loginClientBySOAP(String username, String password)
			throws MalformedURLException, IOException {
		request = Generator.getInstance().getValidLoginClient(username,
				password, device, token);
//		garbage.add(request);
		System.out.println("request : " + request);

		client.performCall(urlLogin, request, actionLogin);
		logger.info("Client is logging...");

		reader = new XPathReader(Generator.getInstance().getXMLHeader() + 
						client.getResponse());
		String actualLoginHASH = reader.getLoginHASH();
		logger.info("              ... login HASH = {} Client is logged in.",
				actualLoginHASH);
//		garbage.add(actualLoginHASH);

		return actualLoginHASH;
	}

	public String[] getFundedSomethingBySOAP(String device, String token,
				String SomethingID)
			throws MalformedURLException, IOException {
		request = Generator.getInstance().getRequestSOAP(device, token, somethingID);
		// garbage.add(request);

		client.performCall(urlGetSomething, request, actionSomething);
		logger.info("Client is getting Something...");

		reader = new XPathReader(Generator.getInstance().getXMLHeader()
				+ client.getResponse());
		String[] actualSomethingData = new String[]{
				reader.getSomethingPercentChange(), 
				reader.getSomethingWeight(),
				reader.getSomethingDefaultCurrency()};
		logger.info("              ... expected Something data: {}",
				actualSomethingData);
		// garbage.add(actualLoginHASH);

		return actualSomethingData;
	}
	
	public boolean unregisterHost(String request, String uuid) {
		boolean result = true;

		return result;
	}

	public boolean unregisterHosts() {
		boolean result = true;

		return result;
	}	
	
}
