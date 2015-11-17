package com.hidata.web.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hidata.framework.util.StringUtil;
import com.hidata.web.dao.AdCheckProcessDao;
import com.hidata.web.dao.AdCountDao;
import com.hidata.web.dao.PagerDao;
import com.hidata.web.dto.AdCheckConfigDto;
import com.hidata.web.dto.AdCheckProcessDto;
import com.hidata.web.dto.AdCountDetailDto;
import com.hidata.web.dto.AdCrowdLinkDto;
import com.hidata.web.dto.AdExtLinkDto;
import com.hidata.web.dto.AdInstanceCheckProcessDto;
import com.hidata.web.dto.AdInstanceDto;
import com.hidata.web.dto.AdInstanceShowDto;
import com.hidata.web.dto.AdIpTargeting;
import com.hidata.web.dto.AdPlanPortrait;
import com.hidata.web.dto.AdRegionLink;
import com.hidata.web.dto.AdSiteDto;
import com.hidata.web.dto.AdTimeFilterDto;
import com.hidata.web.dto.AllLabelAdPlan;
import com.hidata.web.dto.CheckHistoryDto;
import com.hidata.web.dto.KeyWordAdPlan;
import com.hidata.web.dto.TAdCheckConfigDto;
import com.hidata.web.dto.TAdCheckProcessDto;
import com.hidata.web.service.AdCheckProcessService;
import com.hidata.web.util.Pager;

@Service
public class AdCheckProcessServiceImpl implements AdCheckProcessService {

    @Autowired
    private AdCheckProcessDao adCheckProcessDao;
    @Autowired PagerDao pagerDao;
    @Autowired
    private AdCountDao adCountDao;

    @Override
    public int addAdCheckProcess(AdCheckProcessDto adCheckProcessDto) {
        return adCheckProcessDao.insertAdCheckProcess(adCheckProcessDto);
    }

    @Override
    public int addAdCheckConfig(AdCheckConfigDto adCheckConfigDto) {
        return adCheckProcessDao.insertAdCheckConfig(adCheckConfigDto);
    }

    @Override
    public AdCheckProcessDto findAdCheckProcessDtoByPk(String adCheckProcessPk) {
        return adCheckProcessDao.findAdCheckProcessByPk(adCheckProcessPk);
    }

    @Override
    public AdCheckConfigDto findAdCheckConfigDtoByPk(String adCheckConfigPk) {
        return adCheckProcessDao.findAdCheckConfigByPk(adCheckConfigPk);
    }

	@Override
	public Pager getRoleListPager(Map<String, String> map, String curPage) {
		Pager pager = null;
		StringBuffer sqlBuilder = new StringBuffer();
		sqlBuilder.append("select * from ad_check_config where 1=1");
		if(map!=null && map.size()>0){
			String checkRoleName = map.get("checkRoleName");
			if(StringUtil.isNotEmpty(checkRoleName)){
				sqlBuilder.append(" and check_role_name like '%"+checkRoleName+"%'");
			}
			String userName = map.get("userName");
			if(StringUtil.isNotEmpty(userName)){
				sqlBuilder.append(" and user_name like '%"+userName+"%'");
			}
			String deptName = map.get("deptName");
			if(StringUtil.isNotEmpty(deptName)){
				sqlBuilder.append(" and dept_name like '%"+deptName+"%'");
			}
			String telNbr = map.get("telNbr");
			if(StringUtil.isNotEmpty(telNbr)){
				sqlBuilder.append(" and tel_nbr like '%"+telNbr+"%'");
			}
		}
		sqlBuilder.append(" order by sts_date desc");
		pager = pagerDao.getPagerBySql(sqlBuilder.toString(), Integer.parseInt(curPage), 5, AdCheckConfigDto.class);
		return pager;
	}

