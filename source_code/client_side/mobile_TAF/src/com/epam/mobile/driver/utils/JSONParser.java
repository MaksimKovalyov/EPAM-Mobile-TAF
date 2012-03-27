package com.epam.mobile.driver.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class JSONParser {
	private JSONObject jsonObject;
	private String jsonTxt;
	
	private static JSONParser instance;
	
	static {
		try {
			instance = new JSONParser();
		} catch (Exception e) {
			System.out.println(e.toString());
//			System.exit(-1);
		}
	}
	
	public static JSONParser getInstance() {
		return instance;
	}

	public String getJsonTxt() {
		return jsonTxt;
	}

	public void setJsonString(String jsonString) {
		this.jsonTxt = jsonString;
		this.jsonObject = (JSONObject) JSONSerializer.toJSON(jsonString);
	}
	
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	public void setJsonObject(String uriToJSONObject) {
		try {
			InputStream is = JSONParser.class.getResourceAsStream(uriToJSONObject);
			this.jsonTxt = convertStreamToString(is);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		JSONObject json = (JSONObject) JSONSerializer.toJSON(this.jsonTxt);
		this.jsonObject = json;
	}
	
	public String getValueByKey(String jsonFileUri, String key) throws IOException {
		InputStream is = JSONParser.class.getResourceAsStream(jsonFileUri);
		String jsonTxt = convertStreamToString(is);
		String value = "ValueNotFound";
		
		System.out.println("jsonTxt: " + jsonTxt);

		JSONObject json = (JSONObject) JSONSerializer.toJSON(jsonTxt);
		
		value = json.getString(key);
		System.out.println("Value: " + value);
		
		return value;
	}

	public JSONObject getObjectByKey(String jsonFileUri, String key) throws IOException {
		InputStream is = JSONParser.class.getResourceAsStream(jsonFileUri);
		String jsonTxt = convertStreamToString(is);
		
		System.out.println("jsonTxt: " + jsonTxt);

		JSONObject json = (JSONObject) JSONSerializer.toJSON(jsonTxt);
		
		JSONObject object = json.getJSONObject(key);;
		System.out.println("Object: " + object);
		
		return object;
	}

	public String getValueByKey(String key) throws IOException {
		String value = "ValueNotFound";
		
//		System.out.println("jsonTxt: " + this.jsonTxt);
//		System.out.println("jsonObject: " + this.jsonObject);

		//JSONObject json = (JSONObject) JSONSerializer.toJSON(jsonTxt);
		
		value = this.jsonObject.getString(key);
		System.out.println(" [LOG]   key: " + key);
		System.out.println(" [LOG] value: " + value);
		
		return value;
	}

	public JSONObject getObjectByKey(String key) throws IOException {
//		System.out.println("jsonTxt: " + this.jsonTxt);
//		System.out.println("jsonObject: " + this.jsonObject);

		//JSONObject json = (JSONObject) JSONSerializer.toJSON(jsonTxt);
		
		JSONObject object = this.jsonObject.getJSONObject(key);
//		System.out.println("Object: " + object);
		
		return object;
	}

	public static String convertStreamToString(InputStream is) throws IOException {
		/*
		 * To convert the InputStream to String we use the Reader.read(char[]
		 * buffer) method. We iterate until the Reader return -1 which means there's
		 * no more data to read. We use the StringWriter class to produce the
		 * string.
		 */
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}
	
	public static void main(String[] args) {
		
	}	
	
}
