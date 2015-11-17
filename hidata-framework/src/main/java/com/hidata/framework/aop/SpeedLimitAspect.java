package com.hidata.framework.aop;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.hidata.framework.annotation.LoginRequired;
import com.hidata.framework.annotation.SpeedLimit;
import com.hidata.framework.log.LogManager;
import com.hidata.framework.util.http.RequestUtil;
import com.hidata.framework.util.http.SpeedLimitUtil;

/**
 * 切面类 用来处理 {@link SpeedLimit} 注解
 * @author houzhaowei
 */
@Aspect
@Component
public class SpeedLimitAspect {
	
	LogManager logger = LogManager.getLogger(SpeedLimitAspect.class);
	private static String LOG_KEY = "LimitAspect";
	
	/**
	 * 定义在类或方法都可以加 {@link SpeedLimit} 注解
	 */
	@Pointcut("@target(com.hidata.framework.annotation.SpeedLimit) || @annotation(com.hidata.framework.annotation.SpeedLimit)")
	public void limit(){}
	
	/**
	 * 在 {@link LoginRequired} 标记的方法/类 之前执行此方法，加注解的方法必须有 request 和 response 两个参数
	 * @param request
	 * @param response
	 */
	@Around("limit() && args(request,response,..)")
	public void around(ProceedingJoinPoint joinPoint,HttpServletRequest request,HttpServletResponse response){
		//获取客户端ip地址
		String ip = RequestUtil.getClientIp(request);
		
//-- 统一限制 先注释掉这段
//		//先从类上获取注解
//		SpeedLimit sl = joinPoint.getTarget().getClass().getAnnotation(SpeedLimit.class);
//		//如果类没注解，则在方法上找注解
//		if(null == sl){
//			sl = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(SpeedLimit.class);
//		}
//		
//		//这里原则上不会再为null
//		if(null == sl){
//			return;
//		}
		
//		int period = sl.period();
//		int times  = sl.times();
		
		SpeedLimitUtil<String> limit = SpeedLimitUtil.getInstance();
		
		boolean isAllowed = limit.add(ip);
		if(!isAllowed){
			try {
				logger.info(LOG_KEY, "too many requests! ip : " + RequestUtil.getClientIp(request));
				response.sendRedirect("500.jsp");
				//不执行方法
				return;
			} catch (IOException e) {
				logger.error(LOG_KEY, "print error message error : " + e.getMessage());
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		//继续执行方法
		try {
			joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
