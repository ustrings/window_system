package com.hidata.ad.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.hidata.ad.web.dao.UrlCrowdDao;
import com.hidata.ad.web.dto.KeyWordCrowdDto;
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
public class UrlCrowdDaoImpl implements UrlCrowdDao {

	@Autowired
	private DBManager db;
	
	@Override
	public List<UrlCrowdDto> queryUrlCrowdListByCondition(UrlCrowdDto urlCrowdDto) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select  * ");
		sql.append(" from url_crowd ");
		sql.append(" where 1 = 1 ");
		
		List<Object> paramList = new ArrayList<Object>();
		
		if(!StringUtils.isEmpty(urlCrowdDto.getCrowdId())){
			sql.append(" and crowd_id=?");
			paramList.add(urlCrowdDto.getCrowdId());
		}
		
		return db.queryForListObject(sql.toString(), paramList.toArray(),UrlCrowdDto.class);
	}
}
