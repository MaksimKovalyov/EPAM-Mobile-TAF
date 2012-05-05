package com.epam.mobile.exampleproject.tests.architecture.foundation;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.epam.mobile.driver.core.NativeDriver;

public abstract class AbstractScreen<T> {
    private NativeDriverContext context;
    private String currentScreen;
	private T screen;

    private static int default_timeout = 5000;

    protected AbstractScreen(String screenLocator) {
        this.currentScreen = screenLocator;
        setContext(NativeDriverContext.getInstance());
        init();
        parsePage();
    }

    private void setContext(NativeDriverContext instance) {
        this.context = instance;
    }

    public T getScreen(){
    	return screen;
    }
    
    protected abstract void init();
    protected abstract void parsePage();

    public String getCurrentPage() {
    	return this.currentScreen;//context.getAppURL() + 		
    }

    protected NativeDriver getDriver() {
        return context.getDriver();
    }

    // TODO: add getLocation method for defining Screen on the Device
    // optional
    protected String getLocation() {
    	String result = null;
    	
    	//context.getDriver().; 	
    	//context.getDriver().getElementValue(currentScreen, "text");
    	
        return result;
    }
    
    // service methods:
    // .......................................................................
    protected void wait(int timeout){
    	getDriver().wait(timeout);
    }
    
    // why do you use this solution?
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