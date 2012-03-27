package com.epam.mobile.driver.probationproject.exampleproject.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
//import org.slf4j.Logger;

import org.apache.log4j.Logger;

import com.epam.mobile.driver.core.NativeDriver;
import com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.foundation.NativeDriverContext;
import com.epam.mobile.driver.probationproject.exampleproject.tests.protoinstances.TestFixture;
import com.epam.mobile.driver.utils.DBManager;
import com.epam.mobile.driver.utils.Generator;
import com.epam.mobile.driver.utils.StubHost;
import com.epam.mobile.driver.utils.XPathReader;
import com.epam.mobile.extensions.report.CustomLevels;
import com.epam.mobile.testing.utils.AppModel;
import com.epam.mobile.testing.utils.AppView;
import com.epam.mobile.testing.utils.WebModel;


// anti-pattern = Overkill initialization,
// link: http://www.odi.ch/prog/design/newbies.php
// Check some bugs related to initialization process in Java.

// issue to clarify: static final vs unstatic logger.  
public class TestTexture {
	// test helpers
	protected static NativeDriver   driver        = null;
	public static StubHost          host          = null;
	public static StubHost          client        = null;
	public static XPathReader       reader        = null;
	public static DBManager         db            = null;
	public static String            db_name       = "";
	public static ArrayList<String> garbage       = null;
	public static AppModel          expectedModel = null;
	public static AppModel          actualModel   = null;
	
	public static WebModel          expectedWebModel = null;
	public static WebModel          actualWebModel   = null;

	
	public static AppView           expectedView  = null;
	public static AppView           actualView    = null;
	
	// credentials
	protected static String username = "";
	protected static String password = "";
	

	protected static final String test_desktop_user = "user";
	protected static final String test_desktop_pass = "pass";
	protected static String test_user         = "";
	protected static String test_pass         = "";
	
	// extra credential options
	protected static String device = "";
	protected static String token  = "";

	// other parameters
	protected static int    timeout     = 0;
	protected static final int test_timeout = 60000;
	protected static String ip          = "";
	protected static int    limit       = 0;
	protected static int    size        = 0;
	protected static int    sizeVersion = 0;
	protected static String request     = "";
	protected static String response    = "";
	protected static int    step        = 0;

	protected static String faultCode   = "";
	protected static String faultString = "";
	
	protected static int passTests = 0;
	protected static int failTests = 0;

	protected static Logger logger = null;//LoggerFactory.getLogger(TestTexture.class);
	
	protected static String expected;
	protected static String actual;
	protected static String[] expecteds;
	protected static String[] actuals;
	
	@Rule
	public MethodRule watchman = new TestWatchman() {
		@Override
		public void starting(FrameworkMethod method) {
			logger.info("-----------------------------------------------------------");			
			logger.info("Test " +  method.getName() + " being run...");
			
			// Filter is set up: logging only if level is set up to DEMO
			logger.log(CustomLevels.DEMO_TRACE, "********************************************");
			logger.log(CustomLevels.DEMO_TRACE, "* Test " +  method.getName() + " starts...");
			
			// process custom annotation
			try {
				TestFixture tf = method.getAnnotation(TestFixture.class);
				process(tf);				
			} catch (Exception e) {
				System.out.println("[LOG]" + e.toString());
			}
			
			super.starting(method);
		}

		@Override
		public void succeeded(FrameworkMethod method) {
			logger.info("Test status: [ PASS ]");
			
			// Filter is set up: logging only if level is set up to DEMO
			logger.log(CustomLevels.DEMO_TRACE, "* Test status:                   [ PASS ]");
			logger.log(CustomLevels.DEMO_TRACE, "********************************************");
			
			super.succeeded(method);
		}

		@Override
		public void failed(Throwable e, FrameworkMethod method) {			
			logger.info("Test status: [ FAIL ]");
			logger.info("Error: " + e.getMessage() + " " + e.getStackTrace());
			
			// Filter is set up: logging only if level is set up to DEMO
			logger.log(CustomLevels.DEMO_TRACE, "* Test status:                   [ FAIL ]");
			logger.log(CustomLevels.DEMO_TRACE, "* Reason: " + e.getMessage());
			logger.log(CustomLevels.DEMO_TRACE, "********************************************");
			
			super.failed(e, method);
		}
		
		private void process(TestFixture tf){
			if (tf != null) {
				username = tf.username();
				password = tf.password();
			}
		}
	};

