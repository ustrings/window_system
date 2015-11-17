package com.hidata.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hidata.web.dao.AdTouFangStsDao;
import com.hidata.web.dto.AdTouFangStsDto;
import com.hidata.web.service.AdTouFangStsService;

@Service
public class AdTouFangStsServiceImpl implements AdTouFangStsService{
	
	@Autowired
	private AdTouFangStsDao adTouFangStsDao;
	
	@Override
	public List<AdTouFangStsDto> getAllLists() {
		return adTouFangStsDao.qryAllSts();
	}
}
