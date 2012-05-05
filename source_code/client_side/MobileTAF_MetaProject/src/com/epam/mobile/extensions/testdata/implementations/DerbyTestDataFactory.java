package com.epam.mobile.extensions.testdata.implementations;

import com.epam.mobile.extensions.testdata.TestData;
import com.epam.mobile.extensions.testdata.TestDataFactory;

public class DerbyTestDataFactory implements TestDataFactory{
	
	private static DerbyTestDataFactory instance;
	
	private DerbyTestDataFactory() throws Exception {
	}

	public static DerbyTestDataFactory getInstance() {
		return instance;
	}

	static {
		try {
			instance = new DerbyTestDataFactory();
		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(-1);
		}
	}	
	
	public TestData create() {
		return new DerbyTestData();
	}

	public TestData create(String tdObjectPath) {
		return new DerbyTestData();
	}
	
	public TestData read() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public TestData write(String dataLine) {
		// TODO Auto-generated method stub
		return null;
	}
}
