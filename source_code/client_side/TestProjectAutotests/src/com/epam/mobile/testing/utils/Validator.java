package com.epam.mobile.testing.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.epam.mobile.driver.core.Constants;
import com.epam.mobile.extensions.report.DateUtils;
import com.thoughtworks.selenium.DefaultSelenium;

@SuppressWarnings("unused")
public class Validator {

	// Date related attributes, iPad case
	private String criteria;
	private String period;
	
	// Date related attributes, iPad case
	private String[] unformatted_expecteds;  
	
	private String current_day;
	
	private long max_2_Days_interval;
	private long max_5_Days_interval;
	private long max_1_Month_interval;
	private long max_3_Months_interval;
	private long max_6_Months_interval;
	private long max_1_Year_interval;
	private long max_3_Years_interval;
	private long max_5_Years_interval;
	
	private String date_format;
	private String formatted_date;
	
	private String day;
	private String first_day;
	private String second_day;
	
	private String[] actuals;
	private String[] expecteds;
	
	
	public Validator() {
		// set up criterias <= 5 Days
		getDateFromWebSite();
	
		setUpIntervals();
		// set up criterias > 5 Days
	}
	
	private void setUpIntervals(){
		current_day = unformatted_expecteds[0];
		try {
		Calendar first_day_in_cal = Calendar.getInstance();
		Calendar second_day_in_cal = Calendar.getInstance();
		 
		// Set the date for both of the calendar instance, 2 Days case
		first_day_in_cal.setTime(new SimpleDateFormat("MM/dd/yyyy").parse(unformatted_expecteds[1]));    //set(2006, 12, 30);
		second_day_in_cal.setTime(new SimpleDateFormat("MM/dd/yyyy").parse(unformatted_expecteds[2]));   //set(2007, 5, 3);
		max_2_Days_interval = (second_day_in_cal.getTimeInMillis() - 
									first_day_in_cal.getTimeInMillis() ) / (24 * 60 * 60 * 1000);
		
		// 5 Days case
		first_day_in_cal.setTime(new SimpleDateFormat("MM/dd/yyyy").parse(unformatted_expecteds[3]));    //set(2006, 12, 30);
		second_day_in_cal.setTime(new SimpleDateFormat("MM/dd/yyyy").parse(unformatted_expecteds[4]));   //set(2007, 5, 3);
		max_5_Days_interval = (second_day_in_cal.getTimeInMillis() - 
									first_day_in_cal.getTimeInMillis() ) / (24 * 60 * 60 * 1000);
		
		// clarify for months case
		
		// 1 Year case
		current_day = unformatted_expecteds[0];
		String second_date_year = new SimpleDateFormat("yyyy").format(current_day);
		String first_date_year = String.valueOf(Integer.parseInt(second_date_year) - 1);
		String one_year_before = new SimpleDateFormat("MM/dd/").format(current_day) + first_date_year;

		first_day_in_cal.setTime(new SimpleDateFormat("MM/dd/yyyy").parse(one_year_before));    //set(2006, 12, 30);
		second_day_in_cal.setTime(new SimpleDateFormat("MM/dd/yyyy").parse(current_day));   //set(2007, 5, 3);
		max_1_Year_interval = (second_day_in_cal.getTimeInMillis() - 
									first_day_in_cal.getTimeInMillis() ) / (24 * 60 * 60 * 1000);
		
		
/*		
		// Get the represented date in milliseconds
		long milis1 = first_day_in_cal.getTimeInMillis();
		long milis2 = second_day_in_cal.getTimeInMillis();
		 
		
		// Calculate difference in milliseconds
		long diff = milis2 - milis1;
		 
		// Calculate difference in seconds
		long diffSeconds = diff / 1000;
		 
		// Calculate difference in minutes
		long diffMinutes = diff / (60 * 1000);
		 
		// Calculate difference in hours
		long diffHours = diff / (60 * 60 * 1000);
		 
		// Calculate difference in days
		long diffDays = diff / (24 * 60 * 60 * 1000);
*/
		
		//max_2_Days_interval =
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
		
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}



	// unstrict validation
	// just check
	public static boolean isTimePeriodValidFormat(String[] dates){
		boolean result = false;
		
		try {
			if (isValidDate(dates[0]) && isValidDate(dates[1])) {
				result = true;
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}

	/*
	 * 1D:  Mar 19, 2012 4:35 PM - 6:10 PM
	 * 5D:  Mar 8, 2012 8:15 PM - Mar 19, 2012 6:15 PM
	 * 30D: Feb 22, 2012 - Mar 16, 2012
	 * 3M:  Dec 20, 2011 - Mar 16, 2012
	 * 6M:  Sep 28, 2011 - Mar 16, 2012
	 * 1Y:  Mar 21, 2011 - Mar 16, 2012
	 * 3Y:  Mar 19, 2009 - Mar 16, 2012
	 * 5Y:  Mar 19, 2007 - Mar 16, 2012
	 */
	public boolean isTimePeriod(String period, String criteria){
		boolean result = false;
		
		setCriteria(criteria);
		setPeriod(period);
		// define periods for comparison
		// 2 cases:
		// - 1 Day -> 1 period in a full time format
		// - 2 Days - ... -> 2 periods in a short time format
		
		try {
			/*
			if (isValidDate(period) && isValidDate(period)) {
				result = true;
			}
			*/
			
			if (isValidTimeInterval()){
				result = true;
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}
	
	// String fake = "Mar 19, 2012 11:35 PM - 6:10 PM";
	private String parseDay(String day){
		return day.split(" (\\d+):")[0];
	}
	
	private String parseDaysForFirstDay(String period_){
		return period_.split(" - ")[0].split(" (\\d+):")[0];
	}
	
	private String parseDaysForSecondDay(String period_){
		return period_.split(" - ")[1].split(" (\\d+):")[0];
	}
	
	private String parseOtherIntervalForFirstDay(String period_){
		return period_.split(" - ")[0];
	}
	
	private String parseOtherIntervalForSecondDay(String period_){
		return period_.split(" - ")[1];
	}
	
	private void parse(){
		
		// one Day case
		if (getCriteria().equalsIgnoreCase(Constants.DAY)){
			this.day = parseDay(getPeriod());
			actuals = new String[]{this.day};
		} else
			// two or five Days case
			if (getCriteria().equalsIgnoreCase(Constants.DAYS_2) || 
				getCriteria().equalsIgnoreCase(Constants.DAYS_5)){
			
				this.first_day = parseDaysForFirstDay(getPeriod());
				this.second_day = parseDaysForSecondDay(getPeriod());
				actuals = new String[]{this.first_day, this.second_day};
			} else {
				// more than 5 Days case
				this.first_day = parseOtherIntervalForFirstDay(getPeriod());
				this.second_day = parseOtherIntervalForSecondDay(getPeriod());
				actuals = new String[]{this.first_day, this.second_day};
			}	
	}
	
	public String format(String date) {
		expecteds = new String[]{
				new SimpleDateFormat("dd MMM, yyyy", DateUtils.DATE_LOCALE).format(date)};
		return expecteds[0];
	}
	
	public String[] format(String[] date) {
		expecteds = new String[]{
				new SimpleDateFormat("dd MMM, yyyy", DateUtils.DATE_LOCALE).format(date[0]), 
				new SimpleDateFormat("dd MMM, yyyy", DateUtils.DATE_LOCALE).format(date[1])};
		return expecteds;
	}
	
	private String[] getDateFromT1_com(){
		return unformatted_expecteds;
	}
	
	private String[] getDateFromWebSite(){
		String[] result = new String[5];
		try {
			
			DefaultSelenium selenium = new DefaultSelenium("localhost", 4444, "*iexplore ", "https://website.com");
		    selenium.start();
		    selenium.open("https://website.com"); // /DirectoryServices/2006-04-01/Web.Public/Login.aspx?brandname=www.thomsonone.com&version=3.3.8.16918&protocol=0");
		    selenium.waitForPageToLoad("4000");
			selenium.type("//input[@name='txtUserName']", 
					"username");
			selenium.type("//input[@name='txtPassword']", "password");
			selenium.click("//input[@name='commandLogin']");
			selenium.waitForPageToLoad("15000");
			//Thread.sleep(15000);
			
			// set ticker
			selenium.type("//input[@name='symbol']", "ticker");
			Thread.sleep(3000);		
			
			selenium.select("//select[@class='selectWidth' and @name='time']", "1 day");			
			Thread.sleep(3000);
			
			selenium.click("//input[@name='go']");
			Thread.sleep(5000);
			
			selenium.mouseOver("//map[@id='main']//area[@id='area0']");
			result[0] = selenium.getText("//div[@id='change']/span");
			System.out.println("Day: " + result[0]);
			
			// 2 Days
			selenium.select("//select[@class='selectWidth' and @name='time']", "2 days");
			Thread.sleep(2000);			
			selenium.click("//input[@name='go']");
			
			selenium.mouseOver("//map[@id='main']//area[@id='area0']");
			result[1] = selenium.getText("//div[@id='change']/span");
			System.out.println("Days 2, the first day:  " + result[1]);
			
			selenium.mouseOver("//map[@id='main']//area[@id='area155']");
	 		result[2] = selenium.getText("//div[@id='change']/span");
			System.out.println("Days 2, the second day:  " + result[2]);
			
			// 5 Days
			selenium.select("//select[@class='selectWidth' and @name='time']", "5 days");
			Thread.sleep(2000);						
			selenium.click("//input[@name='go']");
			
			selenium.mouseOver("//map[@id='main']//area[@id='area0']");
			result[3] = selenium.getText("//div[@id='change']/span");
			System.out.println("Days 5, the first day:  " + result[3]);
			
			selenium.mouseOver("//map[@id='main']//area[@id='area389']");
	 		result[4] = selenium.getText("//div[@id='change']/span");
			System.out.println("Days 5, the second day:   " + result[4]);
			
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		
		unformatted_expecteds = result;
			
		return result;
	}
	
	private boolean isValidTimeInterval(){

		// parse Date from iPad to Day(s) according to criteria
		parse();
		
		// format Date from T1.com to iPad format
		// format(getDateFromT1_com());
	
		// compare expected and actual results
		// compare();
		
		return compare();
	}

	private boolean compare(){
		boolean result = false;
		if (criteria.equalsIgnoreCase(Constants.DAY)){
			if (expecteds[0].equalsIgnoreCase(actuals[0]))
				result = true;
		} else {
			if (expecteds[0].equalsIgnoreCase(actuals[0]) && 
				expecteds[1].equalsIgnoreCase(actuals[1]))
				result = true;
		}
		
		return result;
	}

	
	private static boolean isValidDate(String date){
		String day = "";
		String month = "";
		String year = "";
		boolean result = false;
		
		year = date.split(", ")[1];
		day = date.split(",  ")[0].split(" ")[1].replace(",", "");
		month = date.split(",  ")[0].split(" ")[0];
		
		System.out.println(" day: " + day + " month: " + month + " year: " + year);
		
		if (isDay(day) && isMonth(month) && isYear(year))
			result = true;
		
		return result;
	}
	
	private static boolean isDay(String day){
		int day_ = Integer.valueOf(day);
		boolean result = false;
		
		if (day_ > 0 && day_ < 32)
			result = true;
		
		return result;
	}

	private static boolean isMonth(String month){
		boolean result = false;
		
		if (month.equalsIgnoreCase("Jan") ||
				month.equalsIgnoreCase("Feb") ||
				month.equalsIgnoreCase("Mar") ||
				month.equalsIgnoreCase("Apr") ||
				month.equalsIgnoreCase("May") ||
				month.equalsIgnoreCase("Jun") ||
				month.equalsIgnoreCase("Jul") ||
				month.equalsIgnoreCase("Aug") ||
				month.equalsIgnoreCase("Sep") ||
				month.equalsIgnoreCase("Oct") ||
				month.equalsIgnoreCase("Nov") ||
				month.equalsIgnoreCase("Dec"))
			result = true;
		else
			System.out.println(" wrong month: " + month);
		
		return result;
	}
	
	private static boolean isYear(String year){
		int year_ = Integer.valueOf(year);
		boolean result = false;
		
		if (year_ > 2000 && year_ < 2020)
			result = true;
		
		return result;
	}
}
