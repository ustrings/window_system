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

import com.hidata.framework.util.StringUtil;
import com.hidata.web.dto.AdExtLinkDto;
import com.hidata.web.dto.AdInstanceDto;
import com.hidata.web.dto.AdInstanceShowDto;
import com.hidata.web.dto.CheckHistoryDto;
import com.hidata.web.model.User;
import com.hidata.web.service.AdCheckProcessService;
import com.hidata.web.service.UserService;
import com.hidata.web.util.Pager;

/**
 * 审核进度查看
 * @author ssq
 *
 */
@Controller
@RequestMapping("/checkProgress/*")
public class CheckProgressController {

	private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private AdCheckProcessService adCheckProcessService;
    
    @Autowired
    private UserService userService;
    
    /**
     * 审核进度查看页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/init",method=RequestMethod.GET)
    public String initIndex(HttpServletRequest request,HttpServletResponse response){
    	
    	return "/check/check_history";
    }
    
    /**
     * 广告审核历史展示
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/qryCheckHistoryPageList", method = RequestMethod.POST)
    public String qryCheckHistoryPageList(HttpServletRequest request, HttpServletResponse response, Model model) {
    	try{
    	   User user = (User)request.getSession().getAttribute("user");
    	   String adName = request.getParameter("adName");
      	   String checksts = request.getParameter("checksts");
      	   String startTime = request.getParameter("startTime");
      	   String endTime = request.getParameter("endTime");
      	   String curPage = request.getParameter("curPage");     	         	 
      	      	   
      	   if(StringUtil.isEmpty(curPage)) {
  			  curPage = "1";
  		   }
  		   Map<String,String> map = new HashMap<String,String>();
  		   map.put("adName",adName );
  		   map.put("checksts",checksts);
  		   map.put("startTime",startTime);
  		   map.put("endTime",endTime);
  		   Pager pager = null; 
  		   if("1".equals(userService.getUser(user.getUserName()).getType())){
  			  pager = adCheckProcessService.getCheckHistoryPager(map,curPage);
  		   }else if("3".equals(userService.getUser(user.getUserName()).getType())){
  			  pager = adCheckProcessService.getTCheckHistoryPager(map,curPage);
  		   }	   
  		   List<AdInstanceShowDto> pagerResult =(List<AdInstanceShowDto>) pager.getResult();
		   for(int i=0;i<pagerResult.size();i++){
			  AdInstanceShowDto adInstanceShowDto = pagerResult.get(i);
			  String linkType = adInstanceShowDto.getLinkType();
			  if(linkType.equals("E")){
				  String adInstanceId = adInstanceShowDto.getAdId();
				  AdExtLinkDto adExtLink = adCheckProcessService.findAdExtLinkByAdInstanceId(adInstanceId);
			      String throwUrl = adExtLink.getThrowUrl();
			      adInstanceShowDto.setAdUrl(throwUrl);
			  }
		   }
  		   model.addAttribute("pager",pager);
    	}catch(Exception e){
    	   logger.error("CheckProgressController qryCheckHistoryPageList error",e);
    	   e.printStackTrace();
    	}
    	return "/check/check_history_page_list";
    }
    
    /**
     * 各个审核人的审核历史 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/checkHistoryList",method=RequestMethod.POST)
    public String checkHistoryList(HttpServletRequest request, HttpServletResponse response, Model model){
    	User user = (User)request.getSession().getAttribute("user");
    	String adId = request.getParameter("adId");
    	if("1".equals(userService.getUser(user.getUserName()).getType())){
    		List<CheckHistoryDto> adCheckHistory = adCheckProcessService.findCheckHistoryByAdId(adId);
        	model.addAttribute("adCheckHistory",adCheckHistory);
    	}else if("3".equals(userService.getUser(user.getUserName()).getType())){
    		List<CheckHistoryDto> adCheckHistory = adCheckProcessService.findTCheckHistoryByAdId(adId);
        	model.addAttribute("adCheckHistory",adCheckHistory);
    	}
    	return "/check/check_history_role_list";
    }
    
    /**
     * js代码展示
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="jsShow", method = RequestMethod.GET)
	 public String showJsCodeAd(HttpServletRequest request,HttpServletResponse response,Model model){
		 	String adId = request.getParameter("adId");
	    	if(StringUtil.isNotEmpty(adId)){
	    		AdExtLinkDto adExtLink =  adCheckProcessService.findAdExtLinkByAdInstanceId(adId);
	        	model.addAttribute("adExtLink", adExtLink);
	    	}
	    	return "/html/jsCode";
	  }
}
