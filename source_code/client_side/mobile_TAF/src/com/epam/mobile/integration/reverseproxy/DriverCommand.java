package com.epam.mobile.integration.reverseproxy;

import net.sf.json.JSONObject;

public class DriverCommand {

	private String cmdName = null;
	private String jsonRest = null;
	private boolean validness = false;
	private String hostAddress = null;
	private int port = 0;
	
	public DriverCommand() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public String getCmdName() {
		return cmdName;
	}

	public String getJsonRest() {
		return jsonRest;
	}



	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void parse(String unclearCommand) {
		try {
			String command = unclearCommand.split(" ")[1];
			cmdName = command.split("\\?")[0];
			cmdName = cmdName.replace("/", "");
			jsonRest = command.split("\\?")[1];
			reencodeURL();
			validate(cmdName, jsonRest);
			System.out.println("[LOG] command: " + cmdName + " jsonRest: " + jsonRest);
		} catch (Exception e) {
			System.out.println("[ERROR] " + e.toString());
		}
	}
	
	private void reencodeURL(){
		jsonRest = jsonRest.replace("%22", "\"");
		jsonRest = jsonRest.replace("%20", " ");
		System.out.println("[LOG] reencoded json rest: " + jsonRest);
	}
	
	private boolean validate(String command, String params){
		if (command.length() > 0){
			validness = true;
		}
		
		return validness;
	}
	
	public boolean isValid(){
		return validness;
	}
	
	public String getOperation(){
		
		return "";
	}
	
	public JSONObject getParams(){
		
		return new JSONObject();
	}
	
	public String getHostName(){
		return hostAddress;
	}
	
	public int getPort(){
		return port;
	}
	
	public String getFullAddress(){
		return getHostName() + getPort(); 
	}
}
