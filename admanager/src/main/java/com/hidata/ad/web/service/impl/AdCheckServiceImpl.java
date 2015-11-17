package com.hidata.ad.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.AdCheckDao;
import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.dto.AdCheckConfigDto;
import com.hidata.ad.web.dto.AdCheckProcessDto;
import com.hidata.ad.web.dto.TAdCheckConfigDto;
import com.hidata.ad.web.dto.TAdCheckProcessDto;
import com.hidata.ad.web.service.AdCheckService;

@Component
public class AdCheckServiceImpl implements AdCheckService{
   
	@Autowired
	private AdCheckDao adCheckDao;
	@Override
	public List<AdCheckConfigDto> getAdCheckConfigInfo() {
		
		return adCheckDao.getAdCheckConfigInfo();
	}
	@Override
	public int insertAdCheckProcess(AdCheckProcessDto adCheckProcessDto) {
		
		return adCheckDao.insertAdCheckProcess(adCheckProcessDto);
	}
	@Override
	public List<AdCheckProcessDto> findAdCheckProcessByAdId(String adId) {
		
		return adCheckDao.findAdCheckProcessByAdId(adId);
	}
	@Override
	public int updateAdCheckSts(AdCheckProcessDto AdCheckProcessDto) {
		
		return adCheckDao.updateAdCheckSts(AdCheckProcessDto);
	}
	@Override
	public int deleteAdCheckProcessByAdId(int id) {
		
		return adCheckDao.deleteAdCheckProcessByAdId(id);
	}
	@Override
	public int updateAdInstanceByAdId(AdInstanceDto adInstanceDto) {
		
		return adCheckDao.updateAdInstanceByAdId(adInstanceDto);
	}
	@Override
	public List<TAdCheckConfigDto> getTAdCheckConfigInfo() {
		
		return adCheckDao.getTAdCheckConfigInfo();
	}
	@Override
	public int insertTAdCheckProcess(TAdCheckProcessDto t_adCheckProcess) {
		
		return adCheckDao.insertTAdCheckProcess(t_adCheckProcess); 
		
	}
	@Override
	public List<TAdCheckProcessDto> findTAdCheckProcessByAdId(String adId) {
		
		return adCheckDao.finTAdCheckProcessByAdId(adId);
	}
	@Override
	public void deleteTAdCheckProcessByAdId(int adId) {
		
		adCheckDao.deleteTAdCheckProcessByAdId(adId);
		
	}

}
