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
import com.hidata.web.service.AgentManagerService;
import com.hidata.web.util.Pager;

/**
 * 账户管理controller
 * @author xiaoming
 *
 */
@RequestMapping("/agent/*")
@Controller
public class AgentManagerController {
	
	private Logger logger = LoggerFactory.getLogger(AgentManagerController.class);
	
	@Autowired
	private AgentManagerService  agentManagerService; 
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
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AgentManagerController initIndex error ",e);
		}
		return "/agent/agent_manager";
	}
	
	/**
	 * 表格展示
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String getList(HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			String companyName = request.getParameter("companyName");
			String userName = request.getParameter("userName");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String telNbr = request.getParameter("telNbr");
			String curPage = request.getParameter("curPage");
			if(StringUtil.isEmpty(curPage)){
				curPage = "1";
			}
			Map<String,String> map = new HashMap<String,String>();
			
			map.put("companyName", companyName);
			map.put("userName", userName);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("telNbr", telNbr);
			Pager pager = agentManagerService.getPager(map,curPage);
			model.addAttribute("pager", pager);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AgentManagerController  getList  error",e);
		}
		
		return "/agent/agent_manager_list";
	}
	
	/**
	 * 添加代理商用户
	 * @param request
	 * @param response
	 * @param userDto
	 * @return
	 */
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String addAgentUser(HttpServletRequest request, HttpServletResponse response, UserDto userDto){
		try {
			agentManagerService.addAgentUser(userDto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AgentManagerController addAgentUser error",e);
		}
		return "redirect:/agent/init?navigation=zhgl_1";
	}
	
	/**
	 * 初始化编辑页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="editInit",method=RequestMethod.GET)
	public String editInit(HttpServletRequest request, HttpServletResponse response, Model model){
		model.addAttribute("jsession",request.getSession().getId());
		String userId = request.getParameter("userId");
		if(StringUtil.isNotEmpty(userId)){
			UserDto userDto = agentManagerService.getUserDtoById(userId);
			model.addAttribute("userDto", userDto);
		}
		return "/agent/agent_manager_edit";
	}
	
	/**
	 * 编辑代理商用户
	 * @param request
	 * @param response
	 * @param userDto
	 * @return
	 */
	@RequestMapping(value="edit",method=RequestMethod.POST)
	public String editAgentUser(HttpServletRequest request, HttpServletResponse response, UserDto userDto){
		try {
			agentManagerService.updateAgentUser(userDto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AgentManagerController addAgentUser error",e);
		}
		return "redirect:/agent/init?navigation=zhgl_1";
	}
	
	/**
	 * 删除代理商用户
	 * @param request
	 * @param response
	 * @param userDto
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.GET)
	public String deleteAgentUser(HttpServletRequest request, HttpServletResponse response){
		try {
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)){
				agentManagerService.deleteUserDto(userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("AgentManagerController addAgentUser error",e);
		}
		return "redirect:/agent/init?navigation=zhgl_1";
	}
	
	@RequestMapping(value="addAgent",method=RequestMethod.POST)
	public String addAgent(HttpServletRequest request,HttpServletResponse response,Model model){
		model.addAttribute("jsession",request.getSession().getId());
		return "/agent/agent_manager_add";
	}
}
