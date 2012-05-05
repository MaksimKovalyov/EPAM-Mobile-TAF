package com.epam.mobile.taf.core;

public interface Strategy {
	
	public void setUp(String configuration);
	public boolean execute();
	
}
