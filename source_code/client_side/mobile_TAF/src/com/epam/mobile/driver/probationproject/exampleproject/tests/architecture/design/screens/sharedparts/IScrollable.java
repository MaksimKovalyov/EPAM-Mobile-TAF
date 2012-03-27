package com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.design.screens.sharedparts;

public interface IScrollable<T> {
	abstract T scrollRight();
	abstract T scrollRight(String locator);
	abstract T scrollRightShort();

	abstract T scrollDown();
	abstract T scrollDown(String locator);
	
	abstract T scrollUp();
	abstract T scrollUp(String locator);

	abstract T scrollLeft();
	abstract T scrollLeft(String locator);
			
	abstract String getScrollPosition(String UIXPath);
}
