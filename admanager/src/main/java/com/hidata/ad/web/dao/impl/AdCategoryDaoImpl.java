package com.hidata.ad.web.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.hidata.ad.web.dao.AdCategoryDao;
import com.hidata.ad.web.dto.AdCategoryDto;
import com.hidata.ad.web.dto.AdCategoryLinkDto;
import com.hidata.framework.db.DBManager;

/**
 * 广告计划管理数据操作 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Component
public class AdCategoryDaoImpl implements AdCategoryDao {

	@Autowired
	private DBManager db;
	

	@Override
	public List<AdCategoryDto> findAdCategoryDtoListByCondition(AdCategoryDto adCategoryDto){
		List<Object> paramList = new ArrayList<Object>();
		StringBuilder sqlBuilder = new StringBuilder("select *  from ad_category where 1=1");
		if(!StringUtils.isEmpty(adCategoryDto.getId())){
			sqlBuilder.append(" and ad_category_id=?");
			paramList.add(adCategoryDto.getId());
		}
		if(!StringUtils.isEmpty(adCategoryDto.getParentCode())){
			sqlBuilder.append(" and parent_code=?");
			paramList.add(adCategoryDto.getParentCode());
		}
		if(!StringUtils.isEmpty(adCategoryDto.getName())){
			sqlBuilder.append(" and name like ?");
			paramList.add("%"+adCategoryDto.getName()+"%");
		}
		Object[] args = paramList.toArray();
		return db.queryForListObject(sqlBuilder.toString(),args, AdCategoryDto.class);
	}
	
	private static final String FIND_ADCATEGORYDTO_LIST_BY_ADID = "select b.ad_category_id, b.name, b.parent_code  from ad_category_link a ,ad_category b where b.ad_category_id=a.ad_category_id and  a.ad_id = ?";

	@Override
	public List<AdCategoryDto> findAdCategoryDtoListByAdId(String adId){
		Object[] args = new Object[]{adId};
		return db.queryForListObject(FIND_ADCATEGORYDTO_LIST_BY_ADID, args, AdCategoryDto.class);
	}
	
	@Override
	public int insertAdCategoryLink(AdCategoryLinkDto adCategoryLinkDto) {
		return db.insertObject(adCategoryLinkDto);
	}
}
