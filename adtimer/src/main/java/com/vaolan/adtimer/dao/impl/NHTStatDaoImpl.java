package com.vaolan.adtimer.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hidata.framework.db.DBManager;
import com.vaolan.adtimer.model.AdInstance;
import com.vaolan.adtimer.model.AdMaterialCache;
import com.vaolan.adtimer.dao.NHTStatDao;

@Repository
public class NHTStatDaoImpl implements NHTStatDao {

	@Autowired
	private DBManager db;

	@Override
	public void subStat2TempStat(String adId) {

		String sql = "update ad_instance set ad_3stat_code_temp = ad_3stat_code_sub,ad_3stat_code_sub='' where ad_id = ?";
		db.update(sql, new Object[] { adId });

	}

	public AdInstance getAdInstanceByAdId(String adId) {

		String sql = "select ad_id,ad_name,ad_desc,userid,charge_type,date_format(start_time,'%Y-%m-%d') start_time,date_format(end_time,'%Y-%m-%d') end_time,all_budget,day_budget,ad_url,create_time,sts, ad_3stat_code,ad_3stat_code_sub,ad_3stat_code_temp from ad_instance where ad_id = ?";

		Object[] args = new Object[] { adId };
		AdInstance adInst = db.queryForObject(sql, args, AdInstance.class);

		return adInst;
	}

	@Override
	public void tempStat2SubStat(String adId) {
		String sql = "update ad_instance set ad_3stat_code_sub = ad_3stat_code_temp,ad_3stat_code_temp='' where ad_id = ?";

		db.update(sql, new Object[] { adId });
	}

	@Override
	public List<AdMaterialCache> findMaterialByAdId(String adId) {

		String sql = "select l.ad_id, m.ad_m_id,m.m_type,k.width,k.height,m.material_name,m.link_url,m.target_url,m.monitor_link,i.ad_3stat_code, i.ad_3stat_code_sub ,i.ad_3stat_code_temp "
				+ "from ad_m_link  l,ad_material m,ad_instance i,map_kv k "
				+ "where l.ad_m_id=m.ad_m_id and i.ad_id=l.ad_id and m.material_size=k.attr_code "
				+ "and k.m_type='material_size' and l.sts='A' and m.sts='A' and l.ad_id=?";
		Object[] args = new Object[] { adId };
		return db.queryForListObject(sql, args, AdMaterialCache.class);
	}

}
