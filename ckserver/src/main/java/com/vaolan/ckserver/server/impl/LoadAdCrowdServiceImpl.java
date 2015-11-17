package com.vaolan.ckserver.server.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaolan.ckserver.dao.AdCrowdLinkDao;
import com.vaolan.ckserver.dao.AdInstanceDao;
import com.vaolan.ckserver.model.AdCrowdLink;
import com.vaolan.ckserver.model.AdInstance;
import com.vaolan.ckserver.server.LoadAdCrowdService;

/**
 * 加载广告人群 放入redis中
 * @author sunly
 * 
 */
@Service
public class LoadAdCrowdServiceImpl implements LoadAdCrowdService {

	public static Logger logger = Logger.getLogger(LoadAdCrowdServiceImpl.class);
	
	private Map<String,String> adCrowdMap = new HashMap<String,String>();
	
	@Autowired
	private AdInstanceDao adInstanceDao;
	
	@Autowired
	private AdCrowdLinkDao adCrowdLinkDao;
	
	/**
	 * 
	 * @throws Exception
	 */
	@Override
	public void loadAdCrowd()throws Exception{
		
		List<AdInstance> adInstanceList = adInstanceDao.queryAdInstanceListUsing();
		Map<String,String> tempMap = new HashMap<String,String>();
		for(AdInstance obj:adInstanceList ){
			String adId = obj.getId();
			
			List<AdCrowdLink> adCrowdLinkList = adCrowdLinkDao.queryAdCrowdLinkByAdId(adId);
			StringBuilder crowdIds = new StringBuilder();
			for(int i=0;i<adCrowdLinkList.size();i++){
				AdCrowdLink adCrowdLink = adCrowdLinkList.get(i);
				if(i==0){
					crowdIds.append(adCrowdLink.getCrowdId());
				}else{
					crowdIds.append(",").append(adCrowdLink.getCrowdId());
				}
			}
			System.out.println("adId:"+adId+",crowdIds:"+crowdIds);
			tempMap.put(adId, crowdIds.toString());
		}
		adCrowdMap.clear();//情况之前的map
		adCrowdMap=tempMap;
		
	}
	@Override
	public String getCrowdIdsByAdId(String adId)throws Exception{
		String crowdIds = adCrowdMap.get(adId);
		if(crowdIds==null){
			crowdIds="";
		}
		return crowdIds;
	}
}
