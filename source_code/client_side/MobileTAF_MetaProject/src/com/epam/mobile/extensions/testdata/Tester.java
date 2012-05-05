package com.epam.mobile.extensions.testdata;

import com.epam.mobile.extensions.testdata.implementations.TXTTestDataFactory;
import com.epam.mobile.taf.core.TAFFactory;

public class Tester {
	private static String TEST_DATA_SOURCE = null;  
	
	public static void main(String[] args) {
		//Tester tester = new Tester();
		//TestDataFactory factory = tester.createFactory(); 
		//TestDataFactory factory_ = TAFFactory.getInstance().createTDFactory(Constants.TXT_TEST_DATA);
		
		//TestData test_data = factory.create();
		//TestData td = factory_.create();
		//td.getValue("");
		TestData td = TAFFactory.getInstance().createTDFactory(TestDataConstants.PROPERTIES_FILE_TEST_DATA).create();
		TestData td_ = TAFFactory.getInstance().createTDFactory(TestDataConstants.TXT_TEST_DATA).create();
		String version = td.getValue("version");
		System.out.println("td.version=" + version);
		version = td_.getValue("version");
		System.out.println("td_.version=" + version);

//		TestData td = TAFFactory.getInstance().createTDFactory(TestDataConstants.MYSQL_TEST_DATA).create();
//		String version = td.getValue("version");
		
		System.out.println("version from DB: " + version);
	}

	@SuppressWarnings("unused")
	private TestDataFactory createFactory() {
		// read from config the type of Test Data implementation
		// or set it manually when it is creating a new instance of factory
		// add creating factory from singleton by string type of source
		
		// stub reading
		TEST_DATA_SOURCE = "TXT Test Data";
		
		if (TEST_DATA_SOURCE.equalsIgnoreCase(TestDataConstants.TXT_TEST_DATA))
			return TXTTestDataFactory.getInstance();
		
		return defaultFactory();				
	}
	
	private TestDataFactory defaultFactory() {
		return TXTTestDataFactory.getInstance();
	}	
}
