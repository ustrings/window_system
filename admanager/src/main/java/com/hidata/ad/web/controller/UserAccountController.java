package com.hidata.ad.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hidata.ad.web.model.User;
import com.hidata.ad.web.model.UserAccount;
import com.hidata.ad.web.model.UserAccountLog;
import com.hidata.ad.web.service.UserAccountService;
import com.hidata.ad.web.session.SessionContainer;

@Controller
public class UserAccountController {

	@Autowired
	private UserAccountService userAccountService;
	
	@RequestMapping("/user/account")
	public String showAccount(HttpServletRequest request, HttpServletResponse response, Model model){
		User user = SessionContainer.getSession();
		List<UserAccountLog> acctList = userAccountService.findUserAccountLogListByUserid(user.getUserId());
		model.addAttribute("userAccounts", acctList);
		return "/userAccount/user_account_list";
	}
}
