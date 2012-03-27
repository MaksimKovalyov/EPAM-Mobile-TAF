package com.epam.mobile.driver.utils;

import com.epam.mobile.driver.core.Constants;

import net.sf.json.JSONObject;

public class JSONGenerator {
	private JSONObject json   = new JSONObject();
	private JSONObject params = new JSONObject();
	
	private static JSONGenerator instance;
	
	static {
		try {
			instance = new JSONGenerator();
		} catch (Exception e) {
			System.out.println(e.toString());
			System.exit(-1);
		}
	}
	
	public static JSONGenerator getInstance() {
		return instance;
	}

	private void createJSONObject(){
		json   = new JSONObject();
		params = new JSONObject();
	}
	
	private void addOperation(String operation){
		json.put(Constants.OPERATION, operation);
	}
	
	@SuppressWarnings("unused")
	private void addParams(JSONObject params){
		json.put(Constants.PARAMS, params);
	}

	private void addParams(){
		json.put(Constants.PARAMS, this.params);
	}

	private void addClassName(String className){
		params.put(Constants.CLASS_NAME, className);
	}

	private void addClassNameChain(String classNameChain){
		params.put(Constants.CLASS_NAME_CHAIN, classNameChain);
	}

	private void addAttributeName(String attributeName){
		params.put(Constants.ATTRIBUTE_NAME, attributeName);
	}

	private void addAttributeValue(String attributeValue){
		params.put(Constants.ATTRIBUTE_VALUE, attributeValue);
	}

	private void addValue(String value){
		params.put(Constants.VALUE, value);
	}
	
	private void addAction(String action){
		params.put(Constants.ACTION, action);
	}
	
	private void addElementIndex(String index){
		params.put(Constants.ELEMENT_INDEX, index);
	}
	
	private String getJSONString(){
		return json.toString();
	}
	
	public String getRequestSetElementValue(
			String classNameChain,
			String className, 
			String attributeName, 
			String attributeValue,
			String elementIndex,
			String value){
    	
		this.createJSONObject();
		this.addOperation(Constants.SET_ELEMENT_VALUE);
		this.addClassNameChain(classNameChain);
		this.addClassName(className);
		this.addAttributeName(attributeName);
		this.addAttributeValue(attributeValue);
		this.addElementIndex(elementIndex);
		this.addValue(value);
		this.addParams();
			
		return this.getJSONString();
	}

	public String getRequestGetElementValue(
			String classNameChain, 
			String attributeName, 
			String elementIndex){
    	
		this.createJSONObject();
		this.addOperation(Constants.GET_ELEMENT_VALUE);
		
		this.addClassNameChain(classNameChain);
		this.addAttributeName(attributeName);
		this.addElementIndex(elementIndex);
		this.addParams();
	
		return this.getJSONString();
	}

	public String getRequestGetElement(
			String classNameChain,
			String className,
			String attributeName,
			String attributeValue,
			String elementIndex){
    	
		this.createJSONObject();
		this.addOperation(Constants.DESCRIBE);//Constants.GET_ELEMENT);
		
		this.addClassNameChain(classNameChain);
		this.addClassName(className);
		this.addAttributeName(attributeName);
		this.addAttributeValue(attributeValue);
		this.addElementIndex(elementIndex);
		this.addParams();
	
		return this.getJSONString();
	}
	
	public String getRequestDoAction(
			String classNameChain,
			String className, 
			String attributeName,
			String attributeValue,
			String elementIndex,
			String action){
    	
		this.createJSONObject();
		this.addOperation(Constants.DO_ACTION);

		this.addClassNameChain(classNameChain);
		this.addClassName(className);
		this.addAttributeName(attributeName);
		this.addAttributeValue(attributeValue);
		this.addElementIndex(elementIndex);
		this.addAction(action);
		this.addParams();
	
		return this.getJSONString();
	}
}
