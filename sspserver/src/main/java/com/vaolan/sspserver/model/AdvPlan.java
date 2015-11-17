package com.vaolan.sspserver.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 本地广告
 * 
 * @author fushengli
 * 
 */
public class AdvPlan {
    /**
     * 广告信息
     */
    private AdInstance adInstance;

    /**
     * 是否站点过滤器
     */
    private boolean isSiteFilter = false;
    
    /**
     * 适合站点
     */
    private List<String> adSites;

    /**
     * 是否负向站点过滤
     */
    private boolean isNegSiteFilter = false;

    /**
     * 负向站点过滤
     */
    private List<String> negAdSites;
    
    /**
     * 适合站点
     */
    private Set<String> adUrls;

    /**
     * 是不是 url 过滤器
     */
    private boolean isUrlFilter = false;

    /**
     * 曝光总数
     */
    private long pvNum;

    /**
     * 当天的曝光次数
     */
    private long pvNumOneDay;

    /**
     * 广告策略
     */
    private List<AdMaterial> adMaterials;

    /**
     * 扩展链接
     */
    private AdExtLink adExtLink = new AdExtLink();

    /**
     * 频次控制过滤
     */
    private AdTimeFrequency adTimeFrequency;

    /**
     * 广告类别过滤
     */
    private List<Integer> adCategorys;

    /**
     * 投放时间过滤
     */
    private List<AdTimeFilter> adTimeFilters;// 可选过滤

    /**
     * 是不是设置时间过滤
     */
    private boolean isPutTimeFilter = false;

    /**
     * 媒体性质过滤
     */
    private List<String> mediaCategory;// 可选过滤

    /**
     * 是不是媒体过滤
     */
    private boolean isMediaCategoryFilter = false;

    /**
     * ip定投
     */
    private Set<String> ipPosTargetFilters;

    /**
     * 是否设置了 ip 过滤
     */
    private boolean isPosIpTargetFilter = false;
    
    /**
     * 是否 ip 不投
     */
    private boolean isNegIpTargetFilter = false;

    /**
     * ip不投
     */
    private Set<String> ipNegTargetFilters;

    /**
     * 定投账号
     */
    private boolean isAdAcctTargetFilter = false;
    
    /**
     * 定投账号 
     */
    private Set<String> adAcctTargetFilters;

    /**
     * 回头客匹配
     */
    private List<String> visitors;

    /**
     * 是不是回头客匹配
     */
    private boolean isVisitorMatch = false;

    /**
     * dmp人群匹配
     */
    private List<String> dmpCrowds;

    /**
     * 是否人群匹配
     */
    private boolean isDmpCrowdMatch = false;
    
    /**
     * 区域匹配
     */
    private Set<String> regionTargetFilters;

    /**
     * 是否区域匹配
     */
    private boolean isRegionTargetFilter = false;
    
    /**
     * 是否标签匹配
     */
    private boolean isLabelFilter = false;

    /**
     * 标签人群
     */
    private List<AdPlanLabelRel> adPlanLabelRelList;
    
    /**
     * 是否关键词匹配
     */
    private boolean isKeywordFilter = false;
    
    /**
     * 关键词人群
     */
    private AdPlanKeyWord adPlanKeyWord;
    
    
    /**
     * 是否关键词匹配
     */
    private boolean isAdUserFrequency = false;
    
    /**
     * 关键词人群
     */
    private AdUserFrequency adUserFrequency;
    
    
    private List<AdDeviceLinkDto> adDeviceLinkList;
    
    private boolean isAdDeviceTarget = false;
    
    

    public boolean isAdDeviceTarget() {
		return isAdDeviceTarget;
	}



	public List<AdDeviceLinkDto> getAdDeviceLinkList() {
		return adDeviceLinkList;
	}


	public void setAdDeviceLinkList(List<AdDeviceLinkDto> adDeviceLinkList) {
		
		if(adDeviceLinkList!=null && adDeviceLinkList.size() >0){
			this.adDeviceLinkList = adDeviceLinkList;
			this.isAdDeviceTarget = true;
		}
		
	}


	public boolean isAdUserFrequency() {
		return isAdUserFrequency;
	}


	public AdUserFrequency getAdUserFrequency() {
		return adUserFrequency;
	}

	public void setAdUserFrequency(AdUserFrequency adUserFrequency) {
		if(adUserFrequency != null) {
			this.adUserFrequency = adUserFrequency;
			this.isAdUserFrequency  = true;
		}
	}

    

	public AdExtLink getAdExtLink() {
        return adExtLink;
    }

