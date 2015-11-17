package com.hidata.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hidata.framework.util.DateUtil;
import com.hidata.framework.util.StringUtil;
import com.hidata.web.dto.AdCheckConfigDto;
import com.hidata.web.dto.AdCheckProcessDto;
import com.hidata.web.dto.AdExtLinkDto;
import com.hidata.web.dto.AdInstanceCheckProcessDto;
import com.hidata.web.dto.AdInstanceDto;
import com.hidata.web.dto.TAdCheckConfigDto;
import com.hidata.web.dto.TAdCheckProcessDto;
import com.hidata.web.model.User;
import com.hidata.web.service.AdCheckProcessService;
import com.hidata.web.service.UserService;
import com.hidata.web.util.Pager;


/**
 * 待审核管理
 * @author ssq
 *
 */
@Controller
@RequestMapping("/check/*")
public class CheckManageController {
	
	private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private AdCheckProcessService adCheckProcessService;
    
    @Autowired
    private UserService userService;
	
    /**
     * 初始页面
     * @param request
     * @param response
     * @param model
     * @return
     */
	@RequestMapping(value="/init",method=RequestMethod.GET)
    public String initIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
        
		HttpSession session = request.getSession();
  	    User user = (User) session.getAttribute("user");
  	    String userAcctRel = user.getUserName();
  	    if("1".equals(userService.getUser(user.getUserName()).getType())){
  	    	List<AdCheckConfigDto> adCheckConfig = adCheckProcessService.findAdCheckConfigDtoByUserAcctRel(userAcctRel);
  	  	    String roleId ="";
  	  	    if(adCheckConfig.size()>0){
  	  	    	roleId = adCheckConfig.get(0).getId();	     	  	    
  	  	    } 	  
  	  	    model.addAttribute("checkRoleId",roleId);
  	    }else if("3".equals(userService.getUser(user.getUserName()).getType())){
  	    	List<TAdCheckConfigDto> adCheckConfig = adCheckProcessService.findTAdCheckConfigDtoByUserAcctRel(userAcctRel);
  	  	    String roleId ="";
  	  	    if(adCheckConfig.size()>0){
  	  	    	roleId = adCheckConfig.get(0).getId();	     	  	    
  	  	    } 	  
  	  	    model.addAttribute("checkRoleId",roleId);
  	    }    
        return "/check/check_soon";
    } 
	
	/**
     * 分页查找相关信息
     * @param request
     * @param response
     * @param model
     * @return
     * @author ssq
     */
    @RequestMapping(value = "/qryCheckPageList", method = RequestMethod.POST)
    public String qryRolePageList(HttpServletRequest request, HttpServletResponse response, Model model) {
    	try{
           String adName = request.getParameter("adName");
     	   String checksts = request.getParameter("checksts");
     	   String startTime = request.getParameter("startTime");
     	   String endTime = request.getParameter("endTime");
     	   String curPage = request.getParameter("curPage");
     	   
     	   String roleId = request.getParameter("roleId");
     	      	   
     	   if(StringUtil.isEmpty(curPage)) {
 			  curPage = "1";
 		   }
 		   Map<String,String> map = new HashMap<String,String>();
 		   map.put("roleId", roleId);
 		   map.put("adName",adName );
 		   map.put("checksts",checksts);
 		   map.put("startTime",startTime);
 		   map.put("endTime",endTime);
 		   
 		  Pager pager = null;
 		  User user = (User)request.getSession().getAttribute("user");
 		  if("1".equals(userService.getUser(user.getUserName()).getType())){
 			 pager = adCheckProcessService.getCheckListPager(map,curPage);
 		  }else  if("3".equals(userService.getUser(user.getUserName()).getType())){
 			 pager = adCheckProcessService.getTCheckListPager(map,curPage);
 		  } 		  
 		   List<AdInstanceCheckProcessDto> pagerResult =(List<AdInstanceCheckProcessDto>) pager.getResult();
 		   for(int i=0;i<pagerResult.size();i++){
 			  AdInstanceCheckProcessDto adInstanceCheckProcessDto = pagerResult.get(i);
 			  String linkType = adInstanceCheckProcessDto.getLinkType();
 			  if(linkType.equals("E")){
 				  String adInstanceId = adInstanceCheckProcessDto.getAdId();
 				  AdExtLinkDto adExtLink = adCheckProcessService.findAdExtLinkByAdInstanceId(adInstanceId);
 			      String throwUrl = adExtLink.getThrowUrl();
 			      adInstanceCheckProcessDto.setThrowUrl(throwUrl);
 			  }
 		   }
 		   model.addAttribute("pager",pager);	
 		   model.addAttribute("roleId",roleId);
        }catch(Exception e){
     	   logger.error("CheckManageController qryCheckPageList error",e);
 		   e.printStackTrace();
        }
         return "/check/check_page_list";
    }
    
    /**
     * 批量审核广告
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/batchCheckAd",method=RequestMethod.POST)
    @ResponseBody
    public String batchcheck(HttpServletRequest request,HttpServletResponse response,Model model){
    	String adCheckProcessPk = request.getParameter("adCheckProcessId");
    	String radioValue = request.getParameter("radioValue");
    	String checkdesc = request.getParameter("checkdesc");
    	String[] adCheckProcessPks = adCheckProcessPk.split(",");
    	User user = (User)request.getSession().getAttribute("user");
		if("1".equals(userService.getUser(user.getUserName()).getType())){
			//批量审核
	    	for(int i=0;i<adCheckProcessPks.length;i++){
	    		AdCheckProcessDto adCheckProcessDto = adCheckProcessService.findAdCheckProcessDtoByPk(adCheckProcessPks[i]);
	    	    AdInstanceDto adInstanceDto = adCheckProcessService.findAdInstanceByAdId(adCheckProcessDto.getAdId());
	    		adCheckProcessDto.setId(adCheckProcessPks[i]); 	      
	    	    adCheckProcessDto.setStsDate(DateUtil.getCurrentDateTimeStr());
	    	    if(radioValue.equals("2")){	    	   
	    	       adCheckProcessDto.setCheckSts(radioValue);
	        	   adCheckProcessDto.setCheckDesc("审核通过");
	        	   adCheckProcessDto.setStsDate(DateUtil.getCurrentDateTimeStr());
	    	    }else if(radioValue.equals("3")){
	    	       adCheckProcessDto.setCheckSts(radioValue);
	        	   adCheckProcessDto.setCheckDesc(checkdesc);
	        	   adCheckProcessDto.setStsDate(DateUtil.getCurrentDateTimeStr());
	        	   adInstanceDto.setAdToufangSts("5");
	    	    }
	    	    adCheckProcessService.updateAdCheckProcessInfo(adCheckProcessDto);
	    	    adCheckProcessService.updateAdTouFangSts(adInstanceDto);
	    	    
	    	    List<AdCheckProcessDto> adCheckProcessList = adCheckProcessService.findAdCheckProcessDtoByAdId(adCheckProcessDto.getAdId());
	    	    for(int j=0;j<adCheckProcessList.size();j++){
	    	    	AdCheckProcessDto adCheckProcess = adCheckProcessList.get(j);
	    	    	if(adCheckProcess.getCheckSts().equals("3")){
	    	    		adInstanceDto.setAdToufangSts("5");
	    	    		break;
	    	    	}else if(adCheckProcess.getCheckSts().equals("1")){
	    	    		adInstanceDto.setAdToufangSts("0");
	    	    		break;
	    	    	}else if(adCheckProcess.getCheckSts().equals("2")){
	    	    		adInstanceDto.setAdToufangSts("2");
	    	    	}
	    	    	 adCheckProcessService.updateAdTouFangSts(adInstanceDto);
	    	    }
	    	}
		}else if("3".equals(userService.getUser(user.getUserName()).getType())){
			//批量审核
	    	for(int i=0;i<adCheckProcessPks.length;i++){
	    		TAdCheckProcessDto t_adCheckProcessDto = adCheckProcessService.findTAdCheckProcessDtoByPk(adCheckProcessPks[i]);
	    	    AdInstanceDto adInstanceDto = adCheckProcessService.findAdInstanceByAdId(t_adCheckProcessDto.getAdId());
	    	    t_adCheckProcessDto.setId(adCheckProcessPks[i]); 	      
	    	    t_adCheckProcessDto.setStsDate(DateUtil.getCurrentDateTimeStr());
	    	    if(radioValue.equals("2")){	    	   
	    	    	t_adCheckProcessDto.setCheckSts(radioValue);
	    	    	t_adCheckProcessDto.setCheckDesc("审核通过");
	    	    	t_adCheckProcessDto.setStsDate(DateUtil.getCurrentDateTimeStr());
	    	    }else if(radioValue.equals("3")){
	    	    	t_adCheckProcessDto.setCheckSts(radioValue);
	    	    	t_adCheckProcessDto.setCheckDesc(checkdesc);
	    	    	t_adCheckProcessDto.setStsDate(DateUtil.getCurrentDateTimeStr());
	        	   adInstanceDto.setAdToufangSts("5");
	    	    }
	    	    adCheckProcessService.updateTAdCheckProcessInfo(t_adCheckProcessDto);
	    	    adCheckProcessService.updateAdTouFangSts(adInstanceDto);
	    	    
	    	    List<TAdCheckProcessDto> adCheckProcessList = adCheckProcessService.findTAdCheckProcessDtoByAdId(t_adCheckProcessDto.getAdId());
	    	    for(int j=0;j<adCheckProcessList.size();j++){
	    	    	TAdCheckProcessDto t_adCheckProcess = adCheckProcessList.get(j);
	    	    	if(t_adCheckProcess.getCheckSts().equals("3")){
	    	    		adInstanceDto.setAdToufangSts("5");
	    	    		break;
	    	    	}else if(t_adCheckProcess.getCheckSts().equals("1")){
	    	    		adInstanceDto.setAdToufangSts("0");
	    	    		break;
	    	    	}else if(t_adCheckProcess.getCheckSts().equals("2")){
	    	    		adInstanceDto.setAdToufangSts("2");
	    	    	}
	    	    	 adCheckProcessService.updateAdTouFangSts(adInstanceDto);
	    	    }
	    	}
		}       	  	
    	return "1";  	
    }
    
    /**
     * 单个审核广告弹层
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/checkadDIV",method=RequestMethod.GET)
    public String checkAd(HttpServletRequest request,HttpServletResponse response,Model model){
    	String checkProcessId =request.getParameter("checkProcessId");
    	
    	User user = (User)request.getSession().getAttribute("user");
		if("1".equals(userService.getUser(user.getUserName()).getType())){
			AdCheckProcessDto adCheckProcessDto = adCheckProcessService.findAdCheckProcessDtoByPk(checkProcessId);
			AdInstanceDto adInstanceDto = adCheckProcessService.findAdInstanceByAdId(adCheckProcessDto.getAdId());
	    	String linkType = adInstanceDto.getLinkType();
	    	if(linkType.equals("E")){
	    		AdExtLinkDto adExtRel = adCheckProcessService.findAdExtLinkByAdInstanceId(adCheckProcessDto.getAdId());
	    		adInstanceDto.setAdUrl(adExtRel.getThrowUrl());
	    	}
	    	model.addAttribute("adInstanceDto",adInstanceDto);
		}else if("3".equals(userService.getUser(user.getUserName()).getType())){
			TAdCheckProcessDto adCheckProcessDto = adCheckProcessService.findTAdCheckProcessDtoByPk(checkProcessId);
			AdInstanceDto adInstanceDto = adCheckProcessService.findAdInstanceByAdId(adCheckProcessDto.getAdId());
	    	String linkType = adInstanceDto.getLinkType();
	    	if(linkType.equals("E")){
	    		AdExtLinkDto adExtRel = adCheckProcessService.findAdExtLinkByAdInstanceId(adCheckProcessDto.getAdId());
	    		adInstanceDto.setAdUrl(adExtRel.getThrowUrl());
	    	}
	    	model.addAttribute("adInstanceDto",adInstanceDto);
		}    
    	model.addAttribute("adCheckProcessPk",checkProcessId);
    	return "/check/check_ad";
    }
    
    /**
     * 对单个广告进行审核操作
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value="/checkonead",method=RequestMethod.POST)
    @ResponseBody
    public String checkad(HttpServletRequest request,HttpServletResponse response,Model model){
    	String radioValue = request.getParameter("radioValue");
    	String checkdesc = request.getParameter("checkdesc");
    	String adCheckProcessPk = request.getParameter("adCheckProcessPk");
    	
    	User user = (User)request.getSession().getAttribute("user");
		if("1".equals(userService.getUser(user.getUserName()).getType())){
			AdCheckProcessDto adCheckProcessDto = adCheckProcessService.findAdCheckProcessDtoByPk(adCheckProcessPk);
		    AdInstanceDto adInstanceDto = adCheckProcessService.findAdInstanceByAdId(adCheckProcessDto.getAdId());
		    if(radioValue.equals("2")){	    	   
	 	       adCheckProcessDto.setCheckSts(radioValue);
	     	   adCheckProcessDto.setCheckDesc("审核通过");
	     	   adCheckProcessDto.setStsDate(DateUtil.getCurrentDateTimeStr());
	 	    }else if(radioValue.equals("3")){
	 	       adCheckProcessDto.setCheckSts(radioValue);
	     	   adCheckProcessDto.setCheckDesc(checkdesc);
	     	   adCheckProcessDto.setStsDate(DateUtil.getCurrentDateTimeStr());
	     	   adInstanceDto.setAdToufangSts("5");
	 	    }
	 	    adCheckProcessService.updateAdCheckProcessInfo(adCheckProcessDto);
	 	    adCheckProcessService.updateAdTouFangSts(adInstanceDto);
	    	
	 	    List<AdCheckProcessDto> adCheckProcessList = adCheckProcessService.findAdCheckProcessDtoByAdId(adCheckProcessDto.getAdId());
	 	    for(int j=0;j<adCheckProcessList.size();j++){
		    	AdCheckProcessDto adCheckProcess = adCheckProcessList.get(j);
		    	if(adCheckProcess.getCheckSts().equals("3")){
		    		adInstanceDto.setAdToufangSts("5");
		    		break;
		    	}else if(adCheckProcess.getCheckSts().equals("1")){
		    		adInstanceDto.setAdToufangSts("0");
		    		break;
		    	}else if(adCheckProcess.getCheckSts().equals("2")){
		    		adInstanceDto.setAdToufangSts("2");
		    	}
		    }
	 	    adCheckProcessService.updateAdTouFangSts(adInstanceDto);
		}else if("3".equals(userService.getUser(user.getUserName()).getType())){
			TAdCheckProcessDto t_adCheckProcessDto = adCheckProcessService.findTAdCheckProcessDtoByPk(adCheckProcessPk);
		    AdInstanceDto adInstanceDto = adCheckProcessService.findAdInstanceByAdId(t_adCheckProcessDto.getAdId());
		    if(radioValue.equals("2")){	    	   
		    	t_adCheckProcessDto.setCheckSts(radioValue);
		    	t_adCheckProcessDto.setCheckDesc("审核通过");
		    	t_adCheckProcessDto.setStsDate(DateUtil.getCurrentDateTimeStr());
	 	    }else if(radioValue.equals("3")){
	 	    	t_adCheckProcessDto.setCheckSts(radioValue);
	 	    	t_adCheckProcessDto.setCheckDesc(checkdesc);
	 	    	t_adCheckProcessDto.setStsDate(DateUtil.getCurrentDateTimeStr());
	     	   adInstanceDto.setAdToufangSts("5");
	 	    }
	 	    adCheckProcessService.updateTAdCheckProcessInfo(t_adCheckProcessDto);
	 	    adCheckProcessService.updateAdTouFangSts(adInstanceDto);
	    	
	 	    List<TAdCheckProcessDto> adCheckProcessList = adCheckProcessService.findTAdCheckProcessDtoByAdId(t_adCheckProcessDto.getAdId());
	 	    for(int j=0;j<adCheckProcessList.size();j++){
		    	TAdCheckProcessDto t_adCheckProcess = adCheckProcessList.get(j);
		    	if(t_adCheckProcess.getCheckSts().equals("3")){
		    		adInstanceDto.setAdToufangSts("5");
		    		break;
		    	}else if(t_adCheckProcess.getCheckSts().equals("1")){
		    		adInstanceDto.setAdToufangSts("0");
		    		break;
		    	}else if(t_adCheckProcess.getCheckSts().equals("2")){
		    		adInstanceDto.setAdToufangSts("2");
		    	}
		    }
	 	    adCheckProcessService.updateAdTouFangSts(adInstanceDto);
		}
    	
 	    return "/check/check_soon";
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
