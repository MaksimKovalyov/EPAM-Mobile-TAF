package com.epam.mobile.exampleproject.tests.architecture.design.screens.specification;

// The class should provide FULL-RELATED-TO-TESTS-STATUS specification of app model.
// Main criteria for model description: class names and id.
// For example:
// UILabel with text attribute or title label;
// UISegment with accessibilityLabel
// and so on...

// One of issues: how to check order? check order or not? --> 
// create test code for processing order or delegate it to View class.

public class AppModel {
	
	public static class HomeScreen{
		
		public static String getScreenTitle(){
			return "MARKETBOARD";
		}
		
		public static String[] getScreenModel(){
			return new String[]{"MARKETBOARD", "MarketBoardPickerView", 
			         			"LATEST NEWS", "STREETEVENTS"};
		}
	}	

	public static class LoginScreen{
		
		public static String[] getScreenModel(){
			return new String[] {"Enter Username", "Enter Password", "button sign in"};
		}
	}
	
	public static class Search{
		public static String[] getSearchResult(){
			return new String[] {"UITableView", "AutosuggestCell"};
		}		
	}
}
