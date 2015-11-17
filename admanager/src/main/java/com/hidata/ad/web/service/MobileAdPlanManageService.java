package com.hidata.ad.web.service;

import java.util.List;

import com.hidata.ad.web.dto.AdCategoryDto;
import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.dto.AdPlanManageInfoDto;
import com.hidata.ad.web.dto.AdTimeFilterDto;
import com.hidata.ad.web.dto.MediaCategoryDto;
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
import com.hidata.ad.web.dto.mongo.MobileAdPlanManageInfoDto;
import com.hidata.ad.web.model.AdInstance;

/**
 * 广告计划管理服务接口 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
public interface MobileAdPlanManageService {
	
	
	/**
	 * 广告新增
	 * @author chenjinzhao
	 * @param adDto
	 */
	public void addAdPlan(MobileAdPlanManageInfoDto adDto);
	
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
	 */
	public void editAdPlan(MobileAdPlanManageInfoDto adDto,String adId);
	
	/**
	 * 查询一个广告计划的所有信息
	 * @param adId
	 * @return
	 * @author chenjinzhao
	 */
	public MobileAdPlanManageInfoDto getOneAdplanInfo(String adId);
	
	
	public AdInstanceDto  getAdplanById(String adId);
	
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
