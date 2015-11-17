package com.hidata.framework.util;


/**
 * 参数通用类，可以判断字符串是否为空，是否为数字，是否为完美通行证等
 * @author houzhaowei
 * 
 */
public class ParamUtil {

	/**
	 * 判断字符串是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNull(String s) {
		if (s == null || s.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为正数
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNum(String s) {

		try {
			if (Integer.parseInt(s) <= 0)
				return false;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
