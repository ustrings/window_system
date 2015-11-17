package com.hidata.ad.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hidata.ad.web.dto.AdCrowdBaseInfoDto;
import com.hidata.ad.web.dto.AdImpressLogDto;
import com.hidata.ad.web.model.AdInstance;
import com.hidata.ad.web.service.AdImpressLogService;
import com.hidata.ad.web.service.AdPlanManageService;
import com.hidata.ad.web.session.SessionContainer;
import com.hidata.framework.annotation.LoginRequired;
import com.hidata.framework.exception.AdException;
import com.hidata.framework.util.DateUtil;

/**
 * 广告曝光 action Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author sunly
 */
@Controller
@RequestMapping("/adimpresslog/*")
public class AdImpressLogController {

	@Autowired
	private AdImpressLogService adImpressLogService;
	@Autowired
	private AdPlanManageService adPlanManageService;

	private static Logger logger = LoggerFactory
			.getLogger(AdImpressLogController.class);

	@RequestMapping(value = "/list")
    @LoginRequired
	public String list(HttpServletRequest request, HttpServletResponse response,Model model) {
		 AdImpressLogDto adImpressLogDto = new AdImpressLogDto();
		String adId = request.getParameter("adId");
		String visitor_ip = request.getParameter("visitor_ip");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String isClicked = request.getParameter("isClicked");
		
		adImpressLogDto.setUserId(SessionContainer.getSession().getUserId());
		long start = System.currentTimeMillis();
		List<AdInstance> adList = adPlanManageService.findAdInstanceListByUser(adImpressLogDto.getUserId());
		long end = System.currentTimeMillis();
		logger.info("querytime："+(end-start));
		if(!StringUtils.isEmpty(adId)){
			adImpressLogDto.setAdId(Integer.valueOf(adId));
		}else{
			if(adList.size()>0){
				adImpressLogDto.setAdId(adList.get(0).getId());
			}
		}
		if(!StringUtils.isEmpty(startTime)){
			adImpressLogDto.setStartTime(startTime);
		}
		if(!StringUtils.isEmpty(endTime)){
			adImpressLogDto.setEndTime(endTime);
		}
		
		adImpressLogDto.setVisitor_ip(visitor_ip);
		adImpressLogDto.setIsClicked(isClicked);
		start = System.currentTimeMillis();
		List<AdImpressLogDto> adImpressLogDtoList= adImpressLogService.findAdImpressLogListByObj(adImpressLogDto);
		end = System.currentTimeMillis();
		System.out.println("querytime："+(end-start));
		logger.debug("querytime："+(end-start));
		model.addAttribute("adImpressLogDtoList", adImpressLogDtoList);
		model.addAttribute("adImpressLogDto", adImpressLogDto);
		model.addAttribute("adList", adList);
		model.addAttribute("isClicked", isClicked);
    	return "/adimpresslog/adimpresslog-list";
    }
	@RequestMapping(value = "/histortylist/{ad_ilog_id}")
    @LoginRequired
	public String historyList(HttpServletRequest request, HttpServletResponse response, AdCrowdBaseInfoDto adCrowdBaseInfoDto,@PathVariable int ad_ilog_id,Model model) throws AdException{
		try {
			adCrowdBaseInfoDto.setAdilogId(ad_ilog_id);
			String startTime = request.getParameter("historyStartTime");
			String endTime = request.getParameter("historyEndTime");
			String defaultTime = request.getParameter("defaultTime");
			if(!StringUtils.isEmpty(startTime)){
				startTime = startTime.replace("-", "");
				adCrowdBaseInfoDto.setHistoryStartTime(startTime);
			}
			if(!StringUtils.isEmpty(endTime)){
				endTime = endTime.replace("-", "");
				adCrowdBaseInfoDto.setHistoryEndTime(endTime);
			}
			String historyStartTime = "";
			if(!StringUtils.isEmpty(defaultTime)){
				endTime = defaultTime.replace("-", "");
				//adCrowdBaseInfoDto.setHistoryEndTime(endTime);
				Date defaultDate = DateUtil.formatToDate(defaultTime, DateUtil.C_DATE_PATTON_DEFAULT);
			
				Date beforeDate = DateUtil.getDay(defaultDate, -14);
				startTime = DateUtil.format(beforeDate, DateUtil.C_DATA_PATTON_YYYYMMDD);
				historyStartTime = DateUtil.format(beforeDate, DateUtil.C_DATE_PATTON_DEFAULT);
				//adCrowdBaseInfoDto.setHistoryStartTime(startTime);
			}
			long start = System.currentTimeMillis();
			List<AdCrowdBaseInfoDto> adCrowdBaseInfoDtoList = adImpressLogService.findAdCrowdBaseInfoDtoList(adCrowdBaseInfoDto, 500, " order by ts desc");
			model.addAttribute("adCrowdBaseInfoDtoList", adCrowdBaseInfoDtoList);
//			adCrowdBaseInfoDto.setHistoryStartTime(historyStartTime);
//			adCrowdBaseInfoDto.setHistoryEndTime(defaultTime);
			model.addAttribute("adCrowdBaseInfoDto", adCrowdBaseInfoDto);
			long end = System.currentTimeMillis();
			logger.debug("histortylist querytime："+(end-start));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return "/adimpresslog/table-template";
	}
	
	
	@RequestMapping(value = "/adAcctImp")
    @LoginRequired
	public String adAcctImp(HttpServletRequest request, HttpServletResponse response, AdCrowdBaseInfoDto adCrowdBaseInfoDto,Model model) throws AdException{
		try {
			
			String startTime = request.getParameter("adAcctImphistoryStartTime");
			String endTime = request.getParameter("adAcctImphistoryEndTime");
			
			String adId = request.getParameter("ad_id");
			String adAcct = request.getParameter("ad_acct");
			
			AdImpressLogDto adImpressLogDto = new AdImpressLogDto();
			adImpressLogDto.setAdId(Integer.parseInt(adId));
			adImpressLogDto.setAd_acct(adAcct);
			if(!StringUtils.isEmpty(startTime)){
				adImpressLogDto.setStartTime(startTime);
			}
			if(!StringUtils.isEmpty(endTime)){
				adImpressLogDto.setEndTime(endTime);
			}
			
			long start = System.currentTimeMillis();
			List<AdImpressLogDto> adImpressLogDtoList= adImpressLogService.findAdImpressLogListByAdAcct(adImpressLogDto);
			long end = System.currentTimeMillis();
			
			System.out.println("adAcctImp,querytime："+(end-start));
			logger.debug("adAcctImp,querytime："+(end-start));
			
			model.addAttribute("adImpressLogDtoList", adImpressLogDtoList);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return "/adimpresslog/adacctimp-template";
	}
	
	
}
