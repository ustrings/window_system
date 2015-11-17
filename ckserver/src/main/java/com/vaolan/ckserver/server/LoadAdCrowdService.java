package com.vaolan.ckserver.server;




public interface LoadAdCrowdService {

	public void loadAdCrowd() throws Exception;

	public String getCrowdIdsByAdId(String adId) throws Exception;

	
}