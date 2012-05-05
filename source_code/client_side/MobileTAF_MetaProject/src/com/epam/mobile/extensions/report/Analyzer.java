package com.epam.mobile.extensions.report;

import java.io.BufferedReader;
import java.util.ArrayList;

//import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

// The class needs for analyzing logs after TAF run, post-process phase
// TODO: in progress...
public class Analyzer {
	// 10 min = 600 seconds - 600 points as seconds, 
	// passRegs[i] - number of pass registrations
	ArrayList<String[]> regsInfo = new ArrayList<String[]>();
	BufferedReader br = null;
	String content = null;
	String path = null;
	String contentLine = null;
	
	public Analyzer(String path) {
		try {
			this.path = path;
			//content = FileUtils.readFileToString(new File(path));
			//br = new BufferedReader(new FileReader(new File(path)));	
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void clear(){
		content = "";
	}
	
	public boolean isExistPairOf(String key, String value, String separator){
		boolean result = false;
		System.out.println("[LOG] key: " + key + " value: " + value + " separator: " + separator);
		
		result = StringUtils.contains(content, key + separator + value);
		System.out.println("result: " + result);
		
		return result;
	}
	
	public ArrayList<String[]> getRegistrationsInfo(){
	// search for "[Test #id][Registration id] summary, pass = number1 fail = number2
	// search for "[Test #id][Registration id] Registration time end:"		
		String text = "";
	// {id, number of fail attempts}
		
		try {
			while ((text = br.readLine()) != null){
				if (text.contains("summary")){
					String[] registrationInfo = new String[3];
					registrationInfo[0] = "fail";
					registrationInfo[1] = getId(text);
					registrationInfo[2] = getFails(text);
					regsInfo.add(registrationInfo);
					System.out.println(registrationInfo[0]+registrationInfo[1]+registrationInfo[2]);
				}
				if (text.contains("Registration time end: ")){
					String[] registrationInfo = new String[3];
					registrationInfo[0] = "endTime";
					registrationInfo[1] = getId(text);
					registrationInfo[2] = getEndTime(text);
					regsInfo.add(registrationInfo);
					System.out.println(registrationInfo[0]+registrationInfo[1]+registrationInfo[2]);
				}			
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return regsInfo;
	}
	
	private String getId(String expression){
	
		return expression.split("#")[1].split("]")[0];
	}
	
	private String getFails(String expression){
		
		return expression.split("fail = ")[1].split("faultString")[0];
	}
	
	private String getEndTime(String expression){
		
		return expression.split("Registration time end: ")[1];
	}
	
	public void printRegsStatistic(){
		String[] regInfo = new String[3];
		for (int i = 0; i < regsInfo.size(); i++) {
			regInfo = (String[])regsInfo.get(i);
			if (regInfo[0].equalsIgnoreCase("fail"))
				System.out.println("key: " + regInfo[0] + "   | id: " + regInfo[1] +
					" | number of fail attempts: " + regInfo[2]);
			else
				System.out.println("key: " + regInfo[0] + "| id: " + regInfo[1] +
						" | time                   : " + regInfo[2]);
		}
	}
	
	public static void main(String[] args) {
		Analyzer an = new Analyzer("Test output.html");
		an.getRegistrationsInfo();
		an.printRegsStatistic();
	}
}
