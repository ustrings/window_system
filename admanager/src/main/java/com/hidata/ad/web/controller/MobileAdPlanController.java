package com.hidata.ad.web.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hidata.ad.web.dto.AdCategoryDto;
import com.hidata.ad.web.dto.mongo.AdApp;
import com.hidata.ad.web.dto.mongo.AdClick;
import com.hidata.ad.web.dto.mongo.AdDevice;
import com.hidata.ad.web.dto.mongo.AdIndustry;
import com.hidata.ad.web.dto.mongo.AdType;
import com.hidata.ad.web.dto.mongo.MobileAdPlanManageInfoDto;
import com.hidata.ad.web.model.AdInstance;
import com.hidata.ad.web.model.AdMaterial;
import com.hidata.ad.web.service.AdMaterialService;
import com.hidata.ad.web.service.AdPlanManageService;
import com.hidata.ad.web.service.CrowdService;
import com.hidata.ad.web.service.MobileAdPlanManageService;
import com.hidata.ad.web.service.TerminalBaseInfoService;
import com.hidata.ad.web.service.VisitorCrowdService;
import com.hidata.ad.web.session.SessionContainer;
import com.hidata.framework.annotation.LoginRequired;
import com.hidata.framework.exception.AdException;

/**
 * 广告计划制定主action Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhangtengda
 */
@Controller
public class MobileAdPlanController {
	//重定向人群 Service
	@Autowired
	private VisitorCrowdService visitorservice;

	@Autowired
	private MobileAdPlanManageService mobileAdPlanManageService;
	@Autowired
	private AdPlanManageService adPlanManageService;

	@Autowired
	private CrowdService crowdService;
	
	@Autowired
	private AdMaterialService adMaterialService;
	
	//终端重定向
	@Autowired
	private TerminalBaseInfoService terminalService;

	private static Logger logger = LoggerFactory
			.getLogger(MobileAdPlanController.class);

