package com.epam.mobile.extensions.report;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

@SuppressWarnings("unchecked")
public class TAFLoggerFactory{

	//private static TAFLoggerFactory instance;
	
	private static Logger logger = null;
	private static Level defaultLevel = null;
	private static String defaultPath = null;
		
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
		defaultLevel = Level.ALL;
		defaultPath = "output/" + "taf_log.txt";
		
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
}
