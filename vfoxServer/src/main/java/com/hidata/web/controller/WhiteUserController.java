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
import org.springframework.web.bind.annotation.ResponseBody;

import com.hidata.framework.util.StringUtil;
import com.hidata.web.service.WhiteUserService;
import com.hidata.web.util.Pager;

/**
 * 用户白名单
 * @author xiaoming
 *
 */
@Controller
@RequestMapping("/people/*")
public class WhiteUserController {
	private static Logger logger = LoggerFactory.getLogger(WhiteUserController.class);
	
	@Autowired
	private WhiteUserService whiteUserService;
	
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
			logger.error("WhiteUserController initIndex error",e);
		}
		return "/user/people_whiteList";
	}
	
	/**
	 * 获取表格信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String getList(HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			String peopleId = request.getParameter("peopleId");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			
			String curPage = request.getParameter("curPage");
			
			if(StringUtil.isEmpty(curPage)){
				curPage = "1";
			}
			
			Map<String,String> map = new HashMap<String,String>();
			
			map.put("peopleId", peopleId);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			
			Pager pager = whiteUserService.getPager(map,curPage);
			
			model.addAttribute("pager", pager);
		} catch (Exception e) {
			logger.error("WhiteUserController getList error",e); 
			e.printStackTrace();
		}
		return "/user/people_whiteList_list";
	}
	
	/**
	 * 添加
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="add",method=RequestMethod.POST)
	public String addWhiteUser(HttpServletRequest request, HttpServletResponse response, Model model){
		String userMd5Id = request.getParameter("userMd5Id");
		if(StringUtil.isNotEmpty(userMd5Id)){
			whiteUserService.add(userMd5Id);
		}
		return "redirect:/people/init?navigation=ggtfgl_2";
	}
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.GET)
	public String deleteWhiteUser(HttpServletRequest request, HttpServletResponse response, Model model){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			whiteUserService.delete(id);
		}
		return "redirect:/people/init?navigation=ggtfgl_2";
	}
	
	/**
	 * 批量删除
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/deleteAll",produces="application/json")
	@ResponseBody
	public String deleteAll(HttpServletRequest request, HttpServletResponse response, Model model){
		String ids = request.getParameter("ids");
		if(StringUtil.isNotEmpty(ids)){
			Boolean flag = whiteUserService.deleteAll(ids);
			if(flag){
				return "1";
			}
		}
		return "0";
	}
}
