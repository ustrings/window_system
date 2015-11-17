package com.hidata.ad.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hidata.ad.web.service.TestService;

/**
 * 支付平台页面
 * @author lihongxu
 * 2013-04-03
 */
@Controller
public class TestControllor {
	
	@Autowired
	private TestService testService;
	
	/**
	 * 测试
	 * @param request
	 * @param corderid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/test", method ={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String test(HttpServletRequest request,HttpServletResponse response){
//		String signedSecKey = MD5Util.MD5Encode(request.getParameter("secKey"), "UTF-8"); 
//		String sign = MD5Util.MD5Encode("corderid=" + request.getParameter("corderid") + "&userid="
//					+ request.getParameter("userid") + "&companyid=" + request.getParameter("companyid") + "&" + signedSecKey
//					+ "&deptid=" + request.getParameter("deptid") + "&pid=" + request.getParameter("pid") + "&productid="
//					+ request.getParameter("productid"), "UTF-8");
//		return sign;
		
		List<Map<String, Object>> list = testService.queryKeyword();
		
		for(Map<String, Object> map : list){
			for (Map.Entry<String, Object> entry :map.entrySet()){
				System.out.println(entry.getKey() +":"+entry.getValue());
			}
		}
		
		return "1";
	}
	
	@RequestMapping(value = "/check", method ={RequestMethod.POST,RequestMethod.GET})
	public String check(HttpServletRequest request,HttpServletResponse response, Model model){
		model.addAttribute("str", "hello world");
		return "/test/test";
	}
	
	@RequestMapping(value = "/blank", method ={RequestMethod.POST,RequestMethod.GET})
	public String blank(HttpServletRequest request,HttpServletResponse response, Model model){
		model.addAttribute("str", "hello world");
		return "/test/blank";
	}
	
	@RequestMapping(value = "/calendar", method ={RequestMethod.POST,RequestMethod.GET})
	public String calendar(HttpServletRequest request,HttpServletResponse response, Model model){
		model.addAttribute("str", "hello world");
		return "/test/calendar";
	}
	
	@RequestMapping(value = "/error", method ={RequestMethod.POST,RequestMethod.GET})
	public String error(HttpServletRequest request,HttpServletResponse response, Model model){
		model.addAttribute("str", "hello world");
		return "/test/error";
	}
	
	@RequestMapping(value = "/file-manager", method ={RequestMethod.POST,RequestMethod.GET})
	public String filemanager(HttpServletRequest request,HttpServletResponse response, Model model){
		model.addAttribute("str", "hello world");
		return "/test/file-manager";
	}
	
	@RequestMapping(value = "/form", method ={RequestMethod.POST,RequestMethod.GET})
	public String form(HttpServletRequest request,HttpServletResponse response, Model model){
		model.addAttribute("str", "hello world");
		return "/test/form";
	}
	
	@RequestMapping(value = "/gallery", method ={RequestMethod.POST,RequestMethod.GET})
	public String gallery(HttpServletRequest request,HttpServletResponse response, Model model){
		model.addAttribute("str", "hello world");
		return "/test/gallery";
	}
	
	@RequestMapping(value = "/grid", method ={RequestMethod.POST,RequestMethod.GET})
	public String grid(HttpServletRequest request,HttpServletResponse response, Model model){
		model.addAttribute("str", "hello world");
		return "/test/grid";
	}
	
	@RequestMapping(value = "/icon", method ={RequestMethod.POST,RequestMethod.GET})
	public String icon(HttpServletRequest request,HttpServletResponse response, Model model){
		model.addAttribute("str", "hello world");
		return "/test/icon";
	}
	
	@RequestMapping(value = "/table", method ={RequestMethod.POST,RequestMethod.GET})
	public String table(HttpServletRequest request,HttpServletResponse response, Model model){
		model.addAttribute("str", "hello world");
		return "/test/table";
	}
	
	@RequestMapping(value = "/tour", method ={RequestMethod.POST,RequestMethod.GET})
	public String tour(HttpServletRequest request,HttpServletResponse response, Model model){
		model.addAttribute("str", "hello world");
		return "/test/tour";
	}
	
	@RequestMapping(value = "/typography", method ={RequestMethod.POST,RequestMethod.GET})
	public String typography(HttpServletRequest request,HttpServletResponse response, Model model){
		model.addAttribute("str", "hello world");
		return "/test/typography";
	}
	
	
	@RequestMapping(value = "/ui", method ={RequestMethod.POST,RequestMethod.GET})
	public String ui(HttpServletRequest request,HttpServletResponse response, Model model){
		model.addAttribute("str", "hello world");
		return "/test/ui";
	}
	
	@RequestMapping(value = "/chart", method ={RequestMethod.POST,RequestMethod.GET})
	public String chart(HttpServletRequest request,HttpServletResponse response, Model model){
		return "/test/chart";
	}
}
