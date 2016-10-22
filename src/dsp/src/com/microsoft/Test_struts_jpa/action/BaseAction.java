package com.microsoft.Test_struts_jpa.action;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.*;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;


public class BaseAction {
	public Map<String, Object> getRequest(){
		return (Map<String, Object>) ActionContext.getContext().get("request"); 
	}
	public Map<String ,Object> getSession(){
		return ActionContext.getContext().getSession();
	}
	public Map<String ,Object> getApplication(){
		return ActionContext.getContext().getApplication();
	}
	public HttpServletResponse getResponse(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html,charset=utf-8");
		return response;
	}
	public HttpServletRequest getRes(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return request;
	}
}
