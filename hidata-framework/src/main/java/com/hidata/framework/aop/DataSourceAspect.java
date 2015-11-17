package com.hidata.framework.aop;

import java.lang.reflect.Method;



import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.CustomerContextHolder;
import com.hidata.framework.db.DynamicDataSource;
import com.hidata.framework.util.StringUtil;

/**
 * 数据源切面
 * @author houzhaowei
 *
 */
@Aspect
@Component
public class DataSourceAspect {


	
	@Autowired
	private DynamicDataSource dynamicDataSource;
 
    @Before("dtSourcePointcut()")
    public void doBefore() {
    }
     
    //定义切面
    @Pointcut("execution(* com.hidata.framework.db.DBManager.*(..))")
    public void dtSourcePointcut() {}
 
    @Around("dtSourcePointcut() && args(..)")
    public Object doAround(ProceedingJoinPoint call){
        Object rs = null;
         try {
        	 Signature signature = call.getSignature();
             MethodSignature methodSignature = (MethodSignature) signature;  
             Method method = methodSignature.getMethod();
             String name = method.getName().toLowerCase();
             //包含这几个字符串的操作，走写数据源
             String[] updateOpts = {"update","insert","delete"};
             if(StringUtil.containsItemContains(updateOpts, name)){
                 this.setWriteDataSource();
             }else{
                 this.setReadDataSource();
             }
             rs = call.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return rs;
    }
     
    private void setWriteDataSource() {
    	CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_WRITE);
    	//由于spring不能在每个查询方法及时调用 DynamicDataSource determineCurrentLookupKey，所以手动改！
    	dynamicDataSource.determineCurrentLookupKey();
//    	templete.setDataSource((DataSource) context.getBean(CustomerContextHolder.DATA_SOURCE_WRITE));
    }  
 
    private void setReadDataSource() {
    	CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_READ);
    	//由于spring不能在每个查询方法及时调用 DynamicDataSource determineCurrentLookupKey，所以手动改！
    	dynamicDataSource.determineCurrentLookupKey();
//    	templete.setDataSource((DataSource) context.getBean(CustomerContextHolder.DATA_SOURCE_READ));
    }  
 
}