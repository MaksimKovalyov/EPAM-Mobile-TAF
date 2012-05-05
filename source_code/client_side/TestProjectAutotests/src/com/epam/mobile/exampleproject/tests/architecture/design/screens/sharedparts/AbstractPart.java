package com.epam.mobile.exampleproject.tests.architecture.design.screens.sharedparts;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.epam.mobile.driver.core.NativeDriver;
import com.epam.mobile.exampleproject.tests.architecture.foundation.NativeDriverContext;

public abstract class AbstractPart<T> {

	private T screen;
	private NativeDriverContext context;
	private int default_timeout = 3000;
	
	public AbstractPart() {
		this.context = NativeDriverContext.getInstance();
	}

	public AbstractPart(T screen) {
		this.screen = screen;
		this.context = NativeDriverContext.getInstance();
	}
	
	public T getScreen() {
		return screen;
	}
	
	//public void setScreen(T screen) {
	//	this.screen = screen;
	//}
	
	protected NativeDriver getDriver() {
	    return context.getDriver();
	}
	
    protected void wait(int timeout){
    	getDriver().wait(timeout);
    }
	
	protected void pressEnter(){
		try {
			Robot rb = new Robot();
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
			wait(2*default_timeout);
		}
		catch (AWTException ex) {
			System.err.println("Robot error: the press enter action doesn't work.");
		}
	}
}