	/**
	 * 广告计划新增页面初始化
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author zhoubin
	 * @throws AdException
	 */
	@RequestMapping("/mobileadplan/initadd")
	public String adPlanInitAdd(HttpServletRequest request,
			HttpServletResponse response, Model model) throws AdException {
		try {
			List<AdIndustry> adIndustries = mobileAdPlanManageService.getAdIndustryList();
			List<AdType> adTypes = mobileAdPlanManageService.getAdTypeList();
			List<AdApp> adApps = mobileAdPlanManageService.getAdAppList();
			List<AdDevice> adDevices = mobileAdPlanManageService.getAdDeviceList();
			List<AdClick> adClicks = mobileAdPlanManageService.getAdClickList();
			
			List<AdCategoryDto> adCategoryDtoLeveOneList = adPlanManageService.findAdCategoryDtoListByParentCode("-1");
			model.addAttribute("adCategoryDtoLeveOneList", adCategoryDtoLeveOneList);
			
			model.addAttribute("adIndustries", adIndustries);
			model.addAttribute("adTypes", adTypes);
			model.addAttribute("adApps", adApps);
			model.addAttribute("adDevices", adDevices);
			model.addAttribute("adClicks", adClicks);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询基础配置信息失败 ！！" + e, e);
		}
		return "mobileadplan/mobileAdplanAdd";
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @author chenjinzhao
	 * @throws AdException
	 */
	@RequestMapping("/mobileadplan/initedit/{adId}")
	public String adPlanInitEdit(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String adId, Model model) {

		MobileAdPlanManageInfoDto mobileAdPlanManageInfo = mobileAdPlanManageService.getOneAdplanInfo(adId);
		// 广告行业分类

		List<AdApp> adApps =mobileAdPlanManageService.getAdAppListByAdId(adId);
		List<AdApp> allAdApps =mobileAdPlanManageService.getAdAppList();

		// 把已经选中的移除
		for (AdApp adApp : adApps) {
			String id = adApp.getId();
			Iterator<AdApp> allAdAppsIt = allAdApps.iterator();
			while (allAdAppsIt.hasNext()) {
				AdApp allAdApp  = allAdAppsIt.next();
				if (allAdApp.getId().equals(id)) {
					allAdAppsIt.remove();
				}
			}
		}

		List<AdMaterial> materialList = adMaterialService.findAdMaterialListByAdId(Integer.valueOf(adId));
		List<AdDevice> adDevices = mobileAdPlanManageService.getAdDeviceList();
		List<AdClick> adClicks = mobileAdPlanManageService.getAdClickList();
		List<AdType> adTypes  = mobileAdPlanManageService.getAdTypeList();

		List<AdCategoryDto> adCategoryDtoLeveOneList = adPlanManageService.findAdCategoryDtoListByParentCode("-1");
		List<AdCategoryDto> adCategoryDtoList = adPlanManageService.findAdCategoryDtoListByAdId(adId);//已选广告分类
		model.addAttribute("adCategoryDtoLeveOneList", adCategoryDtoLeveOneList);
		model.addAttribute("adCategoryDtoList", adCategoryDtoList);
		
		model.addAttribute("mobileAdPlanManageInfo", mobileAdPlanManageInfo);
		model.addAttribute("materialList", materialList);
		model.addAttribute("adApps", adApps);
		model.addAttribute("allAdApps", allAdApps);
		model.addAttribute("adDevices", adDevices);
		model.addAttribute("adClicks", adClicks);
		model.addAttribute("adTypes", adTypes);
		
		return "mobileadplan/mobileAdplanEdit";
	}

	/**
	 * 广告计划新增
	 * 
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @author zhoubin
	 * @throws AdException
	 */
	@RequestMapping(value = "/mobileadplan/add", method = RequestMethod.POST)
	@LoginRequired
	public String addAdPlan(HttpServletRequest request,
			HttpServletResponse response,
			MobileAdPlanManageInfoDto mobileAdPlanManageInfoDto) throws AdException {

		try {
			mobileAdPlanManageService.addAdPlan(mobileAdPlanManageInfoDto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("add adplan失败 ！！" + e, e);
			throw new AdException("add adplan失败 ！！", e);
		}
		return "redirect:/mobileadplan/list";
	}
	
	
	@RequestMapping(value = "/mobileadplan/adplaneditsaveaction/{adId}", method = RequestMethod.POST)
	@LoginRequired
	public String adPlanEditSave(HttpServletRequest request,
			HttpServletResponse response,
			MobileAdPlanManageInfoDto mobileAdPlanManageInfoDto,@PathVariable String adId) throws AdException {
		try {
			mobileAdPlanManageService.editAdPlan(mobileAdPlanManageInfoDto, adId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("edit adplan失败 ！！" + e, e);
			throw new AdException("edit adplan失败 ！！", e);
		}
		return "redirect:/mobileadplan/list";
	}


	 /**
     * 查询广告计划
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/mobileadplan/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
    	List<AdInstance> adList = mobileAdPlanManageService.findAdInstanceListByUser(SessionContainer.getSession().getUserId());
    	model.addAttribute("adList", adList);
    	return "/mobileadplan/mobileadplan-list";
    }
    
    
    /**
     * 删除广告计划
     * @param request
     * @param response
     * @param id
     * @return
     */
    @RequestMapping(value = "/mobileadplan/delete/{id}", method = RequestMethod.GET)
    public String del(HttpServletRequest request, HttpServletResponse response, @PathVariable int id) {
    	mobileAdPlanManageService.updateAdInstanceByIdAndUser(SessionContainer.getSession().getUserId(), id);
    	return "redirect:/mobileadplan/list";
    }
    
    @RequestMapping(value = "/mobileadplan/getSubMediaCateNum/{code}", method = RequestMethod.GET)
    @ResponseBody
    public String  getSubMediaCateNumByCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String code, Model model) {
    	int mediaCateTotalNum = 0;
    	return String.valueOf(mediaCateTotalNum);
    }
    
    @RequestMapping(value = "/mobileadplan/adCategoryLeveTwo-template/{code}", method = RequestMethod.GET)
    public String adCategoryLeveTwolist(HttpServletRequest request, HttpServletResponse response, @PathVariable String code, Model model) {
    	return "/mobileadplan/adCateLeveTwo-template";
    } 
}
