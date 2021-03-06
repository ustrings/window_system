package com.hidata.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.hidata.framework.util.StringUtil;

/**
 * Created with IntelliJ IDEA. User: chenjinzhao Date: 13-11-11 To change this
 * template use File | Settings | File Templates.
 */
public class TimeUtil {

	public static final SimpleDateFormat datetimeformat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static final SimpleDateFormat dateformat = new SimpleDateFormat(
			"yyyyMMdd");

	public static long getDateStamp(long timestamp) throws ParseException {
		String dateStr = dateformat.format(new Date(timestamp));
		long dateStamp = dateformat.parse(dateStr).getTime();

		return dateStamp;
	}

	/**
	 * 把时间戳转化成yyymmdd格式的日期字符串
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String getyyyyMMddStr(long timestamp) {
		String dateStr = dateformat.format(new Date(timestamp));
		return dateStr;
	}

	public static String dateLongToString(Long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return format.format(cal.getTime());
	}

	public static String dateLongToMMHHssString(Long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return format.format(cal.getTime());
	}

	/**
	 * 修改日期
	 * 
	 * @param time
	 * @return
	 */
	public static String dateLongToYMDString(Long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return format.format(cal.getTime());
	}

	public static String timeIntervalDays(long start, long end) {
		return (int) (end - start) / (1000 * 60 * 60 * 24) + "";
	}

	public static String getDateAfter(String start, int i) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(format.parse(start));
		} catch (ParseException e) {
		}
		cal.add(Calendar.DATE, i);
		return format.format(cal.getTime());
	}

	public static Long dateStringToLong(String str) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(format.parse(str));
		return cal.getTimeInMillis();
	}

	public static Long dateDDMMSSStringToLong(String str) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(format.parse(str));
		return cal.getTimeInMillis();
	}

	public static String getCurYYYYMMDDHHMMSS() {
		return datetimeformat.format(new java.util.Date());
	}

	public static void main(String[] args) throws ParseException {
		// long dd = getDateStamp(System.currentTimeMillis());
		// System.out.println(dd);
		// System.out.println(getyyyyMMddStr(System.currentTimeMillis()));
		//
		// String xx = " 2014-06-16 12:00:0";
		//
		// System.out.println(dateDDMMSSStringToLong(xx));
		//
		// String end = " 2014-06-16 23:59:59";
		//
		// System.out.println(dateDDMMSSStringToLong(end));
		//
		// long curTimeStamp = Long.valueOf("1402881597763");
		//
		// System.out.println(dateLongToMMHHssString(curTimeStamp));
		//
		// String beginDateStr = "20140301";
		//
		// String endDateStr = "20140316";
		//
		// String result = "";
		//
		// String iterDate = "";
		//
		// int inx = 0;
		//
		// while (!iterDate.equals(endDateStr)) {
		//
		// iterDate = getDateAfter(beginDateStr, inx);
		//
		// result = (result + "'" + iterDate + "'" + ",");
		//
		// inx++;
		//
		// }
		//
		// System.out.println("最终结果是:"+result);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = sdf.parse("2014-04-08");
		date.getTime();
		System.out.println(date);

	}

	/**
	 * 两日期之差，得到天数 时间格式为 ‘yyyy-MM-dd’
	 * 
	 * @param maxTime
	 * @param minTime
	 * @return
	 * @throws ParseException
	 */
	public static String get_ByTwoTimes(String maxTime, String minTime)
			throws ParseException {
		if (StringUtil.isEmpty(maxTime) && StringUtil.isEmpty(minTime)) {
			return "0";
		}
		if (maxTime.equals(minTime)) {
			return "1";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateMax = sdf.parse(maxTime);
		java.util.Date dateMin = sdf.parse(minTime);
		Long times = dateMax.getTime() - dateMin.getTime();
		String days = String.valueOf(times / 86400000);
		if (StringUtil.isNotEmpty(days) && Integer.parseInt(days) > 0) {
			return days;
		}
		return "0";
	}

	public static String getCurrentDateTime() {
		return datetimeformat.format(new Date());
	}
}
