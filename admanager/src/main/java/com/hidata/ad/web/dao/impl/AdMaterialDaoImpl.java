package com.hidata.ad.web.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.AdMaterialDao;
import com.hidata.ad.web.model.AdInstance;
import com.hidata.ad.web.model.AdMLink;
import com.hidata.ad.web.model.AdMaterial;
import com.hidata.ad.web.model.AdMaterialCache;
import com.hidata.framework.db.DBManager;

@Component
public class AdMaterialDaoImpl implements AdMaterialDao {

	@Autowired
	private DBManager db;
	//添加 图片尺寸值，修改sql语句  周晓明
	private static final String INSERT_SQL = "insert into ad_material(m_type, material_name, material_size, link_url, target_url, create_time, material_desc, third_monitor, monitor_link, rich_text, userid, material_type, material_size_value) values(?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ? ,?)";
	public int insertMaterail(AdMaterial material) {
		int ad_m_id = db.insertObjectAndGetAutoIncreaseId(material);
		return ad_m_id;
	}
	
	/**
	 * 广告物料列表
	 */
	private static final String QUERY_MATERIAL_LIST_BY_USER = "select ad_m_id, material_name, material_size, sts, m_type, link_url,material_size_value,target_url,check_status from ad_material a where a.userid = ? and a.sts = 'A' and useful_type = 'N'";
	public List<AdMaterial> findAdMaterialListByUserid(int userid) {
		Object[] args = new Object[]{userid};
		return db.queryForListObject(QUERY_MATERIAL_LIST_BY_USER, args, AdMaterial.class);
	}
	
	/**
	 * 已经审核的广告物料(改为不审核  也能看见)
	 */
	//private static final String QUERY_CHECKED_MATERIAL_LIST_BY_USER = "select ad_m_id, material_name, material_size, sts, m_type, link_url,material_size_value,target_url,check_status from ad_material a where a.userid = ? and a.sts = 'A' and check_status=1";
	private static final String QUERY_CHECKED_MATERIAL_LIST_BY_USER = "select ad_m_id, material_name, material_size, sts, m_type, link_url,material_size_value,target_url,check_status from ad_material a where a.userid = ? and a.sts = 'A' and useful_type = 'N'";
	@Override
	public List<AdMaterial> findCheckedAdMaterialListByUserid(int userid) {
		Object[] args = new Object[]{userid};
		return db.queryForListObject(QUERY_CHECKED_MATERIAL_LIST_BY_USER, args, AdMaterial.class);
	}
	//根据ID查找物料所有属性，添加图片尺寸 周晓明
	private static final String QUERY_MATERIAL_BY_ID = "select ad_m_id, m_type, material_name, material_size, link_url, target_url, material_desc, third_monitor, monitor_link, rich_text, material_type , material_size_value,cover_flag from ad_material a where a.userid = ? and a.ad_m_id=?";
	public AdMaterial findAdMaterialById(int userid, int id) {
		Object[] args = new Object[]{userid, id};
		return db.queryForObject(QUERY_MATERIAL_BY_ID, AdMaterial.class, args);
	}
	
	//根据ID查找物料所有属性
	private static final String QUERY_MATERIAL_BY_ID1 = "select ad_m_id, m_type, material_name, material_size, link_url, target_url, material_desc, third_monitor, monitor_link, rich_text, material_type , material_size_value from ad_material a where a.ad_m_id=?";
	public AdMaterial findAdMaterialById(String id) {
		Object[] args = new Object[]{ id };
		return db.queryForObject(QUERY_MATERIAL_BY_ID1, AdMaterial.class, args);
	}
	
	private static final String DELETE_MATERIAL_BY_ID = "delete from ad_material where userid = ? and ad_m_id = ?";
	public int deleteAdMaterialById(int userid, int id) {
		Object[] args = new Object[]{userid, id};
		return db.update(DELETE_MATERIAL_BY_ID, args);
	}
	