	@Override
	public int deleteAdCheckConfigById(String adCheckConfigPk) {
		int result = 0; 
		result = adCheckProcessDao.deleteAdCheckConfigById(adCheckConfigPk);
		List<AdCheckProcessDto> list = adCheckProcessDao.findAdCheckProcessDtoByCheckUserId(adCheckConfigPk);
		if(result > 0 && list.size()>0){						
			  result = adCheckProcessDao.deleteAdCheckProcessByCheckUserId(adCheckConfigPk);			
			  return result;
		}
		return result;
	}

	@Override
	public int updateAdCheckConfig(AdCheckConfigDto adCheckConfigDto) {
		
		return adCheckProcessDao.updateAdCheckConfig(adCheckConfigDto);
	}

	@Override
	public Pager getCheckListPager(Map<String, String> map, String curPage) {
		Pager pager = null;
		StringBuffer sqlBuilder = new StringBuffer();
		sqlBuilder.append("select ai.ad_id,ai.ad_name,ai.start_time,ai.end_time,ai.ad_url,"
				         + "ai.charge_type,ai.unit_price,ai.link_type,acp.id as check_process_id,acp.check_user_id,"
				         + "acp.check_sts,acp.sts_date,atf.day_limit");
		sqlBuilder.append(" from ad_instance ai,ad_check_process acp,ad_time_frequency atf where 1=1");
		sqlBuilder.append(" and ai.ad_id = acp.ad_id and ai.ad_id = atf.ad_id and ai.ad_toufang_sts not in (2,3,4,5,6)");
		if(map!=null && map.size()>0){
			String checkUserId = map.get("roleId");
			String adName = map.get("adName");
			if(StringUtil.isNotEmpty(adName)){
				sqlBuilder.append(" and ai.ad_name like '%"+adName+"%'");
			}
			String checksts = map.get("checksts");
			if(StringUtil.isNotEmpty(checksts)){
				sqlBuilder.append(" and acp.check_sts ='"+checksts+"'");
			}
			String startTime = map.get("startTime");
			if(StringUtil.isNotEmpty(startTime)){
				sqlBuilder.append(" and ai.start_time >= '"+startTime+"'");
			}
			String endTime = map.get("endTime");
			if(StringUtil.isNotEmpty(endTime)){
				sqlBuilder.append(" and ai.end_time <= '"+endTime+"'");
			}
			if(StringUtil.isNotEmpty(checkUserId)){
				sqlBuilder.append(" and acp.check_user_id='"+checkUserId+"'");
			}
		}
		sqlBuilder.append(" group by ai.ad_id");
		sqlBuilder.append(" order by acp.sts_date desc");
		
		pager = pagerDao.getPagerBySql(sqlBuilder.toString(), Integer.parseInt(curPage), 5, AdInstanceCheckProcessDto.class);
		if(pager.getResult() != null && pager.getResult().size() > 0){
			@SuppressWarnings("unchecked")
			List<AdInstanceCheckProcessDto> list = (List<AdInstanceCheckProcessDto>) pager.getResult();
			for(AdInstanceCheckProcessDto adInstanceCheckProcessDto : list){
				StringBuffer dingxiangs = new StringBuffer();
				String adId = adInstanceCheckProcessDto.getAdId();
				//判断都有哪些定向 (共八个)
				List<KeyWordAdPlan> keyWordAdPlans = adCountDao.qryKeyWordAdPlans(adId);//关键词定向				
				if(keyWordAdPlans != null && keyWordAdPlans.size() > 0){
					dingxiangs.append("[关键词定向]").append(" ");
				}
				List<AdPlanPortrait> adPlanPortraits = adCountDao.qryAdPlanPortraits(adId);//人群画像定向				
				if(adPlanPortraits != null && adPlanPortraits.size() > 0){
					dingxiangs.append("[人群画像定向]").append(" ");
				}
				List<AdTimeFilterDto> adTimeFilterDtos = adCountDao.qryAdTimeFilterDtos(adId);//时间定向				
				if(adTimeFilterDtos != null && adTimeFilterDtos.size() > 0){
					dingxiangs.append("[时间定向]").append(" ");
				}
				List<AdCrowdLinkDto> adCrowdLinkDtos = adCountDao.qryAdCrowdLinkDtos(adId);//受众人群定向				
				if(adCrowdLinkDtos != null && adCrowdLinkDtos.size() > 0){
					dingxiangs.append("[受众人群定向]").append(" ");
				}
				List<AdSiteDto> adSiteDtos = adCountDao.qryAdSiteDtos(adId);//站点定向				
				if(adSiteDtos != null && adSiteDtos.size() > 0){
					dingxiangs.append("[站点定向]").append(" ");
				}
				List<AdIpTargeting> adIpTargetings = adCountDao.qryAdIpTargetings(adId);//IP定向				
				if(adIpTargetings != null && adIpTargetings.size() > 0){
					dingxiangs.append("[IP定向]").append(" ");
				}
				List<AdRegionLink> adRegionLinks = adCountDao.qryAdCountDaos(adId);//区域定向				
				if(adRegionLinks != null && adRegionLinks.size() > 0){
					dingxiangs.append("[区域定向]").append(" ");
				}				
				List<AllLabelAdPlan> allLabelAdPlans = adCountDao.qryAllLabelAdPlans(adId);//标签定向				
				if(allLabelAdPlans != null && allLabelAdPlans.size() > 0){
					dingxiangs.append("[标签定向]");
				}				
				adInstanceCheckProcessDto.setAdStrategy(dingxiangs.toString());
			}	
		}	
		return pager;
	}
	
