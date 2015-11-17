package com.hidata.ad.web.service;

import java.util.List;

import com.hidata.ad.web.model.AdInstance;
import com.hidata.ad.web.model.AdMaterial;
import com.hidata.ad.web.model.AdMaterialCache;

public interface AdMaterialService {
	
	public boolean addMaterial(AdMaterial material);
	
	public List<AdMaterial> findAdMaterialListByUserid(int userid);
	
	public AdMaterial findAdMaterialById(int userid, int id);
	
	public AdMaterial findAdMaterialById(String id);
	
	public int deleteAdMaterialById(int userid, int id);
	
	public int editAdMaterial(AdMaterial materail);
	
	public int insertMaterialAndGetKey(AdMaterial material);
	
	public List<AdMaterial> findAdMaterialListByAdId(int adId);
	
	
	public List<AdMaterialCache> findAdMaterialCacheListByAdId(String adId);
	
	/**
	 * 根据物料查询与之相关的广告实体
	 * @param ad_m_id
	 * @return
	 */
	public List<AdInstance> getListByadIds(String ad_m_id ,String userid);

	public List<AdMaterial> findCheckedAdMaterialListByUserid(int userid);
}
