package com.hidata.ad.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hidata.ad.web.dao.AdExtLinkDao;
import com.hidata.ad.web.dto.AdExtLinkDto;
import com.hidata.ad.web.service.AdExtLinkService;
import com.hidata.framework.util.StringUtil;

@Service
public class AdExtLinkServiceImpl implements AdExtLinkService {
	
	@Autowired
	private AdExtLinkDao adExtLinkDao;
	@Override
	public AdExtLinkDto getAdExtLinkByadId(String adId) {
		if(StringUtil.isNotEmpty(adId)){
			List<AdExtLinkDto> list = adExtLinkDao.qryAdExtLinkByAdId(adId);
			if(list != null && list.size() == 1){
				return list.get(0);
			}
		}
		return null;
	}
	@Override
	public Boolean deleteAdExtLinkByadId(String adId) {
		Integer rows = adExtLinkDao.deleteAdExtLinkByAdId(adId);
		if(rows != null){
			return true;
		}
		return false;
	}

}
