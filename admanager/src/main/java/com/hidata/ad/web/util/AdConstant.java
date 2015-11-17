package com.hidata.ad.web.util;

/**
 * 广告模块常量 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月14日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
public class AdConstant {
	//广告计划状态
	
	public static final String AD_TYPE_M = "M";//广告物料
	public static final String AD_TYPE_E = "E";//投放链接
    // 广告使用状态:A 在用 D 废
    public static final String AD_STS_A = "A";

    public static final String AD_STS_D = "D";

    // URL模糊匹配
    public static final String AD_MATCHTYPE_R = "R";

    // URL完全匹配
    public static final String AD_MATCHTYPE_F = "F";

    // 背景定向url
    public static final String AD_MATCHTYPE_A = "A";

    // 兴趣标签树根节点标签值
    public static final String INTREST_LABEL_ALL = "all";

    // 兴趣标签树根节点标签名称
    public static final String INTREST_LABEL_ROOT_NAME = "兴趣标签";
    
    
 // 所有的ad统计信息在redis中的key
  	public static String ADSTAT_KEYS_PATTERN = "adstat_main_*";

  	// 所有的material统计信息在redis中的key
  	public static String MATERIALSTAT_KEYS_PATTERN = "materialstat_main_*";

  	public static String STAT_NUM_TYPE_DAY = "D";

  	public static String STAT_NUM_TYPE_HOUR = "H";

  	// 广告统计信息在redis中的key前缀
  	public static String AD_STAT_KEY_PREFIX = "adstat_main_";

  	// 物料统计信息在redis中的key的前缀
  	public static String MATERIAL_STAT_KEY_PREFIX = "materialstat_main_";

  	// 某个广告当天的uv明细set集合
  	public static String AD_STAT_DAY_UV_KEY_PREFIX = "adstat_day_uv_";

  	// 某个广告当天的ip明细set集合
  	public static String AD_STAT_DAY_IP_KEY_PREFIX = "adstat_day_ip_";

  	// 某个广告当前小时的uv明细set集合
  	public static String AD_STAT_HOUR_UV_KEY_PREFIX = "adstat_hour_uv_";

  	// 某个广告当前小时的ip明细set集合
  	public static String AD_STAT_HOUR_IP_KEY_PREFIX = "adstat_hour_ip_";

  	// 某个物料当天的uv明细set集合
  	public static String MATERIAL_STAT_DAY_UV_KEY_PREFIX = "materialstat_day_uv_";

  	// 某个物料当天的ip明细set集合
  	public static String MATERIAL_STAT_DAY_IP_KEY_PREFIX = "materialstat_day_ip_";

  	// 某个物料当前小时的uv明细set集合
  	public static String MATERIAL_STAT_HOUR_UV_KEY_PREFIX = "materialstat_hour_uv_";

  	// 某个物料当前小时的ip明细set集合
  	public static String MATERIAL_STAT_HOUR_IP_KEY_PREFIX = "materialstat_hour_ip_";
     

}
