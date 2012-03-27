package com.epam.mobile.extensions.report;

import org.apache.log4j.Level;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

// remove demo log independently from current set up level
public class DemoLevelFilter extends Filter{

	public DemoLevelFilter() {
		// TODO Auto-generated constructor stub
	}
	
	public int decide(LoggingEvent event) {
	    int result = Filter.ACCEPT;
	    
	    Level set_up_level = event.getLogger().getLevel();
	    //System.out.println("set up level:" + set_up_level);
	    
	    int currentLevelInt = event.getLevel().toInt();
	    //System.out.println(" current level: " + currentLevel);
	    //System.out.println(" current int level: " + currentLevelInt);
	    //checking if the message object is of correct type
	    if (set_up_level.toInt() == CustomLevels.DEMO_TRACE_INT) {
	        result = Filter.NEUTRAL;
	    }else{
	    	if (currentLevelInt == CustomLevels.DEMO_TRACE_INT){
	    		result = Filter.DENY;
	    	}
	    }
	    
	    /*else {
	      //this filter can ignore this, pass to next filter
	      result = this.DENY;
	    }*/

	    return result;
	}
}
