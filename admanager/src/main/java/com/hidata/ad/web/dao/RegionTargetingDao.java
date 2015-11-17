package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.dto.AdRegionLink;
import com.hidata.ad.web.model.RegionTargeting;


public interface RegionTargetingDao {


	int insertAdRegionLink(AdRegionLink adRegionLink);

	List<RegionTargeting> findAdRegionListByCondition(
			RegionTargeting regionTargeting);

	List<RegionTargeting> findAdRegionListByAdId(String adId);


}
