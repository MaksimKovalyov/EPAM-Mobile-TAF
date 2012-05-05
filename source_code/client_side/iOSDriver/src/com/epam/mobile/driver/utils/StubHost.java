package com.epam.mobile.driver.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.HttpsURLConnection;

import java.security.KeyManagementException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

//import org.apache.commons.httpclient.util.URIUtil;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.epam.mobile.driver.guard.controllability.ServerConnectionLostException;

public class StubHost {
	private String requestMethod = "";

	private int    responseCode = 0;
	private String response = "";
	
	private int id = 0;
	
	private HttpsURLConnection connection = null;
	
	private static final Logger logger = Logger.getLogger(StubHost.class);
	
//	private static final String AUTH_HOST = "";
//	private static final int CONNECTION_TIMEOUT = 30000;
//	private static final int AUTH_SITE_TIMEOUT = 10000;

	X509Certificate clientCertificates[];
	Socket requestSocket;

	String message;

	BufferedReader in;
	BufferedWriter out;

	public StubHost(int id) {
		setId(id);
	}
	
	public void addAppender(Appender appender){
    	logger.setAdditivity(false);
		logger.removeAllAppenders();
			
		logger.setLevel(Level.ALL);
		logger.addAppender(appender);
	}
	
	public void wait(int timeout){
		logger.info(" Stub host waits... timeout=" + timeout);
		try {
			Thread.sleep(timeout);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}
	
	public boolean sendCommand(String url, 
								String commandName, 
								String commandData) throws ServerConnectionLostException{
		boolean result = true;
		HttpClient httpclient = new DefaultHttpClient();
		 
		try {
			String encodedCommandData = URLEncoder.encode(commandData, "ISO-8859-1");
			
			//String encodedCommandData = URIUtil.encodePath(commandData, "ISO-8859-1");
			logger.info(" request:");
			logger.info("      full URL = " + url + commandName + "?" + encodedCommandData);
			HttpGet httpget = new HttpGet(url + commandName + "?" + encodedCommandData);
	
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			//HttpResponse res = httpclient.execute(httpget);
			response = httpclient.execute(httpget, responseHandler);
			logger.info(" response: " + response);			
		} catch (Exception e) {
			result = false;
			throw new ServerConnectionLostException(e);
			//throw new TAFException(e);
		}	
		
		return result;
	}
	
	public boolean sendCommand(String url, String commandName) 
												throws ServerConnectionLostException{
		logger.info(" request:");
		logger.info("      full URL = " + url + commandName);
		
		boolean result = true;		
		HttpClient httpclient = new DefaultHttpClient();
		 
		try {
			HttpGet httpget = new HttpGet(url + commandName);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			response = httpclient.execute(httpget, responseHandler);
			
			logger.info(" response: " + response);
		} catch (Exception e) {
			//System.out.println(e.toString());
			result = false;
			throw new ServerConnectionLostException(e);
			//throw new TAFException(e);
		}
		
		return result;
	}
	
	public String getMD5Password(String password) {
		MessageDigest digest;
		String password16Digest = "";
		
		try {
			digest = MessageDigest.getInstance("MD5");
			byte[] binMsg = password.getBytes(Charset.forName("UTF-8"));
			digest.update(binMsg, 0, binMsg.length);
			password16Digest = String.format("%032x", new BigInteger(1, digest.digest()));
			
			System.out.println("MD5: " + password16Digest);
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.toString());
		}
		
		return password16Digest;
	}

	private final static Charset CHARSET = Charset.forName("ISO-8859-1");

	public String getRequestMethod() {
		return requestMethod;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getResponse() {
		return response;
	}

	public String performCall(URL url, String request, String actionSOAP)
			throws MalformedURLException, IOException {

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		response = "";

		// connection set up
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "text/xml");
		// connection.setFixedLengthStreamingMode(request.getBytes(CHARSET).length);
		connection.setRequestProperty("Content-Length", ""
				+ Integer.toString(request.getBytes(CHARSET).length));
		connection.setRequestProperty("SOAPAction", actionSOAP);
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);

		connection.connect();
		
