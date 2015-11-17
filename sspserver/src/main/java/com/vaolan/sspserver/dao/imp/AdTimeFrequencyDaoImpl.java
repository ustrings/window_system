package com.vaolan.sspserver.dao.imp;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hidata.framework.db.DBManager;
import com.vaolan.sspserver.dao.AdTimeFrequencyDao;
import com.vaolan.sspserver.model.AdTimeFrequency;

@Repository
public class AdTimeFrequencyDaoImpl implements AdTimeFrequencyDao {
	
	@Autowired
	private DBManager db;

	/* (non-Javadoc)
	 * @see com.vaolan.adtarget.dao.impl.AdTimeFrequencyDao#getAllAdTimeFrequency(java.lang.String)
	 */
	@Override
	public AdTimeFrequency getFrequencyByAdId(String adId)
	{
		String sql = "select ad_tf_id,ad_id,put_interval_unit,put_interval_num,day_limit,create_time,sts,minute_limit,is_uniform from ad_time_frequency a where a.ad_id=?";
		Object[] args = new Object[] {adId};
		return db.queryForBean(sql,args,AdTimeFrequency.class);
	}
}
