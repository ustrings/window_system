package com.vaolan.sspserver.util;

import javax.servlet.ServletContext;

public class Constant {
	/**
	 *  系统编码
	 */
	public static String ENCODING = "utf-8";

	/**
	 *  VIZURY的广告id，这个id 是虚拟的，并没有一个真的广告，只是vizury的投放统计挂在这个上面
	 */
	public static String VIZURY_ADID = "304";

	/**
	 * VIZURY的广告id，这个id 是虚拟的，并没有一个真的广告，只是vizury的投放统计挂在这个上面
	 */
	public static String VIZURY_USERID = "42";

	public static String VIZURY_MAID = "278";

	/**
	 * Wangmeng的广告id，这个id 是虚拟的，并没有一个真的广告，只是wangmeng的投放统计挂在这个上面
	 */
	public static String WANG_MENG = "289";

	/**
	 * 每一次展示生成一个唯一id，存入redis，key的前缀
	 */
	public static String IMPRESS_ID_KEY_PRIEXL = "imp_";

	/**
	 * redis存储pvcount的key值
	 */
	final public static String REDIS_PVCOUNT = "PVCount";

	/**
	 * 离线人权前缀标志
	 */
	public static String ADUA = "adua_";

	/**
	 * 当前分钟的pv量
	 */
	public static String AD_MIN_NUM = "ad_min_num";

	// 广告命中原因

	/**
	 * 既搜既投
	 */
	public static String HIT_CAUSE_JSJT = "JSJT";

	/**
	 * 离线人群
	 */
	public static String HIT_CAUSE_OFFCROWD = "OFF_CROWD";

	/**
	 * URL定向
	 */
	public static String HIT_CAUSE_URLTARGET = "URL_TARGET";

	/**
	 * AD帐号定向
	 */
	public static String HIT_CAUSE_ADTARGET = "AD_TARGET";

	/**
	 * IP定向
	 */
	public static String HIT_CAUSE_IP_TARGET = "IP_TARGET";

	/**
	 * IP定向
	 */
	public static String HIT_CAUSE_HOST_TARGET = "HOST_TARGET";

	/**
	 * 标签定向
	 */
	public static String HIT_CAUSE_LABEL_TARGET = "LABEL_TARGET";
	
	/**
	 * 关键词定向
	 */
	public static String HIT_CAUSE_KEYWORD_TARGET = "KEYWORD_TARGET";

	/**
	 *  诏兰盲投
	 */
	public static String HIT_CAUSE_VL_MT = "VL_MT";

	/**
	 * 第三方盲投
	 */
	public static String HIT_CAUSE_3_MT = "3_MT";

	/**
	 * 没有可合适的广告
	 */
	public static String HIT_CAUSE_NO = "NO";

	/**
	 * 广告是诏兰精准(既搜既投,url定向,ad帐号定向，离线人群)
	 */
	public static String AD_THROW_TYPE_VLJZ = "1";

	/**
	 * 广告类型，非精准
	 */
	public static String AD_THROW_TYPE_FJZ = "2";

	/**
	 * 广告创意关联类型  : 自有物料
	 */
	public static String LINK_TYPE_M = "M";
	/**
	 * 广告创意关联类型  : 外部投放连接
	 */
	public static String LINK_TYPE_E = "E";
	/**
	 * 广告创意关联类型  : 生成广告的js连接，客户给的js 已经绝对位置放到右下角
	 */
	public static String LINK_TYPE_J = "J";
	
	
	/**
	 * 广告创意关联类型  : 生成广告的js连接，还在想对位置，需要我们去放
	 */
	public static String LINK_TYPE_L = "L";
	
	/**
	 * 关键字精准匹配
	 */
	public static String KEYWORD_EQUAL_MACH = "1";
	
	/**
	 * 关键字中心匹配
	 */
	public static String KEYWORD_CONTAINS_MACH = "2";
	
	/**
	 * 关键词存放类型 1.文本框输入形式
	 */
	public static String KEYWORD_TYPE_KW = "1";
	
	/**
	 * 关键词存放类型 2.文本文件形式
	 */
	public static String KEYWORD_TYPE_TEXT = "2";
	
	/**
	 * ftp 用户名
	 */
	public static String  FTP_USER = Config.getProperty("ftp_user");
	/**
	 * ftp 密码
	 */
	public static String  FTP_PWD = Config.getProperty("ftp_pwd");
	/**
	 * ftp host
	 */
	public static String  FTP_HOST = Config.getProperty("ftp_host");
	/**
	 * ftp 端口
	 */
	public static String  FTP_PORT = Config.getProperty("ftp_port");
	/**
	 * 
	 */
	public static String  FTP_DOWNLOAD_FILE_PATH= Config.getProperty("ftpDownloadFilePath");
//	public static String  FTP_DOWNLOAD_FILE_PATH = System.getProperty("user.dir"); 
//	public static String  FTP_DOWNLOAD_FILE_PATH = System.getenv("CATALINA_HOME");  
	
	/**
	 * 标签关键词查询 url
	 */
	public static String  KEYWORD_LABLE_QUERY_URL= Config.getProperty("keyword_lable_query_url");
	
	/**
	 * 
	 */
	public static int  NUM_FOR_KEYWORD_TO_FENCI= Integer.parseInt(Config.getProperty("num_for_keyword_to_fenci"));
	
	/**
	 * 即搜即投的标识为 4
	 */
	public static final String JSJT_ADID_FLAGE = "4";
	
	public static final String BAIDU_JSJT_ADID_FLAGE = "5";
	
	public static final String COOKIE_PREFIX = "cookieForAdId";
	
}
