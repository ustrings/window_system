package com.vaolan.adserver.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.vaolan.adserver.model.AdMaterialCache;


public interface AdMaterialService {
	
	/**
	 * 根据广告id和materialId定位唯一广告物料
	 * @param adId
	 * @param meterialId
	 */
	public <T> T findMaterialById(String adId, String meterialId,Class<T> classType);
	/**
	 * 根据广告id查找所属物料
	 * @param adId
	 */
	public List<AdMaterialCache> findMaterialByAdId(String adId);
	
	
	/**
	 * 
	 * 为每次展示生成一个唯一，用来标识一次展示的id，存入redis，
	 * 然后记录pv的时候，先查redis是否有这个id，如果有记录pv信息，并从redis中删除id。
	 * 如果没有则表示pv信息已经记录过了，不在记录，这样能避免NHT重复记录pv细心，导致假的pv量
	 * @return  返回生成的展示id
	 */
	public String  genImpressionUUID(String clientIp,String url,String referrer,String userAgent);
	
	
	/**
	 * 判断impuuid 是否存在，如果存在则需要执行记录pv的统计代码，并删除impuuid
	 * @param impuuid
	 * @return
	 */
	public boolean isNeedExecStatCode(String impuuid);	
	
	/**
	 * 如果一个广告，启用了NHT模式统计，则每一次NHT请求，要处理
	 * 处理内容：1，判断当前广告是否启用NHT统计模式，
	 * 2、如果启用，该广告的NHT数量+1
	 * @param adId
	 */
	public void processNHTstat(String adId,String isHaveNHTStat);
	
	/**
	 * js的version，放入redis，每次从redis取出来，如果要更新version直接把redis里的version修改
	 * @return
	 */
	public String getJsVersion();
	
	/**
	 * 获取广告终端规则
	 * @param adId
	 * @return
	 * @author xiaoming
	 */
	public Map<String,String> findValueByAdIdAndType(String adId);
	
	
	public String findAdDynamicMaterial();
	
	/**
	 * 获取用户终端信息
	 * @param request
	 * @return
	 */
	public Map<String, String> getUserAgentMassage(HttpServletRequest request);
	
	public Boolean getValueByKey(String key);
	
	public void add(String key, String type, String value);
	
}
