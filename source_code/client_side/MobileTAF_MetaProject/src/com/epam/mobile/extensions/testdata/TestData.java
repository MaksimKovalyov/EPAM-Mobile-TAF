package com.epam.mobile.extensions.testdata;

public abstract class TestData {
//	private String data;
	private String key;
	private String value;
	
	// meta signs
	public static final String EOL_STRING = 
								System.getProperty("line.separator");
	public static final String SPACE = " ";
	public static final String KEY_VALUE_SEPARATOR = "=";
	
	protected static String IMPLEMENTATION_TYPE = null;
	
	// getters and setters
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	public abstract void findValue();
	public abstract String getValue(String key);
}
