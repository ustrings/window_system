package com.hidata.framework.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 为 {@link DynamicDataSource} 提供数据源
 * 现在有问题，spring不能在每个查询方法及时调用determineCurrentLookupKey，所以暂时停用
 * @author houzhaowei
 *
 */
public class CustomerContextHolder {

 
    public static final String DATA_SOURCE_READ = "dataSourceRead";
     
    public static final String DATA_SOURCE_WRITE = "dataSourceWrite";
     
    private static ThreadLocal<String> contextHolder = new ThreadLocal<String>();
     
    public static void setCustomerType(String customerType) {
        contextHolder.set(customerType);
    }


    public static String getCustomerType() {

        return contextHolder.get();
    }
     
    public static void clearCustomerType() {
        contextHolder.remove();
    }
}