package com.epam.mobile.exampleproject.tests.customization;

import com.epam.mobile.extensions.excel.Driver;
import com.epam.mobile.extensions.excel.ExcelDriver;

public class CustomTest {

	// name of an automated test (the name of java test method)
	private String automatedTest;
	
	// name of manual test or test step that corresponded an automated test
	private String excelReference;
	
	// line number of a manual test in Excel file
	private int lineNumber;
	
	// step before a test fail 
	private String testStep;
	
	// reason of test fail 
	private String reason;
	
	// elapsed time for a test
	private long time;
	
	// result
	private String result;
	
	// elapsed time for a test
	private long startTime;
	
	// elapsed time for a test
	private long endTime;
	
	
	public CustomTest() {
		// TODO Auto-generated constructor stub
	}
	
	public CustomTest(String automatedTest) {
		setAutomatedTest(automatedTest);
	}

	private String[] getExcelTCData(){
		Driver driver = new ExcelDriver();
		 
		String path = ".";
		String name = "Automated Smoke tests - Checklist_March_xx.xls";
		String sheetName = "MarketBoard_1.6";
		
		driver.getWorkBook(path + "/" + name);
		driver.setActiveSheet(sheetName);
		
		String[] result = driver.getCheckDescription(this.getAutomatedTest());
		driver.stop();
		
		return result;
	}
	
	public void setExcelData(){
		try {
			String[] data = getExcelTCData();
			setExcelReference(data[0]);
			setLineNumber(Integer.valueOf(data[1]));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public String getAutomatedTest() {
		return automatedTest;
	}

	public void setAutomatedTest(String automatedTest) {
		this.automatedTest = automatedTest;
	}

	public String getExcelReference() {
		return excelReference;
	}

	public void setExcelReference(String excelReference) {
		this.excelReference = excelReference;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getTestStep() {
		return testStep;
	}

	public void setTestStep(String testStep) {
		this.testStep = testStep;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	public void print(){
		System.out.println(" TC print starts:");
		System.out.println(this.getAutomatedTest());
		System.out.println(this.getExcelReference());
		System.out.println(this.getLineNumber());
		System.out.println(this.getTestStep());
		System.out.println(this.getReason());		
		System.out.println(this.getTime());
		System.out.println(" TC print end.");
	}
	
	public static void main(String[] args) {
		CustomTest test = new CustomTest("isEventsSplitPaneOpenedByTapAnyDay");
		String[] data = test.getExcelTCData();
		System.out.println("data 0: " + data[0] + " data 1: " + data[1]);
	}
}
