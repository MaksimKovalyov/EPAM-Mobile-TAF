package com.epam.mobile.driver.core;

public abstract class BidirectionalCommand implements Command{
	private String commandResult;

	public abstract boolean execute() throws TAFException; 
	
	public String getCommandResult() {
		return commandResult;
	}
	
	public void setCommandResult(String commandResult) {
		this.commandResult = commandResult;
	}
}