	//修改物料实体，包括图片尺寸 周晓明
	private static final String UPDATE_SQL = "update ad_material a set m_type=?, material_name=?, material_size=?, link_url=?, target_url=?, material_desc=?, third_monitor=?, monitor_link=?, rich_text=?, material_type=?, material_size_value=? ,cover_flag=? where a.userid = ? and a.ad_m_id = ?";
	public int updateAdMaterial(AdMaterial material) {
		Object[] args = new Object[]{material.getMType(), material.getMaterialName(), material.getMaterialSize(), 
				material.getLinkUrl(), material.getTargetUrl(), material.getMaterialDesc(), material.getThirdMonitor(), 
				material.getMonitorLink(), material.getRichText(), material.getMaterialType(), material.getMaterialValue(),material.getCoverFlag(), material.getUserid(),
				material.getId()};
		return db.update(UPDATE_SQL, args);
	}
	
	@Override
	public int insertMaterialAndGetKey(AdMaterial material) {
		return db.insertObjectAndGetAutoIncreaseId(material);
	}
	
	private static final String UPDATE_STS_SQL = "update ad_material a set sts = 'D' where a.userid = ? and a.ad_m_id = ?";
	@Override
	public int updateAdMaterialStatus(int userid, int id) {
		Object[] args = new Object[]{userid, id};
		return db.update(UPDATE_STS_SQL, args);
	}

	private static final String QUERY_MATERIAL_LIST_BY_AD_ID = "select a.ad_m_id, a.material_name, a.material_size, a.sts, a.m_type, a.link_url from ad_material a, ad_m_link b where a.ad_m_id = b.ad_m_id and b.ad_id = ?";
	@Override
	public List<AdMaterial> findAdMaterialListByAdId(int adId) {
		Object[] args = new Object[]{adId};
		return db.queryForListObject(QUERY_MATERIAL_LIST_BY_AD_ID, args, AdMaterial.class);
	}
	
	@Override
	public List<AdMaterialCache> findMaterialByAdId(String adId){
		
		String sql = "select  m.ad_m_id,m.m_type,k.width,k.height,m.material_name,m.link_url,m.target_url,m.monitor_link,i.ad_3stat_code,i.ad_3stat_code_sub,i.ad_3stat_code_temp, m.rich_text,m.cover_flag "
				+ "from ad_m_link  l,ad_material m,map_kv k,ad_instance i where l.ad_m_id=m.ad_m_id and m.material_size=k.attr_code" +
				  " and l.sts='A' and m.sts='A' and l.ad_id = i.ad_id and l.ad_id=? and k.m_type='material_size' ";
		Object[] args = new Object[]{adId};
		return db.queryForListObject(sql, args,AdMaterialCache.class);
	}
	
	/**
	 * 根据物料ID查询使用物料ID的广告
	 * @author xiaoming
	 */
	@Override
	public List<AdMLink> getA_M_LbYad_mid(String ad_m_id) {
		String sql = "SELECT ad_id,ad_m_id ,sts id FROM ad_m_link WHERE ad_m_id = ? AND sts = 'A'";
		Object[] args = new Object []{
			ad_m_id	
		};
		return db.queryForListObject(sql, args, AdMLink.class);
	}
	
	/**
	 * 根据广告IDs 查找广告
	 * @author xiaoming
	 */
	@Override
	public List<AdInstance> getAdInstancesByAdId(String ad_ids, String userid) {
		String sql = "SELECT * FROM ad_instance WHERE sts = 'A' AND userid = "+ userid +" AND ad_id IN (" + ad_ids + ")";
		return db.queryForListObject(sql, null, AdInstance.class);
	}
	
	@Override
	public List<AdMaterialCache> findExtLinkByAdId(String adId) {
		String sql = "select i.ad_id, '0' as ad_m_id, '3' as m_type, e.width,e.height, '' as material_name, '' as link_url,'' as target_url, "
				+ "'' as monitor_link, i.ad_3stat_code,i.ad_3stat_code_sub,i.ad_3stat_code_temp,'' as rich_text, '' as cover_flag "
				+ "from ad_ext_link e, ad_instance i "
				+ "where e.ad_instance_id = i.ad_id and i.ad_id=?";
		Object[] args = new Object[] { adId };
		return db.queryForListObject(sql, args, AdMaterialCache.class);
	}
	
	@Override
	public void updateAdMaterialRichText(AdMaterial material) {
		String sql="update ad_material set rich_text=? where ad_m_id=?";
		Object[] args = new Object[]{material.getRichText(),material.getId()};
		db.update(sql,args);
	}
}
