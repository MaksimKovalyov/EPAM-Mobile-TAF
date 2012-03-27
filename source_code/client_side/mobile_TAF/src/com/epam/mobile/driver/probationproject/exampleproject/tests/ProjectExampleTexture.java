package com.epam.mobile.driver.probationproject.exampleproject.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.epam.mobile.driver.core.Constants;
import com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.design.screens.FirstLoginScreen;
import com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.design.screens.HomeScreen;
import com.epam.mobile.extensions.report.TAFLoggerFactory;
import com.epam.mobile.testing.utils.AppModel;
import com.epam.mobile.testing.utils.ScriptExecutor;
import com.epam.mobile.testing.utils.Scripts;

public class ProjectExampleTexture extends TestTexture{

	@BeforeClass
	public static void setUpAllTests() {
		logger = TAFLoggerFactory.getLogger();
		
		setUpTests();
		FirstLoginScreen.tapLoginButton();
	}

	@Before
	public void setUpEachTest() {
		setUpTest();

		reinstall_iPhoneAppByCondition();
	}

	public void reinstall_iPhoneAppByCondition() {
		try {
			if (!driver.isServerAlive()) {
				logger.error(" Server is not alive.");
				
				ScriptExecutor.getInstance().run(Scripts.Quit_iPhoneSimulator);
				Thread.sleep(3000);
				
				ScriptExecutor.getInstance().run(Scripts.Quit_iPhoneSimulator_after_crash);
				Thread.sleep(10000);
				
				ScriptExecutor.getInstance().run(Scripts.Install_iPhoneApp_Into_Simulator);
				driver.wait(5000);
			} else {
				logger.info(" Server is alive.");
			}

		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	@After
	public void tearDownEachTest() {
		tearDownTest();
		// add method for clearing actual model
		new HomeScreen().logout();
	}

	@AfterClass
	public static void tearDownAllTests() {
		logger = null;
		tearDownTests();
		ScriptExecutor.getInstance().run(Scripts.Quit_iPhoneSimulator);
	}

	/*
	 * // set up db for all tests: add a user with registration limits public
	 * static void setUpDBTests(){ try { db.openConnection();
	 * db.addUser(test_error_user, test_error_pass, limit);
	 * db.closeConnection(); } catch (Exception e) {
	 * System.out.println(e.toString()); } }
	 */

	/*
	 * Example of setUpDB:
	 * 
	 * public void setUpDB(){ int id_broker = 2; int id_manager = 1; int
	 * id_broker_address = 11; int id_mask = 3; int id_mask_all = 1; int weight
	 * = 0; String name = "fakebroker"; String url =
	 * "http://10.10.10.10:8090/xml/rpc.do"; String auth = "N"; String user =
	 * "user1"; String pass = "pass1"; String address = "fake-broker.by:64666";
	 * String region = "EU"; String temp_region = "TP";
	 * 
	 * try { System.out.println("Connecion is opening."); db.openConnection();
	 * System.out.println("Connecion is opened."); //db.addUser(test_error_user,
	 * test_error_pass, limit);
	 * 
	 * db.addBroker(id_broker, name, url, auth, user, pass, id_manager);
	 * db.printBroker();
	 * 
	 * db.addBrokerAddress(id_broker_address, id_broker, address);
	 * db.printBrokerAddress();
	 * 
	 * db.updateRegionMask(id_mask_all, temp_region); db.addRegionMask(id_mask,
	 * id_broker, weight, region); db.printRegionMask();
	 * 
	 * db.closeConnection(); } catch (Exception e) {
	 * System.out.println(e.getMessage() + e.getStackTrace()); } }
	 */

	
	/*
	 * Try-Catch issue related to strict or soft test scenario processing.
	 * Strict scenario: each exception turns to error/fail (depends on testing
	 * framework as jUnit, ...) test. Soft scenario: each exception turns to
	 * Assert section and custom processing.
	 * 
	 * Soft scenario option allows flexible processing of Exceptions. We can
	 * classify it and generate different backward actions as file deleting if
	 * it was saved earlier in scenario. Soft scenario allows iron logic for
	 * custom cases.
	 * 
	 * Strict just redirect control and exception handling to jUnit(other
	 * framework) hands.
	 */
	
// method helpers:
	
}
