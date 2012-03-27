package com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.design.screens;

import com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.design.screens.sharedparts.ISearch;
import com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.design.screens.sharedparts.Search;
import com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.foundation.AbstractScreen;
import com.epam.mobile.testing.utils.UIMap;

public class HomeScreen extends AbstractScreen<HomeScreen> 
						implements ISearch<HomeScreen> {
	private static final String SCREEN_LOCATOR = "";
	private Search<HomeScreen> search;
	
	public HomeScreen() {
		super(SCREEN_LOCATOR);
	}

	protected void init() {
		search = new Search<HomeScreen>();
	}

	protected void parsePage() {
	}

	// getters:
	// ...............................................................
	public String getScreenTitle() {
		return getDriver().getElementValue(
				SCREEN_LOCATOR + UIMap.HomeScreen.label_project, "text");
	}

	public String[] getScreenModel() {
		String[] homeScreen = new String[4];

		homeScreen[0] = getDriver().getElementValue(
				UIMap.HomeScreen.label_project,	"text");
		homeScreen[1] = getDriver().getElementValue(
				UIMap.HomeScreen.project_somesection, "className");
		homeScreen[2] = getDriver().getElementValue(
				UIMap.HomeScreen.label_some1, "text");
		homeScreen[3] = getDriver().getElementValue(
				UIMap.HomeScreen.label_some2, "text");

		return homeScreen;
	}
		
	public String getLabelLogoutButton(){	
		return getDriver().getElementValue(
							UIMap.HomeScreen.segment_logout, "accessibilityLabel");
	}
	

	// search:
	public String[] getSearchResult() {		
		return search.getSearchResult();
	}
	
	
	// actions:
	// ...............................................................
	public LoginScreen logout() {
		getDriver().touch(SCREEN_LOCATOR + UIMap.HomeScreen.segment_logout);
		return new LoginScreen();
	}

	
	// search
	public HomeScreen tapSearchButton(){
		search.tap();
		return this;
	}	
}
