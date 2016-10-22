package com.microsoft.Test_struts_jpa.action;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.microsoft.Test_struts_jpa.biz.UserInfoBiz;
import com.microsoft.Test_struts_jpa.entity.Dsp_Users;

public class DspUserAction extends BaseAction{
	private int page = 1;
	private String username;
	private String password;
	private Dsp_Users user;
	private String repassword;
	
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public Dsp_Users getUser() {
		return user;
	}
	public void setUser(Dsp_Users user) {
		this.user = user;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	private UserInfoBiz userinfobiz = new UserInfoBiz();
	//登录
	public String doLogin(){
		Dsp_Users user = userinfobiz.getSelectUserInfo(this.getUsername(), MD5(this.getPassword()));
		if(user != null){
			
			this.getSession().put("user", user);
			return "success";
		}else{
			String jsp = "alert('登录失败！')";
			this.getRequest().put("mes", jsp);
			return "loginsuccess";
		}
	}
	
	//注册
	public String doRegister(){
		//获取本机IP
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String regip = addr.getHostAddress().toString();//获得本机IP
		
		this.getUser().setRegip(regip);
		this.getUser().setLastip(regip);
		//获取当前时间
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");//可以方便地修改日期格式 
		String hehe = dateFormat.format( now ); 
		this.getUser().setRegdate(getStringToDate(hehe));
		
		if(this.getUser().getPassword().toString().equals(this.getRepassword().toString())){
			//加密
			this.getUser().setPassword(MD5(this.getRepassword()));
			
			boolean b = userinfobiz.getRegitster(this.getUser());
			if(b){
				String jsp = "alert('注册成功!')";
				this.getRequest().put("mes", jsp);
				return "register";
			}else{
				String jsp = "alert('注册失败!')";
				this.getRequest().put("mes", jsp);
				return "error";
			}
		}else{
			String jsp = "alert('密码不一致!')";
			this.getRequest().put("mes", jsp);
			return "error";
		}
		
		
		
		
	}
	
	//查询登陆用户信息
	public String doSelectLogin(){
		Dsp_Users user = userinfobiz.getSelectUserInfo(this.getUsername(), this.getPassword());
		if(user != null){
			this.getSession().put("user", user);
			return "success";
		}else{
			return "error";
		}
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
