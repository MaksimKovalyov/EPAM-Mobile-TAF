package com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.design.screens.sharedparts;

import com.epam.mobile.testing.utils.UIMap;

public class Search<T> extends AbstractPart<T>{
	
	// getters:
	// .....................................................................
	public String[] getSearchResult() {
		String[] searchResult = new String[2];

		searchResult[0] = getDriver().getElementValue(
							UIMap.UISearch.view_searchresult, "class");
		searchResult[1] = getDriver().getElementValue(
							UIMap.UISearch.cell_searchresult, "class");

		return searchResult;
	}
	
	// actions:
	// .....................................................................
	public T tap(){
		getDriver().touch(UIMap.UISearch.view_searchresult);
		return getScreen();
	}
}