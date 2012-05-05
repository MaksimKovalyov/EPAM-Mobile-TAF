package com.epam.mobile.driver.core;

public class Configurator {

	private final static String LOCALHOST      = Constants.LOCALHOST_URL;
	//private final static String REMOTEHOST     = Constants.REMOTEHOST_URL;
	
	private String DriverURL = LOCALHOST;
	private String ServerURL = LOCALHOST;
	
	private static Configurator instance;
	
	private Configurator() throws Exception {
	}

	public static Configurator getInstance() {
		return instance;
	}

	static {
		try {
			instance = new Configurator();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}	

	public String getDriverURL(){
		return instance.DriverURL;
	}
	
	public void setDriverURL(String url){
		instance.DriverURL = url;
	}

	public String getServerURL(){
		return instance.ServerURL;
	}
	
	public void setServerURL(String url){
		instance.ServerURL = url;
	}

}
