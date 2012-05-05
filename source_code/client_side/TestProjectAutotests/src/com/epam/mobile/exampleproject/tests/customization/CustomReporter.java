package com.epam.mobile.exampleproject.tests.customization;

import com.epam.mobile.extensions.report.Reporter;

public class CustomReporter extends Reporter{

	private static CustomReporter instance;

	public CustomReporter() throws Exception{
		// TODO Auto-generated constructor stub
	}
	
	public static CustomReporter getInstance() {
		return instance;
	}
	
	public static CustomReporter getInstance(String path_to_file) {
		instance.setPath(path_to_file);
		
		return instance;
	}
	
	static {
		try {
			instance = new CustomReporter();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}	
	
	public synchronized void write(CustomTest tc){
		failTests.println(format(tc));
	}
	
	public synchronized void writePass(CustomTest tc){
		passTests.println("Test case:  " + tc.getAutomatedTest());
	}
	
	private String format(CustomTest tc){
		//tc.print();
		String result = "";
		result += "\n";
		result += "[Automated testcase]       " + tc.getAutomatedTest() + "\n";
		result += "[Excel reference to TC]     " + tc.getExcelReference() + "\n";
		result += "[Excel line number]            " + tc.getLineNumber() + "\n";
		result += "[Step]                                       " + tc.getTestStep() + "\n";
		result += "[Reason]                                 " + tc.getReason() + "\n";
		result += "[Running time]                     " + tc.getTime() + " seconds" + "\n";		
		result += "----------------------------------------------------------------------------------";
		
		return result;
	}

	
}
