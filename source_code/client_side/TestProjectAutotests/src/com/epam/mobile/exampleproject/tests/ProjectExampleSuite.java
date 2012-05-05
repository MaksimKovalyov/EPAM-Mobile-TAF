package com.epam.mobile.exampleproject.tests;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;

//import org.junit.runner.RunWith;
import org.junit.runners.Suite;
//import org.junit.runners.Suite.SuiteClasses;

//compatible with old JUnit 
//@RunWith(Suite.class)
@Suite.SuiteClasses( { 
	WorkingNetworkServicesTest.class,
	ProjectExampleSmokeTestSet.class
//	AnotherTestSet.class
	})
public class ProjectExampleSuite {
	public static TestSuite suite = new TestSuite("Project Example Suite");
	
	public static Test suite() {		
        // if network services works then run the main Test set
        if (runTestSet(WorkingNetworkServicesTest.class)) {
    		suite.addTest(new JUnit4TestAdapter(ProjectExampleSmokeTestSet.class));    		
//    		suite.addTest(new JUnit4TestAdapter(AnotherTestSet.class));
        }

        return suite;
	}
	
	public static boolean runTestSet(Class<WorkingNetworkServicesTest> c){
		TestResult result = new TestResult();
		
        suite.runTest(new JUnit4TestAdapter(c), result);
        
       	System.out.println("Was it successful?" + result.wasSuccessful());
        System.out.println("How many tests were there?" + result.runCount());
        
        System.out.println("Network services status: [" + result.wasSuccessful() + "]");
        
        return result.wasSuccessful();
	}
}