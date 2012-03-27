package com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.foundation;

import com.epam.mobile.driver.core.NativeDriver;
import com.epam.mobile.driver.core.iPhoneNativeDriver;

public class NativeDriverContext {
    public static final String APP_iOS        = "ios";
    public static final String APP_ANDOIRD    = "android";
    public static final String APP_BLACKBERRY = "blackberry";

    private static NativeDriverContext context;
    private static String appURL;
    private static String activeEnvironment;    	
    
    //private Selenium browser;
    //private SeleniumServer seleniumServer;

    private NativeDriver driver;
    
    private NativeDriverContext() {
    }

    public static void initInstance(String appType_, String appURL_) {
        context = new NativeDriverContext();
        appURL  = appURL_;
        activeEnvironment = appType_;
        
        //context.setBrowser(new DefaultSelenium("localhost", 4444, appType, appURL));
        context.initDriver();
        context.start();
    }

    public static NativeDriverContext getInstance() {
        if (context == null) {
            throw new IllegalStateException("Context is not initialized");
        }
        return context;
    }

    public NativeDriver getDriver() {
        if (driver != null) {
        	// optional:
        	// take screenshot before each operation;
        	/*
        	try {
            	driver.takeScreenShot();
			} catch (Exception e) {
				System.out.println("[ERROR] Screenshot is not captured.");
			}
			*/
            
        	return driver;
        }
        throw new IllegalStateException("Native Driver is not initialized");
    }

    public String getAppURL() {
       return appURL;
    }

    public void start() {
        try {
        	boolean result = driver.isServerAlive();
        	if (result) {
				System.out.println("[LOG] Driver has been started.");
			}else {
				throw new IllegalStateException("Native Driver is not started.");
			}
            //seleniumServer = new SeleniumServer();
            //seleniumServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //driver.start();
    }

    public void close() {
        //driver.close();
    }

    public void setDriver(NativeDriver driver) {
        this.driver = driver;
    }
    
    public void initDriver() {
        NativeDriver driver_ = null;
        
        if (activeEnvironment.equalsIgnoreCase(APP_iOS)){
        	driver_ = new iPhoneNativeDriver(appURL);
        }
        // stub implementation
        if (activeEnvironment.equalsIgnoreCase(APP_ANDOIRD)){
        	//driver_ = new AndroidDriver(appURL);
        }
        if (activeEnvironment.equalsIgnoreCase(APP_BLACKBERRY)){
        	//driver_ = new BlakcBerryDriver(appURL);
        }
        
        setDriver(driver_);
    }
}