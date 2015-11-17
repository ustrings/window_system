package com.vaolan.adtimer.dao;

import java.util.List;

import com.vaolan.adtimer.model.AdInstance;
import com.vaolan.adtimer.model.AdMaterialCache;

public interface NHTStatDao {
	
	/**
	 * NHT模式的统计代码，移动到非NHT统计模式
	 * @param adId
	 */
	public void subStat2TempStat(String adId);
	
	
	/**
	 * 非NHT模式的统计代码，重新启动到NHT统计模式
	 * @param adId
	 */
	public void tempStat2SubStat(String adId);
	
	
	
	public List<AdMaterialCache> findMaterialByAdId(String adId);
	
	
	public AdInstance getAdInstanceByAdId(String adId);

}
