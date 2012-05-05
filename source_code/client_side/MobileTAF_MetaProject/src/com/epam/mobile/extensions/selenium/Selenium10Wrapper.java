package com.epam.mobile.extensions.selenium;

import org.openqa.selenium.server.SeleniumServer;
import com.thoughtworks.selenium.DefaultSelenium;

public class Selenium10Wrapper implements Wrapper{
	private DefaultSelenium seleniumDriver = null;
	private SeleniumServer seleniumServer;
	
	public Selenium10Wrapper() {
		seleniumDriver = new DefaultSelenium("127.0.0.1", 
											  4444,
											  "iexplore",
											  "http://google.com");
		try{
			seleniumServer = new SeleniumServer();
		}catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public Selenium10Wrapper(DefaultSelenium driver_) {
		seleniumDriver = driver_;
		//this.start();
	}	
	
	public DefaultSelenium getDriver() {
		return seleniumDriver;
	}
	
//	public Decorator getDecoratedSelenium() {
//		return seleniumDriver;
//	}

	// add Logger
	public void start() {
		seleniumDriver.start();
		System.out.println("[LOG] Selenium starts...");
	}

	public void pause() {
		System.out.println("[LOG] Selenium has been paused...");
		//seleniumDriver.stop();
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		//selenium.start();
	}
		
	public void stop() {
		this.closeBrowser();
		seleniumDriver.stop();
		System.out.println("[LOG] Selenium has been stopped...");
	}
	
	public void stopServer() {
		// TODO Auto-generated method stub
		
	}
	
	public void runServer() {
		try {
			seleniumServer.start();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		//ScriptExecutor.getInstance().run(Scripts.Run_SeleniumServer10);
	}
	
	public boolean runGoogleTest() {
		boolean result = false;
		
		seleniumDriver.open("http://google.com");
		String title = seleniumDriver.getTitle();
		System.out.println("Actual result:     The title of page: " + title);
		System.out.println("Expected result:   The title of page: Google");

		if (title.equalsIgnoreCase("Google"))
			result = true;
		System.out.println("Comparison result: " + "              >>>> " + result + " <<<<");
		
		return result;
	}
	
	public void runServer_() {
		//ScriptExecutor.getInstance().initStreams(Scripts.Run_SeleniumServer10);
	}
	
	public void closeBrowser() {
		seleniumDriver.close();
		System.out.println("[LOG] Browser is closed.");
	}
	
	public static void main(String[] args) {
		// mini-test for selenium server running
		Selenium10Wrapper selenium = new Selenium10Wrapper();
		selenium.runServer();
		selenium.pause();
		selenium.start();
		selenium.pause();
		selenium.runGoogleTest();
		selenium.stop();	
	}
}