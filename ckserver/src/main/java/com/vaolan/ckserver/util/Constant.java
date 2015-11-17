package com.vaolan.ckserver.util;

public class Constant {

    public static String VAOLAN_COOKIE_NAME = "vid";

    public static String ADEXCHANGE_TANX = "1";

    public static String ADEXCHANGE_TANX_COOKIEMPPING_ERROR = "E0";

    // cookiemapping 对应关系在用
    public static String COOKIEMAPPING_STS_A = "A";

    // cookiemapping 对应关系不在用
    public static String COOKIEMAPPING_STS_D = "D";

    // 广告统计信息在redis中的key前缀
    public static String AD_STAT_KEY_PREFIX = "adstat_main_";

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

    // 物料统计信息在redis中的key的前缀
    public static String MATERIAL_STAT_KEY_PREFIX = "materialstat_main_";

    public static String AD_IP_APPED_REL_KEY_PREFIX = "ad_ipapend_rel_";
    
    
    
    public static String AD_HOST_STAT="ad_host_";
    
    public static String AD_MIN_NUM="ad_min_num";

    
    public static String AD_CROWD_IDS="ad_crowd_ids";
    
    //存放被点击的展示id列表
    public static String IMP_CLICK_KEY = "imp_click";

    public static String ENCODING = "utf-8";

    // tanx竞价价格解密秘钥
    public static byte[] G_KEY_VAOLAN = { (byte) 0x34, (byte) 0x4d, (byte) 0x58, (byte) 0xfc,
            (byte) 0xf1, (byte) 0x71, (byte) 0x33, (byte) 0x00, (byte) 0xdd, (byte) 0xb2,
            (byte) 0xb4, (byte) 0x0a, (byte) 0x82, (byte) 0xfb, (byte) 0x70, (byte) 0x4e };

    // tanx的CRC校验。0：失败 1：成功
    public static String CRC_FAIL = "0";

    public static String CRC_SUCCESS = "1";

    // 媒体信息：竞价成功时间
    public static String SEPARATE = "_";

    public static String OK_PRICE = "okPrice";

    public static String OK_TS = "okTS";

    public static String OK_COUNT = "okCount";

    // 用于标识ADX的cookie id
    public static String ADX_COOKIE_FLAG = "adx_";

    // 用于标识唯一用户的cookie id
    public static String UV_COOKIE_FLAG = "u_";

    // YK网站人群类型
    public static String YKCROWDTYPE = "2";
    
    /**
     * 广告ID和域名  作为维度 所统计的信息 前缀(以天为单位)
     */
    
    //host维度, 统计广告信息的前缀KEY
    public static String AD_HOST_KEY = "ad_host_key_";
    
    //某个广告当天的uv明细set集合
    public static String AD_HOST_UV = "ad_host_uv_";
    
    //某个广告当天的pv明细set集合
    public static String AD_HOST_IP = "ad_host_ip_";
    
}
