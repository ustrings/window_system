package com.hidata.ad.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.hidata.ad.web.dao.GisCrowdDao;
import com.hidata.ad.web.dto.GisCrowdDto;
import com.hidata.ad.web.dto.UrlCrowdDto;
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
public class GisCrowdDaoImpl implements GisCrowdDao {

	@Autowired
	private DBManager db;
	

	@Override
	public List<GisCrowdDto> queryGisCrowdListByCondition(
			GisCrowdDto gisCrowdDto) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select  * ");
		sql.append(" from gis_crowd ");
		sql.append(" where 1 = 1 ");
		
		List<Object> paramList = new ArrayList<Object>();
		
		if(!StringUtils.isEmpty(gisCrowdDto.getCrowdId())){
			sql.append(" and crowd_id=?");
			paramList.add(gisCrowdDto.getCrowdId());
		}
		
		return db.queryForListObject(sql.toString(), paramList.toArray(),GisCrowdDto.class);
	}
}
