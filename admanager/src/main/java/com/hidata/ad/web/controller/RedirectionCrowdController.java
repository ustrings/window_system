package com.hidata.ad.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hidata.ad.web.dto.MediaCategoryDto;
import com.hidata.ad.web.dto.VisitorCrowdDto;
import com.hidata.ad.web.model.VisitorCrowd;
import com.hidata.ad.web.service.VisitorCrowdService;
import com.hidata.ad.web.session.SessionContainer;
import com.hidata.framework.annotation.LoginRequired;
import com.hidata.framework.exception.AdException;

/**
 * 重定向人群  Controller
 * @author xiaoming
 * @date 2014年5月26日
 */
@Controller
@RequestMapping("/redirection/*")
public class RedirectionCrowdController {
	@Autowired
	private VisitorCrowdService visitorSeriver;
	
	/**
	 * 查找所有用户  重定向人群
	 * @param request
	 * @param response
	 * @param crowdCalTaskDto
	 * @param model
	 * @return
	 * @throws AdException
	 */
	@RequestMapping("showlist")
	@LoginRequired
	public String showAll(HttpServletRequest request, HttpServletResponse response ,Model model) throws AdException{
		try {
			VisitorCrowd visitor = new VisitorCrowd();
			visitor.setVcUserid(SessionContainer.getSession().getUserId() + "");
			List<VisitorCrowd> visitorList = visitorSeriver.getVisitorList(visitor);
			model.addAttribute("visitorList", visitorList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/crowdredirection/crowdRed-list";
	}
	
	/**
	 * 添加重定向人群跳转
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("initadd")
	@LoginRequired
	public String initAdd(HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			List<MediaCategoryDto> list = visitorSeriver.getMediaCategoryList();
			model.addAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/crowdredirection/crowdRed-add";
	}
	
	/**
	 * 添加重定向人群
	 * @param request
	 * @param response
	 * @param model
	 * @param visitor
	 * @return
	 */
	@RequestMapping(value = "addnew", method = RequestMethod.POST)
	@LoginRequired
	public String addVisitor(HttpServletRequest request, HttpServletResponse response,  VisitorCrowdDto visitordto){
		try {
			String userid = SessionContainer.getSession().getUserId() + "";
			visitordto.setVcUserid(userid);
			visitordto.setVcSts("A");
			visitorSeriver.addVisitorCrowd(visitordto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  "redirect:/redirection/showlist";
	}
	@RequestMapping(value = "del/{vcId}", method = RequestMethod.GET)
	public String delVisitor(HttpServletRequest request, HttpServletResponse response, @PathVariable String vcId){
		try {
			String userid = SessionContainer.getSession().getUserId() + "";
			visitorSeriver.delVisitor(vcId, userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/redirection/showlist";
	}
	
	/**
	 * 编辑跳转
	 * @param request
	 * @param response
	 * @param vcId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "initEdit/{vcId}", method = RequestMethod.GET)
	public String initUpdate(HttpServletRequest request, HttpServletResponse response, @PathVariable String vcId, Model model){
			try {
				String userid = SessionContainer.getSession().getUserId() + "";
				VisitorCrowdDto visitordto = visitorSeriver.getVisotorById(vcId, userid);
				List<MediaCategoryDto> list = visitorSeriver.getMediaCategoryList();
				model.addAttribute("list",list);
				model.addAttribute("visitordto", visitordto);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "/crowdredirection/crowdRed-edit";
	}
	
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(HttpServletRequest request, HttpServletResponse response, VisitorCrowdDto vistordto){
			try {
				String userid = SessionContainer.getSession().getUserId() + "";
				vistordto.setVcUserid(userid);
				visitorSeriver.update(vistordto);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:/redirection/showlist";
	}
}
