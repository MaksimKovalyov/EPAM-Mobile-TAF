package com.epam.mobile.driver.guard.controllability;

import com.epam.mobile.driver.core.Constants;

// refactore: simplify logic for enable condition
public class Guard {
	
	private boolean enable;

	private AssertionErrorTrigger assertionTrigger;
	private ExceptionTrigger exceptionTrigger;
	private AssertionErrorHandler assertionHandler;
	private ExceptionHandler exceptionHandler;
	
	public Guard() {
		enable = true;
		
		assertionHandler = new AssertionErrorHandler();
		assertionTrigger = new AssertionErrorTrigger();
		exceptionHandler = new ExceptionHandler();
		exceptionTrigger = new ExceptionTrigger();
	}
	
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public AssertionErrorTrigger getAssertionTrigger() {
		return assertionTrigger;
	}
	public ExceptionTrigger getExceptionTrigger() {
		return exceptionTrigger;
	}
	public AssertionErrorHandler getAssertionHandler() {
		return assertionHandler;
	}
	public ExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}
	
	public void fireAssertionError(AssertionError e){
		getAssertionTrigger().throwError(e);
	}
	
	public void inspect(String cmdResult){
		if (isEnable()){
			if (cmdResult.equalsIgnoreCase(Constants.VALUE_NOT_FOUND)){
				fireAssertionError(new AttributeValueAssertionError("The attribute value is not found."));
			}
			if (cmdResult.equalsIgnoreCase(Constants.COMMAND_NOT_VALID)){
				fireAssertionError(new DriverAssertionError("The command is not valid."));
			}
			if (cmdResult.equalsIgnoreCase(Constants.ELEMENT_NOT_FOUND)){
				fireAssertionError(new ElementExistAssertionError("The element is not found."));
			}
		}else{
			System.out.println("[WARNING] Guard is not enable.");
		}
	}
	
	public void inspect(String cmdResult, AssertionError e){
		if (isEnable()){
			if (cmdResult.equalsIgnoreCase(Constants.ELEMENT_NOT_FOUND)){
				fireAssertionError(e);
			}
		}else{
			System.out.println("[WARNING] Guard is not enable.");
		}
	}
	
	public void inspectPopUp(String popUpLabel){
		if (isEnable()){
			if (!popUpLabel.equalsIgnoreCase(Constants.VALUE_NOT_FOUND)){
				// force close pop-up - replace to handling section
				//new HomeScreen().tapLogoutButton();
				// fire assertion
				fireAssertionError(new PopUpExistAssertionError("Pop up is not closed."));
			}
		}else{
			System.out.println("[WARNING] Guard is not enable.");
		}
	}
}