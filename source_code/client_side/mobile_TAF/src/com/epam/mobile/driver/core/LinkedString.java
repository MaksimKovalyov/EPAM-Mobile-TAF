package com.epam.mobile.driver.core;

public class LinkedString{
	// issue: how to detect search strategies
	// possible solution: strategy pattern
	private String origin = "";
	
	private String xpath          = "";
	private String className      = "";
//	private String classNameChain = "";	
	private String attributeName  = "";
	private String attributeValue = "";
	private String index          = "";
	
	private static LinkedString instance;

	private LinkedString() throws Exception {
	}

	public static LinkedString getInstance() {
		return instance;
	}

	static {
		try {
			instance = new LinkedString();
		} catch (Exception e) {
			e.printStackTrace();
//			System.exit(-1);
		}
	}	
	
	public void clean(){
		setAttributeName("");
		setAttributeValue("");
		setClassName("");
		setXpath("");
		setIndex("");
		this.origin = "";
	}
	
	public LinkedString andClassName(String className_){
//		link("2");
		this.setClassName(className_);
		
		return getInstance();
	}

	public LinkedString andAttribute(String name, String value){
//		link("3");
		this.setAttributeName(name);
		this.setAttributeValue(value);
		
		return getInstance();
	}

	public LinkedString andIndex(int index){
//		link("4");
		this.setIndex(String.valueOf(index));
		
		return getInstance();
	}
	
	private void link(String bit){
		this.origin += bit;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
		link("1");
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
		link("2");
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
		link("3");
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
		link("4");
	}

	public String getOrigin() {
		return origin;
	}	
}
