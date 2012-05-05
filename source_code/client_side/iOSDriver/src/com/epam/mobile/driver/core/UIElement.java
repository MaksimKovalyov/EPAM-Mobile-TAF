package com.epam.mobile.driver.core;

import com.epam.mobile.driver.utils.JSONParser;

import net.sf.json.JSONObject;

// Issue: how to process many attributes of one class instance? 
// scheme using, composite pattern, object assembly, other solution
public class UIElement {

	private JSONObject proxy = null;
	
	public void gainEntity(JSONObject proxy){
		this.setProxy(proxy);

		//incorporation();
	}
	
	public boolean setValue(String value){
		
		return true;
	}
	
	public boolean scroll(String direction){
		
		return true;
	}
	
	public boolean touch(){
		
		return true;
	}

	public boolean flash(){
	
		return true;
	}
	
	public String getValue(){
		
		return "";
	}
	
	public String getText(){
		String result = "";
		try {
			JSONParser.getInstance().setJsonObject(getProxy());			
			result = JSONParser.getInstance().getValueByKey("text");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	public JSONObject getLabel(){
		JSONObject object = new JSONObject();
	
		
		return object;
	}
	
	public JSONObject getColor(){
		JSONObject object = new JSONObject();
		
		return object;
	}
	
	public JSONObject getProxy(){		
		return this.proxy;
	}

	public void setProxy(JSONObject proxy) {
		this.proxy = proxy;
	}
	
}
