package com.hidata.web.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hidata.framework.util.DateUtil;
import com.hidata.framework.util.StringUtil;
import com.hidata.web.dao.AdCountDao;
import com.hidata.web.dao.PagerDao;
import com.hidata.web.dto.AdCountDetailDto;
import com.hidata.web.dto.AdCrowdLinkDto;
import com.hidata.web.dto.AdExtLinkDto;
import com.hidata.web.dto.AdIpTargeting;
import com.hidata.web.dto.AdPlanPortrait;
import com.hidata.web.dto.AdRegionLink;
import com.hidata.web.dto.AdSiteDto;
import com.hidata.web.dto.AdTimeFilterDto;
import com.hidata.web.dto.AllLabelAdPlan;
import com.hidata.web.dto.KeyWordAdPlan;
import com.hidata.web.service.AdCountService;
import com.hidata.web.util.Pager;

@Service
public class AdCountServiceImpl implements AdCountService{
	
	@Autowired
	private PagerDao pagerDao;
	
	@Autowired
	private AdCountDao adCountDao;
	@Override
	public Pager getPager(Map<String, String> map, String curPage) {
		Pager pager = null;
		String time = DateUtil.format(DateUtil.addDays(DateUtil.getCurrentDate(), 0), DateUtil.C_DATE_PATTON_DEFAULT);
		String now = time + " 00:00:00";
		StringBuffer sb_sql = new StringBuffer();
		sb_sql.append(
				"SELECT m.* , n.throw_url FROM (SELECT a.ad_id, a.ad_name, a.link_type , DATE_FORMAT(a.start_time,'%Y-%m-%d') start_time, DATE_FORMAT(a.end_time,'%Y-%m-%d') end_time, a.sts, a.ad_url ,a.ad_toufang_sts ,IFNULL(b.pv_num,0) pv_num, IFNULL(b.click_num,0) click_num ,ROUND(IFNULL(b.click_num/b.pv_num*100,0),2) click_rate FROM ad_instance a ,ad_stat_info b WHERE b.num_type = 'D' AND a.ad_id = b.ad_id AND a.ad_toufang_sts != -5 AND a.ad_toufang_sts != -1  AND a.ad_toufang_sts != 0 AND a.ad_useful_type = 'N' AND b.ts = ' " + now + " ') m LEFT JOIN ad_ext_link n ON n.ad_instance_id = m.ad_id WHERE 1=1 "
				);
		if(map != null && map.size() > 0){
			String keyword = map.get("keyword");
			if(StringUtil.isNotEmpty(keyword)){
				sb_sql.append(" AND m.ad_name LIKE '%" + keyword +"%'");
			}
			
			String startTime = map.get("startTime");
			if(StringUtil.isNotEmpty(startTime)){
				sb_sql.append(" AND m.start_time >= '" + startTime + "' ");
			}
			
			String endTime = map.get("endTime");
			if(StringUtil.isNotEmpty(endTime)){
				sb_sql.append(" AND m.end_time <= '" + endTime + "' ");
			}
			
			String adTFsts = map.get("adTFsts");
			if(StringUtil.isNotEmpty(adTFsts) && !"-2".equals(adTFsts)){
				sb_sql.append(" AND m.ad_toufang_sts = '" + adTFsts + "' ");
			}
		}
		sb_sql.append(" ORDER BY m.ad_id DESC");
		pager = pagerDao.getPagerBySql(sb_sql.toString(), Integer.parseInt(curPage), 10, AdCountDetailDto.class);
		if(pager.getResult() != null && pager.getResult().size() > 0){
			@SuppressWarnings("unchecked")
			List<AdCountDetailDto> list = (List<AdCountDetailDto>) pager.getResult();
			for(AdCountDetailDto adCountDetail : list){
				StringBuffer dingxiangs = new StringBuffer();
				String adId = adCountDetail.getAdId();
				//判断都有哪些定向 (共八个)
				List<KeyWordAdPlan> keyWordAdPlans = adCountDao.qryKeyWordAdPlans(adId);//关键词定向
				//String sql = "SELECT * FROM adplan_keyword WHERE sts = 'A' AND ad_id = 1";
				if(keyWordAdPlans != null && keyWordAdPlans.size() > 0){
					dingxiangs.append("[关键词定向]").append(" ");
				}
				List<AdPlanPortrait> adPlanPortraits = adCountDao.qryAdPlanPortraits(adId);//人群画像定向
				//String sql = "SELECT * FROM adplan_portrait WHERE ad_id = 1";
				if(adPlanPortraits != null && adPlanPortraits.size() > 0){
					dingxiangs.append("[人群画像定向]").append(" ");
				}
				List<AdTimeFilterDto> adTimeFilterDtos = adCountDao.qryAdTimeFilterDtos(adId);//时间定向
				//String sql = "SELECT * FROM ad_time_filter WHERE ad_id = 1";
				if(adTimeFilterDtos != null && adTimeFilterDtos.size() > 0){
					dingxiangs.append("[时间定向]").append(" ");
				}
				List<AdCrowdLinkDto> adCrowdLinkDtos = adCountDao.qryAdCrowdLinkDtos(adId);//受众人群定向
				//String sql = "SELECT * FROM ad_crowd_link WHERE ad_id = 1 AND sts = 'A'";
				if(adCrowdLinkDtos != null && adCrowdLinkDtos.size() > 0){
					dingxiangs.append("[受众人群定向]").append(" ");
				}
				List<AdSiteDto> adSiteDtos = adCountDao.qryAdSiteDtos(adId);//站点定向
				//String sql = "SELECT * FROM ad_site WHERE ad_id = 1 AND sts = 'A'";
				if(adSiteDtos != null && adSiteDtos.size() > 0){
					dingxiangs.append("[站点定向]").append(" ");
				}
				List<AdIpTargeting> adIpTargetings = adCountDao.qryAdIpTargetings(adId);//IP定向
				//String sql = "SELECT * FROM ad_ip_targeting WHERE ad_id = 1";
				if(adIpTargetings != null && adIpTargetings.size() > 0){
					dingxiangs.append("[IP定向]").append(" ");
				}
				List<AdRegionLink> adRegionLinks = adCountDao.qryAdCountDaos(adId);//区域定向
				//String sql = "SELECT * FROM ad_region_link WHERE ad_id = 1";
				if(adRegionLinks != null && adRegionLinks.size() > 0){
					dingxiangs.append("[区域定向]").append(" ");
				}
				
				List<AllLabelAdPlan> allLabelAdPlans = adCountDao.qryAllLabelAdPlans(adId);//标签定向
				//String sql = "SELECT * FROM all_label_adplan WHERE ad_id = 1 AND sts = 'A'";
				if(allLabelAdPlans != null && allLabelAdPlans.size() > 0){
					dingxiangs.append("[标签定向]");
				}
				
				adCountDetail.setAdStrategy(dingxiangs.toString());
			}
		}
		return pager;
	}

