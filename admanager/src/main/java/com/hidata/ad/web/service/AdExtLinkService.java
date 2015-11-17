package com.hidata.ad.web.service;

import com.hidata.ad.web.dto.AdExtLinkDto;

/**
 * 操作AdExtLink业务逻辑类
 * @author xiaoming
 *
 */
public interface AdExtLinkService {
	/**
	 * 根据广告计划ID查询投放链接
	 * @param adId
	 * @return
	 */
	public AdExtLinkDto getAdExtLinkByadId(String adId);
	
	/**
	 * 根据广告计划ID删除投放链接
	 * @param adId
	 * @return
	 */
	public Boolean deleteAdExtLinkByadId(String adId);
}
