package com.hidata.ad.web.dto;

import java.util.List;
import java.util.Map;

import com.hidata.ad.web.model.AdPlanPortrait;
import com.hidata.ad.web.model.Crowd;
import com.hidata.ad.web.model.KeyWordAdPlan;
import com.hidata.ad.web.model.VisitorCrowd;

/**
 * 广告计划维护信息 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月11日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
public class AdPlanManageInfoDto {
	/**
	 * 当投放创意为 JS代码时;要过渡一下，临时参数
	 */
	private String jsContent;
	private String jsSize;
	private String jsType;
	
	
	
	public String getJsType() {
		return jsType;
	}

	public void setJsType(String jsType) {
		this.jsType = jsType;
	}

	public String getJsContent() {
		return jsContent;
	}

	public void setJsContent(String jsContent) {
		this.jsContent = jsContent;
	}

	public String getJsSize() {
		return jsSize;
	}

	public void setJsSize(String jsSize) {
		this.jsSize = jsSize;
	}
//--------------------------------------------------	
	// 广告核心信息
	private AdInstanceDto adInstanceDto = new AdInstanceDto();

	// 广告站点信息
	private AdSiteDto adSiteDto = new AdSiteDto();

	// 广告时间信息
	private String putHours;
	
	//设备类型
	private String adDeviceIds;
	
	
	private List<AdDeviceLinkDto> adDeviceLinkList;
	
	public List<AdDeviceLinkDto> getAdDeviceLinkList() {
		return adDeviceLinkList;
	}

	public void setAdDeviceLinkList(List<AdDeviceLinkDto> adDeviceLinkList) {
		this.adDeviceLinkList = adDeviceLinkList;
	}

	public String getAdDeviceIds() {
		return adDeviceIds;
	}

	public void setAdDeviceIds(String adDeviceIds) {
		this.adDeviceIds = adDeviceIds;
	}
	// 投放链接实体
	private AdExtLinkDto adExtLink;

	private String crowdSeledIds;
	
	 //关键词
	private KeyWordAdPlan keyWordAdPlan;
	
	private String kwFileName;
	
	public String getKwFileName() {
		return kwFileName;
	}

	public void setKwFileName(String kwFileName) {
		this.kwFileName = kwFileName;
	}
	//人群画像定向
	private AdPlanPortrait adPlanPortrait;
	
	/**
	 * 修改
	 * 周晓明
	 */
	//浏览器
	private String browserSeledIds;
	//设备
	private String  deviceSeledIds;
	//系统
	private String systemSeledIds;
	//重定向人群ids
	private String visitorSeledIds;

	// 广告物料关联信息
	private String adMaterialIds;
	//广告 媒体类别信息
	private String mediaCategoryCodes;
	
	//广告类别
	private String adCategoryCodes;
	
	//地区regionCodes
	private String regionCodes;
	
	//标签数据
	private String allLabelCodes;
    
	//选择的频道域名
	private String channelUrlIds; 
	
	public String getChannelUrlIds() {
		return channelUrlIds;
	}

	public void setChannelUrlIds(String channelUrlIds) {
		this.channelUrlIds = channelUrlIds;
	}

	public KeyWordAdPlan getKeyWordAdPlan() {
		return keyWordAdPlan;
	}

	public void setKeyWordAdPlan(KeyWordAdPlan keyWordAdPlan) {
		this.keyWordAdPlan = keyWordAdPlan;
	}

	public String getRegionCodes() {
		return regionCodes;
	}

	public void setRegionCodes(String regionCodes) {
		this.regionCodes = regionCodes;
	}

	public String getVisitorSeledIds() {
		return visitorSeledIds;
	}

	public void setVisitorSeledIds(String visitorSeledIds) {
		this.visitorSeledIds = visitorSeledIds;
	}

	public String getAdCategoryCodes() {
		return adCategoryCodes;
	}

	public void setAdCategoryCodes(String adCategoryCodes) {
		this.adCategoryCodes = adCategoryCodes;
	}

	public String getMediaCategoryCodes() {
		return mediaCategoryCodes;
	}

	public void setMediaCategoryCodes(String mediaCategoryCodes) {
		this.mediaCategoryCodes = mediaCategoryCodes;
	}

	// 广告时间投放频率
	private AdTimeFrequencyDto adTimeFrequencyDto = new AdTimeFrequencyDto();

	// 唯一访客投放频率
	private AdUserFrequencyDto adUserFrequencyDto = new AdUserFrequencyDto();

	private List<Crowd> adCList;
	
	//重定向人群
	private List<VisitorCrowd> visitorList;
	
	private Map<String, List<TerminalBaseInfo>> map;
	

	public List<VisitorCrowd> getVisitorList() {
		return visitorList;
	}

	public void setVisitorList(List<VisitorCrowd> visitorList) {
		this.visitorList = visitorList;
	}

	// 投放站点自定义列表
	private String whiteAdSites;
	private String blackAdSites;
	
	private String whiteIps;
	private String blackIps;
	
	//背景页面自定义URL
	private String backUrlSite;

	public String getWhiteIps() {
		return whiteIps;
	}

	public void setWhiteIps(String whiteIps) {
		this.whiteIps = whiteIps;
	}

	public String getBlackIps() {
		return blackIps;
	}

	public void setBlackIps(String blackIps) {
		this.blackIps = blackIps;
	}

	public AdInstanceDto getAdInstanceDto() {
		return adInstanceDto;
	}

	public void setAdInstanceDto(AdInstanceDto adInstanceDto) {
		this.adInstanceDto = adInstanceDto;
	}

	public AdSiteDto getAdSiteDto() {
		return adSiteDto;
	}

	public void setAdSiteDto(AdSiteDto adSiteDto) {
		this.adSiteDto = adSiteDto;
	}

	public String getPutHours() {
		return putHours;
	}

	public void setPutHours(String putHours) {
		this.putHours = putHours;
	}

	public String getCrowdSeledIds() {
		return crowdSeledIds;
	}

	public void setCrowdSeledIds(String crowdSeledIds) {
		this.crowdSeledIds = crowdSeledIds;
	}

	public String getAdMaterialIds() {
		return adMaterialIds;
	}

	public void setAdMaterialIds(String adMaterialIds) {
		this.adMaterialIds = adMaterialIds;
	}

	public AdTimeFrequencyDto getAdTimeFrequencyDto() {
		return adTimeFrequencyDto;
	}

	public void setAdTimeFrequencyDto(AdTimeFrequencyDto adTimeFrequencyDto) {
		this.adTimeFrequencyDto = adTimeFrequencyDto;
	}

	public AdUserFrequencyDto getAdUserFrequencyDto() {
		return adUserFrequencyDto;
	}

	public void setAdUserFrequencyDto(AdUserFrequencyDto adUserFrequencyDto) {
		this.adUserFrequencyDto = adUserFrequencyDto;
	}

	public String getWhiteAdSites() {
		return whiteAdSites;
	}

	public void setWhiteAdSites(String whiteAdSites) {
		this.whiteAdSites = whiteAdSites;
	}

	public String getBlackAdSites() {
		return blackAdSites;
	}

	public void setBlackAdSites(String blackAdSites) {
		this.blackAdSites = blackAdSites;
	}

	public List<Crowd> getAdCList() {
		return adCList;
	}

	public void setAdCList(List<Crowd> adCList) {
		this.adCList = adCList;
	}

	public String getBrowserSeledIds() {
		return browserSeledIds;
	}

	public void setBrowserSeledIds(String browserSeledIds) {
		this.browserSeledIds = browserSeledIds;
	}

	public String getDeviceSeledIds() {
		return deviceSeledIds;
	}

	public void setDeviceSeledIds(String deviceSeledIds) {
		this.deviceSeledIds = deviceSeledIds;
	}

	public String getSystemSeledIds() {
		return systemSeledIds;
	}

	public void setSystemSeledIds(String systemSeledIds) {
		this.systemSeledIds = systemSeledIds;
	}

	public Map<String, List<TerminalBaseInfo>> getMap() {
		return map;
	}

	public void setMap(Map<String, List<TerminalBaseInfo>> map) {
		this.map = map;
	}

    public String getBackUrlSite() {
        return backUrlSite;
    }

    public void setBackUrlSite(String backUrlSite) {
        this.backUrlSite = backUrlSite;
    }

    public String getAllLabelCodes() {
        return allLabelCodes;
    }

    public void setAllLabelCodes(String allLabelCodes) {
        this.allLabelCodes = allLabelCodes;
    }

	public AdExtLinkDto getAdExtLink() {
		return adExtLink;
	}

	public void setAdExtLink(AdExtLinkDto adExtLink) {
		this.adExtLink = adExtLink;
	}
	
	public AdPlanPortrait getAdPlanPortrait() {
		return adPlanPortrait;
	}

	public void setAdPlanPortrait(AdPlanPortrait adPlanPortrait) {
		this.adPlanPortrait = adPlanPortrait;
	}

}
