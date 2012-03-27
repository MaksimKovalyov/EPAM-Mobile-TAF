package com.epam.mobile.extensions.testdata.implementations;

import com.epam.mobile.extensions.testdata.TestDataConstants;
import com.epam.mobile.extensions.testdata.TestData;

public class DerbyTestData extends TestData{
	
	public DerbyTestData() {
		IMPLEMENTATION_TYPE = TestDataConstants.DERBY_TEST_DATA;
		
		System.out.println(IMPLEMENTATION_TYPE + " is created.");
	}

	public void findValue() {
		String value = "";
		// find value TODO:
		
		this.setValue(value);
	}

	public String getValue(String key) {
		this.setKey(key);
		findValue();
		
		return this.getValue();
	}

}
