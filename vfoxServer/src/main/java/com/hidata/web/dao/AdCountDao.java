package com.hidata.web.dao;

import java.util.List;

import com.hidata.web.dto.AdCrowdLinkDto;
import com.hidata.web.dto.AdExtLinkDto;
import com.hidata.web.dto.AdInfoDto;
import com.hidata.web.dto.AdIpTargeting;
import com.hidata.web.dto.AdPlanPortrait;
import com.hidata.web.dto.AdRegionLink;
import com.hidata.web.dto.AdSiteDto;
import com.hidata.web.dto.AdTimeFilterDto;
import com.hidata.web.dto.AllLabelAdPlan;
import com.hidata.web.dto.KeyWordAdPlan;

/**
 * 操作广告统计列表的DAO
 * @author xiaoming
 * @date 2014-12-25
 */
public interface AdCountDao {
	
	/**
	 * 查询广告计划列表
	 * @return
	 */
	public List<AdInfoDto> qryAdInfos();
	
	/**
	 * 更改状态
	 * @param sts
	 * @param adId
	 * @return
	 */
	public Integer updateSts(String sts, String adId);
	
	/**
	 * 关键词
	 * @param adId
	 * @return
	 */
	public List<KeyWordAdPlan> qryKeyWordAdPlans(String adId);
	
	/**
	 * 人群画像
	 * @param adId
	 * @return
	 */
	public List<AdPlanPortrait> qryAdPlanPortraits(String adId);//人群画像定向
	
	/**
	 * 时间定向
	 * @param adId
	 * @return
	 */
	public List<AdTimeFilterDto> qryAdTimeFilterDtos(String adId);//时间定向
	
	/**
	 * 受众人群
	 * @param adId
	 * @return
	 */
	public List<AdCrowdLinkDto> qryAdCrowdLinkDtos(String adId);//受众人群定向
	
	/**
	 * 站点定向
	 * @param adId
	 * @return
	 */
	public List<AdSiteDto> qryAdSiteDtos(String adId);//站点定向
	
	/**
	 * IP定向
	 * @param adId
	 * @return
	 */
	public List<AdIpTargeting> qryAdIpTargetings(String adId);//IP定向
	
	/**
	 * 区域定向
	 * @param adId
	 * @return
	 */
	public List<AdRegionLink> qryAdCountDaos(String adId);//区域定向
	
	/**
	 * 标签定向
	 * @param adId
	 * @return
	 */
	public List<AllLabelAdPlan> qryAllLabelAdPlans(String adId);//标签定向
	
	/**
	 * 根据广告ID 查询关联关系
	 * @param adId
	 * @return
	 */
	public List<AdExtLinkDto> getAdExtLinkByAdId(String adId);

}
