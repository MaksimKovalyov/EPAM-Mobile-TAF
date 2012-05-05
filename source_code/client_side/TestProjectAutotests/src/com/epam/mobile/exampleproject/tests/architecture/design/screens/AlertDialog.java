package com.epam.mobile.exampleproject.tests.architecture.design.screens;

import com.epam.mobile.exampleproject.tests.architecture.foundation.AbstractScreen;
import com.epam.mobile.testing.utils.UIMap;

public class AlertDialog extends AbstractScreen<AlertDialog> {
	   public static final String SCREEN_LOCATOR = "alert";
	   private String errorMessage;

	   protected AlertDialog() {
	       super(SCREEN_LOCATOR);
	   }

	   protected void init() {
	       //
	   }

	   protected void parsePage() {
	       //
	       this.errorMessage = getDriver().getElementValue(SCREEN_LOCATOR +
	    		   						UIMap.AlertDialog.textmessage_alert,
	    		   						"text");
	   }

	   public String getErrorMessage() {
	       return this.errorMessage;
	   }

	   public void pressNO(){
		   getDriver().touch(UIMap.AlertDialog.button_no);
	   }
	   
	   public void pressYES(){
		   getDriver().touch(UIMap.AlertDialog.button_yes);
	   }
	   
	   /*
	   public LoginPage backToLoginPage() {
	       getBrowser().click("id=BackLink");
	       return new LoginPage();
	   }*/
	}
