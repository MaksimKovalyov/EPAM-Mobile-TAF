package com.epam.mobile.taf.core;

/*
 * 
 * add all jobs into class related to:
 *  - set up all factories;
 *  - set up all env variables;
 *  - set up other;
 *  - create all default objects;
 *  - init all default states;
 *  - finally: make workable concrete TAF assembly
 *  Extra:
 *   - add registration option.
 *   - add default state.
 *   
 *   add build entity for accumulating 
 *   all significant build information as:
 *   libs set, unit testing set, other.
 *   
 *   
 *   What should be the default states of TAF expression?
 *   
 */

public class MounterMachine {

	private Strategy initialization;
	
	private void setUp(){
		initialization = new InitiateDefaultState();
	}

	public void setUp(Strategy strategy){
		initialization = strategy;
	}
	
	public boolean build(){	
		setUp();
		initialization.execute();
		
		return true;
	}
	
	public void changeBuildScheme(){
		initialization.execute();
	}
	
	public void changeBuildScheme(Strategy strategy){
		setUp(strategy);
		changeBuildScheme();
	}
	
}