	public static void setUpTests(){
		// start data collecting:
		//Report.getInstance().setPath("ProxyConnection_test_output.txt");		
		//Report.getInstance().start();

		// init test helpers
		host          = new StubHost(0);
		client        = new StubHost(0);
		driver 	      = NativeDriverContext.getInstance().getDriver();
		//driver        = new iPhoneNativeDriver();
		//expectedModel = new AppModel(Constants.FULL);
		//actualModel   = new AppModel(Constants.EMPTY);
		
		//expectedWebModel = new WebModel(Constants.FULL);
		//actualWebModel   = new WebModel(Constants.EMPTY);
		
		//expectedView  = new AppView();
		//actualView  = new AppView();
		
		// set BELARUS time zone:
 	    //TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE_BELARUS));
					  //getTimeZone("Europe/Moscow"));
					  //getTimeZone("GMT+3:00"));
		
		
		// set up default state of driver
		//test_timeout = 3000;
		timeout = 3000;
		
		/*		db_name = "test_db_name";
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
		username           = "username";
		//test_desktop_user  = "desktop_user";
		// passwords
		password           = "pass";
		//test_desktop_pass  = "test_user";
		
		// set up extra credential options:
		device           = "AAAAAAAA-BBBB-CCCC-DDDD-AAAAAAAABBBB";
		token            = "";
		
		// set up web services
		
		/*
		// MD5 passwords
		password           = host.getMD5Password(password);
		 */		
		
		// set up MarketBoard and related connection urls:
		try {
			// example of getting property value:
			// urlHttps = new URL(
			//				properties.getProperty(PROXY_RPC_HTTPS_ADDRESS_KEY,
			// 						"https://epbyminmacXXXX:8080/xmlrpc/rpc.do"));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	
		// log basic parameters
		// logger.info("XML-request URL: {} is set up", url1.toString());
		// logger.info("username: {}  is set up", username);
		// logger.info("MD5: {} password is set up", password);
	}
	
	
	public void setUpTest(){
		garbage = new ArrayList<String>();
		//system.store = new HashMap();
	}
	
	public void tearDownTest() {
	    cleanUpRegistrations();
		garbage = null;
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

	public String loginClientBySOAP(String username, String password)
			throws MalformedURLException, IOException {
		request = Generator.getInstance().getValidLoginClient(username,
				password, device, token);
//		garbage.add(request);
		System.out.println("request : " + request);

		client.performCall(new URL("urlLogin"), request, "actionLogin");
		logger.info("Client is logging...");

		reader = new XPathReader(Generator.getInstance().getXMLHeader() + 
						client.getResponse());
		String actualLoginHASH = reader.getLoginHASH();
		//logger.info("              ... login HASH = {} Client is logged in.", actualLoginHASH);
		logger.info("              ... login HASH = " + actualLoginHASH + " Client is logged in.");
//		garbage.add(actualLoginHASH);

		return actualLoginHASH;
	}

	public String[] getSomethingBySOAP(String device, String token,
				String portfolioID)
			throws MalformedURLException, IOException {
		request = Generator.getInstance().getRequestSOAP(device, token, portfolioID);
		// garbage.add(request);

		client.performCall(new URL("urlGetSomething"), request, "action");
		logger.info("Client is getting portfolio...");

		reader = new XPathReader(Generator.getInstance().getXMLHeader()
				+ client.getResponse());
		String[] actualSomethingData = new String[]{
				reader.getSomethingPercentChange(), 
				reader.getSomethingWeight(),
				reader.getSomethingDefaultCurrency()};
		//logger.info("              ... expected portfolio data: {}", actualPortfolioData);
		logger.info("              ... expected portfolio data: " + actualSomethingData);
		// garbage.add(actualLoginHASH);

		return actualSomethingData;
	}
	
	/*
	// method waits for success connection and returns one non-empty host response 
	public String getPatientClientResponse(String user, String pass){
		
		return response;
	}
*/
	
	public void cleanUpRegistrations(){
		if (unregisterHosts()) 
			System.out.println("unregisterHosts is pass.");
		else
			System.out.println("unregisterHosts is fail.");
	}
	
/*	protected void waitServiceWork(int finalStep){
		
//		System.out.println("...operation waitServiceWork start");
//		Commands.WaitServiceRun wait = new Commands.WaitServiceRun();
//		wait.execute(finalStep);
//		System.out.println("...operation waitServiceWork end");

	}*/
	
	
	public boolean unregisterHost(String request, String uuid) {
		boolean result = true;

		return result;
	}

	public boolean unregisterHosts() {
		boolean result = true;

		for (Iterator<String> iterator = garbage.iterator(); iterator.hasNext();) {
			String request = (String) iterator.next();
			String uuid = (String) iterator.next();

			if (!unregisterHost(request, uuid)) {
				result = false;
				System.out.println("Host " + uuid + " didn't be unregistered.");
			}
		}

		return result;
	}	
	
}
