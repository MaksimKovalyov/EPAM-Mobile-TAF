package com.epam.mobile.testing.utils;

import com.epam.mobile.driver.core.NativeDriver;
import com.epam.mobile.driver.core.iPhoneNativeDriver;

public class Screen {

	private static int timeout = 3000;
	private static NativeDriver driver_ = new iPhoneNativeDriver();
	
	public static void scrollRight(String locator){
		try {
			driver_.scrollRight(locator);
			driver_.wait(timeout);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	public static void scrollLeft(String locator){
		try {
			driver_.scrollLeft(locator);
			driver_.wait(timeout);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}		
}
