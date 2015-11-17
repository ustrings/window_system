package com.vaolan.sspserver.dao.imp;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hidata.framework.db.DBManager;
import com.vaolan.sspserver.dao.AdUserFrequencyDao;
import com.vaolan.sspserver.model.AdUserFrequency;

@Repository
public class AdUserFrequencyDaoImpl implements AdUserFrequencyDao {
	
	@Autowired
	private DBManager db;

	@Override
	public AdUserFrequency getUserFrequencyByAdId(String adId) {
		String sql = "select ad_uf_id,uid_impress_num,sts,ad_id, uid_ip_num from ad_user_frequency a where a.ad_id=? and sts='A'";
		Object[] args = new Object[] {adId};
		return db.queryForBean(sql,args,AdUserFrequency.class);
	}
}
