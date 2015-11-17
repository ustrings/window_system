package com.vaolan.adtimer.util;

/**
 * 
 * @author chenjinzhao
 *
 */
public class Constant {

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
	
	//当前分钟广告的投放量
	public static String AD_MIN_NUM = "ad_min_num";
	
	
	 //host维度, 统计广告信息的前缀KEY
    public static String AD_HOST_KEY = "ad_host_key_";
    
    //某个广告当天的uv明细set集合
    public static String AD_HOST_UV = "ad_host_uv_";
    
    //某个广告当天的pv明细set集合
    public static String AD_HOST_IP = "ad_host_ip_";
    
    
    public static String Ad_HOST_KEY_PATTERN = "ad_host_key_*";
	

}
