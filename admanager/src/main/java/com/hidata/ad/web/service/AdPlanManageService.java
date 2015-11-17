package com.hidata.ad.web.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hidata.ad.web.model.AdPlanPortrait;
import com.hidata.ad.web.dto.AdCategoryDto;
import com.hidata.ad.web.dto.AdExtLinkDto;
import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.dto.AdInstanceShowDto;
import com.hidata.ad.web.dto.AdMLinkDto;
import com.hidata.ad.web.dto.AdPlanManageInfoDto;
import com.hidata.ad.web.dto.AdSiteDto;
import com.hidata.ad.web.dto.AdTimeFilterDto;
import com.hidata.ad.web.dto.MediaCategoryDto;
import com.hidata.ad.web.model.AdInstance;
import com.hidata.ad.web.model.AdMLink;
import com.hidata.ad.web.model.ChannelBase;
import com.hidata.ad.web.model.ChannelSiteRel;
import com.hidata.ad.web.model.KeyWordAdPlan;
import com.hidata.ad.web.model.RegionTargeting;

/**
 * 广告计划管理服务接口 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
public interface AdPlanManageService {
	
	
	/**
	 * 广告新增
	 * @author chenjinzhao
	 * @param adDto
	 * @throws Exception 
	 */
	public void addAdPlan(HttpServletRequest request,AdPlanManageInfoDto adDto) throws Exception;
	
	/**
     * 查询广告
     * 
     * @param userid
     * @return
     */
    public List<AdInstance> findAdInstanceListByUser(int userid);
    public List<AdInstance> findAdInstanceListByChannle(int userid, int channel);
    
    /**
     * 更新状态
     * 
     * @param userid
     * @param adId
     * @return
     */
    public int updateAdInstanceByIdAndUser(int userid, int adId);
    
    
    /**
	 * 广告修改
	 * @author chenjinzhao
	 * @param adDto
     * @throws Exception 
	 */
	public void editAdPlan(HttpServletRequest request,AdPlanManageInfoDto adDto,String adId) throws Exception;
	
	/**
	 * 查询一个广告计划的所有信息
	 * @param adId
	 * @return
	 * @author chenjinzhao
	 */
	public AdPlanManageInfoDto  getOneAdplanInfo(String adId);
	
	
	public AdInstanceDto  getAdplanById(String adId);
	
	/**
	 * 查询媒体分类根据上一级分类code
	 * @param code
	 * @return
	 */
	public List<MediaCategoryDto> findMediaCategoryDtoListByParendCode(String parentcode);
	/**
	 * 根据code查询媒体分类
	 * @param code
	 * @return
	 */
	public MediaCategoryDto findMediaCategoryDtoByCode(String code);
	
	/**
	 * 根据广告id查询媒体分类
	 * @param adId
	 * @return
	 */
	public List<MediaCategoryDto> findMediaCategoryDtoListByAdId(String adId);

	/**
	 * 查询广告分类  根据 上级code
	 * @param parentCode
	 * @return
	 */
	public List<AdCategoryDto> findAdCategoryDtoListByParentCode(String parentCode);

	/**
	 * 根据广告id  查询广告下的 分类
	 * @param adId
	 * @return
	 */
	public List<AdCategoryDto> findAdCategoryDtoListByAdId(String adId);

	public List<RegionTargeting> findRegionListByParendCode(String code);

	public List<RegionTargeting> findRegionTargetingListByParendCode(String code);

	public List<RegionTargeting> findAdRegtionListByAdId(String adId);

	public List<RegionTargeting> findRegionTargetingListByParentCode(String parentCode);
    /**
     * 根据广告Id查询广告下的关键词
     * @return
     * @author ssq
     */
	public KeyWordAdPlan getAdPlanKeyWordByAdId(String adId);
	/**
	 * 更新广告计划下的关键词
	 * @param keyWordAdPlan
	 * @return
	 * @author ssq
	 */
	public int updateAdPlanKeyword(KeyWordAdPlan keyWordAdPlan);
    /**
     * 根据广告Id查找该广告下的人群画像定向
     * @param adId
     * @return
     * @author ssq
     */
	public AdPlanPortrait getAdPlanPortraitByAdId(String adId);
	/**
	 * 获取域名频道基础表
	 * @return
	 */
	public List<ChannelBase> getChannelBaseInfo();
    
	/**
	 * 根据频道Id查找属于该频道的域名
	 * @param channels
	 * @return
	 */
	public List<ChannelSiteRel> findChannelSiteRelByChannelIds(String channels);
    
	/**
	 * 根据广告Id查找该广告下的域名
	 * @param adId
	 * @return
	 */
	public List<AdSiteDto> findAdSiteDtoByAdId(String adId);
    
	/**
	 * 根据频道Id查找频道
	 * @param channelId
	 * @return
	 */
	public ChannelBase findChannelBaseByChannelId(String channelId);
	
	/**
     * 查看广告下是否设定了关键词
     * @return
     */
	public KeyWordAdPlan findAdPlanKeywordByAdId(String adId);
	
	/**
     * 查看广告下是否设定了人群画像信息
     * @param adId
     * @return
     */
	public AdPlanPortrait findAdPortraitByAdId(String adId);
	/**
	 * 根据广告Id删除广告下的关键词
	 * @param adId
	 */
	public int deleteKeywordByAdId(String adId);
	/**
	 * 根据广告Id删除广告下的人群画像
	 * @param adId
	 */
	public int deletePortraitByAdId(String adId);
    /**
     * 根据Id查找对应的信息
     * @param channelIds
     * @return
     */
	public List<ChannelSiteRel> findChannelSiteRelByIds(String channelIds);
	
	 /**
     * 向ad_site表中插入数据  域名广告关系表
     * @param adSiteDto
     */
	public int insertAdPlanSite(AdSiteDto adSiteDto);
	
	/**
     * 通过广告Id查找广告物料
     * @param adId
     * @return
     */
	public AdExtLinkDto findAdExtLinkByAdId(String adId);
	
	 /**
     * 通过广告Id获取广告信息
     * @param adId
     * @return
     */
	public AdInstanceDto getAdInstanceDtoByAdId(String adId);

	/**
     * 根据广告Id查找该广告下的物料信息
     * @param adId
     * @return
     */
	public List<AdMLink> getAdMaterialsByAdId(String adId);
	
	/**
	 * 广告计划展示字段
	 * @param userId
	 * @return
	 */
	public List<AdInstanceShowDto> getListAdShow(int userId);
}
