package com.epam.mobile.testing.utils; 

import java.awt.HeadlessException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
 
import javax.swing.JOptionPane;
 
public class OutputGobbler extends Thread{
	
	private OutputStream os;
	private String type;
	
	public OutputGobbler(OutputStream os, String type){
		this.os=os;
		this.type=type;
		
		System.out.println("OS: " + this.os + "type: " + this.type);
	}
	
	public void run(){
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
		
		try {
				if(bw != null){
					
					bw.write(JOptionPane.showInputDialog("stdine called"));	
				}
				
				bw.write(JOptionPane.showInputDialog("perl STDIN??"));
			bw.flush();
			bw.close();
			
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
 
}