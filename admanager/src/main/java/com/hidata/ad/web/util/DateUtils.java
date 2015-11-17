package com.hidata.ad.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {

	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param startTime
	 * @param endTime
	 * @param dateFormat
	 * @return
	 */
	public static long getDateNum(String startTime, String endTime,
			String dateFormat) {
		SimpleDateFormat ft = new SimpleDateFormat(dateFormat);
		long quot = 0;
		try {
			Date date1 = ft.parse(startTime);
			Date date2 = ft.parse(endTime);
			quot = date2.getTime() - date1.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return quot;
	}

	/**
	 * 获取两个日期之间的所有日期
	 * 
	 * @param startTime
	 * @param endTime
	 * @param dateFormat
	 * @throws Exception
	 */
	public static List<String> getDateArr(String startTime, String endTime,
			String dateFormat) {
		List<String> dateArr = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date begin;
		try {
			begin = sdf.parse(startTime);

			Date end = sdf.parse(endTime);
			double between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
			double day = between / (24 * 3600);
			for (int i = 0; i <= day; i++) {
				Calendar cd = Calendar.getInstance();
				cd.setTime(sdf.parse(startTime));
				cd.add(Calendar.DATE, i);// 增加一天
				// cd.add(Calendar.MONTH, n);//增加一个月
				dateArr.add(sdf.format(cd.getTime()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateArr;
	}
	
		public static void main(String[] args) {
			String start = "20140827";
			String end = "20140821";
			String dateFormat = "yyyyMMdd";
			
			List<String> dateList = getDateArr(start, end, dateFormat);
			for (String dateStr : dateList) {
				
				System.out.println(dateStr);
			}
			
		}
}
