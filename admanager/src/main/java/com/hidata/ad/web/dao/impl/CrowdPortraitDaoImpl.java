package com.hidata.ad.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.CrowdPortraitDao;
import com.hidata.ad.web.model.CrowdPortrait;
import com.hidata.framework.db.DBManager;

/**
 * 
 * @author sunly
 */
@Component
public class CrowdPortraitDaoImpl implements CrowdPortraitDao {

	@Autowired
	private DBManager db;
	
	@Override
	public int insertObject(CrowdPortrait crowdPortrait)throws Exception {
		String sql = "insert into crowd_portrait (crowd_id,sex,age_range) values (?,?,?)";
		
		return db.update(sql,new String[]{crowdPortrait.getCrowdId(),crowdPortrait.getSex(),crowdPortrait.getAgeRange()});
	}
	
	@Override
	public int delCrowdPortraitByCrowdId(String crowdId)throws Exception {
		String sql = "delete  from crowd_portrait where crowd_id=?";
		return db.update(sql, new String[]{crowdId});
	}
	
	@Override
	public List<CrowdPortrait> queryCrowdPortraitListByCodition(CrowdPortrait crowdPortrait)throws Exception {
		StringBuilder sql = new StringBuilder("select *  from crowd_portrait where 1=1 ");
		List<Object> paramList = new ArrayList<Object>();
		if(!StringUtils.isEmpty(crowdPortrait.getCrowdId())){
			sql.append(" and crowd_id=?");
			paramList.add(crowdPortrait.getCrowdId());
		}
		
		return db.queryForListObject(sql.toString(), paramList.toArray(), CrowdPortrait.class);
	}
	
}
