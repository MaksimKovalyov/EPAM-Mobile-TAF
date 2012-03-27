package com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.design.screens.sharedparts;

public class Scroller<T> extends AbstractPart<T>{

	public T scrollRight(String locator){
		getDriver().scrollRight(locator);
		return getScreen();
	}
	public T scrollLeft(String locator){
		getDriver().scrollLeft(locator);
		return getScreen();
	}		
			
	public String getScrollPosition(String UIXPath){
		return getDriver().getElementValue(UIXPath , "contentPosition");
	}
}
