package com.epam.mobile.exampleproject.tests.architecture.design.screens;

import com.epam.mobile.exampleproject.tests.architecture.foundation.AbstractScreenFactory;

public class ScreenFactory implements AbstractScreenFactory{
		
	private static ScreenFactory instance;
	
	private ScreenFactory() throws Exception {
	}

	public static ScreenFactory getInstance() {
		return instance;
	}

	static {
		try {
			instance = new ScreenFactory();
		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(-1);
		}
	}	
	
	public LoginScreen getLoginScreen() {
		return new LoginScreen();
	}
	
	public HomeScreen getHomeScreen() {
		return new HomeScreen();
	}
	
/*	public FirstLoginScreen getFirstLoginScreen() {
		return new FirstLoginScreen();
	}
*/	
	public AlertDialog getAlertDialog() {
		return new AlertDialog();
	}
}
