package com.epam.mobile.extensions.testdata.implementations;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.epam.mobile.extensions.testdata.TestData;
import com.epam.mobile.extensions.testdata.TestDataFactory;

public class PropertiesFileTestDataFactory implements TestDataFactory{

	private static final String DEFAULT_PROPERTIES_FILE = "properties_file_test_data.properties";
	private static String PROPERTIES_FILE;
	
	private Properties properties = null;
	
	private static PropertiesFileTestDataFactory instance;
	
	private PropertiesFileTestDataFactory() throws Exception {
	}

	public static PropertiesFileTestDataFactory getInstance() {
		return instance;
	}

	static {
		try {
			instance = new PropertiesFileTestDataFactory();
		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(-1);
		}
	}	
	
	public TestData create() {
		PROPERTIES_FILE = DEFAULT_PROPERTIES_FILE;
		initialization();
		
		return new PropertiesFileTestData(properties);
	}

	public TestData create(String tdObjectPath) {
		PROPERTIES_FILE = tdObjectPath;
		initialization();
		
		return new PropertiesFileTestData(properties);
	}

	
	public TestData read() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public TestData write(String dataLine) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void initialization(){
		Properties properties = new Properties();
		try {
		    properties.load(new FileInputStream(PROPERTIES_FILE));
		    this.properties = properties;
		} catch (IOException e) {
			System.err.println("[ERROR] Properties file isn't load.");
			System.err.println("[ERROR] Properties file path: " + PROPERTIES_FILE);
		}
	}
}
