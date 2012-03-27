package com.epam.mobile.extensions.testdata.implementations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

import com.epam.mobile.extensions.testdata.TestData;
import com.epam.mobile.extensions.testdata.TestDataFactory;

public class TXTTestDataFactory implements TestDataFactory{
	
	private static final String DEFAULT_TXT_FILE = "txt_test_data.txt";
	private static String TXT_FILE;
	
	private PrintStream content = null;
//	private String path = null;
	private HashMap<String, String> data = new HashMap<String, String>();
	
	private static TXTTestDataFactory instance;
	
	private TXTTestDataFactory() throws Exception {
	}

	public static TXTTestDataFactory getInstance() {
		return instance;
	}

	static {
		try {
			instance = new TXTTestDataFactory();
		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(-1);
		}
	}	
	
	public TestData create() {
		TXT_FILE = DEFAULT_TXT_FILE;
		initialization();
		data = getProperties(TXT_FILE);
		
		return new TXTTestData(this.data);
	}

	public TestData create(String tdObjectPath) {
		TXT_FILE = tdObjectPath;
		initialization();
		data = getProperties(TXT_FILE);
		
		return new TXTTestData(this.data);
	}
	
	public TestData read() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public TestData write(String dataLine) {
		content.println(dataLine);
		String[] result = dataLine.split("=");
		data.put(result[0], result[1]);
		
		return new TXTTestData(this.data);
	}
	
	private void initialization(){
/*
		String path = TXT_FILE;
		try {
			FileOutputStream outfos = new FileOutputStream(path);// "Test_output.txt");
			this.content = new PrintStream(outfos);
			this.path = path;
		} catch (Exception e) {
			// TODO: handle exception
		}
*/
	}
	
	private HashMap<String, String> getProperties(String path){
		// ...checks on aFile are elided
//		StringBuilder contents = new StringBuilder();
		String[] result = null;

		try {
			// use buffering, reading one line at a time
			// FileReader always assumes default encoding is OK!
			BufferedReader input = new BufferedReader(new FileReader(new File(path)));
			try {
				String line = null; // not declared within while loop
				/*
				 * readLine is a bit quirky : it returns the content of a line
				 * MINUS the newline. it returns null only for the END of the
				 * stream. it returns an empty String if two newlines appear in
				 * a row.
				 */
				while ((line = input.readLine()) != null) {
//					contents.append(line);
					result = line.split("=");
					data.put(result[0], result[1]);
//					contents.append(System.getProperty("line.separator"));
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return data;
}

	
	@SuppressWarnings("unused")
	private String getContents(String path) {
		// ...checks on aFile are elided
		StringBuilder contents = new StringBuilder();

		try {
			// use buffering, reading one line at a time
			// FileReader always assumes default encoding is OK!
			BufferedReader input = new BufferedReader(new FileReader(new File(path)));
			try {
				String line = null; // not declared within while loop
				/*
				 * readLine is a bit quirky : it returns the content of a line
				 * MINUS the newline. it returns null only for the END of the
				 * stream. it returns an empty String if two newlines appear in
				 * a row.
				 */
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return contents.toString();
	}
}
