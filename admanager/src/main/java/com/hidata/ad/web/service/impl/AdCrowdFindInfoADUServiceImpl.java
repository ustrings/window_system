package com.hidata.ad.web.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hidata.ad.web.dao.AdCrowdFindInfoADUDao;
import com.hidata.ad.web.dao.AdCrowdFindInfoDao;
import com.hidata.ad.web.dao.CrowdPortraitDao;
import com.hidata.ad.web.dao.IIntrestCrowdDao;
import com.hidata.ad.web.dto.AdCrowdFindInfo;
import com.hidata.ad.web.dto.AdCrowdManageDto;
import com.hidata.ad.web.dto.GisCrowdDto;
import com.hidata.ad.web.dto.IntegerModelCrowdDto;
import com.hidata.ad.web.dto.KeyWordCrowdDto;
import com.hidata.ad.web.dto.LatLngDto;
import com.hidata.ad.web.dto.UrlCrowdDto;
import com.hidata.ad.web.model.CrowdPortrait;
import com.hidata.ad.web.model.IntrestLabelCrowd;
import com.hidata.ad.web.service.AdCrowdFindInfoADUService;
import com.hidata.ad.web.session.SessionContainer;
import com.hidata.ad.web.util.GetLatAndLngByBaidu;
import com.hidata.ad.web.util.StateConstant;

/**
 * 人群操作接口
 * @author ZTD
 *
 */
@Component
public class AdCrowdFindInfoADUServiceImpl implements AdCrowdFindInfoADUService {
	@Autowired
	private AdCrowdFindInfoADUDao adCrowdFindInfoADUDao;
	
	@Autowired
	private IIntrestCrowdDao iIntrestCrowdDao;
	
	@Autowired
	private AdCrowdFindInfoDao adCrowdFindInfoDao;
	@Autowired
	private CrowdPortraitDao crowdPortraitDao;

