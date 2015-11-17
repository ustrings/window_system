package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.dto.TerminalBaseInfo;

/**
 * 操作终端定向的DAO
 * @author xiaoming
 *
 */
public interface TerminalBaseInfoDao {
	//获取所有终端定向
	public List<TerminalBaseInfo> getList();
	//根据类型获取终端定向（分别为 浏览器、设别、操作系统）
	public List<TerminalBaseInfo> getListByType(String type);
	public List<TerminalBaseInfo> getValueByTid(String tId);
}
