package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.dto.AdCrowdLinkDto;
import com.hidata.ad.web.dto.AdDeviceLinkDto;
import com.hidata.ad.web.dto.AdExtLinkDto;
import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.dto.AdInstanceShowDto;
import com.hidata.ad.web.dto.AdMLinkDto;
import com.hidata.ad.web.dto.AdMaterialLinkDto;
import com.hidata.ad.web.dto.AdMediaCategoryLinkDto;
import com.hidata.ad.web.dto.AdSiteDto;
import com.hidata.ad.web.dto.AdTimeDto;
import com.hidata.ad.web.dto.AdTimeFilterDto;
import com.hidata.ad.web.dto.AdTimeFrequencyDto;
import com.hidata.ad.web.dto.AdUserFrequencyDto;
import com.hidata.ad.web.dto.AdVisitorLinkDto;
import com.hidata.ad.web.dto.MediaCategoryDto;
import com.hidata.ad.web.dto.TerminalBaseInfo;
import com.hidata.ad.web.dto.mongo.AdDeviceLink;
import com.hidata.ad.web.model.AdInstance;
import com.hidata.ad.web.model.AdIpTargeting;
import com.hidata.ad.web.model.AdMLink;
import com.hidata.ad.web.model.AdMaterial;
import com.hidata.ad.web.model.AdPlanPortrait;
import com.hidata.ad.web.model.AdStatBase;
import com.hidata.ad.web.model.AdTerminalLink;
import com.hidata.ad.web.model.ChannelBase;
import com.hidata.ad.web.model.ChannelSiteRel;
import com.hidata.ad.web.model.Crowd;
import com.hidata.ad.web.model.KeyWordAdPlan;
import com.hidata.ad.web.model.MaterialStatBase;
import com.hidata.ad.web.model.RegionTargeting;
import com.hidata.ad.web.model.VisitorCrowd;

