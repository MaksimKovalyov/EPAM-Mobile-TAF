package com.epam.mobile.driver.core;

public final class Constants {

	// command structure
	public static final String OPERATION               = "Operation";
	public static final String PARAMS                  = "Params";	
	public static final String CLASS_NAME              = "ClassName";
	public static final String CLASS_NAME_CHAIN        = "ClassNameChain";
	public static final String ATTRIBUTE_NAME          = "AttributeName";
	public static final String ATTRIBUTE_VALUE         = "AttributeValue";
	public static final String VALUE                   = "Value";
	public static final String ACTION                  = "Action";
	public static final String TIMEOUT                 = "Timeout";
	public static final String ELEMENT_INDEX           = "ElementIndex";
	
	// operation types
	public static final String SET_ELEMENT_VALUE       = "setElementValue";
	public static final String GET_ELEMENT_VALUE       = "getElementValue";
	public static final String GET_ELEMENT             = "getElement";
	public static final String DESCRIBE                = "describe";
	public static final String DO_ACTION               = "doAction";
	
	// server commands
	public static final String RUN_SCRIPT              = "run_script";
	public static final String SIMULATE_MEMORY_WARNING = "simulate_memory_warning";
	public static final String PING_SERVER             = "ping_server";
	public static final String TEST_COMMAND            = "test_command";
	public static final String TAKE_SCREENSHOT         = "take_screenshot";
	
	// server options
	public static final String LOCALHOST_URL           = "http://localhost:37265/";
	public static final String REMOTEHOST_URL          = "http://10.143.24.206:37265/";
	
	// server response keys
	public static final String COMMAND_STATUS          = "test_command";
	public static final String DATA                    = "data";
	public static final String RESULT                  = "result";
	public static final String RESULT_VALUE            = "value";
	public static final String RESULT_PROXY            = "proxy";

	// time intervals
	public static final String DAY                     = "1D";
	public static final String DAYS_2                  = "2D";
	public static final String DAYS_5                  = "5D";
	public static final String MONTH                   = "1M";
	public static final String MONTHS_3                = "3M";
	public static final String MONTHS_6                = "6M";
	public static final String YEAR                    = "1Y";
	public static final String YEARS_3                 = "3Y";
	public static final String YEARS_5                 = "5Y";
	
	//server errors
	public static final String VALUE_NOT_FOUND         = "The element value is not found.";
	public static final String ELEMENT_NOT_FOUND       = "The element is not found.";
	public static final String COMMAND_NOT_VALID       = "The command is not valid. Please, see details info: ";
	// add to separate file
	// scroll actions:
	public static final String UI_SCROLL_DOWN          = "scrollDown";
	public static final String UI_SCROLL_UP            = "scrollUp";
	public static final String UI_SCROLL_LEFT          = "scrollLeft";
	public static final String UI_SCROLL_RIGHT         = "scrollRight";
	public static final String UI_SCROLL_RIGHT_SHORT   = "scrollRightShort";

	public static final String CONTENT_POSITION        = "contentPosition";
	public static final String IS_SCROLL_ENABLED       = "isScrollEnabled_";
	
	public static final String TOUCH                   = "touch";
	public static final String FLASH                   = "flash";
	public static final String WAIT                    = "waitFor";
	
	// app modal 
	public static final String FULL                    = "Full";
	public static final String EMPTY                   = "Empty";
	
	// PRIVATE //

	  /**
	   The caller references the constants using <tt>Consts.EMPTY_STRING</tt>, 
	   and so on. Thus, the caller should be prevented from constructing objects of 
	   this class, by declaring this private constructor. 
	  */
	private Constants(){
	    //this prevents even the native class from 
	    //calling this ctor as well :
	    throw new AssertionError();
	  }
}
