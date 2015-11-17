package com.hidata.ad.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.hidata.ad.web.dao.RegionTargetingDao;
import com.hidata.ad.web.dto.AdRegionLink;
import com.hidata.ad.web.model.RegionTargeting;
import com.hidata.framework.db.DBManager;

/**
 */
@Component
public class RegionTargetingDaoImpl implements RegionTargetingDao {

	@Autowired
	private DBManager db;
	

	@Override
	public List<RegionTargeting> findAdRegionListByCondition(RegionTargeting regionTargeting){
		List<Object> paramList = new ArrayList<Object>();
		StringBuilder sqlBuilder = new StringBuilder("select *  from region_targeting where 1=1");
		if(!StringUtils.isEmpty(regionTargeting.getCode())){
			sqlBuilder.append(" and code=?");
			paramList.add(regionTargeting.getCode());
		}
		if(!StringUtils.isEmpty(regionTargeting.getParentCode())){
			sqlBuilder.append(" and parent_code=?");
			paramList.add(regionTargeting.getParentCode());
		}
		if(!StringUtils.isEmpty(regionTargeting.getName())){
			sqlBuilder.append(" and name like ?");
			paramList.add("%"+regionTargeting.getName()+"%");
		}
		Object[] args = paramList.toArray();
		return db.queryForListObject(sqlBuilder.toString(),args, RegionTargeting.class);
	}
	
	private static final String FIND_ADCATEGORYDTO_LIST_BY_ADID = "select b.code, b.name, b.parent_code  from ad_region_link a ,region_targeting b where b.code=a.region_code and  a.ad_id = ?";

	@Override
	public List<RegionTargeting> findAdRegionListByAdId(String adId){
		Object[] args = new Object[]{adId};
		return db.queryForListObject(FIND_ADCATEGORYDTO_LIST_BY_ADID, args, RegionTargeting.class);
	}
	
	@Override
	public int insertAdRegionLink(AdRegionLink adRegionLink) {
		return db.insertObject(adRegionLink);
	}
}