	@Override
	public List<AdCheckConfigDto> findAdCheckConfigDtoByUserAcctRel(
			String userAcctRel) {
		
		return adCheckProcessDao.findAdCheckConfigByUserAcctRel(userAcctRel);
	}

	@Override
	public AdExtLinkDto findAdExtLinkByAdInstanceId(String adInstanceId) {
		
		return adCheckProcessDao.findAdExtLinkByAdInstanceId(adInstanceId);
	}

	@Override
	public void updateAdCheckProcessInfo(AdCheckProcessDto adCheckProcessDto) {
		
		adCheckProcessDao.updateAdCheckProcessInfo(adCheckProcessDto);
	}

	@Override
	public AdInstanceDto findAdInstanceByAdId(String adId) {
		
		return adCheckProcessDao.findAdInstanceByAdId(adId);
	}

	@Override
	public void updateAdTouFangSts(AdInstanceDto adInstanceDto) {
		
		 adCheckProcessDao.updateAdTouFangSts(adInstanceDto);
	}

	@Override
	public Pager getCheckHistoryPager(Map<String, String> map, String curPage) {	
		Pager pager = null;
		StringBuffer sqlBuilder = new StringBuffer();
		sqlBuilder.append("select ai.*,atf.day_limit from (");
		sqlBuilder.append("select * from ad_instance a where 1=1");
		if(map!=null && map.size()>0){
			String adName = map.get("adName");
			if(StringUtil.isNotEmpty(adName)){
				sqlBuilder.append(" and a.ad_name like '%"+adName+"%'");
			}
			String checksts = map.get("checksts");
			if(StringUtil.isNotEmpty(checksts)&&!"2".equals(checksts)){
				sqlBuilder.append(" and a.ad_toufang_sts ='"+checksts+"'");
			}else if("2".equals(checksts)){
				sqlBuilder.append(" and (a.ad_toufang_sts in (2,3,4))");
			}else {
				sqlBuilder.append(" and a.ad_toufang_sts <> 6");
			}
			String startTime = map.get("startTime");
			if(StringUtil.isNotEmpty(startTime)){
				sqlBuilder.append(" and a.start_time >= '"+startTime+"'");
			}
			String endTime = map.get("endTime");
			if(StringUtil.isNotEmpty(endTime)){
				sqlBuilder.append(" and a.end_time <= '"+endTime+"'");
			}
		}
		sqlBuilder.append(" and ad_id in ( select distinct(ad_id) from ad_check_process)");
		sqlBuilder.append(" order by create_time desc ");
		sqlBuilder.append(") ai,ad_time_frequency atf where ai.ad_id = atf.ad_id");
		pager = pagerDao.getPagerBySql(sqlBuilder.toString(), Integer.parseInt(curPage), 5, AdInstanceShowDto.class);
		if(pager.getResult() != null && pager.getResult().size() > 0){
			@SuppressWarnings("unchecked")
			List<AdInstanceShowDto> list = (List<AdInstanceShowDto>) pager.getResult();
			for(AdInstanceShowDto adInstanceShowDto : list){
				StringBuffer dingxiangs = new StringBuffer();
				String adId = adInstanceShowDto.getAdId();
				//判断都有哪些定向 (共八个)
				List<KeyWordAdPlan> keyWordAdPlans = adCountDao.qryKeyWordAdPlans(adId);//关键词定向				
				if(keyWordAdPlans != null && keyWordAdPlans.size() > 0){
					dingxiangs.append("[关键词定向]").append(" ");
				}
				List<AdPlanPortrait> adPlanPortraits = adCountDao.qryAdPlanPortraits(adId);//人群画像定向				
				if(adPlanPortraits != null && adPlanPortraits.size() > 0){
					dingxiangs.append("[人群画像定向]").append(" ");
				}
				List<AdTimeFilterDto> adTimeFilterDtos = adCountDao.qryAdTimeFilterDtos(adId);//时间定向				
				if(adTimeFilterDtos != null && adTimeFilterDtos.size() > 0){
					dingxiangs.append("[时间定向]").append(" ");
				}
				List<AdCrowdLinkDto> adCrowdLinkDtos = adCountDao.qryAdCrowdLinkDtos(adId);//受众人群定向				
				if(adCrowdLinkDtos != null && adCrowdLinkDtos.size() > 0){
					dingxiangs.append("[受众人群定向]").append(" ");
				}
				List<AdSiteDto> adSiteDtos = adCountDao.qryAdSiteDtos(adId);//站点定向				
				if(adSiteDtos != null && adSiteDtos.size() > 0){
					dingxiangs.append("[站点定向]").append(" ");
				}
				List<AdIpTargeting> adIpTargetings = adCountDao.qryAdIpTargetings(adId);//IP定向				
				if(adIpTargetings != null && adIpTargetings.size() > 0){
					dingxiangs.append("[IP定向]").append(" ");
				}
				List<AdRegionLink> adRegionLinks = adCountDao.qryAdCountDaos(adId);//区域定向				
				if(adRegionLinks != null && adRegionLinks.size() > 0){
					dingxiangs.append("[区域定向]").append(" ");
				}				
				List<AllLabelAdPlan> allLabelAdPlans = adCountDao.qryAllLabelAdPlans(adId);//标签定向				
				if(allLabelAdPlans != null && allLabelAdPlans.size() > 0){
					dingxiangs.append("[标签定向]");
				}				
				adInstanceShowDto.setAdStrategy(dingxiangs.toString());
			}	
		}			
		return pager;
	}

