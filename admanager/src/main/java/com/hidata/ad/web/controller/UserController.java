package com.hidata.ad.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hidata.ad.web.model.User;
import com.hidata.ad.web.service.UserService;
import com.hidata.ad.web.session.SessionContainer;
import com.hidata.framework.annotation.LoginRequired;

@Controller
@RequestMapping("/user/*")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/changePassword", method=RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		Object objUser = session.getAttribute("user");
		if (objUser != null){
			return "/user/user-edit";
		}
		return "redirect:/login";
	}
	
	@RequestMapping(value="/edit")
	@ResponseBody
	@LoginRequired
	public String editUserPassword(HttpServletRequest request, HttpServletResponse response){
		User objUser = 	SessionContainer.getSession();
	
		String oldpassword = request.getParameter("oldpassword");
		String newpassword = request.getParameter("newpassword");
		JSONObject jsonObj = new JSONObject();
		int ret = -1;
		String msg = "";
		if (objUser != null){
			boolean flag = userService.checkUser(objUser.getUserName(), oldpassword);
			if(flag){
				objUser.setPassWord(newpassword);
				ret = userService.editUser(objUser);
			}else{
				// 密码错误
				msg="原始密码错误";
			}
		}else{
			msg="登录超时";
		}
		jsonObj.put("result", ret);
		jsonObj.put("msg", msg);
		return jsonObj.toString();
	}
}
