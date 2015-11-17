package com.hidata.web.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.DBManager;
import com.hidata.web.dao.AdCountDao;
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

@Component
public class AdCountDaoImpl implements AdCountDao{
	private static Logger logger = LoggerFactory.getLogger(AdCountDaoImpl.class);
	
	@Autowired
	private DBManager db;
	
	@Override
	public List<AdInfoDto> qryAdInfos() {
		String sql = "";
		try {
			List<AdInfoDto> list = db.queryForListObject(sql, AdInfoDto.class);
			return list;
		} catch (DataAccessException e) {
			
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer updateSts(String sts, String adId) {
		String sql = "UPDATE ad_instance SET ad_toufang_sts = ? WHERE ad_id = ?";
		try {
			Object[] args = new Object[]{
					sts,adId
			};
			Integer rows = db.update(sql,args);
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<KeyWordAdPlan> qryKeyWordAdPlans(String adId) {
		String sql = "SELECT * FROM adplan_keyword WHERE  ad_id = " + adId;
		try {
			List<KeyWordAdPlan> list = db.queryForListObject(sql, KeyWordAdPlan.class);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AdPlanPortrait> qryAdPlanPortraits(String adId) {
		String sql = "SELECT * FROM adplan_portrait WHERE ad_id = " + adId;
		try {
			List<AdPlanPortrait> list = db.queryForListObject(sql, AdPlanPortrait.class);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AdTimeFilterDto> qryAdTimeFilterDtos(String adId) {
		String sql = "SELECT * FROM ad_time_filter WHERE ad_id = " + adId;
		try {
			List<AdTimeFilterDto> list = db.queryForListObject(sql, AdTimeFilterDto.class);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AdCrowdLinkDto> qryAdCrowdLinkDtos(String adId) {
		String sql = "SELECT * FROM ad_crowd_link WHERE ad_id = " + adId ;
		try {
			List<AdCrowdLinkDto> list = db.queryForListObject(sql, AdCrowdLinkDto.class);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AdSiteDto> qryAdSiteDtos(String adId) {
		String sql = "SELECT * FROM ad_site WHERE ad_id = " +  adId ;
		try {
			List<AdSiteDto> list = db.queryForListObject(sql, AdSiteDto.class);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AdIpTargeting> qryAdIpTargetings(String adId) {
		String sql = "SELECT * FROM ad_ip_targeting WHERE ad_id = " + adId;
		try {
			List<AdIpTargeting> list = db.queryForListObject(sql, AdIpTargeting.class);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AdRegionLink> qryAdCountDaos(String adId) {
		String sql = "SELECT * FROM ad_region_link WHERE ad_id = " + adId;
		try {
			List<AdRegionLink> list = db.queryForListObject(sql, AdRegionLink.class);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AllLabelAdPlan> qryAllLabelAdPlans(String adId) {
		String sql = "SELECT * FROM all_label_adplan WHERE ad_id = " + adId ;
		try {
			List<AllLabelAdPlan> list = db.queryForListObject(sql, AllLabelAdPlan.class);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<AdExtLinkDto> getAdExtLinkByAdId(String adId) {
		try {
			String sql = "SELECT * FROM ad_ext_link WHERE ad_instance_id = " + adId ;
			List<AdExtLinkDto> list = db.queryForListObject(sql, AdExtLinkDto.class);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
