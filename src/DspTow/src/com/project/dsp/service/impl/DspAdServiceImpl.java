package com.project.dsp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.dsp.dao.DspAdDao;
import com.project.dsp.entity.Dsp_Ad;
import com.project.dsp.entity.Dsp_Domain;
import com.project.dsp.service.DspAdService;

@Service("dspusersService")
@Transactional
public class DspAdServiceImpl implements DspAdService{
	
	@Resource
    private DspAdDao dspAdDao;
	
	public List<Dsp_Ad> queryDspAdList(Dsp_Ad dsp_Ad) {
		return this.dspAdDao.queryDspAdList(dsp_Ad);
	}

	public int updateDspAdStatus(Map<String, Object> adMap) {
		return this.dspAdDao.updateDspAdStatus(adMap);
	}

	public List<Dsp_Ad> queryOneDspAdList(int id) {
		return this.dspAdDao.queryOneDspAdList(id);
	}

	public List<Dsp_Domain> queryOneDspDomainList(int ad_id) {
		return this.dspAdDao.queryOneDspDomainList(ad_id);
	}

	public int deleteDspAd(int id) {
		return this.dspAdDao.deleteDspAd(id);
	}

	public int deleteDspDomain(int ad_id) {
		return this.dspAdDao.deleteDspDomain(ad_id);
	}

	public int insertDspAd(Dsp_Ad ad) {
		return this.dspAdDao.insertDspAd(ad);
	}

	public int insertDspDomain(Dsp_Domain domain) {
		return this.dspAdDao.insertDspDomain(domain);
	}

	public List<Dsp_Ad> queryOneDspAdListPrio(int prio) {
		return this.dspAdDao.queryOneDspAdListPrio(prio);
	}

	public int updateDspAdNum(int id) {
		return this.dspAdDao.updateDspAdNum(id);
	}

	public int updateDspDomainNum(int id) {
		return this.dspAdDao.updateDspDomainNum(id);
	}

	public String queryAdbyDomaid(int domainId) {
		// TODO Auto-generated method stub
		return this.dspAdDao.queryAdbyDomaid(domainId);
	}

	@Override
	public int updateDspAd(Dsp_Ad ad) {
		// TODO Auto-generated method stub
		return dspAdDao.updateDspAd(ad);
	}

	@Override
	public int updateDspDomain(Dsp_Domain domain) {
		// TODO Auto-generated method stub
		return dspAdDao.updateDspDomain(domain);
	}

	@Override
	public String insertORUpdate(Dsp_Ad ad) {
		// TODO Auto-generated method stub
		String[] timeStr = ad.getTime().split(" - ");
		ad.setBegin_time(timeStr[0]);
		ad.setEnd_time(timeStr[1]);
		if(ad.getId()==0){
			//添加信息
			int adCount = dspAdDao.insertDspAd(ad);
			if(adCount > 0){
				//判断domain是否为空
				
				if(ad.getDomain() != null && ad.getDomain().trim().toString().length() > 0){
					//获取主键并赋值
					int domainCount = dspAdDao.insertDspDomain(new Dsp_Domain(ad.getId(),ad.getDomain()));
					if(domainCount>0){
						return "/Admin/query";
					}
				}else{
					return "/Admin/query";
				}
			}
		}else{
			int adCount =dspAdDao.updateDspAd(ad);
			if(adCount > 0){
				if(ad.getDomain() != null && ad.getDomain().trim().toString().length() > 0){
					
					int domainCount  = dspAdDao.updateDspDomain(new Dsp_Domain(ad.getId(),ad.getDomain()));
					if(domainCount>0){
						return "/Admin/query";
					}
				}else{
					return "/Admin/query";
				}
				
				
			}
		}
		return "0";
	}
	
}