    public void setAdExtLink(AdExtLink adExtLink) {

        if (adExtLink == null) {
            this.adExtLink = new AdExtLink();
            this.adExtLink.setThrowUrl("");
        } else {
            this.adExtLink = adExtLink;
        }
    }

    public boolean isRegionTargetFilter() {
        return isRegionTargetFilter;
    }

    public Set<String> getRegionTargetFilters() {
        return regionTargetFilters;
    }

    public void setRegionTargetFilters(Set<String> regionTargetFilters) {
        this.regionTargetFilters = regionTargetFilters;

        if (null != this.regionTargetFilters && this.regionTargetFilters.size() > 0) {
            this.isRegionTargetFilter = true;
        }

    }

    public Set<String> getAdAcctTargetFilters() {
        return adAcctTargetFilters;
    }

    public void setAdAcctTargetFilters(Set<String> adAcctTargetFilters) {
        this.adAcctTargetFilters = adAcctTargetFilters;

        if (null != this.adAcctTargetFilters && this.adAcctTargetFilters.size() > 0) {
            this.isAdAcctTargetFilter = true;
        }

    }

    public boolean isAdAcctTargetFilter() {
        return isAdAcctTargetFilter;
    }

    public void setAdAcctTargetFilter(boolean isAdAcctTargetFilter) {
        this.isAdAcctTargetFilter = isAdAcctTargetFilter;
    }

    /**
     * 返回广告投放开始时间
     * 
     * @return
     */
    public String getStartTime() {
        return this.adInstance.getStartTime();
    }

    /**
     * 返回广告投放结束时间
     * 
     * @return
     */
    public String getEndTime() {
        return this.adInstance.getEndTime();
    }

    public AdInstance getAdInstance() {
        return this.adInstance;
    }

    /**
     * 获取广告Id
     * 
     * @return
     */
    public String getAdvId() {
        return this.adInstance.getAdId();
    }

    /**
     * @return the pvNumOneDay
     */
    public long getPvNumOneDay() {
        return pvNumOneDay;
    }

    /**
     * @param pvNumOneDay
     *            the pvNumOneDay to set
     */
    public void setPvNumOneDay(long pvNumOneDay) {
        this.pvNumOneDay = pvNumOneDay;
    }

