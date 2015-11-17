package com.hidata.ad.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.hidata.ad.web.dao.IntrestLabelCrowdDao;
import com.hidata.ad.web.dto.UrlCrowdDto;
import com.hidata.ad.web.model.IntrestLabelCrowd;
import com.hidata.framework.db.DBManager;

/**
 * 人群关键词 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author sunly
 */
@Component
public class IntrestLabelCrowdDaoImpl implements IntrestLabelCrowdDao {

	@Autowired
	private DBManager db;
	
	@Override
	public List<IntrestLabelCrowd> queryIntrestLabelCrowdListByCondition(
			IntrestLabelCrowd intrestLabelCrowd) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select  * ");
		sql.append(" from intrest_label_crowd ");
		sql.append(" where 1 = 1 ");
		
		List<Object> paramList = new ArrayList<Object>();
		
		if(!StringUtils.isEmpty(intrestLabelCrowd.getCrowdId())){
			sql.append(" and crowd_id=?");
			paramList.add(intrestLabelCrowd.getCrowdId());
		}
		
		return db.queryForListObject(sql.toString(), paramList.toArray(),IntrestLabelCrowd.class);
	}
}
