package com.vaolan.sspserver.model;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hidata.framework.util.StringUtil;

/**
 * 存放所有广告的当天前总投放 pv 信息，使用定时任务，定时更新
 */
@Component
public class AdvWareHouse {
	private static Logger logger = Logger.getLogger(AdvWareHouse.class);
	private JSONObject jsonDayPvCount;
	
	private Map<String,String> adMinMap;
	
	public Map<String, String> getAdMinMap() {
		return adMinMap;
	}



	public void setAdMinMap(Map<String, String> adMinMap) {
		this.adMinMap = adMinMap;
	}



	/**
	 * 初始化Pv Count
	 * 
	 * @param pvCountInfo
	 */
	public void initDayPvCount(String dayPvCountInfo) {
		if (!StringUtil.isEmpty(dayPvCountInfo)) {
			jsonDayPvCount = JSONObject.fromObject(dayPvCountInfo);
		}
	}
	
	
	
	/**
	 * 获取PV Count
	 * 
	 * @param advId
	 * @return
	 */
	public long getPvCount(String advId) {
		long count = 0;
		try {
			if (null != jsonDayPvCount) {
				
				if(null != jsonDayPvCount.get(advId)){
					count = Long.parseLong(jsonDayPvCount.get(advId).toString());
				}
				
			}
		} catch (Exception e) {
			logger.error("获取广告计划id=" + advId + "失败：", e);
		}
		return count;
	}
}
