package com.epam.mobile.extensions.report;

import java.io.OutputStreamWriter;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

@SuppressWarnings("unchecked")
public class TAFLoggerFactory{

	// reduced factory:
	//private static TAFLoggerFactory instance;
	private static Logger logger = null;
	private static Level defaultLevel = Level.ALL;
	private static String defaultPath = "output/" + "taf_log.txt";
		
	public TAFLoggerFactory(String className) {
		logger = Logger.getLogger(className);
		
		init();
	}

	public TAFLoggerFactory(Class clazz) {
		logger = Logger.getLogger(clazz);
		
		init();
	}
	
	public static Logger getLogger(){
		init();
		
		return logger;
	}
	
	public static Logger getLogger(Class clazz){
		logger = Logger.getLogger(clazz);

		init();
		
		return logger;
	} 
	 
	private static void init(){
		//logger = Logger.getLogger(TestTexture.class);
		logger.setAdditivity(false);
		logger.removeAllAppenders();
		
		defaultLevel = Level.ALL;
		defaultPath = "output/" + "taf_log.txt";
		
		// add Console appender
		ConsoleAppender ca = new ConsoleAppender();
		
		ca.setWriter(new OutputStreamWriter(System.out));
		ca.setLayout(new PatternLayout("%-5p [%t]: %m%n"));
		ca.setName("TAF Console Appender.");
		ca.activateOptions();
		logger.addAppender(ca);
		
		logger.setLevel(defaultLevel);
		logger.addAppender(getFileAppender());
				
		// set default time zone for Belarus
		DateUtils.setBelarusTimeZone();
	}
	
	public void setPath(String path_to_file) {
		defaultPath = path_to_file;		
		//initFileIOStreams();		
	}
	
	public String getPath() {
		return defaultPath;				
	}
	
	public static FileAppender getFileAppender(String path_to_file){
		defaultPath = path_to_file;
		
		return getFileAppender();
	}
	
	public static FileAppender getFileAppender(){
		// Note, %n is newline
	    String pattern = "";//pattern =  "Milliseconds since program start: %r %n";
	             //pattern += "Classname of caller: %C %n";
	             //pattern += "Date in ISO8601 format: %d{ISO8601} %n";
	             pattern = DateUtils.now() + "[LOG][%-3p] %m %n";
	    		 //pattern += "Location: %l %n %n";
	             //pattern += "[LOG][%-3p] %m %n %n"; 
	      
	    PatternLayout layout = new PatternLayout(pattern);  
		FileAppender appender = null;
		try {
			appender = new FileAppender(layout, 
								defaultPath,
								false);
			
			// remove all demo log from main log thread
			// log demo level only when this level is set up directly
			appender.addFilter(new DemoLevelFilter());
		} catch(Exception e) {
			System.out.println(e.toString());
		}
		
		return appender;
	}
	
	public static void printAllLevelsCodes(){
		System.out.println("----------------------------------------------------------");
		System.out.println("level DEMO: " + CustomLevels.DEMO_TRACE_INT);
		System.out.println("level FULL_DETAILED: " + CustomLevels.FULL_DETAILED_TRACE_INT);
		System.out.println("level ALL: " + Level.ALL_INT);
		System.out.println("level DEBUG: " + Level.DEBUG_INT);
		System.out.println("level ERROR: " + Level.ERROR_INT);
		System.out.println("level FATAL: " + Level.FATAL_INT);
		System.out.println("level INFO: " + Level.INFO_INT);
		System.out.println("level OFF: " + Level.OFF_INT);
		System.out.println("level TRACE: " + Level.TRACE_INT);
		System.out.println("level WARN: " + Level.WARN_INT);
		System.out.println("----------------------------------------------------------");
	}
}
