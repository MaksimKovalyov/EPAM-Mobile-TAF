package com.epam.mobile.extensions.db;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class Report {
	private static Report instance;
	private PrintStream content = null;
	private PrintStream testsSummary = null;
//	private  document = null;
	private String path = "";
	private String pathSummary = "";
	
	private PrintStream getContent() {
		if (content == null) {
			getInstance().setPath("test.txt");
		}
		return content;
	}

	private Report() throws Exception {
	}
	
	public static Report getInstance() {
		return instance;
	}

	static {
		try {
			instance = new Report();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}	
	
	public void setPath(String path) {
		this.path = path;
		
		try {
			FileOutputStream outfos = new FileOutputStream(path);//"Test_output.txt");
			content = new PrintStream(outfos);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}	
	
	public void setPathSummary(String path) {
		this.pathSummary = path;
		
		try {
			FileOutputStream out = new FileOutputStream(path);//"Tests_summary.txt");
			testsSummary = new PrintStream(out);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void start(){
		getContent().println("                                Output of Active Connections Tests.");
		content.println("                                                           Time: " + DateUtils.now());
	}

	public synchronized void write(String str){
		getContent().println(str);
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
		getContent().println("");
		content.println("   Report is finished.");
		content.println("   File:     " + path);		
		content.println("   Summary: " + pathSummary);
		content.close();
		//testsSummary.println("");
		//testsSummary.println("   Report is finished.");
		//testsSummary.close();
	}

}
