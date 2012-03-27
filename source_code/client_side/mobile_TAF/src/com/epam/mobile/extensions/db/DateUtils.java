package com.epam.mobile.extensions.db;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class DateUtils {
  public static final String DATE_FORMAT_NOW = "[dd MMMMM yyyy aaa hh:mm:ss:SSS z]";

  public synchronized static String now() {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
    return sdf.format(cal.getTime());

  }

  public static void  main(String arg[]) {
    System.out.println("Time: " + DateUtils.now());
  }
}
