package com.epam.mobile.exampleproject.tests.architecture.design.screens.sharedparts;

public interface ISearch<T> {
	abstract T tapSearchButton();

	abstract String[] getSearchResult();
	
}
