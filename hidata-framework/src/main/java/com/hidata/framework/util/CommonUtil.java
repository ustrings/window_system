package com.hidata.framework.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;



public class CommonUtil {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
  
    private static String getBusinessNumber() {
        Date date = new Date();
        return sdf.format(date);
    }
    
    /**
     * 获取订单号，同步
     * @return
     */
	public static String getOrderFormNumber(){
    	return getBusinessNumber() + RandomStringUtils.randomNumeric(6);
    }
}
