package com.hidata.ad.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncodUtil {
	public  static final String UTF_ENCODING = "UTF-8";
	//处理中文
	public static String EncodeUrl(String url){
		StringBuffer sb = new StringBuffer();
		try {
			String regEx = "[\\u4e00-\\u9fa5]"; 
			Pattern p = Pattern.compile(regEx); 
			char[] args = url.toCharArray();
			for(int i = 0; i < args.length; i ++){
				String one = String.valueOf(args[i]);
				Matcher m = p.matcher(one); 
				  if (m.find()) { 
					 one = URLEncoder.encode(one,UTF_ENCODING);
				  } 
				  sb.append(one);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
