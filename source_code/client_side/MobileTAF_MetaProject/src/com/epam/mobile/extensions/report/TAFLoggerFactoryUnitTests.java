package com.epam.mobile.extensions.report;

import junit.framework.Assert;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TAFLoggerFactoryUnitTests {

	public static Logger logger = null;
	
	@Before
	public void beforeEachTest(){		
	}

	@BeforeClass
	public static void beforeAllTest(){
		// DOMConfigurator.configure("log4j.xml");
		logger = TAFLoggerFactory.getLogger(TAFLoggerFactoryUnitTests.class);
		logger.setLevel(Level.ALL);
		//		Logger.getLogger(LoggerUnitTests.class);
		//logger.addAppender(getFileAppender("output_file_log4j_unit_tests.txt"));
		//logger.setLevel(Level.ALL);
	}
	
	@After
	public void afterEachTest(){		
	}
	
	@AfterClass
	public static void afterAllTests(){
		logger = null;
		System.gc();
	}

	// specific test and logger set up: log all except demo by default.
	// to remove this option it should be deleted DemoLevelFilter
	@Ignore
	@Test
	public void logLevelAllExceptDemo(){
		String output_file_name = "output/output_file_log4j_unit_tests_" + new Object(){}.getClass().getEnclosingMethod().getName() + ".txt";
		logger.addAppender(TAFLoggerFactory.getFileAppender(output_file_name));
		logger.setLevel(Level.ALL);		
		
		logger.log(CustomLevels.DEMO_TRACE,"Log Level = [DEMO_TRACE], status = [LOGGED]");  
		logger.log(CustomLevels.FULL_DETAILED_TRACE,"Log Level = [FULL_DETAILED_TRACE], status = [LOGGED]");
		logger.log(Level.ALL ,"Log Level = [ALL], status = [LOGGED]");
		logger.log(Level.DEBUG ,"Log Level = [DEBUG], status = [LOGGED]");
		logger.log(Level.ERROR ,"Log Level = [ERROR], status = [LOGGED]");
		
		logger.log(Level.FATAL ,"Log Level = [FATAL], status = [LOGGED]");
		logger.log(Level.INFO ,"Log Level = [INFO], status = [LOGGED]");
		logger.log(Level.OFF ,"Log Level = [OFF], status = [LOGGED]");
		logger.log(Level.TRACE ,"Log Level = [TRACE], status = [LOGGED]");
		logger.log(Level.WARN ,"Log Level = [WARN], status = [LOGGED]");
		
		Analyzer analyzer = new Analyzer(output_file_name);
		Assert.assertFalse(analyzer.isExistPairOf("[LOG]", "[" + CustomLevels.DEMO_TRACE.toString() + "]", ""));
		Assert.assertTrue(analyzer.isExistPairOf("[LOG]", "[" + CustomLevels.FULL_DETAILED_TRACE.toString() + "]", ""));
		Assert.assertTrue(analyzer.isExistPairOf("[LOG]", "[" + Level.ALL.toString() + "]", ""));
		Assert.assertTrue(analyzer.isExistPairOf("[LOG]", "[" + Level.DEBUG.toString() + "]", ""));
		Assert.assertTrue(analyzer.isExistPairOf("[LOG]", "[" + Level.ERROR.toString() + "]", ""));
		
		Assert.assertTrue(analyzer.isExistPairOf("[LOG]", "[" + Level.FATAL.toString() + "]", ""));
		Assert.assertTrue(analyzer.isExistPairOf("[LOG]", "[" + Level.INFO.toString() + "]", ""));
		Assert.assertTrue(analyzer.isExistPairOf("[LOG]", "[" + Level.OFF.toString() + "]", ""));
		Assert.assertTrue(analyzer.isExistPairOf("[LOG]", "[" + Level.TRACE.toString() + "]", ""));
		Assert.assertTrue(analyzer.isExistPairOf("[LOG]", "[" + Level.WARN.toString() + "]", ""));
		analyzer.clear();
	}
	
	@Ignore
	@Test
	public void logLevelOff(){
		String output_file_name = "output/output_file_log4j_unit_tests_" + new Object(){}.getClass().getEnclosingMethod().getName() + ".txt";
		logger.addAppender(TAFLoggerFactory.getFileAppender(output_file_name));
		logger.setLevel(Level.OFF);
		
		logger.log(CustomLevels.DEMO_TRACE,"Log Level = [DEMO_TRACE], status = [LOGGED]");  
		logger.log(CustomLevels.FULL_DETAILED_TRACE,"Log Level = [FULL_DETAILED_TRACE], status = [LOGGED]");
		logger.log(Level.ALL ,"Log Level = [ALL], status = [LOGGED]");
		logger.log(Level.DEBUG ,"Log Level = [DEBUG], status = [LOGGED]");
		logger.log(Level.ERROR ,"Log Level = [ERROR], status = [LOGGED]");
		
		logger.log(Level.FATAL ,"Log Level = [FATAL], status = [LOGGED]");
		logger.log(Level.INFO ,"Log Level = [INFO], status = [LOGGED]");
		logger.log(Level.OFF ,"Log Level = [OFF], status = [LOGGED]");
		logger.log(Level.TRACE ,"Log Level = [TRACE], status = [LOGGED]");
		logger.log(Level.WARN ,"Log Level = [WARN], status = [LOGGED]");
		
		Analyzer analyzer = new Analyzer(output_file_name);
		Assert.assertFalse(analyzer.isExistPairOf("[LOG]", "[" + CustomLevels.DEMO_TRACE.toString() + "]", ""));
		Assert.assertFalse(analyzer.isExistPairOf("[LOG]", "[" + CustomLevels.FULL_DETAILED_TRACE.toString() + "]", ""));
		Assert.assertFalse(analyzer.isExistPairOf("[LOG]", "[" + Level.ALL.toString() + "]", ""));
		Assert.assertFalse(analyzer.isExistPairOf("[LOG]", "[" + Level.DEBUG.toString() + "]", ""));
		Assert.assertFalse(analyzer.isExistPairOf("[LOG]", "[" + Level.ERROR.toString() + "]", ""));
		
		Assert.assertFalse(analyzer.isExistPairOf("[LOG]", "[" + Level.FATAL.toString() + "]", ""));
		Assert.assertFalse(analyzer.isExistPairOf("[LOG]", "[" + Level.INFO.toString() + "]", ""));
		Assert.assertTrue(analyzer.isExistPairOf("[LOG]", "[" + Level.OFF.toString() + "]", ""));
		Assert.assertFalse(analyzer.isExistPairOf("[LOG]", "[" + Level.TRACE.toString() + "]", ""));
		Assert.assertFalse(analyzer.isExistPairOf("[LOG]", "[" + Level.WARN.toString() + "]", ""));
		analyzer.clear();
	}
	
	@Ignore
	@Test
	public void logLevelIfDemoOFFWhenInfoON(){
		String output_file_name = "output/output_file_log4j_unit_tests_" + new Object(){}.getClass().getEnclosingMethod().getName() + ".txt";
		//Appender appender = TAFLoggerFactory.getFileAppender(output_file_name);
		//appender.addFilter(new DemoLevelFilter());
		logger.addAppender(TAFLoggerFactory.getFileAppender(output_file_name));
		logger.setLevel(Level.INFO);
		
		//if (logger.getLevel() == CustomLevels.DEMO_TRACE)
		logger.log(CustomLevels.DEMO_TRACE,"Log Level = [DEMO_TRACE], status = [LOGGED]");  
		
		logger.log(Level.INFO ,"Log Level = [INFO], status = [LOGGED]");
		
		Analyzer analyzer = new Analyzer(output_file_name);
		Assert.assertFalse(analyzer.isExistPairOf("[LOG]", "[" + CustomLevels.DEMO_TRACE.toString() + "]", ""));
		Assert.assertTrue(analyzer.isExistPairOf("[LOG]", "[" + Level.INFO.toString() + "]", ""));
		analyzer.clear();
	}
	
	@Ignore
	@Test
	public void logLevelIfDemoON(){
		String output_file_name = "output/output_file_log4j_unit_tests_" + new Object(){}.getClass().getEnclosingMethod().getName() + ".txt";
		//Appender appender = TAFLoggerFactory.getFileAppender(output_file_name);
		//appender.addFilter(new DemoLevelFilter());
		logger.addAppender(TAFLoggerFactory.getFileAppender(output_file_name));
		logger.setLevel(CustomLevels.DEMO_TRACE);
		//logger.
		
		//if (logger.getLevel() == CustomLevels.DEMO_TRACE)
		logger.log(CustomLevels.DEMO_TRACE,"Log Level = [DEMO_TRACE], status = [LOGGED]");  
		
		logger.log(Level.INFO ,"Log Level = [INFO], status = [LOGGED]");
		
		Analyzer analyzer = new Analyzer(output_file_name);
		Assert.assertTrue(analyzer.isExistPairOf("[LOG]", "[" + CustomLevels.DEMO_TRACE.toString() + "]", ""));
		Assert.assertFalse(analyzer.isExistPairOf("[LOG]", "[" + Level.INFO.toString() + "]", ""));
		analyzer.clear();
	}
	
	@Ignore
	@Test
	public void logLevelDemo(){		
	}
}
