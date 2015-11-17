package com.hidata.web.timer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hidata.framework.util.StringUtil;
import com.hidata.web.dto.AdInstanceDto;
import com.hidata.web.service.AdInstanceService;


public class TimerUtil {
	
	@Autowired
	private AdInstanceService adInstanceService;
	public void changeAdTouFangSts(){
		List<AdInstanceDto> list = adInstanceService.getListsBysts();
		String adIds = "";
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				if(i < list.size() - 1){
					adIds += list.get(i).getAdId() + ",";
				}else{
					adIds += list.get(i).getAdId();
				}
			}
		}
		
		if(StringUtil.isNotEmpty(adIds)){
			adInstanceService.updateSts(adIds);
			System.out.println("定时器已经执行,更改状态广告位" + adIds);
		}
	}
	
	public void destroy() {

	}
}
