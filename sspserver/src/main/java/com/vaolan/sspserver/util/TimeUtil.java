package com.vaolan.sspserver.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	public static void main(String[] args) {
		System.out.println(getPreviousDate());
	}

    public static final SimpleDateFormat datetimeformat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
    
    public static String getCurDateTime()  {
    	return datetimeformat.format(new Date());
    }
    
    public static String getCurDate()  {
    	return dateformat.format(new Date());
    }
    
    public static String getPreviousDate()  {
    	return dateformat.format(new Date(new Date().getTime() - 60*60*24*1000));
    }
    
    public static Long getRemainTime() {
    	return getEndTime() - new Date().getTime();
    }
    
    
    public static Long getStartTime(){
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return todayStart.getTime().getTime();
	}
	
    public static Long getEndTime(){
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTime().getTime();
	}
    
    public static Long getTestTime(){
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR_OF_DAY, 14);
		todayEnd.set(Calendar.MINUTE,45);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return todayEnd.getTime().getTime();
	}
   
}
