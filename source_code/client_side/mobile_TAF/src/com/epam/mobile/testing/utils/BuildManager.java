package com.epam.mobile.testing.utils;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.epam.mobile.extensions.selenium.Selenium10Wrapper;
import com.thoughtworks.selenium.DefaultSelenium;

public class BuildManager {

	private String username = "";
	private String password = "";
	private String build_system_url = "http://mail.tut.by";
//	private String build_dir_url = "link_to_build_dir";
//	private String build_link = "";
	
	private Selenium10Wrapper selenium = new Selenium10Wrapper();
	private DefaultSelenium seleniumEntity = null;
	
	// possible options: 
	// - download from svn via shell script with autorization;
	// - download from web site with autorization;
	// - copy on the local machine from one dir to another;
	// - generate from sources;
	public void download(){
		if (isWebPageOpen()) {
			getBuildViaWeb();
		}else{
			generateFailReport();
		}
	}
	
	private void init(){
		seleniumEntity = selenium.getDriver();
		seleniumEntity.open(build_system_url);
	}
	
	private void getBuildViaWeb(){
		init();
		login();
		openBuildDir();
		downloadBuild();
	}
	
	private void login(){
		System.out.println("[LOG] The user:" + username + " starts login.");
		seleniumEntity.type("id=username", username);
		seleniumEntity.type("id=password", password);
		seleniumEntity.click("link=ttttt");
	}
	
	private void openBuildDir(){
		
	}

	private void downloadBuild(){
	
	}
	
	private void generateFailReport(){
		System.out.println("Selenium server doesn't work.");
	}
	
	private boolean isWebPageOpen(){
		selenium.start();
		selenium.pause();
		
		return selenium.runGoogleTest();
	}
	
	public void clickEnter(){
		try {
			Robot robot = new Robot();
	        robot.keyPress(KeyEvent.VK_ENTER);
	        robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static void main(String[] args) {
		BuildManager manager = new BuildManager();
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		manager.clickEnter();
		//manager.download();
	}
}
