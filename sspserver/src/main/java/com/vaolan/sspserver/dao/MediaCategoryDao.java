package com.vaolan.sspserver.dao;

import java.util.List;


public interface MediaCategoryDao {

	/**
	 * 查询广告计划  所要求的媒体类型。<br>
	 * 此方法只返回media_category_code字段<br>
	 * @param adId
	 * @return List<MediaCategory>
	 * */
	public abstract List<String> getMediaCategoryCodeByAdId(String adId);

}