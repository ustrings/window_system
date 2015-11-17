package com.hidata.web.controller;

import java.util.HashMap;
import java.util.List;
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
import com.hidata.web.dto.AdExtLinkDto;
import com.hidata.web.dto.AdTouFangStsDto;
import com.hidata.web.model.User;
import com.hidata.web.service.AdCountService;
import com.hidata.web.service.AdTouFangStsService;
import com.hidata.web.service.UserService;
import com.hidata.web.util.Pager;

/**
 * 广告统计列表Controller
 * @author xiaoming
 * @date 2014-12-24
 */
@Controller
@RequestMapping("/ad_count/*")
public class AdCountController {
	
	private static Logger logger = LoggerFactory.getLogger(AdCountController.class);
	
	@Autowired
	private AdTouFangStsService adTouFangStsService;
	
	@Autowired
	private AdCountService adCountService;
	
	@Autowired
	private UserService userService;
	
	
	/**
	 * 广告统计列表查看初始化页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="init", method=RequestMethod.GET)
	public String initIndex(HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			List<AdTouFangStsDto> list = adTouFangStsService.getAllLists();
			model.addAttribute("list", list);
		} catch (Exception e) {
			logger.error("AdCountController initIndex error",e);
		}
		return "/ad/ad_count";
	}
	
	/**
	 * 广告计划列表查看表格内容
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="list", method=RequestMethod.POST)
	public String getList(HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			String keyword = request.getParameter("keyword");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String adTFsts = request.getParameter("adTFsts");
			String curPage = request.getParameter("curPage");
			if(StringUtil.isEmpty(curPage)) {
				curPage = "1";
			}
			Map<String,String> map = new HashMap<String,String>();
			
			map.put("keyword", keyword);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("adTFsts", adTFsts);
			Pager pager = null;
			User user = (User)request.getSession().getAttribute("user");
	 		if("1".equals(userService.getUser(user.getUserName()).getType())){
	 			pager = adCountService.getPager(map,curPage);
	 		}else if("3".equals(userService.getUser(user.getUserName()).getType())){
	 			pager = adCountService.getTPager(map,curPage);
	 		}		
			model.addAttribute("pager",pager);
		} catch (Exception e) {
			logger.error("AdCountController getList error",e);
			e.printStackTrace();
		}
		return "/ad/ad_count_list";
	}
	
	/**
	 * 暂停、开启、停止
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="updateSts", produces="application/json")
	@ResponseBody
	public String updateSts(HttpServletRequest request, HttpServletResponse response, Model model){
		String sts = request.getParameter("sts");
		String adId = request.getParameter("adId");
		if(StringUtil.isEmpty(sts)){
			return "0";
		}
		if(StringUtil.isEmpty(adId)){
			return "0";
		}
		Boolean flag = adCountService.updateSts(sts,adId);
		if(flag){
			/*try {
				URL url = new URL("http://118.26.145.20:8060/reload");  
				URLConnection urlcon = url.openConnection();  
				int i = urlcon.getContentLength();  
				if (i > 0) {  
				    InputStream is = urlcon.getInputStream();  
				    int a =  is.read();
				    is.close();  
				    if((char) a == '1'){
						return "1";
					}
				} else {  
				    System.out.println("响应内容为空...");  
				}
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}  */
			return "1";
			
		}
		return "0";
	}
	
	 @RequestMapping(value="jsShow", method = RequestMethod.GET)
	 public String showJsCodeAd(HttpServletRequest request,HttpServletResponse response,Model model){
		 	String adId = request.getParameter("adId");
	    	if(StringUtil.isNotEmpty(adId)){
	    		AdExtLinkDto adExtLink =  adCountService.getAdExtLinkByAdId(adId);
	        	model.addAttribute("adExtLink", adExtLink);
	    	}
	    	return "/html/jsCode";
	  }
	 
}
