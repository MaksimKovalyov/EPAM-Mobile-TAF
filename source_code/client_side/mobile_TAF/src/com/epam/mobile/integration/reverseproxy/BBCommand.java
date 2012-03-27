package com.epam.mobile.integration.reverseproxy;

import org.apache.commons.httpclient.util.URIUtil;

import com.epam.mobile.driver.utils.JSONParser;

public class BBCommand {

	private String bbCommand = "";
	private String hostName = null;
	private int    port = 0;
	private final static String WEB_PROTOCOL = "http://";
	private final static String BB_SERVICE = "push";
	private final static String DESTINATION_NAME = "DESTINATION";
	private final static String DESTINATION_VALUE = "2100000A";
	private final static String PORT_NAME = "PORT";
	private final static String PORT_VALUE = "100";
	private final static String REQUESTURI_NAME = "REQUESTURI";
	private final static String REQUESTURI_HOST = "localhost";	
	private String driverCommand = "";
	private String jsonRest = "";
//	private JSONObject json = null;
	private final static String PARAMS = "Params";
	private final static String OPERATION = "Operation";
	private final static String CLASS_NAME = "ClassName";
	private final static String ATTRIBUTE_NAME = "AttributeName";
	private final static String ATTRIBUTE_VALUE = "AttributeValue";
	private final static String VALUE = "Value";
	private String requestURI = "";
	
	// bb_command:
	// - request: http://localhost:8080/push?
	//   		  	DESTINATION=2100000A&
	//    	 		PORT=100&
	//    	 		REQUESTURI=localhost/driver_command?
	//                             class=asdasdasdada 
	
	// driver command:
	// - command name: run_script 
	// - json rest: {"Operation":"SetElementValue",
	//               "Params":{
	//                        "ClassName":"UITextField", 
	//				  	      "AttributeName":"placeholder", 
	//					      "AttributeValue":"Enter Password", 
	//					      "Value":"Password@1"}}
	
	public BBCommand(String driverCommand, String jsonRest) {
		this.driverCommand = driverCommand;
		this.jsonRest = jsonRest;
		
		init();
		define();
		buildBBCommand();
	}
	
	private void init(){
		JSONParser.getInstance().setJsonString(jsonRest);
	}
	
	private void define(){
		hostName = "localhost";
		port = 8080;
	}
	
	// add url builder
	private void buildBBCommand(){
		addProtocol();
		addPrimaryHostName();
		addPrimaryPort();
		addBBService();
		addDestination();
		addPort();
		addRequestURI();
	}
	
	private void addProtocol() {
		bbCommand += WEB_PROTOCOL;
	}
	
	private void addPrimaryHostName(){
		bbCommand += hostName; 
	}
	
	private void addPrimaryPort() {
		bbCommand += ":" + String.valueOf(port);
	}
	
	private void addBBService(){
		bbCommand += "/" + BB_SERVICE;
	}
	
	private void addDestination() {
		bbCommand += "?" + DESTINATION_NAME + "=" + DESTINATION_VALUE;
	}
	
	private void addPort() {
		bbCommand += "&" + PORT_NAME + "=" + PORT_VALUE;
	}
	
	private void addRequestURI() {
		bbCommand += "&" + REQUESTURI_NAME + "=" + REQUESTURI_HOST;
		addDriverCommand();
		try {
			addParamOperation();
			addParamClassName();
			addParamAttributeName();
			addParamAttributeValue();
			addParamValue();
			addEncodedRequestURI();
			
			// encode URI (get codes of " ", "\"" and other chars)
			System.out.println("[LOG] encoded bb command" + bbCommand);
		} catch (Exception e) {
			System.out.println("[ERROR] " + e.toString());
		}
	}
	
	private void addDriverCommand(){
		bbCommand += "/" + driverCommand + "?";
	}
	
	private void addParamOperation() throws Exception{
		requestURI += OPERATION + "=" + JSONParser.getInstance().getValueByKey(OPERATION); 
	}
	
	private void addParamClassName() throws Exception{
		requestURI += "&" + CLASS_NAME + "=" + JSONParser.getInstance().getObjectByKey(PARAMS).getString(CLASS_NAME);
	}
	
	private void addParamAttributeName() throws Exception{
		requestURI += "&" + ATTRIBUTE_NAME + "=" + JSONParser.getInstance().getObjectByKey(PARAMS).getString(ATTRIBUTE_NAME);
	}
	
	private void addParamAttributeValue() throws Exception{
		requestURI += "&" + ATTRIBUTE_VALUE + "=" + JSONParser.getInstance().getObjectByKey(PARAMS).getString(ATTRIBUTE_VALUE);
	}
	
	private void addParamValue() throws Exception{
		requestURI += "&" + VALUE + "=" + JSONParser.getInstance().getObjectByKey(PARAMS).getString(VALUE);
	}

	private void addEncodedRequestURI() throws Exception{
		requestURI = URIUtil.encodePath(requestURI, "ISO-8859-1");
		bbCommand += requestURI;
	}
	
	public String getBbCommand() {
		return bbCommand;
	}
	
	
}
