package com.epam.mobile.driver.utils;

import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Pattern;

public class Generator {
	private static Generator instance;
	private        String    request   = "";
//	private        int       length    = 24;
	private        HashMap<String, String>   testStore = new HashMap<String, String>();

	private Generator() throws Exception {
	}

	public static Generator getInstance() {
		return instance;
	}

	static {
		try {
			instance = new Generator();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}	

	
	public HashMap<String, String> getTestStore() {
		return testStore;
	}

	public String getXMLHeader(){
		
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	}
	
	private void addXmlVersion(){
		request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	}
	
	private void addEnvelopeSOAPTag(String header, String body){
		request += "<soap:Envelope " +
			"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
			"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
			"xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
			"xmlns:sometag=\"http://site.com/\" " +
			"xmlns:arr=\"http://schemas.microsoft.com/2003/10/Serialization/Arrays\">" +
			header +
			body +
			"</soap:Envelope>";
		//testStore.put(arg0, arg1);
	}
	
	private String getHeaderSOAPTag(String device, String token){
		String header =	"<soap:Header>" +
				"<Device xmlns=\"http://site.com/\">" + device + "</Device>" +
				"<Token xmlns=\"http://site.com/\">" + token + "</Token>" +
			"</soap:Header>";
		
		testStore.put("device", device);
		testStore.put("token", token);
		
		return header;
	}

	private String getBodySOAPTagOnLogin(String user, String pass){
		String body =	"<soap:Body>" +
				"<thom:Login xmlns=\"http://some.com/\">" +
					"<thom:login>" + user + "</thom:login>" +
					"<thom:password>" + pass + "</thom:password>" +
				"</thom:Login>" +
				"</soap:Body>";
		
		testStore.put("user", user);
		testStore.put("pass", pass);
		
		return body;
	}

	private String getBodySOAPTag(String somethingID){
		String body =	"<soap:Body>" +
				getSomething(somethingID) +
				"</soap:Body>";
		
		testStore.put("somethingID", somethingID);
		
		return body;
	}

	private String getSomething(String id){
		
		return "<sometag:GetSomething xmlns=\"http://site.com/\">" +
					"<sometag:somethingId>" + id + "</sometag:somethingId>" +
			   "</sometag:GetSomething>";
	}

	public String getValidLoginClient(String username, String password, String device, String token){

		return getTemplateLoginClient(username, password, device, token);
	}

	public String getRequestSOAP(String device, String token, String portfolioID){

		return getTemplateSOAPRequest(device, token, portfolioID);
	}
	
	public String getTemplateLoginClient(String username, String password, String device, String token){
		request = "";
		addXmlVersion();
		addEnvelopeSOAPTag(getHeaderSOAPTag(device, token), 
				getBodySOAPTagOnLogin(username, password));

		return request;
	}
	
	public String getTemplateSOAPRequest(String device, String token, 
				String somethingID){
		request = "";
		addXmlVersion();
		addEnvelopeSOAPTag(getHeaderSOAPTag(device, token), 
				getBodySOAPTag(somethingID));

		return request;
	}
	
	public static void main(String[] args) {
		// String uuid = Generator.getInstance().getRandomUUID();// + "1";
		// System.out.println("UUID is " + uuid +
		// "length is " + uuid.length());
		// System.out.println(Generator.getInstance().isUUID(uuid));

		String t = "2000-01-31T19:21:03.000";// Generator.getInstance().getTimestamp();
		System.out.println(t);
		System.out.println(Generator.getInstance().isIsoTimestampFormat(t));

		String str = UUID.randomUUID().toString().substring(0, 5);
		System.out.println(str);
	}

	/*
	 * public String getHostAddressLimited(){ int baseIP = 254; int basePort =
	 * 9999;
	 * 
	 * return "10" + "." + "1" + "." + "1" + "." + getNumber(baseIP + 1) + ":" +
	 * getNumber(basePort + 1); }
	 * 
	 * private String getNumber(int base){ return Integer.toString(new
	 * Random().nextInt(base)); }
	 * 
	 * public String getRandomUUID() { return UUID.randomUUID().toString(); }
	 * 
	 * public String getTimestamp(){
	 * 
	 * return (new java.sql.Timestamp( new java.util.Date().getTime())
	 * ).toString().replace(" ", "T"); }
	 */

	public boolean isIsoTimestampFormat(String expression) {
		if ((expression != null) && (expression.length() <= 23)) {
			return Pattern
					.compile(
							"^(\\{{0,1}([0-9]){4}-" + "([0-9]){2}-"
									+ "([0-9]){2}T" + "([0-9]){2}:"
									+ "([0-9]){2}:" + "([0-9]){2}."
									+ "([0-9]){3}\\}{0,1})$")
					.matcher(expression).matches();
		}

		return false;
	}
	
	public static boolean isUUID(String expression)
	{
	    if ((expression != null) && (expression.length() <= 36))
	    {
	        return Pattern.compile("^(\\{{0,1}([0-9a-fA-F]){8}-" +
	    			"([0-9a-fA-F]){4}-" +
	    			"([0-9a-fA-F]){4}-" +
	    			"([0-9a-fA-F]){4}-" +
	    			"([0-9a-fA-F]){12}\\}{0,1})$").
	    			matcher(expression).matches();	        
	    }
	    
	    return false;
	}
}
