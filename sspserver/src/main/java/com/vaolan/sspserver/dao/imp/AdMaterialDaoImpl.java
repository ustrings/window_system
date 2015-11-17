package com.vaolan.sspserver.dao.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hidata.framework.db.DBManager;
import com.vaolan.sspserver.dao.AdMaterialDao;
import com.vaolan.sspserver.model.AdExtLink;
import com.vaolan.sspserver.model.AdMaterial;

@Repository
public class AdMaterialDaoImpl implements AdMaterialDao {

	@Autowired
	private DBManager db;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vaolan.adtarget.dao.impl.AdMaterialDao#getMaterialByAdId(java.lang
	 * .String)
	 */
	@Override
	public List<AdMaterial> getMaterialByAdId(String adId) {
		String sql = "select  m.ad_m_id,m.m_type,m.material_name,m.link_url,m.target_url,l.check_status, m.material_size_value "
				+ "from ad_m_link l,ad_material m where l.ad_m_id=m.ad_m_id and l.ad_id=?";
		Object[] args = new Object[] { adId };
		return db.queryForListObject(sql, args, AdMaterial.class);
	}

	@Override
	public AdExtLink getAdExtLinkByAdId(String adId) {

		String sql = "select a.id,a.ad_instance_id,a.throw_url,a.pic_size,a.width,a.height,a.sts,a.sts_date,a.remark "
				+ " from ad_ext_link a where a.ad_instance_id=?";
		Object[] args = new Object[] { adId };
		return db.queryForObject(sql, args, AdExtLink.class);
	}
}
