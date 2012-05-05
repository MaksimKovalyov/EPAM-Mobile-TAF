package com.epam.mobile.exampleproject.tests.architecture.configuration;

public final class Configuration {

	// properties naming 
	// (e.g. config.properties: SERVER_URL = http://localhost:37265/)

	public static final String DRIVER_URL  = "DRIVER_URL";
	public static final String SERVER_URL  = "SERVER_URL";
	
	public static final String CONFIG_FILE = "config/config.properties";
		
	
	// PRIVATE //

	  /**
	   The caller references the constants using <tt>Consts.EMPTY_STRING</tt>, 
	   and so on. Thus, the caller should be prevented from constructing objects of 
	   this class, by declaring this private constructor. 
	  */
	private Configuration(){
	    //this prevents even the native class from 
	    //calling this ctor as well :
	    throw new AssertionError();
	  }
}
