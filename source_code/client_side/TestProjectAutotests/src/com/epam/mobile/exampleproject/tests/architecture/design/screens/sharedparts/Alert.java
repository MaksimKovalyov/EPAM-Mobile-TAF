package com.epam.mobile.exampleproject.tests.architecture.design.screens.sharedparts;

import com.epam.mobile.testing.utils.UIMap;

public class Alert<T> extends AbstractPart<T>{

	// actions:
	// ....................................................................
	public T tapNo(){
		getDriver().touch(UIMap.AlertDialog.button_no);
		return getScreen();
	}
	
	public T tapYES(){
		getDriver().touch(UIMap.AlertDialog.button_yes);
		return getScreen();
	}
	
}
