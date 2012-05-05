package com.epam.mobile.testing.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.Test;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import org.junit.runners.Suite;

//compatible with old JUnit 
//@RunWith(Suite.class)
@Suite.SuiteClasses( { 
	})
public class GroupTestClass {
	public static TestSuite suite = new TestSuite("Example Group Test");
	
	
	public static void run(TestResult result){
		suite.run(result);
	}
	
	public static void addTests(Test... tests){
		try {
			for (Test test : tests) {
				System.out.println("[LOG] TC " + getNameOf(test) + " test was added to group test.");
				
				suite.addTest(test);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public static String getNameOf(Test test_) throws SecurityException, NoSuchMethodException, IllegalArgumentException,
     IllegalAccessException, InvocationTargetException {
		 Method m = test_.getClass().getMethod("getName", new Class[] {});
		 Object ret = m.invoke(test_, new Object[] {});
		 
		 return ret.toString();
	}

	
	public static Test suite() {
	//	boolean result = true;
		
        // if network services works then run the main ProxyTest set
		// stub condition
        //if (runTestSet(WorkingNetworkServicesTest.class)) {
        //	System.out.println("Network services status: [PASS]");
    		//suite.addTest(new JUnit4TestAdapter(ExampleTestSet.class));    		
        //}
        //else {
 	    //   	System.out.println("Network services status: [FAIL]");
        //}

	return suite;
	}
	
/*	public static boolean runTestSet(Class<WorkingNetworkServicesTest> c){
	TestResult result = new TestResult();
		
        suite.runTest(new JUnit4TestAdapter(c), result);
        
       	System.out.println("Was it successful?" + result.wasSuccessful());
        System.out.println("How many tests were there?" + result.runCount());
        
        return result.wasSuccessful();
	}*/
}