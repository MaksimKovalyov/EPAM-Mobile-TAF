package com.epam.mobile.testing.utils;

public class TestAppModel {
	
	public String[] ModelPart = null; 
	
	public TestAppModel(String type) {
		if (type.equalsIgnoreCase("Expected"))
			System.out.println("Expected Model is initialised.");
	}

	public String[] getModelPart() {
		ModelPart = new String[]{"ModelPartValue_1", "v_2"};
		return ModelPart;
	}
	
	
	
}
