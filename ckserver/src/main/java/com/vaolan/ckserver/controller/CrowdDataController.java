package com.vaolan.ckserver.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vaolan.ckserver.server.CrowdDataRefreshServer;

@Controller
public class CrowdDataController {
	@Autowired
	private CrowdDataRefreshServer  crowdDataRefreshServer;

	@RequestMapping(value = "/dmpuserstat")
	@ResponseBody
	public void cookieMapping(HttpServletRequest request, HttpServletResponse response,
			Model model) throws IOException {
		
		crowdDataRefreshServer.updateCrowdData(request);
	}
}
