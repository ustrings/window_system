package com.hidata.framework.aop;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.hidata.framework.annotation.LoginRequired;
import com.hidata.framework.log.LogManager;

/**
 * 切面类 用来处理 {@link LoginRequired} 注解
 *
 * @author houzhaowei
 */
@Aspect
@Component
public class LoginAspect {

//    @Autowired
//    private ISsoManager ssoManager;
//
//    @Autowired
//    private SsoEngine engine;

    private static String loginUrl = "http://localhost:8080/login";

    private static LogManager logger = LogManager.getLogger(LoginAspect.class);

    private static String LOG_KEY = "LOGIN ASPECT";

    /**
     * 定义在类或方法都可以加 {@link LoginRequired} 注解
     */
    @Pointcut("@target(com.hidata.framework.annotation.LoginRequired) || @annotation(com.hidata.framework.annotation.LoginRequired)")
    public void checkLogin() {
    }


    /**
     * 在 {@link LoginRequired} 标记的方法/类 之前执行此方法，加注解的方法必须有 request 和 response 两个参数
     *
     * @param request
     * @param response
     */
    @Before("checkLogin() && args(request,response,..)")
    public void before(HttpServletRequest request, HttpServletResponse response) {
        //从cookie中获取token
//        String tk = engine.getTkCookie(request);
//        if (tk == null || tk.equals("")) {
//            //用户退出登陆后清空session
//            request.getSession().setAttribute("user", null);
//            //跳转到登陆页
//            this.redirect(response);
//            return;
//        }
//        //从session中获取user对象
//        Object oUser = request.getSession().getAttribute("user");
//        if (null != oUser) {
//            String uTk = ((LetvUser) oUser).getTk();
//            //如果票相同，则直接返回
//            if (null != uTk && uTk.equals(tk)) {
//                return;
//            }
//        }
//
//        LetvUser user = ssoManager.sign(tk, request);
//        if (null == user) {
//            //跳转到登陆页
//            this.redirect(response);
//            return;
//        }
//        logger.info("USER_LOGIN", "user : " + user.toString() + " logined , ip : " + RequestUtil.getClientIp(request));
    	
    	Object obj = request.getSession().getAttribute("user");
    	if (obj == null){
    		this.redirect(response);
    		return;
    	}
        request.getSession().setAttribute("user", obj);
    }


    /**
     * 跳转到登陆页
     *
     * @param response
     * @return
     */
    private boolean redirect(HttpServletResponse response) {
        try {
            response.sendRedirect(loginUrl);
        } catch (IOException e) {
            logger.error(LOG_KEY, "redirect to login page failed : " + e.getMessage());
            return false;
        }
        return true;
    }


}
