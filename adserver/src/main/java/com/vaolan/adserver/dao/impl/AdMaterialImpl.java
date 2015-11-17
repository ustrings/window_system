package com.vaolan.adserver.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.DBManager;
import com.vaolan.adserver.dao.AdMaterialDao;
import com.vaolan.adserver.model.AdMaterialCache;
import com.vaolan.adserver.model.MobileMaterial;

@Component
public class AdMaterialImpl implements AdMaterialDao {

	@Autowired
	private DBManager dbManager;

	@Override
	public AdMaterialCache findMaterialById(String adId, String meterialId) {
		String sql = "select l.ad_id, m.ad_m_id,m.m_type,k.width,k.height,m.material_name,m.link_url,m.target_url,m.monitor_link,i.ad_3stat_code "
				+ "from ad_m_link  l,ad_material m,ad_instance i,map_kv k "
				+ "where l.ad_m_id=m.ad_m_id and i.ad_id=l.ad_id and m.material_size=k.attr_code "
				+ "and k.m_type='material_size' and l.sts='A' and m.sts='A' and l.ad_id=? and l.ad_m_id=?";
		Object[] args = new Object[] { adId, meterialId };
		return dbManager.queryForBean(sql, args, AdMaterialCache.class);
	}

	@Override
	public List<AdMaterialCache> findMaterialByAdId(String adId) {

		String sql = "select l.ad_id, m.ad_m_id,m.m_type,k.width,k.height,m.material_name,m.link_url,m.target_url,m.monitor_link,"
				+ "i.ad_3stat_code, i.ad_3stat_code_sub ,i.ad_3stat_code_temp, m.rich_text,m.cover_flag "
				+ "from ad_m_link  l,ad_material m,ad_instance i,map_kv k "
				+ "where l.ad_m_id=m.ad_m_id and i.ad_id=l.ad_id and m.material_size=k.attr_code "
				+ "and k.m_type='material_size' and l.sts='A' and m.sts='A' and l.ad_id=?";
		Object[] args = new Object[] { adId };
		return dbManager.queryForListObject(sql, args, AdMaterialCache.class);
	}

	@Override
	public List<AdMaterialCache> findExtLinkByAdId(String adId) {

		String sql = "select i.ad_id, '0' as ad_m_id, '3' as m_type, e.width,e.height, '' as material_name, '' as link_url,'' as target_url, "
				+ "'' as monitor_link, i.ad_3stat_code,i.ad_3stat_code_sub,i.ad_3stat_code_temp,'' as rich_text, '' as cover_flag "
				+ "from ad_ext_link e, ad_instance i "
				+ "where e.ad_instance_id = i.ad_id and i.ad_id=?";
		Object[] args = new Object[] { adId };
		return dbManager.queryForListObject(sql, args, AdMaterialCache.class);
	}

	@Override
	public <T> T findMaterialById(String adId, String meterialId,
			Class<T> classType) {
		String sql = "";
		if (classType == AdMaterialCache.class) {
			sql = "select l.ad_id, m.ad_m_id,m.m_type,k.width,k.height,m.material_name,m.link_url,m.target_url,m.monitor_link,i.ad_3stat_code "
					+ "from ad_m_link  l,ad_material m,ad_instance i,map_kv k "
					+ "where l.ad_m_id=m.ad_m_id and i.ad_id=l.ad_id and m.material_size=k.attr_code "
					+ "and k.m_type='material_size' and l.sts='A' and m.sts='A' and l.ad_id=? and l.ad_m_id=?";
		} else if (classType == MobileMaterial.class) {
			sql = "select l.ad_id, m.ad_m_id,m.m_type,k.width,k.height,mc.click_id,mc.target,m.link_url	"
					+ "from ad_m_link  l,ad_material m,ad_instance i,ad_mongo_click_link mc,map_kv k "
					+ "where l.ad_m_id=m.ad_m_id and i.ad_id=l.ad_id and i.ad_id=mc.ad_id and m.material_size=k.attr_code "
					+ "and k.m_type='material_size' and l.sts='A' and m.sts='A' and l.ad_id=? and l.ad_m_id=?";
		}
		Object[] args = new Object[] { adId, meterialId };
		return dbManager.queryForBean(sql, args, classType);
	}

	@Override
	public List<AdMaterialCache> randomMaterial(int n) {
		String sql = "select l.ad_id, m.ad_m_id,m.m_type,k.width,k.height,m.material_name,m.link_url,m.target_url,m.monitor_link,i.ad_3stat_code,i.ad_3stat_code_sub "
				+ "from ad_m_link  l,ad_material m,ad_instance i,map_kv k "
				+ "where l.ad_m_id=m.ad_m_id and i.ad_id=l.ad_id and m.material_size=k.attr_code "
				+ "and k.m_type='material_size' and l.sts='A' and m.sts='A'  ORDER BY RAND() LIMIT "
				+ n;
		return dbManager.queryForListObject(sql, AdMaterialCache.class);
	}

}
