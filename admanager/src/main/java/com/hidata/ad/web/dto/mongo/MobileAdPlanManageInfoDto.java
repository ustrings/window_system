package com.hidata.ad.web.dto.mongo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hidata.ad.web.dto.AdCategoryDto;
import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.dto.AdSiteDto;
import com.hidata.ad.web.dto.AdTimeFrequencyDto;
import com.hidata.ad.web.dto.AdUserFrequencyDto;
import com.hidata.ad.web.dto.TerminalBaseInfo;
import com.hidata.ad.web.model.Crowd;
import com.hidata.ad.web.model.VisitorCrowd;

/**
 * 广告计划维护信息 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月11日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhangtengda
 */
public class MobileAdPlanManageInfoDto implements Serializable {

	private static final long serialVersionUID = 3779913323709837176L;

	// 广告核心信息
	private AdInstanceDto adInstanceDto = new AdInstanceDto();

	// 广告时间信息
	private String putHours;

	// 广告物料关联信息
	private String adMaterialIds;

	// 广告时间投放频率
	private AdTimeFrequencyDto adTimeFrequencyDto = new AdTimeFrequencyDto();

	// 唯一访客投放频率
	private AdUserFrequencyDto adUserFrequencyDto = new AdUserFrequencyDto();
	
	// 广告行业分类
	private String adIndustryIds;
	
	//广告类型
	private AdType adType;
	
	//app 分类
	private String adAppIds;
	
	//广告 设备类型
	private String adDeviceIds;
	
	// 广告点击类型
	private AdClick adClick;
	
	//广告类别
	private String adCategoryCodes; 

	public String getAdCategoryCodes() {
		return adCategoryCodes;
	}

	public void setAdCategoryCodes(String adCategoryCodes) {
		this.adCategoryCodes = adCategoryCodes;
	}

	public AdInstanceDto getAdInstanceDto() {
		return adInstanceDto;
	}

	public void setAdInstanceDto(AdInstanceDto adInstanceDto) {
		this.adInstanceDto = adInstanceDto;
	}

	public String getPutHours() {
		return putHours;
	}

	public void setPutHours(String putHours) {
		this.putHours = putHours;
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

	public String getAdIndustryIds() {
		return adIndustryIds;
	}

	public void setAdIndustryIds(String adIndustryIds) {
		this.adIndustryIds = adIndustryIds;
	}

	public AdType getAdType() {
		return adType;
	}

	public void setAdType(AdType adType) {
		this.adType = adType;
	}

	public String getAdAppIds() {
		return adAppIds;
	}

	public void setAdAppIds(String adAppIds) {
		this.adAppIds = adAppIds;
	}

	public String getAdDeviceIds() {
		return adDeviceIds;
	}

	public void setAdDeviceIds(String adDeviceIds) {
		this.adDeviceIds = adDeviceIds;
	}

	public AdClick getAdClick() {
		return adClick;
	}

	public void setAdClick(AdClick adClick) {
		this.adClick = adClick;
	}

}
