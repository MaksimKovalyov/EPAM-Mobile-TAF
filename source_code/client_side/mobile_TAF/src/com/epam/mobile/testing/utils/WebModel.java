package com.epam.mobile.testing.utils;

import com.epam.mobile.driver.core.Constants;

public class WebModel {

	public String[] LoginScreen = null;
	
	public WebModel(String population) {
		if (population.equalsIgnoreCase(Constants.FULL)) {
			init_modal();
		}else{
			init_empty_modal();
		}
	}

	private void init(){
		
	}
	
	private void init_modal(){
		init();
	}

	private void init_empty_modal(){
	}
		
	
}
