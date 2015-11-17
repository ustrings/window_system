package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.dto.AdMLinkDto;
import com.hidata.ad.web.dto.AdMaterialLinkDto;
import com.hidata.ad.web.model.AdMLink;
import com.hidata.ad.web.model.AdMaterial;
import com.hidata.ad.web.model.AdMaterialCache;

public interface AdMLinkDao {
	
	public List<AdMLink> findAdMLinkListByMaterialId (int materialId);
	
	public List<AdMLink> findMaterialToCheck();
	
	public void editAdMLinkForCheckJob(AdMLink adMLink);
	
	public List<AdMLinkDto> findAdMLinkDtoByAdId(int adId);
	
	public List<AdMLinkDto> findAdMLinkDtoByAdId(int adId, String status);
	
	public List<AdMaterialLinkDto> findAdMLinkListByAdId(String adId);
	
	public AdMLink getAdMLinkById(String id) ;
}
