package com.epam.mobile.taf.core.entity;

public class TAFBuild {

	private String major = "";
	private String minor = "";
	private String buildServerNumber = "";
	private String revision = "";
	
	private String version = "";
	
	private String generateVersion(){
		version = major + "." + 
					minor + "." +
					buildServerNumber + "." +
					revision;
		
		return version;
	}
	
	public String getVersion(){
		return version; 
	}
	
	public static void main(String[] args) {
		TAFBuild build = new TAFBuild();
		build.generateVersion();
	}
}
