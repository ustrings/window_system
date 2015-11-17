package com.hidata.ad.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hidata.ad.web.dto.AdCrowdBaseInfoDto;
import com.hidata.ad.web.dto.AdCrowdDetailDto;
import com.hidata.ad.web.model.AdInstance;
import com.hidata.ad.web.model.Crowd;
import com.hidata.ad.web.service.AdCrowdDetailService;
import com.hidata.ad.web.service.AdPlanManageService;
import com.hidata.ad.web.session.SessionContainer;
import com.hidata.framework.annotation.LoginRequired;
import com.hidata.framework.exception.AdException;

/**
 * 广告人群明细 action Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author sunly
 */
@Controller
@RequestMapping("/adCrowdDetail/*")
public class AdCrowdDetailController {

	@Autowired
	private AdPlanManageService adPlanManageService;
	
	@Autowired
	private AdCrowdDetailService adCrowdDetailService;

	private static Logger logger = LoggerFactory
			.getLogger(AdCrowdDetailController.class);

	@Deprecated
	@RequestMapping(value = "/oldlist")
    @LoginRequired
	public String list(HttpServletRequest request, HttpServletResponse response,AdCrowdDetailDto adCrowdDetailDto,Model model) {
		
		adCrowdDetailDto.setUserId(SessionContainer.getSession().getUserId());
		List<AdInstance> adList = adPlanManageService.findAdInstanceListByUser(adCrowdDetailDto.getUserId());
		List<Crowd> crowdList = null;
		if(!StringUtils.isEmpty(adCrowdDetailDto.getAdId())){
			for(int i=0;i<adList.size();i++){
				AdInstance obj =adList.get(i);
				if(obj.getId().equals(adCrowdDetailDto.getAdId())){
					adCrowdDetailDto.setAdName(obj.getAdName());
					break;
				}
			}
			crowdList = adCrowdDetailService.getAdCrowdsByAdId(String.valueOf(adCrowdDetailDto.getAdId()));
		}else{
			if(adList.size()>0){
				adCrowdDetailDto.setAdId(adList.get(0).getId());
				adCrowdDetailDto.setAdName(adList.get(0).getAdName());
				crowdList = adCrowdDetailService.getAdCrowdsByAdId(String.valueOf(adList.get(0).getId()));
			}
		}
		String _startTime = "";
		String _endTime = "";
		
		if(!StringUtils.isEmpty(adCrowdDetailDto.getStartTime())){
			_startTime = adCrowdDetailDto.getStartTime();
			adCrowdDetailDto.setStartTime(adCrowdDetailDto.getStartTime().replace("-", ""));
		}
		if(!StringUtils.isEmpty(adCrowdDetailDto.getEndTime())){
			_endTime = adCrowdDetailDto.getEndTime();
			adCrowdDetailDto.setEndTime(adCrowdDetailDto.getEndTime().replace("-", ""));
		}
		long startime = System.currentTimeMillis();
		List<AdCrowdDetailDto> adCrowdDetailDtoList = adCrowdDetailService.queryAdCrowdDetailListByCondition(adCrowdDetailDto, crowdList,500, " order by dt desc");
		long endtime = System.currentTimeMillis();
		System.out.println("queryAdCrowdDetailDto time:"+(endtime-startime));
		model.addAttribute("adCrowdDetailDtoList", adCrowdDetailDtoList);
		adCrowdDetailDto.setStartTime(_startTime);
		adCrowdDetailDto.setEndTime(_endTime);
		
		model.addAttribute("adCrowdDetailDto", adCrowdDetailDto);
		model.addAttribute("adList", adList);
		model.addAttribute("crowdList", crowdList);
    	return "/adcrowddetail/adCrowdDetail-list";
    }

	
	@RequestMapping(value = "/getAdCrowds/{adId}")
    @LoginRequired
	public String getAdCrowds(HttpServletRequest request, HttpServletResponse response, @PathVariable String adId,Model model) {
		List<Crowd> crowdList = adCrowdDetailService.getAdCrowdsByAdId(adId);
		model.addAttribute("crowdList", crowdList);
		return "/adcrowddetail/crowd-option-template";
	}
	@RequestMapping(value = "/viewlist/{crowId}")
	public String viewlist(HttpServletRequest request, HttpServletResponse response, @PathVariable String crowId,AdCrowdBaseInfoDto adCrowdBaseInfoDto,Model model) throws AdException{
		
		try {
			adCrowdBaseInfoDto.setCrowd_id(crowId);
			if(adCrowdBaseInfoDto.getHistoryStartTime()!=null){
				adCrowdBaseInfoDto.setHistoryStartTime(adCrowdBaseInfoDto.getHistoryStartTime().replace("-", ""));
			}
			if(adCrowdBaseInfoDto.getHistoryEndTime()!=null){
				adCrowdBaseInfoDto.setHistoryEndTime(adCrowdBaseInfoDto.getHistoryEndTime().replace("-", ""));
			}
			List<AdCrowdBaseInfoDto>  adCrowdBaseInfoDtoList = adCrowdDetailService.queryAdCrowdBaseInfoDtoListByCrowId(adCrowdBaseInfoDto, 500, " order by ts desc");
			model.addAttribute("adCrowdBaseInfoDtoList", adCrowdBaseInfoDtoList);
			
		} catch (Exception e) {
			logger.error("AdCrowdDetailController viewlist error",e);
			e.printStackTrace();
		}
		return "/adcrowddetail/table-template";
	}
	
	@RequestMapping(value = "/list")
    @LoginRequired
	public String list(HttpServletRequest request, HttpServletResponse response, AdCrowdBaseInfoDto adCrowdBaseInfoDto,Model model) throws AdException{
		
		try {
			AdCrowdBaseInfoDto _adCrowdBaseInfoDto = new AdCrowdBaseInfoDto();
		
			List<Crowd> crowdList = adCrowdDetailService.getCrowdByUserId(SessionContainer.getSession().getUserId());
			if(StringUtils.isEmpty(adCrowdBaseInfoDto.getCrowd_id())){
				if(crowdList!=null&&crowdList.size()>0){
					adCrowdBaseInfoDto.setCrowd_id(crowdList.get(0).getCrowdId());
				}
			}
			
			BeanUtils.copyProperties(adCrowdBaseInfoDto, _adCrowdBaseInfoDto);
			if(adCrowdBaseInfoDto.getHistoryStartTime()!=null){
				adCrowdBaseInfoDto.setHistoryStartTime(adCrowdBaseInfoDto.getHistoryStartTime().replace("-", ""));
			}
			if(adCrowdBaseInfoDto.getHistoryEndTime()!=null){
				adCrowdBaseInfoDto.setHistoryEndTime(adCrowdBaseInfoDto.getHistoryEndTime().replace("-", ""));
			}
			List<AdCrowdBaseInfoDto>  adCrowdBaseInfoDtoList = adCrowdDetailService.queryAdCrowdBaseInfoDtoListByCondition(adCrowdBaseInfoDto, 500, " order by ts desc");
			
			model.addAttribute("adCrowdBaseInfoDto", _adCrowdBaseInfoDto);
			model.addAttribute("crowdList", crowdList);
			model.addAttribute("adCrowdBaseInfoDtoList", adCrowdBaseInfoDtoList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/adcrowddetail/adCrowdDetail-list";
	}
	
}
