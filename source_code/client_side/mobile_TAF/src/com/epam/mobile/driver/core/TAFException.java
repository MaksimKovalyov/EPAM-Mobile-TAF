package com.epam.mobile.driver.core;

@SuppressWarnings("serial")
public class TAFException extends Exception {
	// short description
	String exception_msg_name;
	// full message body of exception with stack trace
	String exception_msg_body;

	public TAFException() {
		super();
		exception_msg_name = "TAFException";
	}

	public TAFException(String exception) {
		super(exception);
		this.exception_msg_name = exception;
	}

	public TAFException(Exception exception) {
		super(exception);
		this.exception_msg_name = "TAFException";
		this.exception_msg_body = exception.getMessage();
	}
	
	public String getExceptionName() {
		return this.exception_msg_name;
	}
	
	public String getExceptionBody() {
		return this.exception_msg_body;
	}
}