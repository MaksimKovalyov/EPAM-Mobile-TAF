package com.epam.mobile.extensions.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;

public class KeyValueTXTParser {
	private PrintStream content = null;
	private String path = null;
	private HashMap<String, String> data = new HashMap<String, String>();
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public KeyValueTXTParser(String path) {
		try {
			FileOutputStream outfos = new FileOutputStream(path);// "Test_output.txt");
			this.content = new PrintStream(outfos);
			this.path = path;
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	private String getContents(File aFile) {
		// ...checks on aFile are elided
		StringBuilder contents = new StringBuilder();

		try {
			// use buffering, reading one line at a time
			// FileReader always assumes default encoding is OK!
			BufferedReader input = new BufferedReader(new FileReader(aFile));
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
	
	private HashMap<String, String> getProperties(File aFile){
			// ...checks on aFile are elided
			StringBuilder contents = new StringBuilder();
			String[] result = null;

			try {
				// use buffering, reading one line at a time
				// FileReader always assumes default encoding is OK!
				BufferedReader input = new BufferedReader(new FileReader(aFile));
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
						result = line.split("=");
						data.put(result[0], result[1]);
						contents.append(System.getProperty("line.separator"));
					}
				} finally {
					input.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			return data;
		
	}

	private void write(String str) {
		content.println(str);
	}

	public static void main(String[] args) {
		KeyValueTXTParser txtParser = new KeyValueTXTParser("txt_test_data.txt");
		txtParser.write("project.name=taram pam pam");
		txtParser.write("project.source=turu ru rum");
		txtParser.write("project.folder=ti tu ti");
		txtParser.write("version=1.55");
		
		System.out.println("entered: " + txtParser.getContents(new File("txt_test_data.txt")));
		HashMap<String, String> result = txtParser.getProperties(new File("txt_test_data.txt"));
		System.out.println("version: " + result.get("version")); //+ " and " + result[1]);
	}

}
