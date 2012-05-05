package com.epam.mobile.exampleproject.tests.architecture.configuration;

import com.epam.mobile.extensions.testdata.TestData;
import com.epam.mobile.extensions.testdata.TestDataConstants;
import com.epam.mobile.taf.core.TAFFactory;

public class Configurator {	

	private static TestData data = TAFFactory.getInstance().createTDFactory(TestDataConstants.PROPERTIES_FILE_TEST_DATA).create(Configuration.CONFIG_FILE);
	
	private static Configurator instance;
	
	private Configurator() throws Exception {
	}

	public static Configurator getInstance() {
		return instance;
	}

	static {
		try {
			instance = new Configurator();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}	
	
	public String getDriverURL(){
		return data.getValue(Configuration.DRIVER_URL);
	}
	
	public String getServerURL(){
		return data.getValue(Configuration.SERVER_URL);
	}
}