    /**
     * 
     * @return
     */
    public long getDayLimit() {
        return adTimeFrequency.getDayLimit();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AdvPlan [adInstance=" + adInstance + ", pvNum=" + pvNum + ", pvNumOneDay="
                + pvNumOneDay + ", adMaterials=" + adMaterials + ", adTimeFrequency="
                + adTimeFrequency + "]";
    }

    /**
     * @return
     */
    public String getUrl() {
        return this.adInstance.getAdTanxUrl();
    }

    /**
     * @return the adCategorys
     */
    public List<Integer> getAdCategorys() {
        return adCategorys;
    }

    /**
     * @return the isBlindBid
     */
    public boolean isBlindBid() {
        return this.adInstance.isBlindBid();
    }

    /**
     * 获取对应尺寸的广告物料
     * 
     * @param size
     * @return
     */
    public AdMaterial getSuitMaterial(String size) {
        for (AdMaterial adMaterial : this.adMaterials) {
            if (adMaterial.getMaterialSizeValue().equals(size)) {
                return adMaterial;
            }
        }

        return null;
    }

    public List<AdMaterial> getAdMaterials() {
        return adMaterials;
    }

    public void setAdInstance(AdInstance adInstance) {
        this.adInstance = adInstance;
    }

    public boolean isSiteFilter() {
        return this.isSiteFilter;
    }

    /**
     * 设置定投站点
     * */
    public void setAdSites(List<String> adSites) {
        this.adSites = adSites;
        if (null != this.adSites && this.adSites.size() > 0) {
            this.isSiteFilter = true;
        }
    }

    /**
     * 设定负向站点
     * @param negAdSites
     */
    public void setNegAdSites(List<String> negAdSites) {
		this.negAdSites = negAdSites;
		   if (null != this.negAdSites && this.negAdSites.size() > 0) {
	            this.isNegSiteFilter = true;
	        }
	}

	/**
     * 设置定投url
     * 
     * @param adUrls
     */
    public void setAdUrls(List<String> adUrls) {
        if (null != adUrls && adUrls.size() > 0) {
            this.isUrlFilter = true;
            this.adUrls = new HashSet<String>();
            for (String url : adUrls) {
                this.adUrls.add(url);
            }
        }
    }

    public Set<String> getAdUrls() {
        return adUrls;
    }

    public boolean isUrlFilter() {
        return isUrlFilter;
    }

    public void setUrlFilter(boolean isUrlFilter) {
        this.isUrlFilter = isUrlFilter;
    }

    public List<String> getAdSites() {
        return adSites;
    }

    public Set<String> getIpPosTargetFilters() {
        return this.ipPosTargetFilters;
    }

    /**
     * 设置定投IP
     * */
    public void setIpPosTargetFilters(Set<String> ipPosTargetFilters) {
        this.ipPosTargetFilters = ipPosTargetFilters;
        if (null != this.ipPosTargetFilters && this.ipPosTargetFilters.size() > 0) {
            this.isPosIpTargetFilter = true;
        }
    }

    public boolean getIsPosIpTargetFilter() {
        return this.isPosIpTargetFilter;
    }

    public Set<String> getIpNegTargetFilters() {
        return this.ipNegTargetFilters;
    }

    /**
     * 设置不投的IP
     * */
    public void setIpNegTargetFilters(Set<String> ipNegTargetFilters) {
        this.ipNegTargetFilters = ipNegTargetFilters;
        if (null != this.ipNegTargetFilters && this.ipNegTargetFilters.size() > 0) {
            this.isNegIpTargetFilter = true;
        }
    }
    
    public boolean isNegSiteFilter() {
		return isNegSiteFilter;
	}

	public List<String> getNegAdSites() {
		return negAdSites;
	}

	public boolean getIsNegIpTargetFilter() {
        return this.isNegIpTargetFilter;
    }

    public void setAdMaterials(List<AdMaterial> adMaterials) {
        this.adMaterials = adMaterials;
    }

    public void setAdTimeFrequency(AdTimeFrequency adTimeFrequency) {
        this.adTimeFrequency = adTimeFrequency;
    }

    public AdTimeFrequency getAdTimeFrequency() {
        return this.adTimeFrequency;
    }

    public void setAdCategorys(List<Integer> adCategorys) {
        this.adCategorys = adCategorys;
    }

    public List<AdTimeFilter> getAdTimeFilters() {
        return adTimeFilters;
    }

    public boolean isPutTimeFilter() {
        return this.isPutTimeFilter;
    }

    public void setAdTimeFilters(List<AdTimeFilter> adTimeFilters) {
        this.adTimeFilters = adTimeFilters;
        if (null != this.adTimeFilters && this.adTimeFilters.size() > 0) {
            this.isPutTimeFilter = true;
        }
    }

    public List<String> getMediaCategory() {
        return mediaCategory;
    }

    public boolean isMediaCategoryFilter() {
        return this.isMediaCategoryFilter;
    }

    public void setMediaCategory(List<String> mediaCategory) {
        this.mediaCategory = mediaCategory;
        if (null != mediaCategory && mediaCategory.size() > 0) {
            this.isMediaCategoryFilter = true;
        }
    }

    public List<String> getVisitors() {
        return visitors;
    }

    public boolean isVisitorMatch() {
        return this.isVisitorMatch;
    }

    public void setVisitors(List<String> visitors) {
        this.visitors = visitors;
        if (null != visitors && visitors.size() > 0) {
            this.isVisitorMatch = true;
        }
    }

    /**
     * 是否需要按dmp人群进行匹配
     * */
    public boolean isDmpCrowdMatch() {
        return isDmpCrowdMatch;
    }

    public void setDmpCrowds(List<String> dmpCrowds) {
        this.dmpCrowds = dmpCrowds;
        if (null != dmpCrowds && dmpCrowds.size() > 0) {
            this.isDmpCrowdMatch = true;
        }
    }

    public List<String> getDmpCrowds() {
        return this.dmpCrowds;
    }
    

	public AdPlanKeyWord getAdPlanKeyWord() {
		return adPlanKeyWord;
	}

	public void setAdPlanKeyWord(AdPlanKeyWord adPlanKeyWord) {
		this.adPlanKeyWord = adPlanKeyWord;
		if (null != adPlanKeyWord) {
            this.isKeywordFilter = true;
        }
	}
	
	public boolean isKeywordFilter() {
		return isKeywordFilter;
	}

	public List<AdPlanLabelRel> getAdPlanLabelRelList() {
        return adPlanLabelRelList;
    }

    public void setAdPlanLabelRelList(List<AdPlanLabelRel> adPlanLabelRelList) {
        this.adPlanLabelRelList = adPlanLabelRelList;
        if (null != adPlanLabelRelList && adPlanLabelRelList.size() > 0) {
            this.isLabelFilter = true;
        }
    }

    public boolean isLabelFilter() {
        return isLabelFilter;
    }

    public void setLabelFilter(boolean isLabelFilter) {
        this.isLabelFilter = isLabelFilter;
    }

}
