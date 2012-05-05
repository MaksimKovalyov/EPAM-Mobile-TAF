package com.epam.mobile.driver.core;

import com.epam.mobile.driver.guard.controllability.ServerConnectionLostException;

public abstract class BidirectionalCommand implements Command{
	private String commandResult;

	public abstract boolean execute() throws ServerConnectionLostException; 
	
	public String getCommandResult() {
		return commandResult;
	}
	
	public void setCommandResult(String commandResult) {
		this.commandResult = commandResult;
	}
}
