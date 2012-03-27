package com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.design.screens;

import com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.foundation.AbstractScreen;
import com.epam.mobile.testing.utils.UIMap;

public class FirstLoginScreen extends AbstractScreen<FirstLoginScreen> {
	   public static final String SCREEN_LOCATOR = UIMap.FirstLoginScreen.view;
	   
	   protected FirstLoginScreen() {
	       super(SCREEN_LOCATOR);
	   }

	   public static LoginScreen tapLoginButton() {
	       FirstLoginScreen screen = new FirstLoginScreen();
	       screen.getDriver().touch(UIMap.FirstLoginScreen.login_button);
	       
	       return new LoginScreen();
	   }

	   public static HomeScreen tapPublicLoginButton() {
	       FirstLoginScreen screen = new FirstLoginScreen();
	       screen.getDriver().touch(UIMap.FirstLoginScreen.publiclogin_button);
	       
	       return new HomeScreen();
	   }

	   
	   protected void parsePage() {
	       // 
	       // 
	   }

	   protected void init() {
	       // 
	       //if(!getDriver().getLocation().equals(SCREEN_LOCATOR)) {
	       //    throw new IllegalStateException("Invalid page is opened");
	       //}
	       // 
	       // 
	   }
}
