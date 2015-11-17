package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.model.AdMaterialCache;


public interface AdMaterialCacheDao {

	/**
	 * 以hash结构存储多个AdMaterial对象<br>
	 * 以ad_m_id为field<br>
	 * 把AdMaterial转换成JsonString 作为value
	 * */
	public abstract void hset(String key, List<AdMaterialCache> list);

	/**
	 * 取具体key对应的所有value<br>
	 * @return List<AdMaterialCache>
	 * */
	public abstract List<AdMaterialCache> hgetAll(String key);
	
	/**
	 * 删除key
	 * @param key
	 */
	public  void del(String key);

	/**
	 * 查看具体的key是否存在。
	 * */
	public abstract boolean isExists(String key);
	
	/**
	 * hset key field value将哈希表key中的域field的值设为value。//用于终端定向的储存
	 * @param key
	 * @param tytpe
	 * @param value
	 * @author xiaoming
	 */
	public abstract void add(String key, String type, String value);

}