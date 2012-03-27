package com.epam.mobile.driver.probationproject.exampleproject.tests.architecture.design.screens.sharedparts;

public interface ISearch<T> {
	abstract T tapSearchButton();

	abstract String[] getSearchResult();
	
}
