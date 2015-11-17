package com.hidata.ad.web.service;

import java.util.List;
import java.util.Map;

import com.hidata.ad.web.dto.TerminalBaseInfo;

/**
 * 操作终端定向的Serivce
 * @author xiaoming
 *
 */
public interface TerminalBaseInfoService {
	//根据类型获取终端定向
	public Map<String, List<TerminalBaseInfo>> getAllGroupByType();
	//根据Id查询value
	public List<TerminalBaseInfo> getValueById(String id);
}
