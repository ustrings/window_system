package com.hidata.web.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.hidata.web.dto.UserDto;
import com.hidata.web.service.ManagerService;
import com.hidata.web.util.Pager;

/**
 * 管理员账户管理
 * @author xiaoming
 * @date 2014-12-29
 */
@Controller
@RequestMapping("/system/*")
public class ManagerController {
	
	private Logger logger = LoggerFactory.getLogger(ManagerController.class);
	
	@Autowired
	private ManagerService managerService;
	
	
	
	/**
	 * 初始化页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="init", method=RequestMethod.GET)
	public String initIndex(HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ManagerController initIndex error",e);
		}
		return "/manager/sys_agent_manager";
	}
	
	/**
	 * 表格详细数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="list", method=RequestMethod.POST)
	public String getList(HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			String curPage = request.getParameter("curPage");
			if(StringUtil.isEmpty(curPage)){
				curPage = "1";
			}
			String userName = request.getParameter("userName");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			
			Map<String,String> map = new HashMap<String,String>();
			
			map.put("userName", userName);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			
			Pager pager = managerService.getPager(map, curPage);
			model.addAttribute("pager", pager);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ManagerController getList error", e);
		}
		return "/manager/sys_agent_manager_list";
	}
	
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String addManagerUserDto(HttpServletRequest request, HttpServletResponse response, UserDto userDto){
		try {
			managerService.addManagerUserDto(userDto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ManagerController addManagerUserDto error",e);
		}
		return "redirect:/system/init?navigation=zhgl_2";
	}
	
	@RequestMapping(value="delete",method=RequestMethod.GET)
	public String deleteManagerUserDto(HttpServletResponse response, HttpServletRequest request){
		try {
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)){
				managerService.deleteManagerUserDto(userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ManagerController deleteManagerUserDto error",e);
		}
		return "redirect:/system/init?navigation=zhgl_2";
	}
}
