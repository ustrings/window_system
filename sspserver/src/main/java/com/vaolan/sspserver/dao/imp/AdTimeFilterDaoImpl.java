package com.vaolan.sspserver.dao.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hidata.framework.db.DBManager;
import com.vaolan.sspserver.dao.AdTimeFilterDao;
import com.vaolan.sspserver.model.AdTimeFilter;


@Repository
public class AdTimeFilterDaoImpl implements AdTimeFilterDao {

	@Autowired
	private DBManager db;
	
	/* (non-Javadoc)
	 * @see com.vaolan.adtarget.dao.impl.AdTimeFilterDao#getTimeFilterByAdId(java.lang.String)
	 */
	@Override
	public List<AdTimeFilter> getTimeFilterByAdId(String adId){
		String sql ="select id,ad_id,days_of_week,start_hour,end_hour from ad_time_filter where ad_id=?";
		Object[] args = new Object[]{adId};
		return db.queryForListObject(sql,args,AdTimeFilter.class);
	}
}
