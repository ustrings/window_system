package com.vaolan.adserver.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.vaolan.adserver.model.AdMaterialCache;

public interface AdMaterialCacheDao {

	public <T> void hset(String key, String field,T obj);
	/**
	 * 以hash结构存储多个AdMaterial对象<br>
	 * 以ad_m_id为field<br>
	 * 把AdMaterial转换成JsonString 作为value
	 * */
	public abstract void hsets(String key, List<AdMaterialCache> list);

	public <T> T hget(String key, String field,Class<T> classType);
	/**
	 * 取具体key对应的所有value<br>
	 * 如果key不存在，返回NULL<br>
	 * @return List<AdMaterialCache>
	 * */
	public abstract List<AdMaterialCache> hgetAll(String key);

	/**
	 * 查看具体的key是否存在。
	 * */
	public abstract boolean isExists(String key);
	
	/**
	 * 根据key值获取广告终端信息
	 * @author xiaoming
	 * @param key
	 * @param field
	 * @return
	 */
	public Map<String, String> hegtTerminal(String key);
	
	/**
	 * 获取用户终端规则
	 * @return
	 */
	public Map<String,String> getUserAngetMassage(HttpServletRequest request);
	
	/**
	 * 添加
	 * @param key
	 * @param type
	 * @param value
	 */
	public void add(String key, String type, String value) ;
	
	public  Set<String> smembers(String key);

}