package com.epam.mobile.integration.reverseproxy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class ReverseProxy extends Thread{
	
	private ServerSocket socket = null;
	private Socket       server = null;
	 
	private SocketAddress address = null;
	
	private int    port     = 0;
	private String hostname = null;
	
	private PrintStream out = null;
	private InputStreamReader isr = null;
	private BufferedReader in = null;

	private HashMap<String, String> headers = null;
	private String response = null;
	
	private final String SUCCESS_RESPONSE = "HTTP/1.1 200 OK";
	private final String PROXY_VERSION    = "Server: proxy/0.1";
	private final String CONTENT_TYPE     = "Content-Type: text/html";
	
	private String client = null;
	
	private DriverCommand command = null;
	
	public ReverseProxy() {
		System.out.println("[LOG] Reverse Proxy starts...");
		define();
		init();
		
		this.start();
	}
	
	private void init(){
		System.out.println("[LOG] init starts...");
		try {
			socket = new ServerSocket();
			address = new InetSocketAddress(hostname, port);
			socket.bind(address);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		headers = new HashMap<String, String>();
		command = new DriverCommand();
		System.out.println("[LOG] init end.");
	}
	
	private void define(){
		System.out.println("[LOG] define starts...");
		hostname = "10.143.13.222";
		port = 1968;
		System.out.println("[LOG] define end.");
	}
	
	public void run() {
		System.out.println("[LOG] run starts...");
		work();
	}
	
	private void work(){
		System.out.println("[LOG] work starts...");
		while (true) {
			try {
				server = socket.accept();
				
				prepareInOutStreams();
				processRequest();
				closeStreams();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/*
	private void openSecondConnection() throws Exception{
		System.out.println("[LOG] prepare streams starts...");
		
		out = new PrintStream(server.getOutputStream());
		isr = new InputStreamReader(server.getInputStream());
		in = new BufferedReader(isr);
		System.out.println("[LOG] prepare streams end.");
	}*/
	
	private void prepareInOutStreams() throws Exception{
		System.out.println("[LOG] prepare streams starts...");
		out = new PrintStream(server.getOutputStream());
		isr = new InputStreamReader(server.getInputStream());
		in = new BufferedReader(isr);
		System.out.println("[LOG] prepare streams end.");
	}
	
	private void processRequest() throws Exception{
		System.out.println("[LOG] process request starts...");
		String line = "";
		ArrayList<String> rawData = new ArrayList<String>();
		
		while ((line = in.readLine()) != null) {
			System.out.println("now got " + line);
			rawData.add(line);
			
			if (line.equals(""))
				break;
		}
		
		processHeaders(rawData);
		processBody();
		String request = reformRequest();
		String response = redirect(request);
		
		sendResponse(response);
		System.out.println("[LOG] process request end.");
	}
	
	private void processHeaders(ArrayList<String> data){
		System.out.println("[LOG] process headers starts...");
		System.out.println("[LOG] Headers: ");
		Iterator<String> iterator = data.iterator();
		while (iterator.hasNext()) {
			String headerKeyValuePair = (String) iterator.next();
			
			String[] property = parseHeaderProperty(headerKeyValuePair);
			property = filter(property);
			headers.put(property[0], property[1]);
			System.out.println("[LOG]      " + "<" + property[0] + "> AND <" + property[1] + ">");
		}
		System.out.println("[LOG] process headers end.");
	}
	
	private String[] filter(String[] prop){
		String[] tmp = prop;
		
		if (prop[0].contains("GET /")){
			command.parse(prop[0]);
		}
		
		if (prop[0].equalsIgnoreCase("Host")){
			hostname = prop[1].split(":")[0];
			port = Integer.parseInt(prop[1].split(":")[1]);
			System.out.println("[LOG] host: " + hostname + " port: " + port);
		}
		
		return tmp;
	}
	
	private String[] parseHeaderProperty(String pair){
		System.out.println("[LOG] pair: " + pair);
		String[] result = new String[]{"ValueNotFound", "ValueNotFound"};
		
		String[] tmp = pair.split(": ");
		if (tmp.length == 2){
			result = tmp;
		}else{
			result[0] = tmp[0];
		}
		 
		//System.out.println("[LOG] parsed pair: " + result[0] + " " + result[1]);
		
		return result;
	}
	
	private void processBody() throws Exception{
		System.out.println("[LOG] process body starts...");
		if (!headers.get("User-Agent").contains("MIDP-2.0")){
			client = "Driver";
			processBodyDriverRequest();
		}else{
			client = "BB";
			processBodyBBRequest();
		}
		System.out.println("[LOG] process body end.");
	}
	
	private void processBodyDriverRequest(){
		System.out.println("[LOG] process body driver request starts...");
		
		System.out.println("[LOG] process body driver request end.");
	}
	
	private String reformRequest(){
		BBCommand bbCommand = new BBCommand(command.getCmdName(), command.getJsonRest());
		
		return bbCommand.getBbCommand();
	}
	
	private void autoforward(String request) throws Exception{
		
		sendResponse_("<html><head></head><body><h1>" + "request.body" + "</h1></Body></html>");
	}

	private String redirect(String request){
		
		String responseFromBB = sendBBHTTPPushRequest(request);
		String httpResponseBB = "";
		
		if (responseFromBB.equalsIgnoreCase("OK")){
			httpResponseBB = waitForBBHttpResponse();
		}
		else{
			httpResponseBB = generateFaultResponse("Faults.BBPushServiceError");
		}
		
		//waitForBBHTTPRequest();
		
		return httpResponseBB;
	}
	
	private String waitForBBHttpResponse(){
		// open again input stream
		// read input stream
		// parse it
		// form response
		
		return "";
	}
	
	private String generateFaultResponse(String error){
		return "Fault: BBPushserviceError";
	}

	// bytes limit = 8 KB
	private String sendBBHTTPPushRequest(String request){
		
		String URI = request;
		String bbDirectResponse = "";
		
		HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpPost httppost = new HttpPost(URI);

   /*         File file = new File("test.txt");

            InputStreamEntity reqEntity = new InputStreamEntity(
                    new FileInputStream(file), -1);
            reqEntity.setContentType("binary/octet-stream");
            //reqEntity.setChunked(true);
            // It may be more appropriate to use FileEntity class in this particular
            // instance but we are using a more generic InputStreamEntity to demonstrate
            // the capability to stream out data from any arbitrary source
            //
            // FileEntity entity = new FileEntity(file, "binary/octet-stream");

            httppost.setEntity(reqEntity);
*/
          
            System.out.println("executing request " + httppost.getRequestLine());
            HttpResponse response = httpclient.execute(httppost);
            //HttpEntity resEntity = response.getEntity();

            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            
            bbDirectResponse = response.getStatusLine().toString();
            
            /*
            if (resEntity != null) {
                System.out.println("Response content length: " + resEntity.getContentLength());
                System.out.println("Chunked?: " + resEntity.isChunked());
            }
            EntityUtils.consume(resEntity);
            */
        }catch (Exception e) {
			System.out.println("[ERROR] push request: " + e.toString());
		}
        finally {
            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            httpclient.getConnectionManager().shutdown();
        }	
		
		return bbDirectResponse;
	}
	
	private void processBodyBBRequest() throws Exception{
		System.out.println("[LOG] process body BB request starts...");
		String tmp = "";
		int contentLength = Integer.parseInt(headers.get("Content_Length"));
		
		char[] chunk = new char[128];
		int read = 0;
		do {
			read = in.read(chunk, 0, 128);

			if (read != -1) {
				tmp = new String(chunk, 0, read);
				response += tmp;
				System.out.println("tmp:" + tmp);
			}
			
			if (response.length() == contentLength){
				break;
			}
			
		} while (read != -1);
		System.out.println("[LOG] process body BB request end.");
	}
	
	// add waiting for BB request
	private void sendResponseToDriver(String response) throws Exception{
		String driverResponse = generateDriverResponse(response);
		autoforward(driverResponse);		
	}
	
	private String generateDriverResponse(String response_){
		
		return "";
	}

	// can be put aside 
	@SuppressWarnings("unused")
	private void sendResponseToBB(String body) throws Exception{
		sendResponse_("<bb>" + body + "<bb>");
	}
	
	private void sendResponse(String body) throws Exception{
		//if (client.equalsIgnoreCase("Driver")){
			sendResponseToDriver(body);
		//}else{
		//	sendResponseToBB(body);
		//}
	}
	
	private void sendResponse_(String body) throws Exception{
		System.out.println("[LOG] send response starts...");
		System.out.println("[LOG] Body: " + body);		
		out.println(SUCCESS_RESPONSE);
		out.println(PROXY_VERSION);
		out.println(CONTENT_TYPE);
		//out.println("Accept-Ranges: bytes");
		
		out.println("Content-Length: " + body.length());
		out.println("");
		out.println(body);
		//out.print("\r\r");
		
		System.out.println("[LOG] send response end.");
	}
	
	private void closeStreams() throws Exception{
		out.close();
		in.close();
	}
	
	public String getFullLog(){
		System.out.println("[LOG][FULL] " + client + " connected to " + "reverse proxy");
		
		return "";
	}
	
	public static void main(String[] args) {
		ReverseProxy proxy = new ReverseProxy();
		proxy.sendBBHTTPPushRequest("");
	}
}