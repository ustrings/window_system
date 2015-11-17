package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.dto.AdCrowdLinkDto;
import com.hidata.ad.web.dto.AdInstanceDto;
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
import com.hidata.ad.web.dto.mongo.AdApp;
import com.hidata.ad.web.dto.mongo.AdAppLink;
import com.hidata.ad.web.dto.mongo.AdClick;
import com.hidata.ad.web.dto.mongo.AdClickLink;
import com.hidata.ad.web.dto.mongo.AdDevice;
import com.hidata.ad.web.dto.mongo.AdDeviceLink;
import com.hidata.ad.web.dto.mongo.AdIndustry;
import com.hidata.ad.web.dto.mongo.AdIndustryLink;
import com.hidata.ad.web.dto.mongo.AdType;
import com.hidata.ad.web.dto.mongo.AdTypeLink;
import com.hidata.ad.web.model.AdInstance;
import com.hidata.ad.web.model.AdMaterial;
import com.hidata.ad.web.model.AdTerminalLink;
import com.hidata.ad.web.model.Crowd;
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
public interface MobileAdPlanManageDao {
    
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
	
	
	public List<AdApp> getAdAppList();
	public List<AdClick> getAdClickList();
	public List<AdDevice> getAdDeviceList();
	public List<AdIndustry> getAdIndustryList();
	public List<AdType> getAdTypeList();
	
	public List<AdApp> getAdAppListByAdId(String adId);
	public AdClick getAdClickByAdId(String adId);
	public List<AdDevice> getAdDeviceListByAdId(String adId);
	public List<AdIndustry> getAdIndustryListByAdId(String adId);
	public AdType getAdTypeByAdId(String adId);
	
	public void insertAdAppList(List<AdAppLink> adAppLinks);
	public void insertAdClick(AdClickLink adClickLink);
	public void insertAdDevice(List<AdDeviceLink> adDeviceLinks);
	public void insertAdIndustryList(List<AdIndustryLink> adIndustriLinks);
	public void insertAdType(AdTypeLink adTypeLink);

	public void deleteAdAppList(String adId);
	public void deleteAdClick(String adId);
	public void deleteAdDevice(String adId);
	public void deleteAdIndustryList(String adId);
	public void deleteAdType(String adId);
}