	@Override
	@Transactional(rollbackFor=Exception.class) 
	public int addAdCrowdManageDto(HttpServletRequest request) throws Exception {
		
		int status = -1;
		try {
			String crowdId = request.getParameter("crowdId");
			AdCrowdManageDto manageDto = null;
			if(StringUtils.isEmpty(crowdId)){
				manageDto = assembleDtoFromRequest(request);
				// 插入 CrowdFindInfoADUDao
				crowdId = adCrowdFindInfoADUDao.addAdCrowFindInfo(manageDto.getAdCrowdFindInfo()) + "";
			}else{
				manageDto = assembleDtoFromRequestForEdit(request);
				
				int ret = adCrowdFindInfoADUDao.editAdCrowFindInfo(manageDto.getAdCrowdFindInfo());
				if(ret>0){
					delOldCrowdInfoByCrowdId(crowdId);//删除
				}else{
					//保存失败
					return status;
				}
			}
			
			// 插入 keyWord
			List<KeyWordCrowdDto> keyWordDtos = manageDto.getKeyWordCrowdDtos();
			if(keyWordDtos!=null && keyWordDtos.size() > 0) {
				for (KeyWordCrowdDto keyWordDto  : keyWordDtos) {
					keyWordDto.setCrowdId(crowdId);
					adCrowdFindInfoADUDao.addKeyWordCrowd(keyWordDto);
				}
			}
			
			// 插入 url
			List<UrlCrowdDto> urlDtos = manageDto.getUrlCrowdDtos();
			if(urlDtos != null && urlDtos.size() > 0) {
				for (UrlCrowdDto urlDto : urlDtos) {
					urlDto.setCrowdId(crowdId);
					adCrowdFindInfoADUDao.addUrlCrowd(urlDto);
				}
			}
			
			// 插入 gis
			List<GisCrowdDto> gisDtos = manageDto.getGisCrowdDtos();
			if(gisDtos != null && gisDtos.size() > 0) {
				for (GisCrowdDto gisDto : gisDtos) {
					gisDto.setCrowdId(crowdId);
					adCrowdFindInfoADUDao.addGisCrowd(gisDto);
				}
			}
			
			// 插入 智能人群
			IntegerModelCrowdDto integerModelDto = manageDto.getIntegerModelCrowdDto();
			if(integerModelDto != null) {
				integerModelDto.setCrowdId(crowdId);
				// 插入标签定向
				adCrowdFindInfoADUDao.addIntegerModelCrowdDto(integerModelDto);	
			}
			
			List<IntrestLabelCrowd> interestlabels = manageDto.getInterestlabels();
			if (interestlabels != null) {
				for (IntrestLabelCrowd label : interestlabels){
					label.setCrowdId(crowdId);
					adCrowdFindInfoADUDao.addIntrestCrowdInfo(label);
				}
			}
			//人群画像
			List<CrowdPortrait> crowdPortraitList = manageDto.getCrowdPortraitList();
			if(crowdPortraitList!=null&&crowdPortraitList.size()>0){
				for(CrowdPortrait crowdPortrait:crowdPortraitList){
					crowdPortrait.setCrowdId(crowdId);
					crowdPortraitDao.insertObject(crowdPortrait);
				}
			}
			status = 1;
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
		
		return status;
	}
	
	private static String getUTF8String(String source) {
		String result  = null;
		try {
			result = new String(source.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 编辑人群
	 * @param request
	 * @return
	 */
	private AdCrowdManageDto assembleDtoFromRequestForEdit(HttpServletRequest request) {
		AdCrowdManageDto adCrowdManageDto= assembleDtoFromRequest(request);
		String crowdId = request.getParameter("crowdId");
		AdCrowdFindInfo adCrowdFindInfo = adCrowdManageDto.getAdCrowdFindInfo();
		adCrowdFindInfo.setCrowdId(crowdId);
		return adCrowdManageDto;
	} 

	private void delOldCrowdInfoByCrowdId(String crowdId) throws Exception {
		
		// 删除 keyWord
		adCrowdFindInfoADUDao.delKeyWordCrowdByCrowdId(crowdId);
		// 删除 url
		adCrowdFindInfoADUDao.delUrlCrowdByCrowdId(crowdId);
		// 删除 gis
		adCrowdFindInfoADUDao.delGisCrowd(crowdId);
		// 删除 智能人群
		adCrowdFindInfoADUDao.delIntegerModelCrowdDto(crowdId);
		//删除 兴趣
		adCrowdFindInfoADUDao.delIntrestCrowdInfoByCrowdId(crowdId);
		//删除人群画像
		crowdPortraitDao.delCrowdPortraitByCrowdId(crowdId);
	}
	private AdCrowdManageDto assembleDtoFromRequest(HttpServletRequest request) {
		AdCrowdManageDto manageDto = new AdCrowdManageDto();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		
		// ===========组装 AdCrowdFindInfo=================
		AdCrowdFindInfo adCrowdFindInfo = new AdCrowdFindInfo();
		adCrowdFindInfo.setCreateDate(date);
		/*
		 * crowdName:crowdName,expressDate:expressDate,isJisoujitou:isJisoujitou,
					kwUrlFetchCicle:kwUrlFetchCicle,searchType:searchType,keyWd:keyWd,
					keyWdSearchDate:keyWdSearchDate,keyWdSearchNum:keyWdSearchNum,urlContent:urlContent
					,urlMatchDate:urlMatchDate,urlMatchNum:urlMatchNum,modelType:modelType,
					modelFetchCicle:modelFetchCicle,urlIsJisoujitou:urlIsJisoujitou,
					modelMatchUrl:modelMatchUrl,lbsFetchCicle:lbsFetchCicle,addrAndDistances:addrAndDistances
		 */
		String crowdName = request.getParameter("crowdName");
		if(!StringUtils.isEmpty(crowdName)) {
			adCrowdFindInfo.setCrowdName(crowdName);
		}
		String expressDate = request.getParameter("expressDate");
		if(!StringUtils.isEmpty(expressDate)) {
			adCrowdFindInfo.setExpressDate(expressDate);
		}
		adCrowdFindInfo.setCrowdSts(StateConstant.CROWD_STATE_RUN);
		adCrowdFindInfo.setUserId(SessionContainer.getSession().getUserId() + "");
		String directType =request.getParameter("direct_type");
		adCrowdFindInfo.setDirectType(directType);
		manageDto.setAdCrowdFindInfo(adCrowdFindInfo);
		
		// ===========组装 AdCrowdFindInfo=================
		
		
		// ===========设置关键词定向==============
		List<KeyWordCrowdDto> keyWordCrowdDtos = new ArrayList<KeyWordCrowdDto>();
		String kwUrlFetchCicle = request.getParameter("kwUrlFetchCicle");
		String keyWds = request.getParameter("keyWd");
		String isJisoujitou = request.getParameter("isJisoujitou");
		if (!StringUtils.isEmpty(keyWds)) {
			String[] keyWdArr = keyWds.split("\\n");
			for (String keyWd : keyWdArr) {
				KeyWordCrowdDto keyWordCrowdDto = new KeyWordCrowdDto();
				// 设置 即搜即投
				
				if(!StringUtils.isEmpty(isJisoujitou)) {
					keyWordCrowdDto.setIsJisoujitou(isJisoujitou);
				}
				// 获取 关键字 和 url 的提取周期
				// 设置提取周期
				if(!StringUtils.isEmpty(kwUrlFetchCicle)) {
					keyWordCrowdDto.setFetchCicle(kwUrlFetchCicle);
				}
				// 设置搜索类型
				String searchType = request.getParameter("searchType");
				if(!StringUtils.isEmpty(searchType)) {
					// 搜索类型1:综合搜索，2:电商搜索,3:全部搜索
					if(searchType.equals("1")) {
						keyWordCrowdDto.setSearchType(searchType);
					} else if(searchType.equals("2")) {
						keyWordCrowdDto.setSearchType(searchType);
					} else {
						keyWordCrowdDto.setSearchType("3");
					}
				}
				
				/*
				 * crowdName:crowdName,expressDate:expressDate,isJisoujitou:isJisoujitou,
							kwUrlFetchCicle:kwUrlFetchCicle,searchType:searchType,keyWd:keyWd,
							keyWdSearchDate:keyWdSearchDate,keyWdSearchNum:keyWdSearchNum,urlContent:urlContent
							,urlMatchDate:urlMatchDate,urlMatchNum:urlMatchNum,modelType:modelType,
							modelFetchCicle:modelFetchCicle,urlIsJisoujitou:urlIsJisoujitou,
							modelMatchUrl:modelMatchUrl,lbsFetchCicle:lbsFetchCicle,addrAndDistances:addrAndDistances
				 */
				String keyWdSearchDate = request.getParameter("keyWdSearchDate");
				if(!StringUtils.isEmpty(keyWdSearchDate)) {
					keyWordCrowdDto.setSearchDate(keyWdSearchDate);
				}
				
				String keyWdSearchNum = request.getParameter("keyWdSearchNum");
				if(!StringUtils.isEmpty(keyWdSearchNum)) {
					keyWordCrowdDto.setSearchNum(keyWdSearchNum);
				}
				
				keyWordCrowdDto.setStsDate(date);
				keyWordCrowdDto.setSts(StateConstant.CROWD_STATE_RUN);
				keyWordCrowdDto.setKeyWd(keyWd.replaceAll("\\t", ""));
				
				String keyWdMatchType = request.getParameter("keyWdMatchType");
				keyWordCrowdDto.setMatchType(keyWdMatchType);
				keyWordCrowdDtos.add(keyWordCrowdDto);
			}
		}
		
		
		// ===========设置关键词定向==============
		manageDto.setKeyWordCrowdDtos(keyWordCrowdDtos);
		
		// 设置 url 人群定向
		List<UrlCrowdDto> urlCrowdDtos = new ArrayList<UrlCrowdDto>();
		String urlContents = request.getParameter("urlContent");
		//String urlIsJisoujitou = request.getParameter("urlIsJisoujitou");
		
		if(!StringUtils.isEmpty(urlContents)) {
		String[] urlContenArr = urlContents.split("\\n");
		
		for(String urlContent : urlContenArr) {
			UrlCrowdDto urlCrowdDto = new UrlCrowdDto();
			if(!StringUtils.isEmpty(isJisoujitou)) {
				urlCrowdDto.setIsJisoujitou(isJisoujitou);
			}
			if(!StringUtils.isEmpty(kwUrlFetchCicle)) {
				urlCrowdDto.setFetchCicle(kwUrlFetchCicle);
			}
			String urlMatchDate= request.getParameter("urlMatchDate");
			if(!StringUtils.isEmpty(urlMatchDate)) {
				urlCrowdDto.setMatchDate(urlMatchDate);
			}
			
			String urlMatchNum= request.getParameter("urlMatchNum");
			if(!StringUtils.isEmpty(urlMatchNum)) {
				urlCrowdDto.setMatchNum(urlMatchNum);
			}
			urlCrowdDto.setSts(StateConstant.CROWD_STATE_RUN);
			urlCrowdDto.setStsDate(date);
			urlCrowdDto.setUrl(urlContent.replaceAll("\\t", ""));
			
			String urlMatchType = request.getParameter("urlMatchType");
			urlCrowdDto.setMatchType(urlMatchType);
			urlCrowdDtos.add(urlCrowdDto);
		}
		}
		manageDto.setUrlCrowdDtos(urlCrowdDtos);
		
		/*
		 * crowdName:crowdName,expressDate:expressDate,isJisoujitou:isJisoujitou,
					kwUrlFetchCicle:kwUrlFetchCicle,searchType:searchType,keyWd:keyWd,
					keyWdSearchDate:keyWdSearchDate,keyWdSearchNum:keyWdSearchNum,urlContent:urlContent
					,urlMatchDate:urlMatchDate,urlMatchNum:urlMatchNum,modelType:modelType,
					modelFetchCicle:modelFetchCicle,urlIsJisoujitou:urlIsJisoujitou,
					modelMatchUrl:modelMatchUrl,lbsFetchCicle:lbsFetchCicle,addrAndDistances:addrAndDistances
		 */
		//  设置 智能人群定向
		IntegerModelCrowdDto integerModelCrowdDto = new IntegerModelCrowdDto();
		String modelType = request.getParameter("modelType");
		if(!StringUtils.isEmpty(modelType)) {
			integerModelCrowdDto.setModelType(modelType);
		}
		String modelFetchCicle = request.getParameter("modelFetchCicle");
		if(!StringUtils.isEmpty(modelFetchCicle)) {
			integerModelCrowdDto.setFetchCicle(modelFetchCicle);
		}
		String modelMatchUrl = request.getParameter("modelMatchUrl");
		if(!StringUtils.isEmpty(modelMatchUrl)) {
			integerModelCrowdDto.setMatchUrl(modelMatchUrl);
		}
		integerModelCrowdDto.setSts(StateConstant.CROWD_STATE_RUN);
		integerModelCrowdDto.setStsDate(date);
		if(integerModelCrowdDto.getMatchUrl() != null &&
				integerModelCrowdDto.getFetchCicle()!=null &&
				integerModelCrowdDto.getModelType()!=null
				) {
			manageDto.setIntegerModelCrowdDto(integerModelCrowdDto);
		}
		
		// 设置 LBS 人群定向
		List<GisCrowdDto> gisCrowdDtos = new ArrayList<GisCrowdDto>();
		String addrAndDistances = request.getParameter("addrAndDistances");
		if(!StringUtils.isEmpty(addrAndDistances) && !addrAndDistances.equals(":")) {
			String[] addrAndDistanceArr = addrAndDistances.split(",");
			
			for(String addrAndDistance : addrAndDistanceArr) {
				String addr = addrAndDistance.split(":")[0];
				String distance = addrAndDistance.split(":")[1];
				LatLngDto dto = null;
				try {
					dto = GetLatAndLngByBaidu.getCoordinate(addr);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(dto != null) {
					GisCrowdDto gisCrowdDto = new GisCrowdDto();
					String lbsFetchCicle = request.getParameter("lbsFetchCicle");
					if(!StringUtils.isEmpty(lbsFetchCicle)) {
						gisCrowdDto.setFetchCicle(lbsFetchCicle);
					}
					gisCrowdDto.setSts(StateConstant.CROWD_STATE_RUN);
					gisCrowdDto.setCentryAddr(addr);
					gisCrowdDto.setDistanceValue(distance);
					gisCrowdDto.setxPosition(dto.getLat());
					gisCrowdDto.setyPosition(dto.getLng());
					gisCrowdDto.setStsDate(date);
					gisCrowdDtos.add(gisCrowdDto);
				}
				
			}
		}
		
		
		manageDto.setGisCrowdDtos(gisCrowdDtos);
		
		
		List<IntrestLabelCrowd> interestlabels = new ArrayList<IntrestLabelCrowd>();
		String interestLabels = request.getParameter("interestLabels");
		String interestlabelCircle = request.getParameter("interestlabelCircle");
		if (StringUtils.isNotEmpty(interestLabels)){
			String[] labelsArray = interestLabels.split(",");
			
			IntrestLabelCrowd  interest = null;
			if(!StringUtils.isEmpty(interestlabelCircle)){
				for (String label : labelsArray){
					interest = new IntrestLabelCrowd();
					interest.setSts(StateConstant.CROWD_STATE_RUN);
					interest.setSts_date(date);
					interest.setLabelValue(label);
					interest.setFetchCicle(interestlabelCircle);
					interestlabels.add(interest);
				}
			}
			manageDto.setInterestlabels(interestlabels);
		}
		
		//人群画像
		String sex = request.getParameter("sex");
		String ageRanges = request.getParameter("age_ranges");
		if(!StringUtils.isEmpty(ageRanges)){
			String[] ageRangeArr = ageRanges.split(",");
			List<CrowdPortrait> crowdPortraitList = new ArrayList<CrowdPortrait>();
			for(int i=0;i<ageRangeArr.length;i++){
				CrowdPortrait crowdPortrait = new CrowdPortrait();
				crowdPortrait.setSex(sex);
				crowdPortrait.setAgeRange(ageRangeArr[i]);
				crowdPortraitList.add(crowdPortrait);
			}
			manageDto.setCrowdPortraitList(crowdPortraitList);
		}
		return manageDto;
	}
	
	
}
