package com.project.dsp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.dsp.dao.DspDomainDao;
import com.project.dsp.entity.Dsp_Domain;
import com.project.dsp.service.DspDomainService;

@Service
@Transactional
public class DspDomainServiceImpl implements DspDomainService{
	
	@Resource
    private DspDomainDao dspDomainDao;

	public List<Dsp_Domain> queryDspDomainList(Dsp_Domain dsp_Domain) {
		return this.dspDomainDao.queryDspDomainList(dsp_Domain);
	}
	
}