/**
 * 广告计划维护操作DAO
 * Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
public interface AdPlanManageDao {
    
    /**
     * 广告信息新增,并返回自增长id
     * @param adInstanceDto
     * @return
     * @author zhoubin
     */
    public int insertAdInstance(AdInstanceDto adInstanceDto);
    
    
    /**
     * 回修改adurl
     * @param adInstanceDto
     */
    public void updateAdUrl(AdInstanceDto adInstanceDto);
    
    /**
     * 广告投放站点新增
     * @param adSiteDto
     * @return
     * @author zhoubin
     */
    public int insertAdSite(AdSiteDto adSiteDto);
    
    /**
     * 广告投放时间新增
     * @param adTimeDto
     * @return
     * @author zhoubin
     */
    public int insertAdTime(AdTimeDto adTimeDto);
    
    public int insertAdTimeFilter(AdTimeFilterDto adTimeFilterDto);
    
    
    /**
     * 广告人群对应关系新增
     * 
     * @param adCrowdLinkDto
     * @return
     * @author zhoubin
     */
    public int insertAdCrowdLink(AdCrowdLinkDto adCrowdLinkDto);
    
    /**
     * 广告重定向人群对应关系新增
     * 
     * @param 
     * @return
     * @author xiaoming 
     * @date 2014年5月29日 （修改）
     */
    public int insertAdVisitorLink(AdVisitorLinkDto adVisitorLinkDto);
    
    /**
     * 广告投放链接 对应关系增加
     * @param adExtLink
     * @return
     */
    public Integer insertAdExtLink(AdExtLinkDto adExtLink);
    /**
     * 广告终端定向人群关系
     * @param adTerminalLink
     * @return
     * @author xiaoming
     * @date 2014-6-16
     */
    public int inserAdTerminalLink(AdTerminalLink adTerminalLink);

    /**
     * 广告物料关系新增
     * 
     * @param adMaterialLinkDto
     * @return
     * @author zhoubin
     */
    public int insertAdMaterialLink(AdMaterialLinkDto adMaterialLinkDto);
    
    /**
     * 广告投放频率
     * @param adTimeFrequencyDto
     * @return
     */
    public int insertAdTimeFrequency(AdTimeFrequencyDto adTimeFrequencyDto);
    
    /**
     * 广告唯一访客投放限制
     * @param adUserFrequencyDto
     * @return
     */
    public int insertAdUserFrequency(AdUserFrequencyDto adUserFrequencyDto);
    
    /**
     * 查询广告
     * 
     * @param userid
     * @return
     */
    
    /**
     * 根据adid查询广告基本信息
     * @param adInstanceDto
     * @return
     */
    public AdInstanceDto getAdInstanceByAdId(AdInstanceDto adInstanceDto);
    
    /**
     * 根据adid查询广告投放时间信息
     * @param adInstanceDto
     * @return
     */
    public List<AdTimeDto> getAdTimesByAdId(AdInstanceDto adInstanceDto);
    
    /**
     * 通过 adid 查找广告投放时间过滤对象
     * @param adInstanceDto
     * @return
     */
    public List<AdTimeFilterDto> getAdTimeFiltersByAdId(AdInstanceDto adInstanceDto);
    
    /**
     * 根据adid查询广告投放站点
     * @param adInstanceDto
     * @return
     */
    public List<AdSiteDto> getAdSitesByAdId(AdInstanceDto adInstanceDto);
    
    /**
     * 根据adid查询广告的人群
     * @param adInstanceDto
     * @return
     */
    public List<Crowd> getAdCrowdsByAdId(AdInstanceDto adInstanceDto);
    
    /**
     * 根据adid查询广告重定向人群
     * @param adInstanceDto
     * @return
     * @author xiaoming
     * @date 2014年5月30日  修改
     */
    public List<VisitorCrowd> getVisitorsByAdId(AdInstanceDto adInstanceDto);
    
    /**
     * 根据adid查询广告终端定向
     * @author xiaoming
     * @param adInstanceDto
     * @return
     */
    //设备
    public List<TerminalBaseInfo> getdevicesByAdId(AdInstanceDto adInstanceDto);
    //系统
    public List<TerminalBaseInfo> getsystemsByAdId(AdInstanceDto adInstanceDto);
    //浏览器
    public List<TerminalBaseInfo> getbrowsersByAdId(AdInstanceDto adInstanceDto);
    
    /**
     * 根据adid查询广告的物料
     * @param adInstanceDto
     * @return
     */
    public List<AdMaterial> getAdMaterialsByAdId(AdInstanceDto adInstanceDto);
    
    /**
     * 根据adid查询广告的投放频率
     * @param adInstanceDto
     * @return
     */
    public AdTimeFrequencyDto getAdTimeFrequencyByAdId(AdInstanceDto adInstanceDto);
    
    /**
     * 根据adid查询广告唯一访客的限制数量
     * @param adInstanceDto
     * @return
     */
    public List<AdUserFrequencyDto> getAdUserFrequencyByAdId(AdInstanceDto adInstanceDto);
    
    
    /**
     * 修改广告计划基本信息
     * @param adDto
     */
    public void updateAdInstance(AdInstanceDto adDto);
    
    /**
     * 修改广告计划投放时间
     * @param adDto
     */
    public void delAdTimes(AdInstanceDto adDto);
    
    public void delAdTimeFilters(AdInstanceDto adDto);
    
    /**
     * 修改广告投放站点
     * @param adDto
     */
    public void delAdSites(AdInstanceDto adDto);
    
    /**
     * 修改广告受众人群
     * @param adDto
     */
    public void delAdCrowdLinks(AdInstanceDto adDto);
    
    /**
     * 修改重定向人群
     * @param adDto
     * @author xiaoming
     * @date 2014年5月30日 修改
     */
    public void delAdVisitorLinks(AdInstanceDto adDto);
    
   /**
    * 删除终端定向
    * @param adDto
    * @author xiaoming
    * @date 2014-6-16
    */
    public void delAdTerminalLinks(AdInstanceDto adDto);
    /**
     * 修改广告物料
     * @param adDto
     */
    public void delAdMaterialLinks(AdInstanceDto adDto);
    
    /**
     * 修改广告投放频率
     * @param adDto
     */
    public void delAdTimeFrequency(AdInstanceDto adDto);
    
    /**
     * 修改广告唯一用户访问限制
     * @param adDto
     */
    public void delAdUserFrequency(AdInstanceDto adDto);
    
    
    public List<AdInstance> findAdInstanceListByUser(int userid);
    public List<AdInstance> findAdInstanceListByChannle(int userid,int channel);
    
    public int updateAdInstanceByIdAndUser(int userid, int adId);
    
    /**
     * 
     * @param mediaCategoryDto
     * @return
     */
    public int insertAdMediaCategoryLink(AdMediaCategoryLinkDto mediaCategoryDto);
    /**
     * 
     * @param adMediaCategoryLinkDto
     * @return
     */
    public int insertMediaCategory(MediaCategoryDto adMediaCategoryLinkDto);


    /**
     * 删除广告 和媒体分类 关联关系
     * @param adDto
     */
    public void delAdMediaCategoryLinks(AdInstanceDto adDto);

    /**
     * 根据上一级媒体code查询媒体分类
     * @param code
     * @return
     */
    public List<MediaCategoryDto> findMediaCategoryDtoListByParendCode(String code);


    /**
     * 根据广告id查询媒体类别
     * @param adId
     * @return
     */
	public List<MediaCategoryDto> findMediaCategoryDtoListByAdId(String adId);
	/**
	 * 根据code查询广告分类
	 * @param code
	 * @return
	 */
	public MediaCategoryDto findMediaCategoryDtoByCode(String code);


	/**
	 * 删除  广告分类
	 * @param adDto
	 */
	public void delAdCategoryLinks(AdInstanceDto adDto);
	
	
	public void saveAdStatBase(AdStatBase adStatBase);

	public void saveMaterialStatBase(MaterialStatBase materialStatBase);


	int delAdIpTargetingByAdId(Integer adId) throws Exception;


	int addAdIpTargeting(AdIpTargeting adIpTargeting) throws Exception;


	public List<RegionTargeting> findRegionListByParendCode(String parentcode);


	void delAdRegionLink(AdInstanceDto adDto);
	
	 /**
     * 添加新的关键词
     * @param keyWordAdPlanDto
     * @return
     * @author ssq
     */
	public int insertAdPlanKeyWord(KeyWordAdPlan keyWordAdPlan);

    /**
     * 根据广告Id查询广告下的关键词
     * @param adId
     * @return
     */
	public KeyWordAdPlan getAdPlanKeyWordByAdId(String adId);

    /**
     * 更新广告计划下的关键词定向信息
     * @param keyWordAdPlan
     * @return
     * @author ssq
     */
	public int updateAdPlanKeyword(KeyWordAdPlan keyWordAdPlan);

    /**
     * 在广告计划下添加人群画像定向
     * @param portrait
     * @author ssq
     */
	public int insertAdPlanPortrait(AdPlanPortrait portrait);

    /**
     * 根据广告Id查找该广告下的人群画像定向
     * @param adId
     * @return
     */
	public AdPlanPortrait getAdPlanPortraitByAdId(String adId);

    /**
     * 更新广告下的人群素描定向
     * @param portrait
     * @author ssq
     */
	public int updateAdPlanPortrait(AdPlanPortrait portrait);

    /**
     *查找域名频道基础表
     * @return
     */
	public List<ChannelBase> getChannelBaseInfo();

    /**
     * 根据频道Id查找属于该频道下的域名
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
     * 根据广告Id删除该广告下的人群画像
     * @param adId
     * @return
     */
	public int deletePortraitByAdId(String adId);

    /**
     * 根据广告Id删除该广告想的人群画像
     * @param adId
     * @return
     */
	public int deleteKeywordByAdId(String adId);

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

    /**
     * 更新广告创意
     * @param adExtLink
     */
	public void updateAdExtLink(AdExtLinkDto adExtLink);
	
	
	
	/**
	 * 添加广告要投放的设备(针对移动端)
	 * @param adDeviceLink
	 * @return
	 */
	public int insertAdDeviceLlink(AdDeviceLinkDto adDeviceLinkDto);
	
	
	/**
	 * 删除告要投放的设备(针对移动端)
	 * @param adDeviceLink
	 * @return
	 */
	public void DelAdDeviceLlink(AdInstanceDto adInstance);
	
	/**
	 * 查询广告要投放的设备
	 * @param adDeviceLink
	 * @return
	 */
	public List<AdDeviceLinkDto> getListAdDeviceLink(AdDeviceLinkDto adDeviceLinkDto);

}
