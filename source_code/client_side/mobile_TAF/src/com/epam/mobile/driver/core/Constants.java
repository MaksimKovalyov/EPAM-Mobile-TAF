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

	
	//server errors
	public static final String VALUE_NOT_FOUND         = "ValueNotFound";

	// add to separate file
	// scroll actions:
	public static final String UI_SCROLL_DOWN          = "scrollDown";
	public static final String UI_SCROLL_UP            = "scrollUp";
	public static final String UI_SCROLL_LEFT          = "scrollLeft";
	public static final String UI_SCROLL_RIGHT         = "scrollRight";
	public static final String UI_SCROLL_RIGHT_SHORT   = "scrollRightShort";
	
	public static final String TOUCH                   = "touch";
	public static final String FLASH                   = "flash";

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
