package com.hidata.ad.web.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.AdIpTargetingDao;
import com.hidata.ad.web.model.AdIpTargeting;
import com.hidata.framework.db.DBManager;

/**
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * 
 */
@Component
public class AdIpTargetingDaoImpl implements AdIpTargetingDao {

	@Autowired
	private DBManager db;
	
	@Override
	public int addAdIpTargeting(AdIpTargeting adIpTargeting) throws Exception{
		String sql ="insert into ad_ip_targeting(ad_id,ip,create_time,type) values(?,?,?,?)";
		Object[] args = new Object[] { adIpTargeting.getAdId(),
				adIpTargeting.getIp(), adIpTargeting.getCreateTime(),
				adIpTargeting.getType() };
		return db.update(sql,args);
	}
	@Override
	public int delAdIpTargetingByAdId(Integer adId) throws Exception{
		StringBuilder sql = new StringBuilder("delete from ad_ip_targeting where ad_id=?");
		return db.update(sql.toString(), new Object[]{adId});
	}
	@Override
	public List<AdIpTargeting> queryAdIpTargetingListByAdId(Integer adId){
		StringBuilder sql = new StringBuilder("select *  from ad_ip_targeting where ad_id=?");
		return db.queryForListObject(sql.toString(), new Object[]{adId}, AdIpTargeting.class);
	}
	
}
