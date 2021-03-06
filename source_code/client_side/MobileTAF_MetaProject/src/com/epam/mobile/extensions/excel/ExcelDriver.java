package com.epam.mobile.extensions.excel;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.extentech.ExtenXLS.CellHandle;
import com.extentech.ExtenXLS.WorkBookHandle;
import com.extentech.ExtenXLS.WorkSheetHandle;
import com.extentech.formats.XLS.WorkSheetNotFoundException;

public class ExcelDriver implements Driver{

	private WorkBookHandle book = null;
	private WorkSheetHandle sheet = null;
	private String path = "";
	
	public ExcelDriver() {
		book = new WorkBookHandle();
				
		//      IMPORTANT PERFORMANCE SETTINGS!!!
        //book.setDupeStringMode(WorkBookHandle.SHAREDUPES);
        //book.setStringEncodingMode(WorkBookHandle.STRING_ENCODING_COMPRESSED);
        
        //System.out.println("ExtenXLS Version: " + WorkBookHandle.getVersion());
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	// issue: correct process all streams closing;
	public void createBook(String name) {
        try{
      	    File f = new File(path + "/" + name);
            FileOutputStream fos = new FileOutputStream(f);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            book.writeBytes(bos);
            
            bos.flush();
		    fos.close();
      	} catch (java.io.IOException e){
      		System.out.println("[LOG][ERROR] Exception: " + e.toString());;
      	}  
	}
	

	public void createBook(String path, String name) {
        setPath(path);
		createBook(name);  
	}
	
	public WorkBookHandle getWorkBook() {
		
		return book;
	}
	
	public WorkBookHandle getWorkBook(String filePath) {
		book = new WorkBookHandle(filePath);
		
		return book;
	}
	
	public void setActiveSheet(int number) {
		try {
			sheet = getSheetBy(number);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void setActiveSheet(String name) {
		try {
			sheet = getSheetBy(name);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void addCell(String cell, String value) {
		sheet.add(value, cell);
	}
	
	public void writeSheet(String name) {
		sheet = book.createWorkSheet(name);
	}
	
	public WorkSheetHandle getSheetBy(String name) throws WorkSheetNotFoundException{
		
		return book.getWorkSheet(name);
	}

	public WorkSheetHandle getSheetBy(int number) throws WorkSheetNotFoundException{
		
		return book.getWorkSheet(number);
	}

	
	public String read() {

		return null;
	}
	
	public void write(String cell) {

	}
	
	public void addCol(String col, String... values){
		for (int i = 0; i < values.length; i++) {
			addCell(col + String.valueOf(i+1), values[i]);
		}
	}

	public void addRaw(String raw, String... values){
		for (int i = 0; i < values.length; i++) {
			addCell(intToLetter(i+1) + raw, values[i]);
		}
	}

	public static String intToLetter(int number){
		int codeOfLetterA = 65;
		int codeOfNeedLetter = codeOfLetterA + number -1;
		char result = (char)codeOfNeedLetter;
		
		System.out.println("[LOG] code: " + codeOfNeedLetter + 
				" number: " + number + 
				" result: " + result);
		
		return String.valueOf(result);
	}
	
	public void print() {
		int rows = sheet.getNumRows();
		int cols = 6;//sheet.getNumCols();
		System.out.println(" A sheet " + sheet.getSheetName() + " of size=["+ rows + ", " + cols + "]");
		System.out.println("--------------------------------------------------");
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				try {
					CellHandle cell = sheet.getCell(i, 3);
					/*
					if ((j == 0)&&(i != 0)&&(i != 1)) {
					    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						System.out.print(" " + sdf.format(DateConverter.getDateFromCell(cell)));
						
					}else{
						System.out.print(" " + cell.getStringVal());
					}*/
					System.out.print(" " + cell.getStringVal());
				} catch (Exception e) {
					System.out.println("[LOG][ERROR] Exception" + e.toString());
				}
			}
			System.out.println();
		}
	}
	
	public void pause(){
		try {
			System.out.println("[LOG] Driver has been paused. Timeout: 5000" );
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("[LOG][ERROR] Exception: " + e.toString());
		}
	}
	
	public String[] getCheckDescription(String testName){
		//testName = "isLoginValid";
		boolean isTestNotFound = true;
		String[] result = new String[]{"VALUE NOT FOUND", "VALUE NOT FOUND"};
		int rows = sheet.getNumRows();
		rows = 440;
		int row = 1;
		int cols = sheet.getNumCols();//sheet.getNumCols();
		System.out.println(" A sheet " + sheet.getSheetName() + " of size=["+ rows + ", " + cols + "]");
		System.out.println("--------------------------------------------------");
		
		while (isTestNotFound && (row < rows)) {
			try {
				CellHandle cell = sheet.getCell(row, 4);	
				//System.out.println(" " + cell.getStringVal() + " at row: " + row);
				
				if (cell.getStringVal().equalsIgnoreCase(testName) ||
						cell.getStringVal().contains(testName)){
					isTestNotFound = false;
					
					// test check:
					CellHandle cell_check = sheet.getCell(row, 1);	
					//System.out.println(" " + cell_check.getStringVal() + " at row: " + row);
					result[0] = cell_check.getStringVal();
					result[1] = String.valueOf(row + 1);
				}
				
				row++;
			} catch (Exception e) {
				System.out.println(e.toString());
				break;
			}
			
		}
		/*
		for (int i = 0; i < rows; i++) {
			try {
					CellHandle cell = sheet.getCell(i, 3);
					/*
					if ((j == 0)&&(i != 0)&&(i != 1)) {
					    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						System.out.print(" " + sdf.format(DateConverter.getDateFromCell(cell)));
						
					}else{
						System.out.print(" " + cell.getStringVal());
					}*/
		/*			System.out.print(" " + cell.getStringVal());
			} catch (Exception e) {
				System.out.println("[LOG][ERROR] Exception" + e.toString());
			}
			System.out.println();
		}*/
		
		return result;
	}
	
	public void stop() {
		book = null;
		sheet = null;
	}
	
	public static void main(String[] args) {
		
		Driver driver = new ExcelDriver();
		String path = ".";
		//String file = "C:/experiments/OpenXLSProject/work_dir/test.xls";
		String name = "Automated Smoke tests - Checklist_March_26_2.xls";;
		String sheetName = "ExampleProject";

/*		
		// create test sheet
		driver.writeSheet(sheetName);
		//driver.addCell("A3", "Test table name.");
		driver.addCol("A", " ", " ", "1", "1", "1", "1", "1");
		driver.addCol("B", " ", " ", "0", "0", "0", "0", "0");
		driver.addCol("C", "Test table name.", " ", "1", "1", "1", "1", "1");
		driver.addCol("D", " ", " ", "0", "0", "0", "0", "0");
		driver.addCol("E", " ", " ", "1", "1", "1", "1", "1");
		driver.addCol("F", " ", " ", "0", "0", "0", "0", "0");
		driver.addCol("G", " ", " ", "1", "1", "1", "1", "1");
		driver.addRaw("2", "CName_1", "CName_2", "CName_3", "CName_4", "CName_5", "CName_6", "CName_7");
		
		driver.createBook(path, name);
		
		driver.pause();
*/		
		// read created test sheet
		driver.getWorkBook(path + "/" + name);
		driver.setActiveSheet(sheetName);
		String[] result = driver.getCheckDescription("isRealTimeListValid");
		driver.stop();
		System.out.println(" res 1: " + result[0]);
		System.out.println(" res 2: " + result[1]);
	}
}