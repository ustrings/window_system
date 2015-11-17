package com.hidata.web.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hidata.web.dao.AdControlDao;
import com.hidata.web.dto.AdControlDto;
import com.hidata.web.service.AdControlService;
import com.hidata.web.util.TimeUtil;

@Service
public class AdControlServiceImpl implements AdControlService{
	
	@Autowired
	private AdControlDao adControlDao;
	@Override
	public Boolean editAdControl(AdControlDto adControlDto) {
		String date = TimeUtil.dateLongToMMHHssString(new Date().getTime());
		adControlDto.setStsDate(date);
		Integer rows = adControlDao.updateAdControl(adControlDto);
		if(rows != null){
			return true;
		}
		return false;
	}

	@Override
	public AdControlDto getAdControlDto() {
		List<AdControlDto> list = adControlDao.qryAdControl();
		if(list != null && list.size() == 1){
			return list.get(0);
		}
		return null;
	}

}
