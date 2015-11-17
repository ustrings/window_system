package com.hidata.framework.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期操作工具
 * @author houzhaowei
 *
 */
public class DateUtil {
	public static final String C_DATE_DIVISION = "-";
	public static final String C_TIME_PATTON_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	public static final String C_DATE_PATTON_DEFAULT = "yyyy-MM-dd";
	public static final String C_DATA_PATTON_YYYYMMDD = "yyyyMMdd";
	public static final String C_TIME_PATTON_HHMMSS = "HH:mm:ss";

	public static final int C_ONE_SECOND = 1000;
	public static final int C_ONE_MINUTE = 60 * C_ONE_SECOND;
	public static final int C_ONE_HOUR = 60 * C_ONE_MINUTE;
	public static final long C_ONE_DAY = 24 * C_ONE_HOUR;

	/**
	 * Return the current date
	 * 
	 * @return － DATE<br>
	 */
	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();

		return currDate;
	}

	/**
	 * Return the current date string
	 * 
	 * @return － 产生的日期字符串<br>
	 */
	public static String getCurrentDateStr() {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();

		return format(currDate);
	}
	
	/**
	 * Return the current datetime string
	 * 
	 * @return － 产生的日期字符串<br>
	 */
	public static String getCurrentDateTimeStr() {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();

		return format(currDate,C_TIME_PATTON_DEFAULT);
	}

	/**
	 * Return the current date in the specified format
	 * 
	 * @param strFormat
	 * @return
	 */
	public static String getCurrentDateStr(String strFormat) {
		Calendar cal = Calendar.getInstance();
		Date currDate = cal.getTime();

		return format(currDate, strFormat);
	}

	/**
	 * Parse a string and return a date value
	 * 
	 * @param dateValue
	 * @return
	 * @throws Exception
	 */
	public static Date parseDate(String dateValue) {
		return parseDate(C_DATE_PATTON_DEFAULT, dateValue);
	}

	/**
	 * Parse a strign and return a datetime value
	 * 
	 * @param dateValue
	 * @return
	 */
	public static Date parseDateTime(String dateValue) {
		return parseDate(C_TIME_PATTON_DEFAULT, dateValue);
	}

	/**
	 * Parse a string and return the date value in the specified format
	 * 
	 * @param strFormat
	 * @param dateValue
	 * @return
	 * @throws ParseException
	 * @throws Exception
	 */
	public static Date parseDate(String strFormat, String dateValue) {
		if (dateValue == null)
			return null;

		if (strFormat == null)
			strFormat = C_TIME_PATTON_DEFAULT;

		SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
		Date newDate = null;

		try {
			newDate = dateFormat.parse(dateValue);
		} catch (ParseException pe) {
			newDate = null;
		}
		return newDate;
	}

	/**
	 * 将Timestamp类型的日期转换为系统参数定义的格式的字符串。
	 * 
	 * @param aTs_Datetime
	 *            需要转换的日期。
	 * @return 转换后符合给定格式的日期字符串
	 */
	public static String format(Date aTs_Datetime) {
		return format(aTs_Datetime, C_DATE_PATTON_DEFAULT);
	}

	/**
	 * 将Timestamp类型的日期转换为系统参数定义的格式的字符串。
	 * 
	 * @param aTs_Datetime
	 *            需要转换的日期。
	 * @return 转换后符合给定格式的日期字符串
	 */
	public static String formatTime(Date aTs_Datetime) {
		return format(aTs_Datetime, C_TIME_PATTON_DEFAULT);
	}

	/**
	 * 将Date类型的日期转换为系统参数定义的格式的字符串。
	 * 
	 * @param aTs_Datetime
	 * @param as_Pattern
	 * @return
	 */
	public static String format(Date aTs_Datetime, String as_Pattern) {
		if (aTs_Datetime == null || as_Pattern == null)
			return null;
		SimpleDateFormat dateFromat = new SimpleDateFormat();
		dateFromat.applyPattern(as_Pattern);

		return dateFromat.format(aTs_Datetime);
	}

	/**
	 * @param aTs_Datetime
	 * @param as_Format
	 * @return
	 */
	public static String formatTime(Date aTs_Datetime, String as_Format) {
		if (aTs_Datetime == null || as_Format == null)
			return null;
		SimpleDateFormat dateFromat = new SimpleDateFormat();
		dateFromat.applyPattern(as_Format);

		return dateFromat.format(aTs_Datetime);
	}

	/**
	 * 将字符串按照  as_Format 的格式转化成日期
	 * @param s_date
	 * @param as_Format
	 * @return
	 */
	public static Date formatToDate(String s_date, String as_Format) {
		SimpleDateFormat sdf = new SimpleDateFormat(as_Format);
		try {
			return sdf.parse(s_date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static String getFormatTime(Date dateTime) {
		return formatTime(dateTime, C_TIME_PATTON_HHMMSS);
	}

	/**
	 * @param aTs_Datetime
	 * @param as_Pattern
	 * @return
	 */
	public static String format(Timestamp aTs_Datetime, String as_Pattern) {
		if (aTs_Datetime == null || as_Pattern == null)
			return null;
		SimpleDateFormat dateFromat = new SimpleDateFormat();
		dateFromat.applyPattern(as_Pattern);

		return dateFromat.format(aTs_Datetime);
	}

	/**
	 * 取得指定日期N天后的日期
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(Calendar.DAY_OF_MONTH, days);

		return cal.getTime();
	}
	
	/**
	 * 取得N分钟后的时间
	 * 
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addMinutes(Date date, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(Calendar.MINUTE, minute);

		return cal.getTime();
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int daysBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 计算当前日期相对于"1977-12-01"的天数
	 * 
	 * @param date
	 * @return
	 */
	public static long getRelativeDays(Date date) {
		Date relativeDate = DateUtil.parseDate("yyyy-MM-dd", "1977-12-01");

		return DateUtil.daysBetween(relativeDate, date);
	}

	public static Date getDateBeforTwelveMonth() {
		String date = "";
		Calendar cla = Calendar.getInstance();
		cla.setTime(getCurrentDate());
		int year = cla.get(Calendar.YEAR) - 1;
		int month = cla.get(Calendar.MONTH) + 1;
		if (month > 9) {
			date = String.valueOf(year) + C_DATE_DIVISION
					+ String.valueOf(month) + C_DATE_DIVISION + "01";
		} else {
			date = String.valueOf(year) + C_DATE_DIVISION + "0"
					+ String.valueOf(month) + C_DATE_DIVISION + "01";
		}

		Date dateBefore = parseDate(date);
		return dateBefore;
	}

	/**
	 * 传入时间字符串,加一天后返回Date
	 * 
	 * @param date
	 *            时间 格式 YYYY-MM-DD
	 * @return
	 */
	public static Date addDate(String date) {
		if (date == null) {
			return null;
		}
		Date tempDate = parseDate(C_DATE_PATTON_DEFAULT, date);
		String year = format(tempDate, "yyyy");
		String month = format(tempDate, "MM");
		String day = format(tempDate, "dd");

		GregorianCalendar calendar = new GregorianCalendar(
				Integer.parseInt(year), Integer.parseInt(month) - 1,
				Integer.parseInt(day));

		calendar.add(GregorianCalendar.DATE, 1);
		return calendar.getTime();
	}
	
	public static Long dateDDMMSSStringToLong(String str) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(format.parse(str));
        return cal.getTimeInMillis();
    }


	public static void main(String[] args) {
		// Date date1 = DateUtil.addDays(DateUtil.getCurrentDate(),1);
		// Date date2 = DateUtil.addDays(DateUtil.getCurrentDate(),101);
		//
		// System.out.println(DateUtil.getRelativeDays(date1));
		// System.out.println(DateUtil.getRelativeDays(date2));

		// Timestamp date = new Timestamp(801);
		//
		// System.out.println(date);
		// String strDate = DateUtil.format(date, C_DATA_PATTON_YYYYMMDD);
		//
		// System.out.println(strDate);\
//		String C_TIME_PATTON_DEFAULT = "yyyyMMddhhmmss";
		Date endDate = new Date(System.currentTimeMillis());
		Date curDate = DateUtil.addDate("2013-05-01 13:07:35");
		long differ = endDate.getTime() - curDate.getTime();
		long diffdays = differ/(3600000*24);
		 
		System.out.println(diffdays);
//		DateFormat df = new SimpleDateFormat(C_TIME_PATTON_DEFAULT);
//		Date expireTime = DateUtil.addMinutes(new Date(), 30);
//		String sExpireTime = DateUtil.format(expireTime,DateUtil.C_TIME_PATTON_DEFAULT);
//		df.format(DateUtil.formatToDate(sExpireTime, "yyyy-MM-dd hh:mm:ss"));
//		String now = DateUtil.getCurrentDateStr(DateUtil.C_TIME_PATTON_DEFAULT);
//		System.out.println(sExpireTime);
//		System.out.println(now);
//		String date = "2006-07-31";
//		System.out.println(date);
//		Date date2 = addDate(date);
//		System.out.println(date2);

	}
	
	/**
	 * 前推天数
	 */
	public static Date parseDay(int day) {
		int INTERVAL_DAY = -day;
		Calendar calender = Calendar.getInstance();
		calender.setTime(new Date());
		calender.add(Calendar.DATE, INTERVAL_DAY);
		return calender.getTime();
	}
	
	public static Date getDay(Date date,int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, n);
		date = calendar.getTime();
		return date;
	}
	
	
	/**
	 * 得到当天的开始时间，格式为yyyy-mm-dd HH:MM:ss, 其中时分秒都为0
	 * @return
	 */
	public static String getCurrentDayBeginDateTimeStr(){
		String dateStr = "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		
		cal.set(Calendar.HOUR_OF_DAY, 0);  
		cal.set(Calendar.MINUTE, 0);  
		cal.set(Calendar.SECOND, 0);  
		
		dateStr = DateUtil.format(cal.getTime(), DateUtil.C_TIME_PATTON_DEFAULT);
		
		return dateStr;
	}
	
	
	/**
	 * 得到上一天的开始时间，格式为yyyy-mm-dd HH:MM:ss, 其中时分秒都为0
	 * @return
	 */
	public static String getPreviousDayBeginDateTimeStr(){
		String dateStr = "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);  
		cal.set(Calendar.MINUTE, 0);  
		cal.set(Calendar.SECOND, 0);  
		
		dateStr = DateUtil.format(cal.getTime(), DateUtil.C_TIME_PATTON_DEFAULT);
		
		return dateStr;
	}
	
	
	/**
	 * 得到当前时间下一分钟：45秒的时间，比如2014-06-26 11:03:45
	 * @return
	 */
	public static String getNextMin45Str(){
		String dateStr = "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		
		cal.add(Calendar.MINUTE, 1);
		cal.set(Calendar.SECOND, 0);  
		cal.add(Calendar.SECOND, 45);
		
		dateStr = DateUtil.format(cal.getTime(), DateUtil.C_TIME_PATTON_DEFAULT);
		
		return dateStr;
	}
	
	
	/**
	 * 得到当前时间下一分钟：45秒的时间，比如2014-06-26 11:03:45
	 * @return
	 */
	public static String getNextMin30Str(){
		String dateStr = "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		
		cal.add(Calendar.MINUTE, 1);
		cal.set(Calendar.SECOND, 0);  
		cal.add(Calendar.SECOND, 30);
		
		dateStr = DateUtil.format(cal.getTime(), DateUtil.C_TIME_PATTON_DEFAULT);
		
		return dateStr;
	}
	
	/**
	 * 得到上一个时辰的开始时间，格式为yyyy-mm-dd HH:MM:ss,
	 * @return
	 */
	public static String getPreviousHourBeginDateTimeStr(){
		String dateTimeStr = "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		
		cal.add(Calendar.HOUR_OF_DAY, -1);
		cal.set(Calendar.MINUTE, 0);  
		cal.set(Calendar.SECOND, 0);  
		
		dateTimeStr = DateUtil.format(cal.getTime(), DateUtil.C_TIME_PATTON_DEFAULT);
		
		return dateTimeStr;
	}
	
	
	/**
	 * 得到下一天的开始时间，格式为yyyy-mm-dd HH:MM:ss, 其中时分秒都为0
	 * @return
	 */
	public static String getNextDayBeginDateTimeStr(){
		String dateStr = "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);  
		cal.set(Calendar.MINUTE, 0);  
		cal.set(Calendar.SECOND, 0);  
		
		dateStr = DateUtil.format(cal.getTime(), DateUtil.C_TIME_PATTON_DEFAULT);
		
		return dateStr;
	}
	
	/**
	 * 得到下一个时辰的开始时间，格式为yyyy-mm-dd HH:MM:ss,
	 * @return
	 */
	public static String getNextHourBeginDateTimeStr(){
		String dateTimeStr = "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		
		cal.add(Calendar.HOUR_OF_DAY, 1);
		cal.set(Calendar.MINUTE, 0);  
		cal.set(Calendar.SECOND, 0);  
		
		dateTimeStr = DateUtil.format(cal.getTime(), DateUtil.C_TIME_PATTON_DEFAULT);
		
		return dateTimeStr;
	}
	
	
	/**
	 * 得到当前时辰的开始时间，格式为yyyy-mm-dd HH:MM:ss,
	 * @return
	 */
	public static String getCurrentHourBeginDateTimeStr(){
		String dateTimeStr = "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		
		cal.set(Calendar.MINUTE, 0);  
		cal.set(Calendar.SECOND, 0);  
		
		dateTimeStr = DateUtil.format(cal.getTime(), DateUtil.C_TIME_PATTON_DEFAULT);
		
		return dateTimeStr;
	}
}