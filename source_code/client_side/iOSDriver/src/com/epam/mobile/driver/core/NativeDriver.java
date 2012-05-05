package com.epam.mobile.driver.core;

public interface NativeDriver {

	public void setServerURL(String url);
	
	public boolean simulateMemoryWarning();
	public boolean isServerAlive();
	
	public String getElementValue(String classNameChain,
									 String attributeName, 
									 String elementIndex);
	
	public String getElementValue(String classNameChain,
			 String attributeName);
	
	public UIElement getElement(String classNameChain);
	
	public UIElement getElement(String className,
								   String attributeName,
								   String attributeValue,
								   String index);
	
	public boolean setElementValue(String className,
									  String attributeName, 
									  String attributeValue,
									  String elementIndex, 
									  String value);

	public boolean setElementValue(String classNameChain,
			  String value);
	
	public boolean doAction(String className,
							   String attributeName, 
							   String attributeValue,
							   String elementIndex, 
							   String action);
	
	public String doAction(String classNameChain,
							  String action);

	public boolean touch(String className,
			   String attributeName, 
			   String attributeValue);

	public String touch(String classNameChain);

	public String scroll(String classNameChain, 
			   String scrollDirection);
	
	public String scrollUp(String classNameChain);
	public String scrollDown(String classNameChain);
	public String scrollRight(String classNameChain);
	public String scrollLeft(String classNameChain);
	
	public String scrollRightShort(String classNameChain);
	
	public boolean flash(String className,
			   String attributeName, 
			   String attributeValue);

	public String flash(String classNameChain);
	
	public boolean isElementExist(String ui_xpath);
	
	//public boolean waitForElementPresent(String ui_xpath, int time_period);
	public boolean waitForElementPresent(String ui_xpath, double time_period);
	
	public boolean waitForElementPresent(String ui_xpath);
	
	public void wait(int timeout);
	
	public void wait_();
	
	public UIElement findElement(LinkedString xpath);
	
	public UIElement findElement(String xpath);
	
}
