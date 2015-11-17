package com.vaolan.adserver.util;

public class Constant {

	//系统有需要用到编码时，统一使用utf-8编码
	public static String ENCODING = "utf-8";
	//被推送客户点击广告物料，访问url
	public static String ACCESS_URL = "1";
	//广告主自己浏览物料
	public static String USER_TYPE_VIEW="view";
	//是否收集客户端信息（1:收集）
	public static String COLLECT_USER_INFOR_Y="1";
	public static String COLLECT_USER_INFOR_N="0";
	//客户端没有vaolan_uid 则保存cookie信息,否则不保存（1:yes，0:no）
	public static String IS_SAVE_COOKIE_Y = "1";
	public static String IS_SAVE_COOKIE_N = "0";
	//移动广告展示的不同页面
	final public static String MOBILE_OPEN_PAGE = "mobile/open_page";//open一个地址
	final public static String MOBILE_DOWNLOAD_ANDROID_APK="mobile/download_android_apk";
	final public static String MOBILE_DOWNLOAD_IOS_APP="mobile/download_ios_app";
	
	public static String AD_ALL_INFO = "ad_all_info_";
	
	public static String RANDOM = "random";
	
	public static String BLANK = "";
	
	//每一次展示生成一个唯一id，存入redis，key的前缀
	public static String IMPRESS_ID_KEY_PRIEXL="imp_";
	
	
	public static String NHT_STAT_PATTERN_PRIEXL="NHT_";
	
	public static String CLICK_DOMAIN_KEY="dsp_click_domain";
	
	public static String JS_VERSION = "js_version";
	
	//访问平率COOKIE
	public static String USER_FREQUENCY_COOKIE = "user_prequency_cookie";
	public static String FREQUENCY = "prequency_";
	
	//参与动态广告的广告id 列表
	public static String DYNAMIC_ADS = "dynamic_ads";
}
