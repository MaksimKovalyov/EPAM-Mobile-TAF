package com.epam.mobile.driver.probationproject.exampleproject.tests.protoinstances;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import org.junit.runners.Suite;

//compatible with old JUnit 
//@RunWith(Suite.class)
@Suite.SuiteClasses( { 
	TestSet_NetworkCheck.class,
	TestSet_OrdinaryCase.class,
	})
public class TestSuite_OrdinaryCase {
	
	public static TestSuite suite = new TestSuite("Test Suite: Ordinary Case.");
	
	public static Test suite() {
		// if network services works then run the main Test set
        if (runTestSet(TestSet_NetworkCheck.class)) {
        	suite.addTest(new JUnit4TestAdapter(TestSet_OrdinaryCase.class));    		
    		
        	// add another test sets
    		// suite.addTest(new JUnit4TestAdapter(ProjectNameFunctionalityNameTestSet.class));
        }

        return suite;
	}
	
	public static boolean runTestSet(Class<TestSet_NetworkCheck> c){
		TestResult result = new TestResult();
		
        suite.runTest(new JUnit4TestAdapter(c), result);
        
       	System.out.println("Network services status: [" + result.wasSuccessful() + "]");
        System.out.println("Number of executed tests: " + result.runCount());
        
        return result.wasSuccessful();
	}
}