package com.hidata.web.interceptor;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class WebSessionInterceptor implements HandlerInterceptor {
	
	private static ArrayList<String> excludeList = new ArrayList<String>();
	
	static {
		excludeList.add("/login");
		excludeList.add("/logout");
		excludeList.add("/checkuser");
	}


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView view) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		
		String url = request.getRequestURL().toString();
		boolean isNotValidation = false;
		
		for (String str : excludeList){
			// 不用登陆验证的链接
			if (url.indexOf(str) > 0){
				isNotValidation = true;
				break;
			}
		}
		
		if (!isNotValidation){
			Object obj = request.getSession().getAttribute("user");
	    	if (obj == null){
	    		response.sendRedirect("/login");
	    		return false;
	    	} else{
	    		return true;
	    	}
		} else{
			return true;
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