	@Override
	public List<AdCheckProcessDto> findAdCheckProcessDtoByAdId(String adId) {
		
		return adCheckProcessDao.findAdCheckProcessDtoByAdId(adId);
	}

	@Override
	public List<CheckHistoryDto> findCheckHistoryByAdId(String adId) {
	
		return adCheckProcessDao.findCheckHistoryByAdId(adId);
	}

	@Override
	public int deleteAdCheckProcessByCheckUserId(String adCheckConfigPk) {
		
		return adCheckProcessDao.deleteAdCheckProcessByCheckUserId(adCheckConfigPk);
	}

	@Override
	public List<AdCheckProcessDto> findAdCheckProcessDtoByCheckUserId(
			String checkUserId) {
		
		return adCheckProcessDao.findAdCheckProcessDtoByCheckUserId(checkUserId);
	}

	@Override
	public Pager getTRoleListPager(Map<String, String> map, String curPage) {
		
		Pager pager = null;
		StringBuffer sqlBuilder = new StringBuffer();
		sqlBuilder.append("select * from t_ad_check_config where 1=1");
		if(map!=null && map.size()>0){
			String checkRoleName = map.get("checkRoleName");
			if(StringUtil.isNotEmpty(checkRoleName)){
				sqlBuilder.append(" and check_role_name like '%"+checkRoleName+"%'");
			}
			String userName = map.get("userName");
			if(StringUtil.isNotEmpty(userName)){
				sqlBuilder.append(" and user_name like '%"+userName+"%'");
			}
			String deptName = map.get("deptName");
			if(StringUtil.isNotEmpty(deptName)){
				sqlBuilder.append(" and dept_name like '%"+deptName+"%'");
			}
			String telNbr = map.get("telNbr");
			if(StringUtil.isNotEmpty(telNbr)){
				sqlBuilder.append(" and tel_nbr like '%"+telNbr+"%'");
			}
		}
		sqlBuilder.append(" order by sts_date desc");
		pager = pagerDao.getPagerBySql(sqlBuilder.toString(), Integer.parseInt(curPage), 5, TAdCheckConfigDto.class);
		return pager;
	}

