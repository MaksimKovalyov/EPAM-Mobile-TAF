package com.epam.mobile.driver.probationproject.exampleproject.tests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/*
 *   Test Set inspects state of Test Automation Framework. - in progress.
 */
public class SelfInspectionTestSet extends SITexture{

	// vars related to Test Set

	@BeforeClass
	public static void setUpAllTests(){
		setUpTests();
		 		
		// generate report file:
//		Report.getInstance().setPath("ProxyConnection_test_output.txt");		
//		Report.getInstance().start();
	}
	
	@Before
	public void setUpEachTest(){
		setUpTest();
	}
	
	@After
	public void tearDownEachTest(){
		tearDownTest();
	}
	
	@AfterClass
	public static void tearDownAllTests(){
		tearDownAllTests();
	}

	// Here, it should be added test for self-inspection
}