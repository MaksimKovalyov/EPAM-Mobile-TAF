package com.epam.mobile.extensions.report;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class Reporter {
	private static Reporter instance;
	private PrintStream content = null;
	private PrintStream testsSummary = null;
//	private  document = null;
	private String path = "";
	private String pathSummary = "";
	
	private Reporter() throws Exception {
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
			//System.exit(-1);
		}
	}	
	
	public void setPath(String path_to_file) {
		this.path = path_to_file;
		
		initFileIOStreams();		
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
		this.pathSummary = path;
		
		try {
			FileOutputStream out = new FileOutputStream(this.pathSummary);//"Tests_summary.txt");
			testsSummary = new PrintStream(out);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void start(){
		content.println("                                Output of Active Connections Tests.");
		content.println("                                                           Time: " + DateUtils.now());
	}

	public synchronized void write(String str){
		content.println(str);
		content.println("                                                           Time: " + DateUtils.now());
	}

	public synchronized void writeTest(String s){
		testsSummary.println(s);
		testsSummary.println("                                                           Time: " + DateUtils.now());
	}
	
/*	
	public void read(int registration){
		String line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(this.path)));//"Test Output.html");
			while ((line = br.readLine()) != null){
				if (line.cont)
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		//content.println(str);
		//content.println("                                                           Time: " + DateUtils.now());
	}
*/	
	public void end(){
		content.println("");
		content.println("   Report is finished.");
		content.close();
		//testsSummary.println("");
		//testsSummary.println("   Report is finished.");
		//testsSummary.close();
	}
	
	public static void main(String[] args) {
		Reporter.getInstance().write("dsds");
	}
}