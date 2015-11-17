package com.vaolan.sspserver.filter;

import org.springframework.stereotype.Service;

import com.hidata.framework.util.StringUtil;
import com.vaolan.sspserver.model.AdDeviceLinkDto;
import com.vaolan.sspserver.model.AdFilterElement;
import com.vaolan.sspserver.model.AdvPlan;

/**
 * AD帐号过滤:ad过滤 Date: 2014年6月16日 <br>
 * 
 * @author chenjinzhao
 * */
@Service
public class DeviceFilter {

	public boolean doFilter(AdvPlan advPlan, AdFilterElement adFilterElement) {
		return this.deviceFilter(advPlan, adFilterElement);
	}

	/**
	 * 
	 * 
	 * @param advPlan
	 * @param adFilterElement
	 * @return
	 */
	public boolean deviceFilter(AdvPlan advPlan, AdFilterElement adFilterElement) {
		boolean flag = true;
		if (advPlan.isAdDeviceTarget()) {
			if (StringUtil.isNotBlank(adFilterElement.getUserAgent())) {
				
				String dCode = "";
				if(adFilterElement.getUserAgent().toLowerCase().contains("android")){
					dCode ="1";
				}else if(adFilterElement.getUserAgent().toLowerCase().contains("iphone")){
					dCode="2";
				}
				
				flag = false;
				for(AdDeviceLinkDto adDev:advPlan.getAdDeviceLinkList()){
					if(adDev.getAdDeviceId().equals(dCode)){
						flag =true;
						break;
					}
				}
				
			} else {
				flag = false;
			}
		}
		return flag;
	}



}