	@Override
	public List<TAdCheckConfigDto> findTAdCheckConfigDtoByUserAcctRel(
			String userAcctRel) {
		
		return adCheckProcessDao.findTAdCheckConfigDtoByUserAcctRel(userAcctRel);
	}

	@Override
	public int addTAdCheckConfig(TAdCheckConfigDto t_adCheckConfigDto) {
		
		return adCheckProcessDao.insertTAdCheckConfig(t_adCheckConfigDto);
	}

	@Override
	public Pager getTCheckHistoryPager(Map<String, String> map, String curPage) {
		Pager pager = null;
		StringBuffer sqlBuilder = new StringBuffer();
		sqlBuilder.append("select ai.*,atf.day_limit from (");
		sqlBuilder.append("select * from ad_instance a where 1=1");
		if(map!=null && map.size()>0){
			String adName = map.get("adName");
			if(StringUtil.isNotEmpty(adName)){
				sqlBuilder.append(" and a.ad_name like '%"+adName+"%'");
			}
			String checksts = map.get("checksts");
			if(StringUtil.isNotEmpty(checksts)&&!"2".equals(checksts)){
				sqlBuilder.append(" and a.ad_toufang_sts ='"+checksts+"'");
			}else if("2".equals(checksts)){
				sqlBuilder.append(" and (a.ad_toufang_sts in (2,3,4))");
			}else {
				sqlBuilder.append(" and a.ad_toufang_sts <> 6");
			}
			String startTime = map.get("startTime");
			if(StringUtil.isNotEmpty(startTime)){
				sqlBuilder.append(" and a.start_time >= '"+startTime+"'");
			}
			String endTime = map.get("endTime");
			if(StringUtil.isNotEmpty(endTime)){
				sqlBuilder.append(" and a.end_time <= '"+endTime+"'");
			}
		}
		sqlBuilder.append(" and ad_id in ( select distinct(ad_id) from t_ad_check_process)");
		sqlBuilder.append(" order by create_time desc ");
		sqlBuilder.append(") ai,ad_time_frequency atf where ai.ad_id = atf.ad_id");
		pager = pagerDao.getPagerBySql(sqlBuilder.toString(), Integer.parseInt(curPage), 5, AdInstanceShowDto.class);
		if(pager.getResult() != null && pager.getResult().size() > 0){
			@SuppressWarnings("unchecked")
			List<AdInstanceShowDto> list = (List<AdInstanceShowDto>) pager.getResult();
			for(AdInstanceShowDto adInstanceShowDto : list){
				StringBuffer dingxiangs = new StringBuffer();
				String adId = adInstanceShowDto.getAdId();
				//判断都有哪些定向 (共八个)
				List<KeyWordAdPlan> keyWordAdPlans = adCountDao.qryKeyWordAdPlans(adId);//关键词定向				
				if(keyWordAdPlans != null && keyWordAdPlans.size() > 0){
					dingxiangs.append("[关键词定向]").append(" ");
				}
				List<AdPlanPortrait> adPlanPortraits = adCountDao.qryAdPlanPortraits(adId);//人群画像定向				
				if(adPlanPortraits != null && adPlanPortraits.size() > 0){
					dingxiangs.append("[人群画像定向]").append(" ");
				}
				List<AdTimeFilterDto> adTimeFilterDtos = adCountDao.qryAdTimeFilterDtos(adId);//时间定向				
				if(adTimeFilterDtos != null && adTimeFilterDtos.size() > 0){
					dingxiangs.append("[时间定向]").append(" ");
				}
				List<AdCrowdLinkDto> adCrowdLinkDtos = adCountDao.qryAdCrowdLinkDtos(adId);//受众人群定向				
				if(adCrowdLinkDtos != null && adCrowdLinkDtos.size() > 0){
					dingxiangs.append("[受众人群定向]").append(" ");
				}
				List<AdSiteDto> adSiteDtos = adCountDao.qryAdSiteDtos(adId);//站点定向				
				if(adSiteDtos != null && adSiteDtos.size() > 0){
					dingxiangs.append("[站点定向]").append(" ");
				}
				List<AdIpTargeting> adIpTargetings = adCountDao.qryAdIpTargetings(adId);//IP定向				
				if(adIpTargetings != null && adIpTargetings.size() > 0){
					dingxiangs.append("[IP定向]").append(" ");
				}
				List<AdRegionLink> adRegionLinks = adCountDao.qryAdCountDaos(adId);//区域定向				
				if(adRegionLinks != null && adRegionLinks.size() > 0){
					dingxiangs.append("[区域定向]").append(" ");
				}				
				List<AllLabelAdPlan> allLabelAdPlans = adCountDao.qryAllLabelAdPlans(adId);//标签定向				
				if(allLabelAdPlans != null && allLabelAdPlans.size() > 0){
					dingxiangs.append("[标签定向]");
				}				
				adInstanceShowDto.setAdStrategy(dingxiangs.toString());
			}	
		}			
		return pager;
	}

