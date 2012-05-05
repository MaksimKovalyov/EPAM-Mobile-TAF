package com.epam.mobile.exampleproject.tests.architecture.design.screens;

import com.epam.mobile.exampleproject.tests.architecture.foundation.AbstractScreen;
import com.epam.mobile.testing.utils.UIMap;

/**
 * Created by IntelliJ IDEA.
 * User: ab83625
 * Date: 10.11.2010
 * To change this template use File | Settings | File Templates.
 */
public class LoginScreen extends AbstractScreen<LoginScreen> {
   public static final String SCREEN_LOCATOR = "UIView:atIndex=2";

   protected LoginScreen() {
       super(SCREEN_LOCATOR);
   }

   private void setUsername(String username) {
       getDriver().setElementValue(UIMap.LoginScreen.textfield_username, 
    		   					username);
   }

   private void setPassword(String password) {
       getDriver().setElementValue(UIMap.LoginScreen.textfield_password,
    		   					password);
   }

   private void touchLoginButton() {
       getDriver().touch(UIMap.LoginScreen.button_sign_in);
   }

   protected void parsePage() {
       // 
       // 
   }

   protected void init() {
       // 
       //if(!getDriver().getLocation().equals(PAGE_URL)) {
       //    throw new IllegalStateException("Invalid page is opened");
      // }
       // 
       // 
   }

   private void loginAs(String username, String password) {
       setUsername(username);
       setPassword(password);
       touchLoginButton();
   }

   // getters:
   // ........................................................................
	public String[] getScreenModel() {
		String[] loginScreen = new String[3];

		loginScreen[0] = getDriver().getElementValue(
								UIMap.LoginScreen.textfield_username, "placeholder");
		loginScreen[1] = getDriver().getElementValue(
								UIMap.LoginScreen.textfield_password, "placeholder");
		loginScreen[2] = getDriver().getElementValue(
								UIMap.LoginScreen.button_sign_in, "accessibilityLabel");

		return loginScreen;
	}
   
   // actions:
   // ........................................................................
   public HomeScreen login(String username, String password) {
       loginAs(username, password);
       return new HomeScreen();
   }

   public AlertDialog loginInvalid(String username, String password) {
       loginAs(username, password);
       return new AlertDialog();
   }
}