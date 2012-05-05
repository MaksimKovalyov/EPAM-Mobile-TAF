package com.epam.mobile.driver.guard.controllability;

public class AssertionErrorTrigger {

	public void throwValueNotFoundAssertionError(){
		throw new AttributeValueAssertionError();
	}
	
	public void throwError(AssertionError e){
		throw e;
	}
}
