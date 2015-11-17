package com.vaolan.ckserver.server;

import javax.servlet.http.HttpServletRequest;

public interface CrowdDataRefreshServer
{
	/**
	 * 更新重定向人群信息
	 * @param request
	 */
	public void updateCrowdData(HttpServletRequest request); 
}
