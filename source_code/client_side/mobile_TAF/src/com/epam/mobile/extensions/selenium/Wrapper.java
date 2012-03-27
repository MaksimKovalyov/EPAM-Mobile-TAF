package com.epam.mobile.extensions.selenium;

public interface Wrapper {

	public void start();
	public void stop();
	public void closeBrowserWindow();
	public void runServer();
	public void stopServer();
	public boolean runGoogleTest();
	
}
