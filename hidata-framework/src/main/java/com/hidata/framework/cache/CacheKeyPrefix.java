package com.hidata.framework.cache;

/**
 * 所有缓存的key前缀
 * 请将所有缓存的key前缀定义在此类里面
 * key之间的value “不允许” 重复！
 * @author houzhaowei
 */
public class CacheKeyPrefix {

	//支付类型缓存key
	public static final String PAY_TYPE_KEY = "pay_types_%s_%s";
	//根据type 取bean
	public static final String PAY_TYPE_BEAN_KEY = "pay_type_bean_%s_%s";
    //获取电影收费信息
    public static final String MOVIE_PRICE = "movie_price_%s";

    //套餐列表信息
    public static final String PACKAGE_LIST_KEY = "package_list_%s";
}
