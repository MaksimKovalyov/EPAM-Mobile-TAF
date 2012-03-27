package com.epam.mobile.testing.utils;

public class Validator {

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
	public static boolean isTimePeriod(String period, String criteria){
		boolean result = false;
		
		try {
			if (isValidDate(period) && isValidDate(period)) {
				result = true;
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return result;
	}

	/*
	 * 
	 * // Creates two calendars instances
10.Calendar cal1 = Calendar.getInstance();
11.Calendar cal2 = Calendar.getInstance();
12. 
13.// Set the date for both of the calendar instance
14.cal1.set(2006, 12, 30);
15.cal2.set(2007, 5, 3);
16. 
17.// Get the represented date in milliseconds
18.long milis1 = cal1.getTimeInMillis();
19.long milis2 = cal2.getTimeInMillis();
20. 
21.// Calculate difference in milliseconds
22.long diff = milis2 - milis1;
23. 
24.// Calculate difference in seconds
25.long diffSeconds = diff / 1000;
26. 
27.// Calculate difference in minutes
28.long diffMinutes = diff / (60 * 1000);
29. 
30.// Calculate difference in hours
31.long diffHours = diff / (60 * 60 * 1000);
32. 
33.// Calculate difference in days
34.long diffDays = diff / (24 * 60 * 60 * 1000);
35. 
36.System.out.println("In milliseconds: " + diff + " milliseconds.");
37.System.out.println("In seconds: " + diffSeconds + " seconds.");
38.System.out.println("In minutes: " + diffMinutes + " minutes.");
39.System.out.println("In hours: " + diffHours + " hours.");
40.System.out.println("In days: " + diffDays + " days.");
	 * 
	 */
	
	
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
	
	public static void main(String[] args) {
		String[] dates = new String[]{"Jan 31, 2012", "Feb 22, 2012"};
		System.out.println(Validator.isTimePeriodValidFormat(dates));
	}
	
}
