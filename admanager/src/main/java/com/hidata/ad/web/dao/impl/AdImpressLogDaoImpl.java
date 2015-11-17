package com.hidata.ad.web.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.hidata.ad.web.cache.SSDBCache;
import com.hidata.ad.web.dao.AdImpressLogDao;
import com.hidata.ad.web.dto.AdCrowdBaseInfoDto;
import com.hidata.ad.web.dto.AdImpressLogDto;
import com.hidata.ad.web.dto.DatePressDto;
import com.hidata.ad.web.dto.ImpressAdRelDto;
import com.hidata.ad.web.model.Crowd;
import com.hidata.ad.web.util.EssSearchInstance;
import com.hidata.framework.db.DBManager;
import com.hidata.framework.util.DateUtil;
import com.zel.es.manager.ws.client.ESSearchServiceManager;
import com.zel.es.pojos.SearchConditionItem;
import com.zel.es.pojos.SearchConditionPojo;
import com.zel.es.pojos.SearchConditionUnit;
import com.zel.es.pojos.StaticValue4SearchCondition;
import com.zel.es.pojos.enums.SearchType;
import com.zel.es.pojos.enums.SortOrderEnum;

/**
 * 广告曝光数据操作 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Component
public class AdImpressLogDaoImpl implements AdImpressLogDao {

	private static Logger logger = LoggerFactory
			.getLogger(AdImpressLogDaoImpl.class);

	@Autowired
	private DBManager db;
	
	@Autowired
	private SSDBCache sSDBCache;
	
	
	
	@Override
	public List<AdImpressLogDto> findAdImpressLogListByObj(AdImpressLogDto adImpressLogDto){
		StringBuilder sql = new StringBuilder();
		sql.append("select b.ad_ilog_id as id ,a.ad_id,a.ad_name,b.impress_url,date_format(b.ts,'%Y-%m-%d %H:%i:%S') ts,b.visitor_ip, (TO_DAYS(NOW()) - TO_DAYS(b.ts)) op,b.ad_acct,b.is_clicked from ad_instance a,ad_impress_log b where a.ad_id = b.ad_id");
		
		List<Object> paramList = new ArrayList<Object>();
	
		if(!StringUtils.isEmpty(adImpressLogDto.getAdId())){
			sql.append(" and a.ad_id=?");
			paramList.add(adImpressLogDto.getAdId());
		}
		if(!StringUtils.isEmpty(adImpressLogDto.getVisitor_ip())){
			sql.append(" and b.visitor_ip=?");
			paramList.add(adImpressLogDto.getVisitor_ip());
		}
		if(!StringUtils.isEmpty(adImpressLogDto.getStartTime())){
			sql.append(" and b.ts>=?");
			paramList.add(adImpressLogDto.getStartTime()+" 00:00:00");
		}
		if(!StringUtils.isEmpty(adImpressLogDto.getEndTime())){
			sql.append(" and b.ts<=?");
			paramList.add(adImpressLogDto.getEndTime()+" 59:59:59");
		}
		if(!StringUtils.isEmpty(adImpressLogDto.getUserId())){
			sql.append(" and a.userid=?");
			paramList.add(adImpressLogDto.getUserId());
		}
		
		if(!StringUtils.isEmpty(adImpressLogDto.getIsClicked())){
			if(adImpressLogDto.getIsClicked().equals("1")) {
				sql.append(" and b.is_clicked is not null");
			}
		}
		
//		sql.append(" and b.ad_acct is not null and b.ad_acct!=''");
		sql.append(" order by b.ts desc limit 500");
		Object[] args = paramList.toArray();
		
		List<AdImpressLogDto> adImpressLogDtoList = db.queryForListObject(sql.toString(), args, AdImpressLogDto.class);
		return adImpressLogDtoList;
	}
	
	
	
	private ImpressAdRelDto findImpressAdRelByAd_ilog_id(int ad_ilog_id){
		ImpressAdRelDto impressAdRelDto = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select ad_ilog_id,user_ad,ad_id,src_ip from impress_ad_rel where  ad_ilog_id=?");
		Object[] args = new Object[]{ad_ilog_id};
		List<ImpressAdRelDto> impressAdRelDtoList = db.queryForListObject(sql.toString(), args, ImpressAdRelDto.class);
		if(impressAdRelDtoList!=null&&impressAdRelDtoList.size()>0){
			impressAdRelDto = impressAdRelDtoList.get(0);
		}
		return impressAdRelDto;
	}
	@Override
	public List<AdCrowdBaseInfoDto> findAdCrowdBaseInfoDtoList(AdCrowdBaseInfoDto adCrowdBaseInfoDto,Integer limit,String orderby){
		
		List<AdCrowdBaseInfoDto> returnList  = new ArrayList<AdCrowdBaseInfoDto>();
		try {
			ESSearchServiceManager searchServiceManager = EssSearchInstance.getInstance();
			SearchConditionPojo searchConditionPojo = new SearchConditionPojo();
			searchConditionPojo.setStart(0);
			searchConditionPojo.setPageSize(500);
			List<SearchConditionItem> searchConditionList = new ArrayList<SearchConditionItem>();
			

			if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getAd_id())){
				List<Crowd> crowdList = getAdCrowdsByAdId(adCrowdBaseInfoDto.getAd_id());
				String ad_acct = adCrowdBaseInfoDto.getAd_acct();
				if(crowdList.size()>0&&!StringUtils.isEmpty(ad_acct)){
					List<SearchConditionUnit> searchConditionUnitList = new ArrayList<SearchConditionUnit>();
					List<SearchConditionItem> searchConditionItemList4Unit = new ArrayList<SearchConditionItem>();
					SearchConditionUnit searchConditionUnit = new SearchConditionUnit();
					searchConditionUnit.setSearchType(SearchType.OR);
					Set<String> aduaSet = null;
					if(ad_acct.length()==12){
						aduaSet = sSDBCache.getAdUaByTid(ad_acct);
					}
					logger.info("ad_acct length:"+ad_acct.length());
					for(int i=0;i<crowdList.size();i++){
						Crowd crowd = crowdList.get(i);
						SearchConditionItem searchConditionItem = new SearchConditionItem();
						searchConditionItem.setSearchType(SearchType.OR);
						if(ad_acct.length()==40){//ad  废弃
							searchConditionItem.setName("adEncoding_crowdid");
							searchConditionItem.setValue(ad_acct+"_"+crowd.getCrowdId());
							searchConditionItemList4Unit.add(searchConditionItem);
						}else if(ad_acct.length()==32){//ad+ua
							searchConditionItem.setName("uidAndUaEncoding_crowdid");
							searchConditionItem.setValue(ad_acct+"_"+crowd.getCrowdId());
							searchConditionItemList4Unit.add(searchConditionItem);
						}else if(ad_acct.length()==12){//tid (ad+ua)
							if(aduaSet==null||aduaSet.size()==0){
								//adua  不查询
								return  new ArrayList<AdCrowdBaseInfoDto>();
							}
							for(String adUa:aduaSet){
								SearchConditionItem searchConditionItem1 = new SearchConditionItem();
								searchConditionItem1.setSearchType(SearchType.OR);
								searchConditionItem1.setName("uidAndUaEncoding_crowdid");
								searchConditionItem1.setValue(adUa+"_"+crowd.getCrowdId());
								searchConditionItemList4Unit.add(searchConditionItem1);
							}
						}
					}
					searchConditionUnit.setSearchConditionItemList(searchConditionItemList4Unit);
					searchConditionUnitList.add(searchConditionUnit);
					searchConditionPojo.setSearchConditionUnitList(searchConditionUnitList);
				}else{
					//没有人群  不能查询
					return  new ArrayList<AdCrowdBaseInfoDto>();
				}
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
			List<SearchConditionItem> sortConditionItemList = new ArrayList<SearchConditionItem>();
			SearchConditionItem searchConditionItem = new SearchConditionItem();
			searchConditionItem.setSearchType(SearchType.SORT);
			searchConditionItem.setName("ts");
			searchConditionItem.setSortOrderEnum(SortOrderEnum.DESC);
			sortConditionItemList.add(searchConditionItem);
			
			searchConditionPojo.setSearchPostFilterList(searchPostFilterList);
			searchConditionPojo.setSearchConditionItemList(searchConditionList);
			searchConditionPojo.setSortConditionItemList(sortConditionItemList);
			searchConditionPojo.setIndexName(StaticValue4SearchCondition.crowd_calc_show_reason);
			String crowd_baseInfo = searchServiceManager.searchAdCrowdBaseInfo4RetJson(searchConditionPojo);
			System.out.println("crowd_baseInfo:"+crowd_baseInfo);
			logger.info(crowd_baseInfo);
			Map<String,Map<String,List<Map<String,JSON>>>> jsonObj = (Map<String, Map<String,List<Map<String,JSON>>>>) JSON.parse(crowd_baseInfo);
			List<Map<String,JSON>> hitsList = jsonObj.get("hits").get("hits");
			for(int i=0;i<hitsList.size();i++){
				Map<String,JSON> map  = hitsList.get(i);
				AdCrowdBaseInfoDto obj =  JSON.toJavaObject(map.get("_source"), AdCrowdBaseInfoDto.class);
				String crowdId = "";
				if(!StringUtils.isEmpty(obj.getUidAndUaEnconding_crowdid())){
					crowdId = obj.getUidAndUaEnconding_crowdid().split("_")[1];
				}else{
					crowdId = obj.getAdEncoding_crowdid().split("_")[1];
				}
				Crowd crowd = getCrowdById(crowdId);
				obj.setCrowdName(crowd.getCrowdName());
				Date date = new Date(Long.valueOf(obj.getTs()));
				obj.setTs(DateUtil.format(date, DateUtil.C_TIME_PATTON_DEFAULT));
				returnList.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@Deprecated
	@Override
	public List<AdCrowdBaseInfoDto> findAdCrowdBaseInfoDtoList_old(AdCrowdBaseInfoDto adCrowdBaseInfoDto,Integer limit,String orderby){
		
//		String special_ad_acct="71f8e7976e4cbc4561c9d62fb283e7f788202acb";//特殊ad_acct  //需要放到配置文件中
		List<AdCrowdBaseInfoDto> returnList  = new ArrayList<AdCrowdBaseInfoDto>();
		StringBuilder sql = new StringBuilder();
		
		sql.append("select distinct  match_content,ts,host from  ad_crowd_base_info where ad_acct=? ");
		List<Object> paramList = new ArrayList<Object>();
		if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getAd_acct())){
			paramList.add(adCrowdBaseInfoDto.getAd_acct());
		}
		if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getAd_id())){
			List<Crowd> crowdList = getAdCrowdsByAdId(adCrowdBaseInfoDto.getAd_id());
			StringBuilder crowdsId = new StringBuilder();
			if(crowdList.size()>0){
				crowdsId.append("(");
				for(int i=0;i<crowdList.size();i++){
					if(i==0){
						crowdsId.append(crowdList.get(i).getCrowdId());
					}else{
						crowdsId.append(",").append(crowdList.get(i).getCrowdId());
					}
				}
				crowdsId.append(")");
				sql.append(" and crowd_id in ").append(crowdsId);
			}else{
				//没有人群  不能查询
				return  new ArrayList<AdCrowdBaseInfoDto>();
			}
		}
		/*
		ImpressAdRelDto impressAdRelDto = findImpressAdRelByAd_ilog_id(adCrowdBaseInfoDto.getAdilogId());
		if(impressAdRelDto!=null){
			if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getAd_acct())){
				paramList.add(adCrowdBaseInfoDto.getAd_acct());
				impressAdRelDto.setUser_ad(adCrowdBaseInfoDto.getAd_acct());
			}else{
				paramList.add(impressAdRelDto.getUser_ad());
			}
			if(special_ad_acct.equals(impressAdRelDto.getUser_ad())){
				sql.append(" and src_ip=?");
				paramList.add(impressAdRelDto.getSrc_ip());
			}
			if(!StringUtils.isEmpty(impressAdRelDto.getAd_id())){
				List<Crowd> crowdList = getAdCrowdsByAdId(impressAdRelDto.getAd_id());
				StringBuilder crowdsId = new StringBuilder();
				if(crowdList.size()>0){
					crowdsId.append("(");
					for(int i=0;i<crowdList.size();i++){
						if(i==0){
							crowdsId.append(crowdList.get(i).getCrowdId());
						}else{
							crowdsId.append(",").append(crowdList.get(i).getCrowdId());
						}
					}
					crowdsId.append(")");
					sql.append(" and crowd_id in ").append(crowdsId);
				}else{
					//没有人群  不能查询
					return  new ArrayList<AdCrowdBaseInfoDto>();
				}
			}
		}else{
			//没有曝光  信息
			return returnList;
		}*/
		if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getHistoryStartTime())){
			sql.append(" and dt>=?");
			paramList.add(adCrowdBaseInfoDto.getHistoryStartTime());
		}
		
		if(!StringUtils.isEmpty(adCrowdBaseInfoDto.getHistoryEndTime())){
			sql.append(" and dt<=?");
			paramList.add(adCrowdBaseInfoDto.getHistoryEndTime());
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
		
		logger.info("findAdCrowdBaseInfoDtoList querytime:"+(end-start));
		
		for(int i=0;i<list.size();i++){
			AdCrowdBaseInfoDto obj = list.get(i);
			Date date = new Date(Long.valueOf(obj.getTs()));
			obj.setTs(DateUtil.format(date, DateUtil.C_TIME_PATTON_DEFAULT));
			returnList.add(obj);
		}
		return returnList;
	}
	private List<Crowd> getAdCrowdsByAdId(String adId) {
		
		String sql = "select b.crowd_id,b.crowd_name,b.create_date as create_time,b.crowd_sts as sts,b.user_id from ad_crowd_link a,crowd_find_info b where a.crowd_id = b.crowd_id and a.ad_id = ?";
		Object[] args = new Object[] {adId};

		List<Crowd> adCIList = db.queryForListObject(sql, args, Crowd.class);
		return adCIList;
	}
	private Crowd getCrowdById(String crowdId)throws Exception{
		String sql = "select * from crowd_find_info where crowd_id=?";
		Object[] args = new Object[] {crowdId};
		return db.queryForBean(sql, args, Crowd.class);
	}
	@Override
	public long getTotalExposureByAdId(String adId) {
		String sql = "select count(*) from ad_impress_log where ad_id=" +adId;
		return db.queryForLong(sql);
	}
	@Override
	public List<DatePressDto> adImpressLogDao(String adId) {
		String sql = "SELECT count(*) as press, date_format(ts,'%Y-%m-%d') as date FROM `ad_impress_log` where ad_id=" +  adId + "  GROUP BY date_format(ts,'%Y-%m-%d') ORDER BY ts;";
		return db.queryForListObject(sql, DatePressDto.class);
	}



	@Override
	public List<AdImpressLogDto> findAdImpressLogListByAdAcct(
			AdImpressLogDto adImpressLogDto) {
		StringBuilder sql = new StringBuilder();
		sql.append("select ad_ilog_id,ad_id,impress_url,ts,ad_acct from ad_impress_log  where 1=1");
		
		List<Object> paramList = new ArrayList<Object>();
	
		if(!StringUtils.isEmpty(adImpressLogDto.getAdId())){
			sql.append(" and ad_id=?");
			paramList.add(adImpressLogDto.getAdId());
		}
		if(!StringUtils.isEmpty(adImpressLogDto.getAd_acct())){
			sql.append(" and ad_acct=?");
			paramList.add(adImpressLogDto.getAd_acct());
		}
		if(!StringUtils.isEmpty(adImpressLogDto.getStartTime())){
			sql.append(" and ts>=?");
			paramList.add(adImpressLogDto.getStartTime()+" 00:00:00");
		}
		if(!StringUtils.isEmpty(adImpressLogDto.getEndTime())){
			sql.append(" and ts<=?");
			paramList.add(adImpressLogDto.getEndTime()+" 59:59:59");
		}
		
		
			
//		sql.append(" and b.ad_acct is not null and b.ad_acct!=''");
		sql.append(" order by ts desc limit 500");
		Object[] args = paramList.toArray();
		
		List<AdImpressLogDto> adImpressLogDtoList = db.queryForListObject(sql.toString(), args, AdImpressLogDto.class);
		return adImpressLogDtoList;
	}
}
