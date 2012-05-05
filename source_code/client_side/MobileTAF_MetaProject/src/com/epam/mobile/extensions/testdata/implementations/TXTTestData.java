package com.epam.mobile.extensions.testdata.implementations;

import java.util.HashMap;

import com.epam.mobile.extensions.testdata.TestDataConstants;
import com.epam.mobile.extensions.testdata.TestData;

public class TXTTestData extends TestData{
	//private static final String IMPLEMENTATION_SOURCE = Constants.TXT_TEST_DATA;
	
	private HashMap<String, String> data = new HashMap<String, String>();
	
	public TXTTestData(HashMap<String, String> data) {
		IMPLEMENTATION_TYPE = TestDataConstants.TXT_TEST_DATA;
		this.data = data;
		
		System.out.println(IMPLEMENTATION_TYPE + " is created.");
		String version = (String)data.get("version");
		System.out.println("Test Data version: " + version);
	}
	
	public void findValue() {
		String value = (String)data.get(this.getKey());
		this.setValue(value);
	}
	
	public String getValue(String key) {
		this.setKey(key);

		findValue();
		
		return getValue();
	}
}