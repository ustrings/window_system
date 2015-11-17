package com.vaolan.adtimer.util;

import java.util.Calendar;

import com.hidata.framework.util.DateUtil;

/**
 * 
 * @author chenjinzhao
 *
 */
public class TimerUtil {

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
	 * 得到下一天的开始时间，格式为yyyy-mm-dd HH:MM:ss, 其中时秒为0; 分钟为5
	 * @return
	 */
	public static String getNextDayBeginDateTimeStrTWOMIN(){
		String dateStr = "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);  
		cal.set(Calendar.MINUTE, 2);  
		cal.set(Calendar.SECOND, 0);  
		
		dateStr = DateUtil.format(cal.getTime(), DateUtil.C_TIME_PATTON_DEFAULT);
		
		return dateStr;
	}
	
	
	/**
	 * 得到下一天的开始时间+15秒钟，格式为yyyy-mm-dd HH:MM:ss,
	 * @return
	 */
	public static String getNextDayBeginDateTimeStr15S(){
		String dateStr = "";
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);  
		cal.set(Calendar.MINUTE, 0);  
		cal.set(Calendar.SECOND, 15);  
		
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
	
	public static void main (String[] args){
		
		
		
		System.out.println(TimerUtil.getNextDayBeginDateTimeStr15S());

		
	}
}
