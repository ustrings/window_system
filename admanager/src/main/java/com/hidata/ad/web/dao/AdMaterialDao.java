package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.model.AdInstance;
import com.hidata.ad.web.model.AdMLink;
import com.hidata.ad.web.model.AdMaterial;
import com.hidata.ad.web.model.AdMaterialCache;

public interface AdMaterialDao {

	public int insertMaterail(AdMaterial material);
	
	public List<AdMaterial> findAdMaterialListByUserid(int userid);
	
	public AdMaterial findAdMaterialById(int userid, int id);
	
	public AdMaterial findAdMaterialById(String id);
	
	public int deleteAdMaterialById(int userid, int id);
	
	public int updateAdMaterial(AdMaterial material);
	
	public int insertMaterialAndGetKey(AdMaterial material);
	
	public int updateAdMaterialStatus(int userid, int id);
	
	public List<AdMaterial> findAdMaterialListByAdId(int adId);
	
	public List<AdMaterialCache> findMaterialByAdId(String adId);
	
	public List<AdMaterialCache> findExtLinkByAdId(String adId);
	
	/**
	 * 根据物料ID查询与该物料相关的广告实体
	 * @author xiaoming
	 * @param ad_m_id
	 * @return
	 */
	public List<AdMLink> getA_M_LbYad_mid(String ad_m_id);
	
	/**
	 * 根据广告IDs 查找广告
	 * @param ad_ids
	 * @return
	 */
	public List<AdInstance> getAdInstancesByAdId(String ad_ids, String userid);

	public List<AdMaterial> findCheckedAdMaterialListByUserid(int userid);

	public void updateAdMaterialRichText(AdMaterial material);

}
