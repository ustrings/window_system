package com.vaolan.ckserver.util;

import java.util.Random;

public class CommonUtil {
	
	public static String genCookieId(String srcIP,String userAgent){
		String cookieId = "";
		String sysTime = System.currentTimeMillis()+"";
		Random r = new Random();
		String rStr = r.nextInt()+"";
		
	    cookieId = Constant.UV_COOKIE_FLAG  + JavaMd5Util.md5Encryp(srcIP,userAgent,sysTime,rStr);
		return cookieId;
	}
	public static void main(String[] args) {
		
		System.out.print(genCookieId("1.1.1.1", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.77 Safari/537.36"));
	}

}
