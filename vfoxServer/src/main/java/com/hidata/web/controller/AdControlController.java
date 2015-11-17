package com.hidata.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hidata.framework.util.StringUtil;
import com.hidata.web.dto.AdControlDto;
import com.hidata.web.service.AdControlService;

/**
 * 广告投放控制设置Controller
 * @author xiaoming
 * @date 2015-1-4
 */
@Controller
@RequestMapping("/control/*")
public class AdControlController {
	private static Logger logger = LoggerFactory.getLogger(AdControlController.class);
	
	@Autowired
	private AdControlService adControlService;
	/**
	 * 初始化页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="init",method=RequestMethod.GET)
	public String initIndex(HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			AdControlDto adControlDto = adControlService.getAdControlDto();
			model.addAttribute("adControlDto", adControlDto);
		} catch (Exception e) {
			logger.error("AdControlController initIndex error",e);
			e.printStackTrace();
		}
		return "/control/put_control";
	}
	
	@RequestMapping(value="edit", method=RequestMethod.POST)
	public String editAdControlDto(HttpServletRequest request , HttpServletResponse response, Model model){
		String frequencyDay = request.getParameter("frequencyDay");
		String spacingMin = request.getParameter("spacingMin");
		String pvTotal = request.getParameter("pvTotal");
		try {
			AdControlDto adControlDto = new AdControlDto();
			if(StringUtil.isEmpty(frequencyDay)){
				frequencyDay = "";
			}
			adControlDto.setFrequencyDay(frequencyDay);
			
			if(StringUtil.isEmpty(spacingMin)){
				spacingMin = "";
			}
			adControlDto.setSpacingMin(spacingMin);
			
			if(StringUtil.isEmpty(pvTotal)){
				pvTotal = "";
			}
			adControlDto.setPvTotal(pvTotal);
			
			adControlService.editAdControl(adControlDto);
		} catch (Exception e) {
			logger.error("AdControlController editAdControlDto error" , e);
			e.printStackTrace();
		}
		
		return "redirect:/control/init?navigation=ggtfgl_3";
	}
}
