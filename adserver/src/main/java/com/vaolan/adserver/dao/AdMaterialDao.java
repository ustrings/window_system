package com.vaolan.adserver.dao;

import java.util.List;

import com.vaolan.adserver.model.AdMaterialCache;
import com.vaolan.adserver.model.MobileMaterial;



public interface AdMaterialDao {

	/**
	 * 根据广告id和meterialId查找所属物料
	 * @param adId
	 * @param meterialId
	 * @return AdMaterialCache
	 */
	public AdMaterialCache findMaterialById(String adId,String meterialId);
	/**
	 * 根据广告id查找所属物料
	 * @param adId
	 * @return AdMaterialCache
	 */
	public List<AdMaterialCache> findMaterialByAdId(String adId);
	
	
	/**
	 * 根据广告id， 把外部连接，或者外部js形式的创意，模拟成物料记录，存入redis，不然每次广告url，redis查不到要查数据库
	 * @param adId
	 * @return
	 */
	public List<AdMaterialCache> findExtLinkByAdId(String adId);
	/**
	 * 在所有可用物料中，随机选取选取n个广告物料
	 * @param n 物料数
	 * */
	public List<AdMaterialCache> randomMaterial(int n);
	/**
	 * 根据广告id和meterialId查找所属物料
	 * @param adId
	 * @param materialId
	 * @param class  AdMaterialCache.class or MobileMaterial.class
	 * @return
	 */
	public <T> T findMaterialById(String adId,String meterialId,Class<T> classType);
}
