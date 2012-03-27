package com.epam.mobile.extensions.testdata.implementations;

import java.util.Properties;

import com.epam.mobile.extensions.testdata.TestDataConstants;
import com.epam.mobile.extensions.testdata.TestData;

public class PropertiesFileTestData extends TestData{

	private static Properties properties = null;
	
	public PropertiesFileTestData(Properties props) {
		IMPLEMENTATION_TYPE = TestDataConstants.PROPERTIES_FILE_TEST_DATA;
		properties = props;
		
		if (props != null)
			System.out.println("[LOG] " + IMPLEMENTATION_TYPE + " is created.");
		else
			System.err.println("[ERROR] properties is null.");
		//String version = (String)properties.getProperty("version", "1.0");
		//System.out.println("Test Data version: " + version);
	}
	
	public void findValue() {
		String value = (String)properties.getProperty(this.getKey(), "ValueNotFound");
		this.setValue(value);
	}
	
	public String getValue(String key) {
		this.setKey(key);

		findValue();
		
		return getValue();
	}
	
}
