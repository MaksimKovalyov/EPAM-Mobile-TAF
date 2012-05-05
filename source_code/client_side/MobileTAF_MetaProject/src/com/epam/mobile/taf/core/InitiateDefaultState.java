package com.epam.mobile.taf.core;

import java.util.HashMap;

import com.epam.mobile.extensions.testdata.TestDataConstants;

// TODO: make one Constants file for all project
public class InitiateDefaultState implements Strategy{

	private HashMap<String, String> config = new HashMap<String, String>();
	
	@Override
	public boolean execute() {
		TAFFactory.getInstance().setDefaultTDFactory(config.get(TestDataConstants.TD_FACTORY));
		
		return false;
	}

	@Override
	public void setUp(String configuration) {
		// read config 
		config.put("configFile", configuration);	
	}
}
