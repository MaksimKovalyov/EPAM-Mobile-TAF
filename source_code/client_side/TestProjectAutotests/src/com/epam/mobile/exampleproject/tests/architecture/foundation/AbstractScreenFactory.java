package com.epam.mobile.exampleproject.tests.architecture.foundation;

import com.epam.mobile.exampleproject.tests.architecture.design.screens.AlertDialog;
import com.epam.mobile.exampleproject.tests.architecture.design.screens.HomeScreen;
import com.epam.mobile.exampleproject.tests.architecture.design.screens.LoginScreen;

public interface AbstractScreenFactory {

	abstract LoginScreen getLoginScreen();
	abstract HomeScreen getHomeScreen();
	//abstract FirstLoginScreen getFirstLoginScreen();
	abstract AlertDialog getAlertDialog();
	
}