	@Override
	public Boolean updateSts(String sts, String adId) {
		if(StringUtil.isNotEmpty(sts) && StringUtil.isNotEmpty(adId)){
			Integer rows = adCountDao.updateSts(sts,adId);
			if(rows != null && rows > 0){
				return true;
			}
		}
		return false;
	}

	@Override
	public AdExtLinkDto getAdExtLinkByAdId(String adId) {
		if(StringUtil.isNotEmpty(adId)){
			List<AdExtLinkDto> list = adCountDao.getAdExtLinkByAdId(adId);
			if(list != null && list.size() == 1){
				return list.get(0);
			}
		}
		return null;
	}

	@Override
	public Pager getTPager(Map<String, String> map, String curPage) {
		Pager pager = null;
		String time = DateUtil.format(DateUtil.addDays(DateUtil.getCurrentDate(), 0), DateUtil.C_DATE_PATTON_DEFAULT);
		String now = time + " 00:00:00";
		StringBuffer sb_sql = new StringBuffer();
		sb_sql.append(
				"SELECT m.* , n.throw_url FROM (SELECT a.ad_id, a.ad_name, a.link_type , DATE_FORMAT(a.start_time,'%Y-%m-%d') start_time, DATE_FORMAT(a.end_time,'%Y-%m-%d') end_time, a.sts, a.ad_url ,a.ad_toufang_sts ,IFNULL(b.pv_num,0) pv_num, IFNULL(b.click_num,0) click_num ,ROUND(IFNULL(b.click_num/b.pv_num*100,0),2) click_rate FROM ad_instance a ,ad_stat_info b WHERE b.num_type = 'D' AND a.ad_id = b.ad_id AND a.ad_toufang_sts != -5 AND a.ad_toufang_sts != -1  AND a.ad_toufang_sts != 0 AND a.ad_useful_type = 'T' AND b.ts = ' " + now + " ') m LEFT JOIN ad_ext_link n ON n.ad_instance_id = m.ad_id WHERE 1=1 "
				);
		if(map != null && map.size() > 0){
			String keyword = map.get("keyword");
			if(StringUtil.isNotEmpty(keyword)){
				sb_sql.append(" AND m.ad_name LIKE '%" + keyword +"%'");
			}
			
			String startTime = map.get("startTime");
			if(StringUtil.isNotEmpty(startTime)){
				sb_sql.append(" AND m.start_time >= '" + startTime + "' ");
			}
			
			String endTime = map.get("endTime");
			if(StringUtil.isNotEmpty(endTime)){
				sb_sql.append(" AND m.end_time <= '" + endTime + "' ");
			}
			
			String adTFsts = map.get("adTFsts");
			if(StringUtil.isNotEmpty(adTFsts) && !"-2".equals(adTFsts)){
				sb_sql.append(" AND m.ad_toufang_sts = '" + adTFsts + "' ");
			}
		}
		sb_sql.append(" ORDER BY m.ad_id DESC");
		pager = pagerDao.getPagerBySql(sb_sql.toString(), Integer.parseInt(curPage), 10, AdCountDetailDto.class);
		if(pager.getResult() != null && pager.getResult().size() > 0){
			@SuppressWarnings("unchecked")
			List<AdCountDetailDto> list = (List<AdCountDetailDto>) pager.getResult();
			for(AdCountDetailDto adCountDetail : list){
				StringBuffer dingxiangs = new StringBuffer();
				String adId = adCountDetail.getAdId();
				//判断都有哪些定向 (共八个)
				List<KeyWordAdPlan> keyWordAdPlans = adCountDao.qryKeyWordAdPlans(adId);//关键词定向
				//String sql = "SELECT * FROM adplan_keyword WHERE sts = 'A' AND ad_id = 1";
				if(keyWordAdPlans != null && keyWordAdPlans.size() > 0){
					dingxiangs.append("[关键词定向]").append(" ");
				}
				List<AdPlanPortrait> adPlanPortraits = adCountDao.qryAdPlanPortraits(adId);//人群画像定向
				//String sql = "SELECT * FROM adplan_portrait WHERE ad_id = 1";
				if(adPlanPortraits != null && adPlanPortraits.size() > 0){
					dingxiangs.append("[人群画像定向]").append(" ");
				}
				List<AdTimeFilterDto> adTimeFilterDtos = adCountDao.qryAdTimeFilterDtos(adId);//时间定向
				//String sql = "SELECT * FROM ad_time_filter WHERE ad_id = 1";
				if(adTimeFilterDtos != null && adTimeFilterDtos.size() > 0){
					dingxiangs.append("[时间定向]").append(" ");
				}
				List<AdCrowdLinkDto> adCrowdLinkDtos = adCountDao.qryAdCrowdLinkDtos(adId);//受众人群定向
				//String sql = "SELECT * FROM ad_crowd_link WHERE ad_id = 1 AND sts = 'A'";
				if(adCrowdLinkDtos != null && adCrowdLinkDtos.size() > 0){
					dingxiangs.append("[受众人群定向]").append(" ");
				}
				List<AdSiteDto> adSiteDtos = adCountDao.qryAdSiteDtos(adId);//站点定向
				//String sql = "SELECT * FROM ad_site WHERE ad_id = 1 AND sts = 'A'";
				if(adSiteDtos != null && adSiteDtos.size() > 0){
					dingxiangs.append("[站点定向]").append(" ");
				}
				List<AdIpTargeting> adIpTargetings = adCountDao.qryAdIpTargetings(adId);//IP定向
				//String sql = "SELECT * FROM ad_ip_targeting WHERE ad_id = 1";
				if(adIpTargetings != null && adIpTargetings.size() > 0){
					dingxiangs.append("[IP定向]").append(" ");
				}
				List<AdRegionLink> adRegionLinks = adCountDao.qryAdCountDaos(adId);//区域定向
				//String sql = "SELECT * FROM ad_region_link WHERE ad_id = 1";
				if(adRegionLinks != null && adRegionLinks.size() > 0){
					dingxiangs.append("[区域定向]").append(" ");
				}
				
				List<AllLabelAdPlan> allLabelAdPlans = adCountDao.qryAllLabelAdPlans(adId);//标签定向
				//String sql = "SELECT * FROM all_label_adplan WHERE ad_id = 1 AND sts = 'A'";
				if(allLabelAdPlans != null && allLabelAdPlans.size() > 0){
					dingxiangs.append("[标签定向]");
				}
				
				adCountDetail.setAdStrategy(dingxiangs.toString());
			}
		}
		return pager;
	}
}
