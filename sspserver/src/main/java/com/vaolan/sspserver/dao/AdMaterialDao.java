package com.vaolan.sspserver.dao;

import java.util.List;

import com.vaolan.sspserver.model.AdExtLink;
import com.vaolan.sspserver.model.AdMaterial;


public interface AdMaterialDao {

	public abstract List<AdMaterial> getMaterialByAdId(String adId);
	
	public AdExtLink getAdExtLinkByAdId(String adId);

}