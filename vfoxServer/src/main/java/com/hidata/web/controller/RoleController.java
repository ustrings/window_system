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

import com.hidata.web.session.SessionContainer;
import com.hidata.framework.util.DateUtil;
import com.hidata.framework.util.StringUtil;
import com.hidata.web.dto.AdCheckConfigDto;
import com.hidata.web.dto.TAdCheckConfigDto;
import com.hidata.web.model.User;
import com.hidata.web.service.AdCheckProcessService;
import com.hidata.web.service.UserService;
import com.hidata.web.util.Constant;
import com.hidata.web.util.Pager;

/**
 * 广告审核管理 Controller
 * 
 * @author xiaoming
 * @date 2014-12-23
 */
@Controller
@RequestMapping("/role/*")
public class RoleController {

    private static Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private AdCheckProcessService adCheckProcessService;
    
    @Autowired
    private UserService userService;

    /**
     * 初始化展示页面
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public String initIndex(HttpServletRequest request, HttpServletResponse response, Model model) {
        
    	
        return "role/role";
    }

    
    /**
     * 分页查找相关信息
     * @param request
     * @param response
     * @param model
     * @return
     * @author zhoubin
     */
    @RequestMapping(value = "/qryRolePageList", method = RequestMethod.POST)
    public String qryRolePageList(HttpServletRequest request, HttpServletResponse response, Model model) {
       try{
    	   User user = (User)request.getSession().getAttribute("user");
           String checkRoleName = request.getParameter("checkRoleName");
    	   String userName = request.getParameter("userName");
    	   String deptName = request.getParameter("deptName");
    	   String telNbr = request.getParameter("telNbr");
    	   String curPage = request.getParameter("curPage");
    	
    	   if(StringUtil.isEmpty(curPage)) {
			  curPage = "1";
		   }
		   Map<String,String> map = new HashMap<String,String>();
		   map.put("checkRoleName",checkRoleName );
		   map.put("userName",userName);
		   map.put("deptName",deptName);
		   map.put("telNbr",telNbr);
		   Pager pager = null;
		   if("3".equals(userService.getUser(user.getUserName()).getType())){
			   pager = adCheckProcessService.getTRoleListPager(map,curPage);
		   }else if("1".equals(userService.getUser(user.getUserName()).getType())){
			   pager = adCheckProcessService.getRoleListPager(map,curPage);
		   }
		   
		   model.addAttribute("pager",pager);
       }catch(Exception e){
    	   logger.error("RoleController qryRolePageList error",e);
		   e.printStackTrace();
       }
        return "/role/role_page_list";
    }
    /**
     * 添加广告审核角色
     * 
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     * @author zhoubin
     */
    @RequestMapping(value = "/addAdCheckConfigRole", produces="application/json",method = RequestMethod.POST)
    @ResponseBody
    public String addAdCheckConfigRole(HttpServletRequest request, HttpServletResponse response,
            Model model, AdCheckConfigDto adCheckConfigDto) throws Exception {
        
    	User user = userService.getUser(adCheckConfigDto.getUserAcctRel());
    	if(user.getUserName()!=null&&"1".equals(user.getType())){
    		List<AdCheckConfigDto> adCheckConfigList = adCheckProcessService.findAdCheckConfigDtoByUserAcctRel(adCheckConfigDto.getUserAcctRel());
            if(adCheckConfigList.size()>0){   
            	return "2";
            }else{
        	   adCheckConfigDto.setSts(Constant.AD_STS_A);
               adCheckConfigDto.setStsDate(DateUtil.getCurrentDateTimeStr());
               adCheckProcessService.addAdCheckConfig(adCheckConfigDto);
               return "1";
            }
    	}else if(user.getUserName()!=null&&"3".equals(user.getType())){
    		List<TAdCheckConfigDto> t_adCheckConfigList = adCheckProcessService.findTAdCheckConfigDtoByUserAcctRel(adCheckConfigDto.getUserAcctRel());
            if(t_adCheckConfigList.size()>0){   
            	return "2";
            }else{
               TAdCheckConfigDto t_adCheckConfigDto = new TAdCheckConfigDto();
               t_adCheckConfigDto.setCheckRoleName(adCheckConfigDto.getCheckRoleName());
               t_adCheckConfigDto.setDeptName(adCheckConfigDto.getDeptName());
               t_adCheckConfigDto.setEmail(adCheckConfigDto.getEmail());
               t_adCheckConfigDto.setTelNbr(adCheckConfigDto.getTelNbr());
               t_adCheckConfigDto.setUserName(adCheckConfigDto.getUserName());
               t_adCheckConfigDto.setSts(Constant.AD_STS_A);
               t_adCheckConfigDto.setUserAcctRel(adCheckConfigDto.getUserAcctRel());
               t_adCheckConfigDto.setStsDate(DateUtil.getCurrentDateTimeStr());
               adCheckProcessService.addTAdCheckConfig(t_adCheckConfigDto);
               return "1";
            }
    	}
    	return "0";
    }

