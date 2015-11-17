package com.hidata.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hidata.framework.util.StringUtil;
import com.hidata.web.dto.AdMonthsDto;
import com.hidata.web.dto.AdReportDetailDto;
import com.hidata.web.dto.AdTouFangStsDto;
import com.hidata.web.dto.UserDto;
import com.hidata.web.model.User;
import com.hidata.web.model.ViewExcel;
import com.hidata.web.service.AdReportService;
import com.hidata.web.service.AdTouFangStsService;
import com.hidata.web.service.UserService;
import com.hidata.web.util.Pager;



/**
 * 广告统计管理Controller
 * @author xiaoming
 * @date 2014-12-22
 */
@Controller
@RequestMapping("/report/*")
public class AdReportController {
	private static Logger logger = LoggerFactory.getLogger(AdReportController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdTouFangStsService adTouFangStsService;
	
	@Autowired
	private AdReportService adReportService;
	/**
	 * 初始化页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="init", method=RequestMethod.GET)
	public String initIndex (HttpServletRequest request,HttpServletResponse response, Model model){
		try {
			String userType = "2";//代理商账号
			List<UserDto> list = userService.getUsersByType(userType);
			model.addAttribute("list", list);
			
			List<AdTouFangStsDto> tfList = adTouFangStsService.getAllLists();//投放状态
			model.addAttribute("tfList", tfList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AdReportController, initIndex error",e);
		}
		return "/report/put_report";
	}
	
	/**
	 * 展示表格内容
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String getLists(HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			String curPage = request.getParameter("curPage");
			if(StringUtil.isEmpty(curPage)) {
				curPage = "1";
			}
			String keyword = request.getParameter("keyword");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endtime");
			String userId = request.getParameter("userId");
			String stsTF = request.getParameter("stsTF");
			String linkType = request.getParameter("linkType");
			
			Map<String,String> map = new HashMap<String,String>();
			
			map.put("keyword", keyword);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("userId", userId);
			map.put("stsTF", stsTF);
			map.put("linkType", linkType);
			
			Pager pager = null;
			User user = (User)request.getSession().getAttribute("user");
	 		if("1".equals(userService.getUser(user.getUserName()).getType())){
	 			pager = adReportService.getPager(map, curPage);
	 		}else if("3".equals(userService.getUser(user.getUserName()).getType())){
	 			pager = adReportService.getTPager(map, curPage);
	 		}			
			model.addAttribute("pager", pager);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AdReportController, getLists error",e);
		}
		return "/report/put_report_list";
	}
	
	/**
	 * 广告投放详情
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="month",method=RequestMethod.GET)
	public String getDetailByMonthAndAdId(HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			String adId = request.getParameter("adId");
			String month = request.getParameter("month");
			if(StringUtil.isNotEmpty(adId)){
				// 获取所有月份
				List<AdMonthsDto> months = adReportService.getMonthsByadId(adId);
				model.addAttribute("months", months);
				if(months != null && months.size() > 0 && StringUtil.isEmpty(month)){
					AdMonthsDto adMonth = months.get(0);
					month = adMonth.getYuefen();
				}
				List<AdReportDetailDto> list = adReportService.getReportDetailsByMonthAndId(adId, month);
				model.addAttribute("list", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/report/put_report_detail";
	}
	
	@RequestMapping(value="upload",method=RequestMethod.POST)
	public ModelAndView exportExcel(ModelMap model, HttpServletRequest request, HttpServletResponse response) {   
		try {
			String adIds = request.getParameter("adIds");
			String type = request.getParameter("type");
			if(StringUtil.isEmpty(type) || StringUtil.isEmpty(adIds)){
				return null;
			}
			ViewExcel viewExcel = new ViewExcel();
			Map<String,String> map = new HashMap<String, String>();
			map.put("adIds", adIds);
			map.put("type", type);
			HSSFWorkbook workbook = adReportService.getHWB(map);  
			viewExcel.buildExcelDocument(null, workbook, request, response);
			return new ModelAndView(viewExcel, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	
	}
}
