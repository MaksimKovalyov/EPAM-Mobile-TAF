package com.epam.mobile.testing.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebNavigator{

	private static WebDriver driver = new FirefoxDriver();
//	private static Wrapper wrappedDriver = new Selenium20Wrapper(driver);
//	private static final int timeout = 3000;
	
	private static final String username = "username";
	private static final String password = "password";
	
	public static class LoginScreen {
		
		public static void login(){
			try {
				driver.findElement(By.id(WebMap.LoginPage.username_textfield)).sendKeys(username);
				driver.findElement(By.id(WebMap.LoginPage.password_textfield)).sendKeys(password);
				driver.findElement(By.id(WebMap.LoginPage.login_button)).click();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		public static String[] getLoginScreen(){
			
			// todo
			
			return null;
		}
		
		public static void main(String[] args) {
			WebNavigator.LoginScreen.login();
		}
	}
}
	