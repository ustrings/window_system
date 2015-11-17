package com.hidata.ad.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hidata.ad.web.model.User;
import com.hidata.ad.web.service.UserService;
import com.hidata.framework.annotation.LoginRequired;

/**
 * 用户登录
 * @author fuyangfan
 *
 */
@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String homePage(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		Object objUser = session.getAttribute("user");
		if (objUser != null){
			return "redirect:/index";
		}
		return "redirect:/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginPage(HttpServletRequest request, HttpServletResponse response, Model model){

		HttpSession session = request.getSession();
		Object objUser = session.getAttribute("user");
		if (objUser != null){
			return "redirect:/index";
		}
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null){
			for (Cookie c : cookies){
				if ("uc".endsWith(c.getName())){
					model.addAttribute("username", c.getValue());
					break;
				}
			}
		}
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response){
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remember = request.getParameter("remember");
		
		//检查用户名、密码是否正确
		if (!userService.checkUser(username, password)){
			request.setAttribute("error", "用户名或密码错误");
			return "/login";
		}
		
		User sessionUser = userService.getUser(username);
		HttpSession session = request.getSession();
		session.setAttribute("sessionUser", sessionUser);
		
		Object objUser = session.getAttribute("user");
		if (objUser == null){
			// 将用户信息存入session
			User user = new User();
			user.setUserName(username);
			user.setPassWord(password);
			session.setAttribute("user", username);
		}
		if (remember != null && "1".equals(remember)){
			Cookie cookie = new Cookie("uc", username);
			cookie.setMaxAge(60 * 60 * 24 * 30);
			response.addCookie(cookie);
		}
		return "redirect:/index";
	}
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	@LoginRequired
	public String index(HttpServletRequest request, HttpServletResponse response){
		return "redirect:/index/ad/report";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		Object objUser = session.getAttribute("user");
		// 退出时清空session
		if (objUser != null){
			session.setAttribute("user", null);
		}
		return "redirect:/login";
	}
	
	@RequestMapping(value="/checkuser", produces="application/json")
	@ResponseBody
	public Map<String, Boolean> checkUser(HttpServletRequest request, HttpServletResponse response){
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		String password = request.getParameter("pwd");
		String uname = request.getParameter("uname");
		if (userService.checkUser(uname, password)){
			map.put("result", true);
		}else{
			map.put("result", false);
			return map;
		}		
		if((userService.getUser(uname).getUsertype().equals("2")||userService.getUser(uname).getUsertype().equals("3"))&&userService.getUser(uname).getSts().equals("A")){
			map.put("resultType", true);
		}else{
			map.put("resultType", false);
		}
		return map;
	}
}
