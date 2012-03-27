package com.epam.mobile.driver.core;

public interface Command {
	public abstract boolean execute() throws TAFException; 
}
