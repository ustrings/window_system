package com.project.dsp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.dsp.dao.DspUsersDao;
import com.project.dsp.entity.Dsp_Users;
import com.project.dsp.service.DspUsersService;

@Service
@Transactional
public class DspUsersServiceImpl implements DspUsersService{
	
	@Resource
    private DspUsersDao dspUsersDao;

	@Override
	public Dsp_Users queryDspUsers(Dsp_Users dsp_Users) {
		return dspUsersDao.queryDspUsersList(dsp_Users);
	}

	
}
