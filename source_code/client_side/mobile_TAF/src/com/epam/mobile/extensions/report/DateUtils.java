package com.epam.mobile.extensions.report;

import java.util.Calendar;
import java.util.TimeZone;
import java.lang.String;
import java.util.Locale;
import java.text.SimpleDateFormat;

public class DateUtils {
  public static final String DATE_FORMAT_NOW = "[dd MMMMM yyyy aaa hh:mm:ss.SSS z]";
  public static final String DATE_MEDIUM_FORMAT_NOW = "dd-MMMMM-yyyy_HH-mm-ss";
  public static final Locale DATE_LOCALE = Locale.UK;
  
  public static final String TIME_ZONE_REGION = "Europe";
  // Minsk has the old time, 
  // so we should set the time as for Moscow city
  public static final String TIME_ZONE_CITY = "Moscow";
  // or use direct time zone
  public static final String TIME_ZONE_BELARUS = "GMT+3:00";


  /*
  static{
	  TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE_BELARUS));
			  //getTimeZone("Europe/Moscow"));
			  //getTimeZone("GMT+3:00"));
  }*/
  
  public synchronized static String now() {
    return new SimpleDateFormat(DATE_FORMAT_NOW, DATE_LOCALE).
    											format(Calendar.getInstance().getTime());
  }

  public synchronized static String getMediumFormatNow() {
	    return new SimpleDateFormat(DATE_MEDIUM_FORMAT_NOW, DATE_LOCALE).
	    											format(Calendar.getInstance().getTime());
	  }

  public static void setBelarusTimeZone() {
	  TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE_BELARUS));
  }
  
  public static void  main(String arg[]) {
    System.out.println("Time: " + DateUtils.now());
    System.out.println("Time in Long format: " + DateUtils.getMediumFormatNow());
  }
}
