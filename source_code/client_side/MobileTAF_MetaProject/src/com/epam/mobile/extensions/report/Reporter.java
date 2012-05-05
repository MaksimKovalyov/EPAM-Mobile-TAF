package com.epam.mobile.extensions.report;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class Reporter {
	private static Reporter instance;
	
	private static final String OUTPUT_FOLDER_PATH = "output";
	private static final String SHORT_SUMMARY = "short_summary.txt";
	private static final String FAIL_TESTS = "fail_tests.txt";
	private static final String PASS_TESTS = "pass_tests.txt";
	
	private static final String REPORT_PATH = OUTPUT_FOLDER_PATH + 
											  "/" + 
											  SHORT_SUMMARY;
	private static final String FAILS_REPORT_PATH = OUTPUT_FOLDER_PATH + 
											  "/" + 
											  FAIL_TESTS;
	private static final String PASSS_REPORT_PATH = OUTPUT_FOLDER_PATH + 
											  "/" + 
											  PASS_TESTS;;
											  
	private PrintStream content = null;
	protected PrintStream failTests = null;
	protected PrintStream passTests = null;

	private PrintStream testsSummary = null;
//	private  document = null;
	private String path = "";
	//private String pathSummary = "";
	
	protected Reporter() throws Exception {
	}
	
	public static Reporter getInstance() {
		return instance;
	}
	
	public static Reporter getInstance(String path_to_file) {
		instance.setPath(path_to_file);
		
		return instance;
	}

	static {
		try {
			instance = new Reporter();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}	
	
	public void defaultInit(){
		this.setPath(REPORT_PATH);
		this.writeHeader();
		
		// write fail tests
		try {
			FileOutputStream os = new FileOutputStream(FAILS_REPORT_PATH);
			failTests = new PrintStream(os);
			
			FileOutputStream os_ = new FileOutputStream(PASSS_REPORT_PATH);
			passTests = new PrintStream(os_);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void setPath(String path_to_file) {
		this.path = path_to_file;
		
		initFileIOStreams();		
	}
	
	private void writeHeader(){
		content.println("                                                      Short Summary \n");
		content.println(" autogenerated by script ");
		content.println(" " + DateUtils.getMediumFormatNow() + " ");
		content.println("=================================================================");
		content.println();
	}
	
	private void initFileIOStreams(){
		try {
			FileOutputStream outfos = new FileOutputStream(this.path);//"Test_output.txt");
			this.content = new PrintStream(outfos);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void setPathSummary(String path) {
		try {
			FileOutputStream out = new FileOutputStream(path);
			testsSummary = new PrintStream(out);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	public void start(){
		content.println("                                Output of Active Connections Tests.");
	}

	public synchronized void write(String str){
		content.println(str);
	}

	public synchronized void writeFile(String path){
		try {			
			InputStream input = new FileInputStream(path);
			int data = 0;
			byte[] chunk = new byte[128];
			//int read = 0;
			String str = "";
			
			while(data != -1) {
				data = input.read (chunk, 0, 128);

				if (data != -1) {
					str = new String(chunk, 0, data);
					//str += tmp;
					//System.out.println("tmp:" + tmp);
					content.print(str);
				}
				
			  
			  //data = input.read();
			}
			
			input.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	
	
	public synchronized void writeTest(String s){
		testsSummary.println(s);
	}

	public void end(){
		content.println("");
		content.println("   Report is finished.");
		content.close();
	}
}