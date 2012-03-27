package com.epam.mobile.extensions.testdata;

public interface TestDataFactory {
	public TestData create();
	public TestData create(String tdObjectPath);
	
	public TestData read();
	public TestData write(String dataLine);
}
