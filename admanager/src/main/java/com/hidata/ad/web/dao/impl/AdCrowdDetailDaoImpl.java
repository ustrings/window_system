package com.hidata.ad.web.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.hidata.ad.web.dao.AdCrowdDetailDao;
import com.hidata.ad.web.dto.AdCrowdBaseInfoDto;
import com.hidata.ad.web.dto.AdCrowdDetailDto;
import com.hidata.ad.web.model.Crowd;
import com.hidata.ad.web.util.EssSearchInstance;
import com.hidata.framework.db.DBManager;
import com.hidata.framework.util.DateUtil;
import com.zel.es.manager.ws.client.ESSearchServiceManager;
import com.zel.es.pojos.SearchConditionItem;
import com.zel.es.pojos.SearchConditionPojo;
import com.zel.es.pojos.StaticValue4SearchCondition;
import com.zel.es.pojos.enums.SearchType;
import com.zel.es.pojos.enums.SortOrderEnum;

/**
 * 广告计划管理数据操作 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Component
public class AdCrowdDetailDaoImpl implements AdCrowdDetailDao {

	private static Logger logger = LoggerFactory
			.getLogger(AdCrowdDetailDaoImpl.class);
	
	@Autowired
	private DBManager db;
	
	@Override
	public List<AdCrowdDetailDto> queryAdCrowdDetailListByCondition(AdCrowdDetailDto adCrowdDetailDto,List<Crowd> crowdList,Integer limit,String orderby){
		Map<String,String> crowdMap = new HashMap<String,String>();
		for(int i=0;i<crowdList.size();i++){
			Crowd crowd = crowdList.get(i);
			crowdMap.put(crowd.getCrowdId(), crowd.getCrowdName());
		}
		StringBuilder sql = new StringBuilder();

		sql.append("select  * ");
		sql.append(" from ad_crowd_statistics ");
		sql.append(" where 1 = 1 ");
		List<Object> paramList = new ArrayList<Object>();
	
		if(!StringUtils.isEmpty(adCrowdDetailDto.getAdId())){
			sql.append(" and ad_id =?");
			paramList.add(adCrowdDetailDto.getAdId());
		}
		if(!StringUtils.isEmpty(adCrowdDetailDto.getCrowdId())){
			sql.append(" and crowd_id =?");
			paramList.add(adCrowdDetailDto.getCrowdId());
		}
		
		if(!StringUtils.isEmpty(adCrowdDetailDto.getStartTime())){
			sql.append(" and dt>=?");
			paramList.add(adCrowdDetailDto.getStartTime());
		}
		if(!StringUtils.isEmpty(adCrowdDetailDto.getEndTime())){
			sql.append(" and dt<=?");
			paramList.add(adCrowdDetailDto.getEndTime());
		}
	
		if(!StringUtils.isEmpty(orderby)){
			sql.append(orderby);
		}
		
		if(limit!=null){
			sql.append(" limit ").append(limit);
		}
		Object[] args = paramList.toArray();
		
		List<AdCrowdDetailDto> list = db.queryForListObject(sql.toString(), args, AdCrowdDetailDto.class);
		List<AdCrowdDetailDto> returnList = new ArrayList<AdCrowdDetailDto>();
		for(int i=0;i<list.size();i++){
			AdCrowdDetailDto obj = list.get(i);
			obj.setCrowdName(crowdMap.get(obj.getCrowdId()));
			returnList.add(obj);
		}
		return list;
	}
	@Override
	public List<AdCrowdBaseInfoDto> queryAdCrowdBaseInfoDtoListByCrowId(AdCrowdBaseInfoDto adCrowdBaseInfoDto,Integer limit,String orderby){
		
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct ad_acct,match_content,ts,host,src_ip from  ad_crowd_base_info where 1=1");
		List<Object> paramList = new ArrayList<Object>();
		
		boolean defaultFlag = true;
		if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getCrowd_id())){
			sql.append(" and crowd_id=?");
			paramList.add(adCrowdBaseInfoDto.getCrowd_id());
		}
		if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getHistoryStartTime())){
			sql.append(" and dt>=?");
			paramList.add(adCrowdBaseInfoDto.getHistoryStartTime());
			defaultFlag = false;
		}
		if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getHistoryEndTime())){
			sql.append(" and dt<=?");
			paramList.add(adCrowdBaseInfoDto.getHistoryEndTime());
			defaultFlag = false;
		}
		if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getSrc_ip())){
     		sql.append(" and src_ip=?");
     		paramList.add(adCrowdBaseInfoDto.getSrc_ip());
     	}
		if(defaultFlag){
			if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getDefaultTime())){
				sql.append(" and dt=?");
				paramList.add(adCrowdBaseInfoDto.getDefaultTime());
			}
		}
		if(!StringUtils.isEmpty(orderby)){
			sql.append(orderby);
		}
		if(limit!=null){
			sql.append(" limit ").append(limit);
		}
		
		Object[] args = paramList.toArray();
		long start = System.currentTimeMillis();
	
		List<AdCrowdBaseInfoDto> list = db.queryForListObject(sql.toString(), args, AdCrowdBaseInfoDto.class);
		long end = System.currentTimeMillis();
		List<AdCrowdBaseInfoDto> returnList = new ArrayList<AdCrowdBaseInfoDto>();
		for(int i=0;i<list.size();i++){
			AdCrowdBaseInfoDto obj = list.get(i);
			if(obj.getTs()!=null){
				Date date = new Date(Long.valueOf(obj.getTs()));
				obj.setTs(DateUtil.format(date, DateUtil.C_TIME_PATTON_DEFAULT));
			}
			
			returnList.add(obj);
		}
		System.out.println("findAdCrowdBaseInfoDtoList querytime:"+(end-start));
		
		return returnList;
	}
	
	
	@Deprecated
	@Override
	public List<AdCrowdBaseInfoDto> queryAdCrowdBaseInfoDtoListByCondition_old(AdCrowdBaseInfoDto adCrowdBaseInfoDto,Integer limit,String orderby){
		
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct ad_acct,match_content,ts,host,src_ip from  ad_crowd_base_info where 1=1");
		List<Object> paramList = new ArrayList<Object>();
		
		if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getCrowd_id())){
			sql.append(" and crowd_id=?");
			paramList.add(adCrowdBaseInfoDto.getCrowd_id());
		}
		if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getHistoryStartTime())){
			sql.append(" and dt>=?");
			paramList.add(adCrowdBaseInfoDto.getHistoryStartTime());
		}
		if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getHistoryEndTime())){
			sql.append(" and dt<=?");
			paramList.add(adCrowdBaseInfoDto.getHistoryEndTime());
		}
		if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getSrc_ip())){
     		sql.append(" and src_ip=?");
     		paramList.add(adCrowdBaseInfoDto.getSrc_ip());
     	}

		if(!StringUtils.isEmpty(orderby)){
			sql.append(orderby);
		}
		if(limit!=null){
			sql.append(" limit ").append(limit);
		}
		
		Object[] args = paramList.toArray();
		long start = System.currentTimeMillis();
	
		List<AdCrowdBaseInfoDto> list = db.queryForListObject(sql.toString(), args, AdCrowdBaseInfoDto.class);
		long end = System.currentTimeMillis();
		List<AdCrowdBaseInfoDto> returnList = new ArrayList<AdCrowdBaseInfoDto>();
		for(int i=0;i<list.size();i++){
			AdCrowdBaseInfoDto obj = list.get(i);
			if(obj.getTs()!=null){
				Date date = new Date(Long.valueOf(obj.getTs()));
				obj.setTs(DateUtil.format(date, DateUtil.C_TIME_PATTON_DEFAULT));
			}
			
			returnList.add(obj);
		}
		System.out.println("findAdCrowdBaseInfoDtoList querytime:"+(end-start));
		
		return returnList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<AdCrowdBaseInfoDto> queryAdCrowdBaseInfoDtoListByCondition(AdCrowdBaseInfoDto adCrowdBaseInfoDto,Integer limit,String orderby){
		
		List<AdCrowdBaseInfoDto> returnList = new ArrayList<AdCrowdBaseInfoDto>();
		ESSearchServiceManager searchServiceManager = EssSearchInstance.getInstance();
		SearchConditionPojo searchConditionPojo = new SearchConditionPojo();
		searchConditionPojo.setStart(0);
		searchConditionPojo.setPageSize(500);
		List<SearchConditionItem> searchConditionList = new ArrayList<SearchConditionItem>();
	
		if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getCrowd_id())){
				SearchConditionItem searchConditionItem = new SearchConditionItem();
				searchConditionItem.setName("crowd_id");
				searchConditionItem.setValue(adCrowdBaseInfoDto.getCrowd_id());
				searchConditionList.add(searchConditionItem);
		}
		
		List<SearchConditionItem> searchPostFilterList = new ArrayList<SearchConditionItem>();
		if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getHistoryStartTime())||!StringUtils.isEmpty(adCrowdBaseInfoDto.getHistoryEndTime())){
			SearchConditionItem searchConditionItem = new SearchConditionItem();
			searchConditionItem.setSearchType(SearchType.RANGE);
			searchConditionItem.setName("dt");
			if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getHistoryStartTime())){
				searchConditionItem.setFromObj(adCrowdBaseInfoDto.getHistoryStartTime());
				searchConditionItem.setIncludeLower(true);
			}
			if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getHistoryEndTime())){
				searchConditionItem.setToObj(adCrowdBaseInfoDto.getHistoryEndTime());
				searchConditionItem.setIncludeHigher(true);
			}
			searchPostFilterList.add(searchConditionItem);
		}
		if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getSrc_ip())){
			SearchConditionItem searchConditionItem = new SearchConditionItem();
			searchConditionItem.setName("src_ip");
			searchConditionItem.setValue(adCrowdBaseInfoDto.getSrc_ip());
			searchConditionList.add(searchConditionItem);
		}
		searchConditionPojo.setSearchConditionItemList(searchConditionList);
		searchConditionPojo.setIndexName(StaticValue4SearchCondition.ad_crowd_base_indexName);
		
		List<SearchConditionItem> sortConditionItemList = new ArrayList<SearchConditionItem>();
		SearchConditionItem searchConditionItem = new SearchConditionItem();
		searchConditionItem.setSearchType(SearchType.SORT);
		searchConditionItem.setName("ts");
		searchConditionItem.setSortOrderEnum(SortOrderEnum.DESC);
		sortConditionItemList.add(searchConditionItem);
		searchConditionPojo.setSortConditionItemList(sortConditionItemList);;
		//searchConditionPojo.setSearchPostFilterList(searchPostFilterList);
		String crowd_baseInfo = searchServiceManager.searchAdCrowdBaseInfo4RetJson(searchConditionPojo);
		
		logger.info("crowd_baseInfo:"+crowd_baseInfo);
		Map<String,Map<String,List<Map<String,JSON>>>> jsonObj = (Map<String, Map<String,List<Map<String,JSON>>>>) JSON.parse(crowd_baseInfo);
		List<Map<String,JSON>> hitsList = jsonObj.get("hits").get("hits");
		for(int i=0;i<hitsList.size();i++){
			Map<String,JSON> map  = hitsList.get(i);
			AdCrowdBaseInfoDto obj =  JSON.toJavaObject(map.get("_source"), AdCrowdBaseInfoDto.class);
			Date date = new Date(Long.valueOf(obj.getTs()));
			obj.setTs(DateUtil.format(date, DateUtil.C_TIME_PATTON_DEFAULT));
			returnList.add(obj);
		}
		
		return returnList;
	}
}