	@Override
	public List<CheckHistoryDto> findTCheckHistoryByAdId(String adId) {
		
		return adCheckProcessDao.findTCheckHistoryByAdId(adId);
	}

	@Override
	public Pager getTCheckListPager(Map<String, String> map, String curPage) {
		Pager pager = null;
		StringBuffer sqlBuilder = new StringBuffer();
		sqlBuilder.append("select ai.ad_id,ai.ad_name,ai.start_time,ai.end_time,ai.ad_url,"
				         + "ai.charge_type,ai.unit_price,ai.link_type,acp.id as check_process_id,acp.check_user_id,"
				         + "acp.check_sts,acp.sts_date,atf.day_limit");
		sqlBuilder.append(" from ad_instance ai,t_ad_check_process acp,ad_time_frequency atf where 1=1");
		sqlBuilder.append(" and ai.ad_id = acp.ad_id and ai.ad_id = atf.ad_id and ai.ad_toufang_sts not in (2,3,4,5,6)");
		if(map!=null && map.size()>0){
			String checkUserId = map.get("roleId");
			String adName = map.get("adName");
			if(StringUtil.isNotEmpty(adName)){
				sqlBuilder.append(" and ai.ad_name like '%"+adName+"%'");
			}
			String checksts = map.get("checksts");
			if(StringUtil.isNotEmpty(checksts)){
				sqlBuilder.append(" and acp.check_sts ='"+checksts+"'");
			}
			String startTime = map.get("startTime");
			if(StringUtil.isNotEmpty(startTime)){
				sqlBuilder.append(" and ai.start_time >= '"+startTime+"'");
			}
			String endTime = map.get("endTime");
			if(StringUtil.isNotEmpty(endTime)){
				sqlBuilder.append(" and ai.end_time <= '"+endTime+"'");
			}
			if(StringUtil.isNotEmpty(checkUserId)){
				sqlBuilder.append(" and acp.check_user_id='"+checkUserId+"'");
			}
		}
		sqlBuilder.append(" group by ai.ad_id");
		sqlBuilder.append(" order by acp.sts_date desc");
		
		pager = pagerDao.getPagerBySql(sqlBuilder.toString(), Integer.parseInt(curPage), 5, AdInstanceCheckProcessDto.class);
		if(pager.getResult() != null && pager.getResult().size() > 0){
			@SuppressWarnings("unchecked")
			List<AdInstanceCheckProcessDto> list = (List<AdInstanceCheckProcessDto>) pager.getResult();
			for(AdInstanceCheckProcessDto adInstanceCheckProcessDto : list){
				StringBuffer dingxiangs = new StringBuffer();
				String adId = adInstanceCheckProcessDto.getAdId();
				//判断都有哪些定向 (共八个)
				List<KeyWordAdPlan> keyWordAdPlans = adCountDao.qryKeyWordAdPlans(adId);//关键词定向				
				if(keyWordAdPlans != null && keyWordAdPlans.size() > 0){
					dingxiangs.append("[关键词定向]").append(" ");
				}
				List<AdPlanPortrait> adPlanPortraits = adCountDao.qryAdPlanPortraits(adId);//人群画像定向				
				if(adPlanPortraits != null && adPlanPortraits.size() > 0){
					dingxiangs.append("[人群画像定向]").append(" ");
				}
				List<AdTimeFilterDto> adTimeFilterDtos = adCountDao.qryAdTimeFilterDtos(adId);//时间定向				
				if(adTimeFilterDtos != null && adTimeFilterDtos.size() > 0){
					dingxiangs.append("[时间定向]").append(" ");
				}
				List<AdCrowdLinkDto> adCrowdLinkDtos = adCountDao.qryAdCrowdLinkDtos(adId);//受众人群定向				
				if(adCrowdLinkDtos != null && adCrowdLinkDtos.size() > 0){
					dingxiangs.append("[受众人群定向]").append(" ");
				}
				List<AdSiteDto> adSiteDtos = adCountDao.qryAdSiteDtos(adId);//站点定向				
				if(adSiteDtos != null && adSiteDtos.size() > 0){
					dingxiangs.append("[站点定向]").append(" ");
				}
				List<AdIpTargeting> adIpTargetings = adCountDao.qryAdIpTargetings(adId);//IP定向				
				if(adIpTargetings != null && adIpTargetings.size() > 0){
					dingxiangs.append("[IP定向]").append(" ");
				}
				List<AdRegionLink> adRegionLinks = adCountDao.qryAdCountDaos(adId);//区域定向				
				if(adRegionLinks != null && adRegionLinks.size() > 0){
					dingxiangs.append("[区域定向]").append(" ");
				}				
				List<AllLabelAdPlan> allLabelAdPlans = adCountDao.qryAllLabelAdPlans(adId);//标签定向				
				if(allLabelAdPlans != null && allLabelAdPlans.size() > 0){
					dingxiangs.append("[标签定向]");
				}				
				adInstanceCheckProcessDto.setAdStrategy(dingxiangs.toString());
			}	
		}	
		return pager;
	}

	@Override
	public TAdCheckProcessDto findTAdCheckProcessDtoByPk(String adCheckProcessPk) {
		
		return adCheckProcessDao.findTAdCheckProcessDtoByPk(adCheckProcessPk);
	}

	@Override
	public int updateTAdCheckProcessInfo(TAdCheckProcessDto t_adCheckProcessDto) {
		
		return adCheckProcessDao.updateTAdCheckProcessInfo(t_adCheckProcessDto);
	}

	@Override
	public List<TAdCheckProcessDto> findTAdCheckProcessDtoByAdId(String adId) {
		
		return adCheckProcessDao.findTAdCheckProcessDtoByAdId(adId);
	}
}
