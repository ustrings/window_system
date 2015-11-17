package com.hidata.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hidata.web.dao.AdInstanceDao;
import com.hidata.web.dto.AdInstanceDto;
import com.hidata.web.service.AdInstanceService;

@Service
public class AdInstanceServiceImpl implements AdInstanceService{
	@Autowired
	private AdInstanceDao adInstanceDao;
	
	@Override
	public List<AdInstanceDto> getListsBysts() {
		List<AdInstanceDto> list = adInstanceDao.getListBySts();
		return list;
	}

	@Override
	public Boolean updateSts(String adIds) {
		Integer rows = adInstanceDao.updateSts(adIds);
		if(rows != null){
			return true;
		}
		return false;
	}

}
