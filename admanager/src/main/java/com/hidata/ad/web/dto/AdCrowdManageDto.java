package com.hidata.ad.web.dto;

import java.util.List;

import com.hidata.ad.web.model.CrowdPortrait;
import com.hidata.ad.web.model.IntrestLabelCrowd;

public class AdCrowdManageDto {
	private AdCrowdFindInfo adCrowdFindInfo;
	private List<KeyWordCrowdDto> keyWordCrowdDtos;
	private List<UrlCrowdDto> urlCrowdDtos;
	private List<GisCrowdDto> gisCrowdDtos;
	private IntegerModelCrowdDto integerModelCrowdDto;
	private List<IntrestLabelCrowd> interestlabels;
	
	private List<CrowdPortrait> crowdPortraitList; 

	public List<CrowdPortrait> getCrowdPortraitList() {
		return crowdPortraitList;
	}
	public void setCrowdPortraitList(List<CrowdPortrait> crowdPortraitList) {
		this.crowdPortraitList = crowdPortraitList;
	}
	public AdCrowdFindInfo getAdCrowdFindInfo() {
		return adCrowdFindInfo;
	}
	public void setAdCrowdFindInfo(AdCrowdFindInfo adCrowdFindInfo) {
		this.adCrowdFindInfo = adCrowdFindInfo;
	}
	public List<KeyWordCrowdDto> getKeyWordCrowdDtos() {
		return keyWordCrowdDtos;
	}
	public void setKeyWordCrowdDtos(List<KeyWordCrowdDto> keyWordCrowdDtos) {
		this.keyWordCrowdDtos = keyWordCrowdDtos;
	}
	public List<UrlCrowdDto> getUrlCrowdDtos() {
		return urlCrowdDtos;
	}
	public void setUrlCrowdDtos(List<UrlCrowdDto> urlCrowdDtos) {
		this.urlCrowdDtos = urlCrowdDtos;
	}
	public List<GisCrowdDto> getGisCrowdDtos() {
		return gisCrowdDtos;
	}
	public void setGisCrowdDtos(List<GisCrowdDto> gisCrowdDtos) {
		this.gisCrowdDtos = gisCrowdDtos;
	}
	public IntegerModelCrowdDto getIntegerModelCrowdDto() {
		return integerModelCrowdDto;
	}
	public void setIntegerModelCrowdDto(IntegerModelCrowdDto integerModelCrowdDto) {
		this.integerModelCrowdDto = integerModelCrowdDto;
	}
	public List<IntrestLabelCrowd> getInterestlabels() {
		return interestlabels;
	}
	public void setInterestlabels(List<IntrestLabelCrowd> interestlabels) {
		this.interestlabels = interestlabels;
	}	
}
