package com.project.dsp.action;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.dsp.entity.Dsp_Users;
import com.project.dsp.service.DspUsersService;
import com.project.dsp.utils.StrUtil;

@Controller
@RequestMapping(value = "/dspUserAction")
public class DspUserAction {
	
	@Resource
    private DspUsersService dspUsersService;
	
	@RequestMapping(value="/toSystemPage")
	public String toSystemPage(){
		return "/Admin/system";
	}
	@RequestMapping(value="/doLogin")
	@ResponseBody
    public String doLogin(HttpServletRequest request,Dsp_Users dsp_Users){
		Dsp_Users user= this.dspUsersService.queryDspUsers(dsp_Users);
		if(user !=null){
			request.getSession().setAttribute("user", user);
			return "1";
		}
		return "0";
    }
	
	//查询登陆用户信息
	@RequestMapping(value="/doSelectLogin")
	@ResponseBody
	public Dsp_Users doSelectLogin(Dsp_Users dsp_Users){
		return dspUsersService.queryDspUsers(dsp_Users);
	}
	/***
	 * MD5加密
	 * 32位
	 */
	public static String MD5(String inStr) {  
		MessageDigest md5 = null;  
		try {  
			md5 = MessageDigest.getInstance("MD5");  
		} catch (Exception e) {  
		    System.out.println(e.toString());  
		    e.printStackTrace();  
		    return "";  
		}  
		char[] charArray = inStr.toCharArray();  
		byte[] byteArray = new byte[charArray.length];  
		   
		for (int i = 0; i < charArray.length; i++)  
		    byteArray[i] = (byte) charArray[i];  
		   
		   byte[] md5Bytes = md5.digest(byteArray);  
		   
		   StringBuffer hexValue = new StringBuffer();  
		   
		   for (int i = 0; i < md5Bytes.length; i++) {  
		    int val = ((int) md5Bytes[i]) & 0xff;  
		    if (val < 16)  
		     hexValue.append("0");  
		    hexValue.append(Integer.toHexString(val));  
		   }  
		   
		return hexValue.toString();  
	}  
	
	//把时间转换为时间戳
	public static long getStringToDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		//SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		try{
			date = sdf.parse(time);
			
		}catch(Exception e) {
			// TODO Auto-generated catch block　　
			e.printStackTrace();
		}
		return date.getTime();
	}
	
	
}
