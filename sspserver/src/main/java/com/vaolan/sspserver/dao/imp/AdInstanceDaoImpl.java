package com.vaolan.sspserver.dao.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hidata.framework.db.DBManager;
import com.vaolan.sspserver.dao.AdInstanceDao;
import com.vaolan.sspserver.model.AdInstance;

@Repository
public class AdInstanceDaoImpl implements AdInstanceDao {

	@Autowired
	private DBManager db;
	
	/* (non-Javadoc)
	 * @see com.vaolan.adtarget.dao.impl.AdInstanceDao#getInstanceByChannel(java.lang.String)
	 */
	@Override
	public List<AdInstance> getInstanceByChannel(String channel)
	{
		String sql = "select ad_id,ad_name,ad_desc,userid,charge_type,start_time,date_format(end_time,'%Y-%m-%d') end_time,"
				+ "ad_useful_type," 
				+ "all_budget,day_budget,ad_url,create_time,sts,ad_tanx_url,channel,close_type,link_type,ad_type "+
					 " from ad_instance where sts='A' and now() between start_time and end_time and channel=? and ad_toufang_sts='2'";
		Object[] args = new Object[] {channel};
		return  db.queryForListObject(sql, args, AdInstance.class);
	}
}
