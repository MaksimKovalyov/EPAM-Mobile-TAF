package com.epam.mobile.testing.utils;

import org.junit.Assert;

public class GroupTestMethods {

	public void doSomethingEarlier(){
		System.out.println("Do something earlier.");
	}
	
	public void isPersonalPortfolioOpen(){
		System.out.println("[LOG] Test " + new Object(){}.getClass().getEnclosingMethod().getName() + " starts...");
		
		System.out.println("action 1");
		System.out.println("action 2");
		System.out.println("action 3");
	
		Assert.assertTrue(true);
	}
	
	public void isPersonalWatchlistOpen(){
		System.out.println("[LOG] Test " + new Object(){}.getClass().getEnclosingMethod().getName() + " starts...");

		System.out.println("action 1");
		System.out.println("action 2");
		System.out.println("action 3");

		Assert.assertTrue(false);
	}
}
