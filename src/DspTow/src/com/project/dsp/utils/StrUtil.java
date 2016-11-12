package com.project.dsp.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class StrUtil {
	/**
	 * 禁止实例化
	 */
	public static SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
	private StrUtil() {
	}
	
	/**
	 * 判断字符串是否为空
	 * @param str 要判断的字符串
	 * @return 为空返回true 反之返回false
	 */
	public static boolean isEmpty(String str){
		if (null==str||str.trim().length()==0) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 判断对象是否是null或者空字符串
	 * @param str
	 * @return 是true，否false
	 */
	public static boolean isBlank(Object str)
	{
		if(str==null||str.toString().trim().length()==0)
		{
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 分割并且去除空格
	 * 
	 * @param str
	 *            待分割字符串
	 * @param sep
	 *            分割符
	 * @param sep2
	 *            第二个分隔符
	 * @return 如果str为空，则返回null。
	 */
	public static String[] splitAndTrim(String str, String sep, String sep2) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		if (!StringUtils.isBlank(sep2)) {
			str = StringUtils.replace(str, sep2, sep);
		}
		String[] arr = StringUtils.split(str, sep);
		// trim
		for (int i = 0, len = arr.length; i < len; i++) {
			arr[i] = arr[i].trim();
		}
		return arr;
	}


	/**
	 * 截断字符串，在截断的字符串后加上后缀，如"..."
	 * 
	 * @param s
	 *            剪切对象。
	 * @param len
	 *            编码小于256的作为一个字符，大于256的作为两个字符。
	 * @param append 截断后追加的后缀，比如 ...
	 * @return
	 */
	public static String subStr(String s, int len, String append) {
		if (s == null) {
			return null;
		}
		int slen = s.length();
		if (slen <= len) {
			return s;
		}
		s=s.substring(0,len);
		s=s+append;
		return s;
	}
	

	/**
	 * 检查字符串中是否包含被搜索的字符串。被搜索的字符串可以使用通配符'*'。
	 * 
	 * @param str
	 * @param search
	 * @return
	 */
	public static boolean contains(String str, String search) {
		if (StringUtils.isBlank(str) || StringUtils.isBlank(search)) {
			return false;
		}
		String reg = StringUtils.replace(search, "*", ".*");
		Pattern p = Pattern.compile(reg);
		return p.matcher(str).matches();
	}
	
	/**
	 * 
	* @Description: 
	* @param reg 正则表达式
	* @param str 需要处理的字符串
	* @param replaceStr 要替换的字符串
	* @return String
	* @throws
	 */
	public static String regxpMatch(String reg,String str,String replaceStr){
		if(str == null){
			return "";
		}else if(reg == null || "".equals(reg)){
			return str;
		}else{
			String s = "";
			s = str.replaceAll(reg,replaceStr);
			return s;
		}
	}
	
	
	/**
	 * 防止alert()弹出
	 * @param str
	 * @return
	 */
	public static String replaceSign(String str){
		if(str==null || "".equals(str)){
			return str;
		}else{
			str = str.replace("<","&lt;");
			str = str.replace(">","&gt;");
			str = str.replace("/","&frasl;");
			return str;
		}
	}
	
	/**
	 * 取得字符窜长度，数字按1文字按2
	 * @param str
	 * @return
	 */
	public static int getRealLength(String str){
		int length=0;
		for (int i = 0; i < str.length(); i++) {
			String tempStr=String.valueOf(str.charAt(i));
			if (tempStr.getBytes().length>1) {
				length=length+2;
			}else {
				length=length+1;
			}
		}
		return length;
		
	}

	/**
	 * 截取字符串
	 * @param memString
	 * @param maxsize
	 * @param needsize
	 * @param suffix
	 * @return
	 */
	public static String getSubString(String memString,int maxsize,int needsize,String suffix){
		if (memString!=null) {
			if (getRealLength(memString)<=maxsize) {
				return memString;
			}else {
				int memCount=0;
				int i = 0;
				for (;i < memString.length(); i++) {
					if (String.valueOf(memString.charAt(i)).getBytes().length>1) {
						memCount=memCount+2;
					}else {
						memCount=memCount+1;
					}
					if (memCount>needsize) {
						break;
					}else if (memCount==needsize) {
						i=i+1;
						break;
					}else {
					}
				}
				return memString.substring(0,i)+suffix;
			}
		}else {
			return null;
		}
	}
	
	/**
	 * 转码
	 * @param str
	 * @return
	 */
	public static String convertUTF82(String str) {
		try {
			if(!"".equals(str) && str !=null){
				System.out.println("before convert encode:--->>" + str);
				byte[] iso = str.getBytes("ISO-8859-1");
				String st1 = new String(iso, "UTF-8");
				//如果包含汉字
				if(StrUtil.extractChina(st1).length()>0){
					str = st1;
				}else{
					str = URLDecoder.decode(str, "UTF-8");
				}
				System.out.println("after convert encode:--->>" + str);
				return str;
			}
			return "";
		} catch (UnsupportedEncodingException e) {
			System.out.println("---------------String convert exception");
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 抽出一个字符串的中文
	 * @param str
	 * @return
	 */
	public static String extractChina(String str){
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		System.out.print("the Chinese words by extract is:--->>");
		String result = "";
		while (m.find()) {
			System.out.print(m.group(0) + " ");
			result += m.group(0);
		}
		System.out.println();
		return result;
	}
	
	/**
	 * 将字符串转为int[]
	 * @param str 1,2,3,4 此参数一定需是逗号隔开
	 * @return
	 */
	public static Integer[] StringtoInt(String str) {  
		String [] idString = str.split(",");
	 	Integer ret[] = new Integer[idString.length];   
	    StringTokenizer toKenizer = new StringTokenizer(str, ",");   
	    int i = 0;  
	    while (toKenizer.hasMoreElements()) {   
	      ret[i++] = Integer.valueOf(toKenizer.nextToken());  
	    }   
	    return ret;  
	} 
	
 /**
  * 判断是否为空
  * @return
  */	
 public static boolean isNotEmpty(String str){
	 if(str !=null && !"".equals(str)){
		 return true;
	 }
	 return false;
 }
 
 public static Timestamp getCurrentTimestampt(){	
		return new Timestamp(new Date().getTime());
	}
//获取当前时间
 public static String getNowDateFull(){
     SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     return f1.format(new Date());
 }
 
//获取当前时间
 public static String getNowDate(){
     return f.format(new Date());
 }
 
 //获取当前是时间的前一天
 public static String getNextDay() {  
     Calendar calendar = Calendar.getInstance();  
     calendar.setTime(new Date());  
     calendar.add(Calendar.DAY_OF_MONTH, -1);  
     return f.format(calendar.getTime());  
 }  

//获取每月的第一天 yyyy-MM-dd HH:mm:ss
 public static String getFirstDate(){
    Calendar c = Calendar.getInstance();    
    c.add(Calendar.MONTH, 0);
    c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
    return f.format(c.getTime());
 }
 
 //获取每月的最后一天
 public static String getLastDate(){
     Calendar ca = Calendar.getInstance(); 
     ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
     return f.format(ca.getTime());
 }

 //获取当前日期前一天数据
 public static String getFirstDay(){
	 Calendar cal = Calendar.getInstance();
	 cal.add(Calendar.DATE, -1);
     return f.format(cal.getTime());
 }

 public static String beforeMonth(){
     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
     Calendar cal = Calendar.getInstance();
     cal.add(Calendar.MONTH, -1);
     return format.format(cal.getTime());
 }
 
/** 
 * 将String型格式化,比如想要将2011-11-11格式化成2011年11月11日,就StringPattern("2011-11-11","yyyy-MM-dd","yyyy年MM月dd日").
 * @param date String 想要格式化的日期
 * @param oldPattern String 想要格式化的日期的现有格式
 * @param newPattern String 想要格式化成什么格式
 * @return String 
 */ 
public static String StringPattern(String date, String oldPattern, String newPattern) { 
    if (date == null || oldPattern == null || newPattern == null) 
        return ""; 
    SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern) ;        // 实例化模板对象  
    SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern) ;        // 实例化模板对象  
    Date d = null ;  
    try{  
        d = sdf1.parse(date) ;   // 将给定的字符串中的日期提取出来  
    }catch(Exception e){            // 如果提供的字符串格式有错误，则进行异常处理  
        e.printStackTrace() ;       // 打印异常信息  
    }  
    return sdf2.format(d);
} 

/**
 * 日期比较  是否属于当天的日期
 * @@param date
 */
 public static String dateComparison(String firstDate,String senconDate,String orderAging){
	 try {
		    String result="";
		    Date d1 = f.parse(senconDate);
			String dStr=StringPattern(firstDate,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd");
			Date d2=f.parse(dStr+" 23:59:59");
			long diff =(d2.getTime() - d1.getTime())/1000;
			if(diff>=0){
				result="";
			}else{
				if(isNotEmpty(orderAging)){
					String symbol=orderAging.substring(1,2);
					if("-".equals(symbol)){
						String  days=orderAging.split("-")[1];
						int d=Integer.parseInt(days)-1;
						result="D-"+d;
					}
					if("+".equals(symbol)){
						String  days=orderAging.split("\\+")[1];
						int d=Integer.parseInt(days)+1;
						result="D+"+d;
					}
				}else{
					result="";
				}
			}
		return result;
	} catch (ParseException e) {
		e.printStackTrace();
	}
	return "";
 }
 
 /**
  * 日期比较  是否属于当天的日期 不显示时效
  * @@param date
  */
  public static String dateComparison1(String firstDate,String senconDate){
 	 try {
 		    String result="";
 		    Date d1 = f.parse(senconDate);
 			String dStr=StringPattern(firstDate,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd");
 			Date d2=f.parse(dStr+" 23:59:59");
 			long diff =(d2.getTime() - d1.getTime())/1000;
 			if(diff>=0){
 				result="";
 			}else{
 			   result=StringPattern(senconDate,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd");;
 			}
 		return result;
 	} catch (ParseException e) {
 		e.printStackTrace();
 	}
 	return "";
  }
  
  /**
   * 返回时间
   * @return
   */
  public static String printTimeStamp() {
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS ",
				Locale.CHINA);
		// 显示当前时间 精确到毫秒
		return sdf.format(ca.getTime());
	}
  
  /**
   * 两日期相减
   * @param date1
   * @param date2
   * @return
   */
  public static long dateSub(String date1,String date2){
	  try {  
		  // 这个的除以1000得到秒，相应的60000得到分，3600000得到小时  
          long result = (f.parse(date2).getTime() - f.parse(date1).getTime()) / 1000; 
          return result;
      } catch (ParseException e) {  
          e.printStackTrace();  
          return 0;
      }  
	  
  }
  
  /**
   * 两日期相减
   * @param date1
   * @param date2
   * @return
   */
  public static long dateSubHours(String date1,String date2){
	  try {  
		  // 这个的除以1000得到秒，相应的60000得到分，3600000得到小时  
          long result = (f.parse(date2).getTime() - f.parse(date1).getTime()) / 3600000; 
          return result;
      } catch (ParseException e) {  
          e.printStackTrace();  
          return 0;
      }  
	  
  }
 
  
  /**
   * @author ksq
   * @Description 获取两时间之间的工作日天数
   * @param startTime
   * @param endTime
   * @return Integer
   */
  public static Double getworktime(String startTime, String endTime){
	//设置时间格式
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//开始日期
	
	Date startDate = null;
	Date endDate = null;
	Double workdays = 0D;
	Double count = 0D;
	Calendar cal = null;
	
	try {
	    startDate = dateFormat.parse(startTime);
	    endDate = dateFormat.parse(endTime);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	while(startDate.before(endDate)|| startDate.equals(endDate)){
	    cal = Calendar.getInstance();
	    //设置日期
	    cal.setTime(startDate);
	    if((cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) &&(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)){
		//进行比较，如果日期不等于周六也不等于周日，工作日+1
		workdays++;
	    }
	    if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
		count++;
	    }
	    //日期加1
	    cal.add(Calendar.DAY_OF_MONTH,1);
	    startDate = cal.getTime();
	}
	System.out.println("------------->"+count*0.5);
	System.out.println("------------->"+workdays);
	return (workdays+count*0.5);
  }
  
  public static Boolean isWeekday(Date date){
    //设置时间格式
      Calendar cal = null;
      cal = Calendar.getInstance();
      
      //设置日期
      cal.setTime(date);
      if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
	  return true;
      }
      return false;
  }
  
  public static String getPercent(Double num1, Double num2){
      //double percent = 50.5D / 150D;
      Double percent = num1 / num2;
      //输出一下，确认你的小数无误
      System.out.println("------------->"+"小数：" + percent);
      //获取格式化对象
      NumberFormat nt = NumberFormat.getPercentInstance();
      //设置百分数精确度2即保留两位小数
      nt.setMinimumFractionDigits(2);
      //最后格式化并输出
      System.out.println("------------->"+"百分数：" + nt.format(percent));
      return nt.format(percent);
  }
  
  public static String[] getQuarter(){
      String []result = new String [2];
      Calendar cal = Calendar.getInstance();
      Integer month = cal.get(Calendar.MONTH)+1;
      
      if(month == 1 || month ==2 || month ==3){
	  result[0]=cal.get(Calendar.YEAR)+"-01-01";
	  result[1]=cal.get(Calendar.YEAR)+"-03-31";
      }
      else if(month == 4 || month ==5 || month ==6){
	  result[0]=cal.get(Calendar.YEAR)+"-04-01";
	  result[1]=cal.get(Calendar.YEAR)+"-06-30";
      }
      else if(month == 7 || month ==8 || month ==9){
	  result[0]=cal.get(Calendar.YEAR)+"-07-01";
	  result[1]=cal.get(Calendar.YEAR)+"-09-30";
      }
      else if(month == 10 || month ==11 || month ==12){
	  result[0]=cal.get(Calendar.YEAR)+"-10-01";
	  result[1]=cal.get(Calendar.YEAR)+"-12-31";
      }
      return result;
  }
  
  public static String[] getYear(){
      String []result = new String [2];
      Calendar cal = Calendar.getInstance();
      System.out.println(cal.get(Calendar.YEAR));
      result[0]=cal.get(Calendar.YEAR)+"-01-01";
      result[1]=cal.get(Calendar.YEAR)+"-12-31";
      System.out.println(result[0]+"---------"+result[1]);
      return result;
  }
  
  public static Integer getPageCount(Integer count, Integer pageSize){
      //Integer pageSize = Constants.PAGE_SIZE;
      Integer pageCount = count%pageSize == 0 ? count/pageSize : (count/pageSize)+1;
      return pageCount;
  }
}
