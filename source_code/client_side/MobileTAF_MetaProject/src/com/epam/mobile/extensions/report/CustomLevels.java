package com.epam.mobile.extensions.report;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class CustomLevels extends Level {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5275500487601517412L;
	
	public static final int FULL_DETAILED_TRACE_INT = TRACE_INT - 5001;
	public static final int DEMO_TRACE_INT = FATAL_INT + 50001;

	public static final Level FULL_DETAILED_TRACE = new CustomLevels(FULL_DETAILED_TRACE_INT,
			"FULL_DETAILED_TRACE", 7);
	
	public static final Level DEMO_TRACE = new CustomLevels(DEMO_TRACE_INT,
			"DEMO_TRACE", 8);

	protected CustomLevels(int arg0, String arg1, int arg2) {
		super(arg0, arg1, arg2);
	}

	public static Level toLevel(String sArg) {
		Level result = (Level) toLevel(sArg, Level.DEBUG);
		
		if (sArg != null && sArg.toUpperCase().equals("DEMO_TRACE")) {
			result = DEMO_TRACE;
		}	
		if (sArg != null && sArg.toUpperCase().equals("FULL_DETAILED_TRACE")) {
			result = FULL_DETAILED_TRACE;
		}
		
		return result;
	}

	public static Level toLevel(int val) {
		Level result = (Level) toLevel(val, Level.DEBUG);
		
		if (val == DEMO_TRACE_INT) {
			result = DEMO_TRACE;
		}
		if (val == FULL_DETAILED_TRACE_INT) {
			result = FULL_DETAILED_TRACE;
		}
		
		return result;
	}

	public static Level toLevel(int val, Level defaultLevel) {
		Level result = Level.toLevel(val, defaultLevel);
		
		if (val == DEMO_TRACE_INT) {
			result = DEMO_TRACE;
		}
		if (val == FULL_DETAILED_TRACE_INT) {
			result = FULL_DETAILED_TRACE;
		}
		
		return result;
	}

	public static Level toLevel(String sArg, Level defaultLevel) {
		Level result = Level.toLevel(sArg, defaultLevel);
		
		if (sArg != null && sArg.toUpperCase().equals("DEMO_TRACE")) {
			result = DEMO_TRACE;
		}
		if (sArg != null && sArg.toUpperCase().equals("FULL_DETAILED_TRACE")) {
			result = FULL_DETAILED_TRACE;
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		DOMConfigurator.configure("C:/log/log4j.xml");
		Logger logger = Logger.getLogger(CustomLevels.class.getClass().getName()); 
		
		//logger.log(LoggerLevels.DEMO_TRACE,"I am FULL_DETAILED_TRACE log");  
		logger.log(Level.DEBUG ,"I am a debug message");  
	}
}