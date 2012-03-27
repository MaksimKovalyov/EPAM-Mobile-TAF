package com.epam.mobile.extensions.testdata;

public final class TestDataConstants {

	// Test Data types:
	public static final String TXT_TEST_DATA             = "TXT Test Data";
	public static final String DERBY_TEST_DATA           = "Derby Test Data";
	public static final String MYSQL_TEST_DATA           = "MySQL Test Data";
	public static final String PROPERTIES_FILE_TEST_DATA = "Properties file Test Data";
	
	// MySQL default settings:
	public static final String MYSQL_DB_NAME        = "mysql_test_data";
	public static final String MYSQL_DB_TABLE_NAME  = "td_table";
	public static final String MYSQL_DB_VERSION_KEY = "version";
	
	// config file properties:
	public static final String TD_FACTORY = "TFFactory";
	
	
	
	// PRIVATE //

	  /**
	   The caller references the constants using <tt>Consts.EMPTY_STRING</tt>, 
	   and so on. Thus, the caller should be prevented from constructing objects of 
	   this class, by declaring this private constructor. 
	  */
	private TestDataConstants(){
	    //this prevents even the native class from 
	    //calling this ctor as well :
	    throw new AssertionError();
	  }
}