    /**
     * 初始化修改审核角色
     * 
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     * @author zhoubin
     */
    @RequestMapping(value = "/initAdCheckConfigRole", method = RequestMethod.GET)
    public String initAdCheckConfigRole(HttpServletRequest request, HttpServletResponse response,
            Model model) throws Exception {

        String adCheckConfigPk = request.getParameter("adCheckConfigPk");

        AdCheckConfigDto adCheckConfigDto = adCheckProcessService
                .findAdCheckConfigDtoByPk(adCheckConfigPk);
        model.addAttribute("adCheckConfigDto",adCheckConfigDto);
        return "/role/editRole";
    }
    
    /**
     * 更新角色信息
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAdCheckConfigRole", method = RequestMethod.POST)
    @ResponseBody
    public String updateAdCheckConfigRole(HttpServletRequest request, HttpServletResponse response,
            Model model) throws Exception {
        String operationType = request.getParameter("operationType");
        String adCheckConfigPk = request.getParameter("adCheckConfigPk");
       
        int result = 0;
        if(operationType.equals("1")){	  
           String checkRoleName = request.getParameter("checkRoleName");
       	   String userName = request.getParameter("userName");
       	   String deptName = request.getParameter("deptName");
       	   String telNbr = request.getParameter("telNbr");
       	   String userAcctRel = request.getParameter("userAcctRel");
       	   String email = request.getParameter("email");
         
       	   User user = userService.getUser(userAcctRel);
       	   if(user.getUserName()!=null){
       	     AdCheckConfigDto adCheckConfigDto = adCheckProcessService.findAdCheckConfigDtoByPk(adCheckConfigPk);
     	     if(adCheckConfigDto.getUserAcctRel().equals(userAcctRel)){
     		   adCheckConfigDto.setCheckRoleName(checkRoleName);
        	   adCheckConfigDto.setDeptName(deptName);
        	   adCheckConfigDto.setEmail(email);
        	   adCheckConfigDto.setTelNbr(telNbr);
        	   adCheckConfigDto.setUserName(userName);
        	   adCheckConfigDto.setStsDate(DateUtil.getCurrentDateTimeStr());
        	   result = adCheckProcessService.updateAdCheckConfig(adCheckConfigDto);   
     	      }else{
     		    List<AdCheckConfigDto> adCheckConfigList = adCheckProcessService.findAdCheckConfigDtoByUserAcctRel(userAcctRel);
     	        if(adCheckConfigList.size()>0){
     	    	   result = -1;
     	        }else{
     	           adCheckConfigDto.setCheckRoleName(checkRoleName);
          	       adCheckConfigDto.setDeptName(deptName);
          	       adCheckConfigDto.setEmail(email);
          	       adCheckConfigDto.setTelNbr(telNbr);
          	       adCheckConfigDto.setUserName(userName);
          	       adCheckConfigDto.setUserAcctRel(userAcctRel);
          	       adCheckConfigDto.setStsDate(DateUtil.getCurrentDateTimeStr());
          	       result = adCheckProcessService.updateAdCheckConfig(adCheckConfigDto);   
     	        }
     	     }  
       	    }else{
       	    	return "3";
       	    }
          }else if(operationType.equals("2")){
        	//删除角色
            result = adCheckProcessService.deleteAdCheckConfigById(adCheckConfigPk);                
          }
          if(result>0){
        	 return "1";
          }else if(result==-1){
        	 return "2";
          }
        return "0";      
    }

    /**
     * 修改审核意见
     * 
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     * @author zhoubin
     */
    @RequestMapping(value = "/updateAdCheckProcess", method = RequestMethod.GET)
    public String updateAdCheckProcess(HttpServletRequest request, HttpServletResponse response,
            Model model) throws Exception {

        return "/ad/ad-list";
    }

}
