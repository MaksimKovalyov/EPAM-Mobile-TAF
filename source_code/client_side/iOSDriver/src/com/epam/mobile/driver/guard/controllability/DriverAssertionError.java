package com.epam.mobile.driver.guard.controllability;

/*
 * For testing approach with a Driver, we assert the following assumptions:
 * 1) For all getElementValue commands it shouldn't be the value = 'The element value isn't found.'; 
 * 2) For all driver commands it shouldn't be the status = false;
 * 
 * If any assumption is false, then will be thrown DriverAssertionError.
 * it means that we prepare a set of assumptions that should be checked during driver commands running.
 * If any of these assumption becomes false, it is thrown the DriverAssertionError.
 * 
 * Another case is an Regular checked and uncheked Exceptions. For example, Connection lost situation
 * should be processed as ordinary subclass of RuntimeException. It is really effect on the any testing system
 * as exceptional situation, so it should be triggered ServerConnectionLostException!!!
 * Just remember the conception of distinguishing Errors, Exceptions and Assertions.
 * 1) For system fatal cases - it should be triggered Error or subclassed from Error classes.
 * 2) For checked Exceptions case, ...
 * 3) For unchecked Exceptions case, ...
 * 4) For AssertionErrors case, ... It replaces assumptions on assertions and reaffirm assumptions.
 * 
 * 
 *  A short review of Assertion in Java:
 *  
 *  1)
 *  An assertion is a Java statement that enables you to assert an assumption about your program. 
 *  An assertion contains a Boolean expression that should be true during program execution. 
 *  Assertions can be used to assure program correctness and avoid logic errors. 
 *  
 *  2)
 *  Assertion should not be used to replace exception handling. 
 *  Exception handling deals with unusual circumstances during program execution. 
 *  Assertions are to assure the correctness of the program. 
 *  Exception handling addresses robustness and assertion addresses correctness. 
 *  Like exception handling, assertions are not used for normal tests, 
 *  but for internal consistency and validity checks. 
 *  Assertions are checked at runtime and can be turned on or off at startup time.
 *  
 *  Link: cs.nyu.edu/courses/spring07/V22.0101-002/17slide.ppt
 *  
*/
public class DriverAssertionError extends AssertionError{
	/**
	 * 
	 */
	private static final long serialVersionUID = 771460503610985409L;

	public DriverAssertionError() {
		// TODO Auto-generated constructor stub
	}
	
	public DriverAssertionError(String msg) {
		super(msg);
	}
}
