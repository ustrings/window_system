package com.hidata.web.service;

import java.util.Map;

import com.hidata.web.dto.AdExtLinkDto;
import com.hidata.web.util.Pager;

/**
 * 操作广告统计列表的Service
 * @author xiaoming
 * @date 2014-12-25
 */
public interface AdCountService {
	
	/**
	 * 分页查询数据，页面表格展示
	 * @param map
	 * @return
	 */
	public Pager getPager(Map<String,String> map, String curPage);
	
	/**
	 * 投放状态的改变
	 * @param sts
	 * @param adId
	 * @return
	 */
	public Boolean updateSts(String sts, String adId);
	
	/**
	 * 根据广告ID 查找关联对象
	 * @param adId
	 * @return
	 */
	public AdExtLinkDto getAdExtLinkByAdId(String adId);

	public Pager getTPager(Map<String, String> map, String curPage);
}
