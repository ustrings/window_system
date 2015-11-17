package com.hidata.ad.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerExceptionResolverComposite;

/**
 * 
* @Description: TODO(异常处理类) 
* @author chenjinzhao
* @date 2014年1月6日 上午11:52:46 
*
 */

@Component
public class AdExceptionHandler extends HandlerExceptionResolverComposite {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
	
		Throwable causeEx = ex.getCause();
		
		ModelAndView mv = new ModelAndView("error/aderror");

		mv.addObject("exception", ex);
		mv.addObject("cause", causeEx);
		return mv;
	}

}
