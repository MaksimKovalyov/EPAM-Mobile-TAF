package com.epam.mobile.extensions.excel;

import com.extentech.ExtenXLS.WorkBookHandle;
import com.extentech.ExtenXLS.WorkSheetHandle;
import com.extentech.formats.XLS.WorkSheetNotFoundException;

public interface Driver {

	public String read();
	
	public void write(String cell);
	
	public void createBook(String name);
	public void createBook(String path, String name);
	public void writeSheet(String name);
	
	public void setActiveSheet(String name);
	public void setActiveSheet(int number);
	
	public WorkSheetHandle getSheetBy(String name) throws WorkSheetNotFoundException;
	public WorkSheetHandle getSheetBy(int number) throws WorkSheetNotFoundException;
	
	public WorkBookHandle getWorkBook();
	public WorkBookHandle getWorkBook(String filePath);
	
	public void addCell(String cell, String value);
	public void addRaw(String raw, String... values);
	public void addCol(String col, String... values);
	
	public void print();
	
	public void stop();
	public void pause();
	
}
