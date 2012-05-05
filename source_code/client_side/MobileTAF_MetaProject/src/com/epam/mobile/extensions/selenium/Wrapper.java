package com.epam.mobile.extensions.selenium;

public interface Wrapper {

	public void start();
	public void stop();
	public void closeBrowser();
	public void runServer();
	public void stopServer();
	public boolean runGoogleTest();
	
}
