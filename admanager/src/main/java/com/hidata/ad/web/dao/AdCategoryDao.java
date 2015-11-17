package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.dto.AdCategoryDto;
import com.hidata.ad.web.dto.AdCategoryLinkDto;

public interface AdCategoryDao {

	/**
	 * 查询广告分类   根据adCategoryDto 对象
	 * @param adCategoryDto
	 * @return
	 */
	public List<AdCategoryDto> findAdCategoryDtoListByCondition(
			AdCategoryDto adCategoryDto);

	public List<AdCategoryDto> findAdCategoryDtoListByAdId(String adId);

	public int insertAdCategoryLink(AdCategoryLinkDto adCategoryLinkDto);

}
