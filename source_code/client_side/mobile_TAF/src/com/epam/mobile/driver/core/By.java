package com.epam.mobile.driver.core;

public class By {

	public static LinkedString XPath(String xpath){
		LinkedString.getInstance().clean();
		LinkedString.getInstance().setXpath(xpath);
		
		return LinkedString.getInstance();
	}

	public static LinkedString ClassName(String className_){
		LinkedString.getInstance().clean();
		LinkedString.getInstance().setClassName(className_);
		
		return LinkedString.getInstance();
	}	

	public static LinkedString Attribute(String name, String value){
		LinkedString.getInstance().clean();
		LinkedString.getInstance().setAttributeName(name);
		LinkedString.getInstance().setAttributeValue(value);
		
		return LinkedString.getInstance();
	}
	
	public static LinkedString Index(int index){
		LinkedString.getInstance().clean();
		LinkedString.getInstance().setIndex(String.valueOf(index));
		
		return LinkedString.getInstance();
	}	

	
}
