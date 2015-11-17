package com.hidata.ad.web.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hidata.ad.web.dao.TerminalBaseInfoDao;
import com.hidata.ad.web.dto.TerminalBaseInfo;
import com.hidata.ad.web.service.TerminalBaseInfoService;

@Service
public class TerminalBaseInfoServiceImpl implements TerminalBaseInfoService {
	@Autowired
	private TerminalBaseInfoDao terminaldao;
	@Override
	public Map<String, List<TerminalBaseInfo>> getAllGroupByType() {
		Map<String, List<TerminalBaseInfo>> map = new HashMap<String, List<TerminalBaseInfo>>();
		//浏览器类型
		List<TerminalBaseInfo> browser_list = terminaldao.getListByType("3");
		//设别类型
		List<TerminalBaseInfo> device_list = terminaldao.getListByType("1");
		//操作系统类型
		List<TerminalBaseInfo> system_list = terminaldao.getListByType("2");
		if(browser_list != null && device_list != null && system_list!= null){
			map.put("browser_list", browser_list);
			map.put("device_list", device_list);
			map.put("system_list", system_list);
			return map;
		}
		return null;
	}
	@Override
	public List<TerminalBaseInfo> getValueById(String id) {
		return terminaldao.getValueByTid(id);
		
	}

}
