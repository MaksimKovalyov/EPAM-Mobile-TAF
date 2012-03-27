package com.epam.mobile.testing.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
 
public class StreamGobbler extends Thread{
	InputStream is;
	String type;
	OutputStream os;
	
	public StreamGobbler(InputStream is, String type){
		this.is=is;
		this.type=type;
	}
	
	public StreamGobbler(InputStream is, String type, OutputStream redirect)
	    {
	        this.is = is;
	        this.type = type;
	        this.os = redirect;
	    }
	
	public void run(){
	BufferedReader br = new BufferedReader(new InputStreamReader(is));	
	String line=null;
	
	try {
		while((line = br.readLine()) != null){
			System.out.println(type + ": " + line);
		}
		
	br.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
}

