package com.hidata.ad.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.AdCategoryDao;
import com.hidata.ad.web.dao.AdMLinkDao;
import com.hidata.ad.web.dao.AdMaterialDao;
import com.hidata.ad.web.dto.AdCategoryDto;
import com.hidata.ad.web.dto.AdMLinkDto;
import com.hidata.ad.web.dto.AdMaterialLinkDto;
import com.hidata.ad.web.model.AdMLink;
import com.hidata.ad.web.model.AdMaterial;
import com.hidata.ad.web.model.AdMaterialCache;
import com.hidata.ad.web.service.AdMLinkService;
import com.hidata.framework.db.DBManager;

@Component
public class AdMLinkServiceImpl implements AdMLinkService {

	@Autowired
	private AdMLinkDao adMLinkDao;
	
	@Autowired
	private AdCategoryDao adCategoryDao;
	
	@Override
	public List<AdMLink> findAdMLinkListByMaterialId(int materialId) {
		return adMLinkDao.findAdMLinkListByMaterialId(materialId);
	}

	@Override
	public List<AdCategoryDto> findAdCategoryDtoListByAdId(String adId) {
		return adCategoryDao.findAdCategoryDtoListByAdId(adId);
	}

	@Override
	public List<AdMLink> findMaterialToCheck() {
		return adMLinkDao.findMaterialToCheck();
	}

	@Override
	public void editAdMLinkForCheckJob(AdMLink adMLink) {
		adMLinkDao.editAdMLinkForCheckJob(adMLink);
	}

	@Override
	public List<AdMLinkDto> findAdMLinkDtoByAdId(int adId) {
		return adMLinkDao.findAdMLinkDtoByAdId(adId);
	}
	
	@Override
	public List<AdMLinkDto> findAdMLinkDtoByAdId(int adId, String status) {
		return adMLinkDao.findAdMLinkDtoByAdId(adId, status);
	}
	
	@Override
	public List<AdMaterialLinkDto> findAdMLinkListByAdId(String adId) {
		return adMLinkDao.findAdMLinkListByAdId(adId);
	}

	@Override
	public AdMLink getAdMLinkById(String id) {
		return adMLinkDao.getAdMLinkById(id);
	}

}