		connection.getOutputStream().write(request.getBytes(CHARSET));
		connection.getOutputStream().flush();
		connection.getOutputStream().close();

		requestMethod = connection.getRequestMethod();
		System.out.println(requestMethod);

		responseCode = connection.getResponseCode();
		System.out.println(responseCode);

		InputStream is = responseCode == 200 ? connection.getInputStream()
				: connection.getErrorStream();
		InputStreamReader isr = new InputStreamReader(is);

		char[] chunk = new char[128];
		int read = 0;
		do {
			read = isr.read(chunk, 0, 128);
			
			if (read != -1) {
				String tmp = new String(chunk, 0, read);
				response += tmp;
			}
			
		} while (read != -1);

		return response;
	}

	public void performSecureCall(URL url, String request)
			throws MalformedURLException, IOException {
		//connection = (HttpsURLConnection) url.openConnection();

		if (enableUncheckedCertificates()) {
			System.out.println("Connection is trusted. Unchecked certificates are enabled.");
		}
		
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			    
		response = "";

		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "text/xml");
		// connection.setFixedLengthStreamingMode(request.getBytes(CHARSET).length);
		connection.setRequestProperty("Content-Length", ""
				+ Integer.toString(request.getBytes(CHARSET).length));

		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);

		connection.connect();

		connection.getOutputStream().write(request.getBytes(CHARSET));
		connection.getOutputStream().flush();
		connection.getOutputStream().close();

		requestMethod = connection.getRequestMethod();
		System.out.println(requestMethod);

		responseCode = connection.getResponseCode();
		System.out.println(responseCode);

		InputStream is = responseCode == 200 ? connection.getInputStream()
				: connection.getErrorStream();
		InputStreamReader isr = new InputStreamReader(is);

		char[] chunk = new char[128];
		int read = 0;
		int position = 0;
		do {
			read = isr.read(chunk, 0, 128);
			position += read;

			String tmp = new String(chunk, 0, read);
			response += tmp;

		} while (read == 128);

	}

	@SuppressWarnings("static-access")
	private boolean enableUncheckedCertificates() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs,
					String authType) {
				// Trust always
			}

			public void checkServerTrusted(X509Certificate[] certs,
					String authType) {
				// Trust always
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			// Create empty HostnameVerifier
			HostnameVerifier hv = new HostnameVerifier() {
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			};

			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			
			connection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
			connection.setDefaultHostnameVerifier(hv);
		} catch (KeyManagementException e) {
			Logger.getLogger(StubHost.class).error(
					"Cannot set all-trusting trust manager", e);
			return false;
		} catch (NoSuchAlgorithmException e) {
			Logger.getLogger(StubHost.class).error(
					"Cannot set all-trusting trust manager", e);
			return false;
		}

		return true;
	}

	public static void main(String[] args) throws MalformedURLException {

//		StubHost me = new StubHost(0);
/*		try {
			me.enableUncheckedCertificates();
			// me.performCall(authenticationServerURL, myParAuth);
			XmlRpcClientConfigImpl clientConfig = new XmlRpcClientConfigImpl();
			MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
			HttpClient httpClient = new HttpClient(connectionManager);

			XmlRpcClient client = new XmlRpcClient();
			XmlRpcCommonsTransportFactory transportFactory = new XmlRpcCommonsTransportFactory(
					client);
			transportFactory.setHttpClient(httpClient);
			clientConfig.setEnabledForExtensions(false);
			clientConfig.setEnabledForExceptions(false);
			clientConfig.setConnectionTimeout(CONNECTION_TIMEOUT);
			clientConfig.setServerURL(new URL(AUTH_HOST));

			client.setConfig(clientConfig);
			client.setMaxThreads(0); // No threads limit
			clientConfig.setReplyTimeout(AUTH_SITE_TIMEOUT);

			// Use own type factory with fixed <string> tag (omitted in the
			// original)
			TypeFactory pTypeFactory = new StringExplicitTypeFactory(client);
			client.setTypeFactory(pTypeFactory);
			client.setConfig(clientConfig);

		} catch (Throwable e) {
			Logger.getLogger(me.getClass()).error(e.getLocalizedMessage());
		}
*/
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
