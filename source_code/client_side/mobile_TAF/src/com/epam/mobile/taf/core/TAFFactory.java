package com.epam.mobile.taf.core;

import com.epam.mobile.extensions.testdata.TestDataConstants;
import com.epam.mobile.extensions.testdata.TestDataFactory;
import com.epam.mobile.extensions.testdata.implementations.DerbyTestDataFactory;
import com.epam.mobile.extensions.testdata.implementations.MySQLTestDataFactory;
import com.epam.mobile.extensions.testdata.implementations.PropertiesFileTestDataFactory;
import com.epam.mobile.extensions.testdata.implementations.TXTTestDataFactory;

public class TAFFactory {

	private static TAFFactory instance;
	
	private String defaultTDFactory = TestDataConstants.TXT_TEST_DATA;
	
	private TAFFactory() throws Exception {
	}

	public static TAFFactory getInstance() {
		return instance;
	}

	static {
		try {
			instance = new TAFFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	// get TestDataFactory
	public TestDataFactory createTDFactory(String type){
		
		if (type.equalsIgnoreCase(TestDataConstants.TXT_TEST_DATA))
			return TXTTestDataFactory.getInstance();
		if (type.equalsIgnoreCase(TestDataConstants.DERBY_TEST_DATA))
			return DerbyTestDataFactory.getInstance();
		if (type.equalsIgnoreCase(TestDataConstants.MYSQL_TEST_DATA))
			return MySQLTestDataFactory.getInstance();
		if (type.equalsIgnoreCase(TestDataConstants.PROPERTIES_FILE_TEST_DATA))
			return PropertiesFileTestDataFactory.getInstance();
		
		return defaultTDFactory();			
	}
	
	private TestDataFactory defaultTDFactory() {
		return getInstance().createTDFactory(defaultTDFactory);
	}

	// get DBFactory
	
	public String getDefaultTDFactory() {
		return defaultTDFactory;
	}

	public void setDefaultTDFactory(String defaultTDFactory) {
		this.defaultTDFactory = defaultTDFactory;
	}	
}
