package com.epam.mobile.driver.core;

import com.epam.mobile.driver.guard.controllability.ServerConnectionLostException;

public interface Command {
	public abstract boolean execute() throws ServerConnectionLostException; 
}
