package com.kd.manage.controller.util;

import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * 日期处理工具
 * @author zlm
 * @version 1.1.0
 */
public class DateUtil {
	
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String YEAR_FORMAT = "yyyy";
	public static final String YEAR_MONTH_FORMAT = "yyyy-MM";
	
	public static Date parseDate(String dateStr, String format) {
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			String dt = dateStr.replaceAll("-", "-");
			if ((!dt.equals("")) && (dt.length() < format.length())) {
				dt = dt
						+ format.substring(dt.length()).replaceAll(
								"[YyMmDdHhSs]", "0");
			}
			date = dateFormat.parse(dt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date parseDate(String dateStr) {
		try
	    {
	     return DateUtils.parseDate(dateStr, new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS" });
	    }
	    catch (ParseException e) {
	      throw new InvalidParameterException(dateStr + "不是一个正确格式的日期。");
	    }
	}
	
	public static boolean isDate(String dateStr) {
		try{
		     if(DateUtils.parseDate(dateStr, new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS", "yyyy-MM", "yyyy"})!= null){
		    	 return true;
		     }
	    }catch (ParseException e) {
	    	 return false;
	    }
		return false;
	}
	
	public static String format(Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				DateFormat dateFormat = new SimpleDateFormat(format);
				result = dateFormat.format(date);
			}
		} catch (Exception localException) {
		}
		return result;
	}

	public static String format(Date date) {
		return format(date, DATE_FORMAT);
	}

	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(1);
	}

	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(2) + 1;
	}

	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(5);
	}

	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(11);
	}

	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(12);
	}

	public static int getSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(13);
	}

	public static long getMillis(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

	public static String getDate(Date date) {
		return format(date, DATE_FORMAT);
	}

	public static String getTime(Date date) {
		return format(date, TIME_FORMAT);
	}

	public static String getDateTime(Date date) {
		return format(date, DATE_TIME_FORMAT);
	}

	public static Date addDate(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		long millis = getMillis(date) + day * 24L * 3600L * 1000L;
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	public static Date minDate(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		long millis = getMillis(date) - day * 24L * 3600L * 1000L;
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	public static int diffDate(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / 86400000L);
	}

	public static String getMonthBegin(String strdate) {
		Date date = parseDate(strdate);
		return format(date, "yyyy-MM") + "-01";
	}

	public static String getMonthEnd(String strdate) {
		Date date = parseDate(getMonthBegin(strdate));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(2, 1);
		calendar.add(6, -1);
		return formatDate(calendar.getTime());
	}

	public static String formatDate(Date date) {
		return formatDateByFormat(date, DATE_FORMAT);
	}

	public static String formatDateByFormat(Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}