package com.epam.mobile.extensions.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.server.SeleniumServer;

public class Selenium20Wrapper implements Wrapper{

	private WebDriver driver;
	private SeleniumServer server;
	
	public WebDriver getDriver() {
		return driver;
	}

	public Selenium20Wrapper() {
	    // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        driver = new FirefoxDriver();
	}
	
	public Selenium20Wrapper(WebDriver driver_) {
	    // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        this.driver = driver_;
	}
	
	public boolean runGoogleTest() {
		boolean result = false;
		
        // And now use this to visit Google
        driver.get("http://www.google.com");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        // Find the text input element by its name
        String title = driver.getTitle();
		System.out.println("Actual result:     The title of page: " + title);
		System.out.println("Expected result:   The title of page: Google");

		if (title.equalsIgnoreCase("Google"))
			result = true;
		System.out.println("Comparison result: " + "              >>>> " + result + " <<<<");

		return result;
	}
	
	public void closeBrowser() {
		driver.close();
		System.out.println("[LOG] Browser is closed.");
	}
	
	public void runServer() {
		//ScriptExecutor.getInstance().run(Scripts.Run_SeleniumServer20);
		try {
			server.start();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void stopServer() {
		// TODO Auto-generated method stub
		
	}
	
	public void start() {
		// ...
		System.out.println("[LOG] Selenium starts...");
	}
	
	public void stop() {
		driver.quit();
		this.closeBrowser();
		System.out.println("[LOG] Selenium has been stopped...");
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
	
	public static void main(String[] args) {
		Selenium20Wrapper selenium = new Selenium20Wrapper();
		selenium.runServer();
		selenium.pause();
		selenium.start();
		selenium.pause();
		selenium.runGoogleTest();
		selenium.stop();		
	}
}
