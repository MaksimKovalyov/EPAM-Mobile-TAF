package com.epam.mobile.driver.guard.controllability;

public class ServerConnectionLostException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3106234972691856974L;

	public ServerConnectionLostException() {
		// TODO Auto-generated constructor stub
	}
	
	public ServerConnectionLostException(String msg) {
		super(msg);
	}
	
	public ServerConnectionLostException(Exception e) {
		super(e);
	}
}
