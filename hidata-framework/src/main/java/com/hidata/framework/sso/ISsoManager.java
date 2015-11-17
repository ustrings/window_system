package com.hidata.framework.sso;


import javax.servlet.http.HttpServletRequest;

import com.hidata.framework.sso.model.LetvUser;

/**
 * 单点登录管理者
 * 可用来做单点登录
 * @author houzhaowei
 *
 */
public interface ISsoManager {

	/**
	 * 登陆 
	 * @param request
	 * @return 当前登录用户 {@link LetvUser}
	 */
	public LetvUser sign(String tk,HttpServletRequest request);
	
}
