package com.hidata.ad.web.dao;

import java.util.List;

import com.hidata.ad.web.dto.AdExtLinkDto;

/**
 * 操作投放链接的DAO
 * @author xiaoming
 *
 */
public interface AdExtLinkDao {
	/**
	 * 根据广告计划ID查询投放链接
	 * @param adId
	 * @return
	 */
	public List<AdExtLinkDto> qryAdExtLinkByAdId(String adId);
	
	/**
	 * 根据广告链接删除投放链接
	 * @param adId
	 * @return
	 */
	public Integer deleteAdExtLinkByAdId(String adId);
}
