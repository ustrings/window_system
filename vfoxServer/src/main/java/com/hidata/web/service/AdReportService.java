package com.hidata.web.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hidata.web.dto.AdInstanceDto;
import com.hidata.web.dto.AdMonthsDto;
import com.hidata.web.dto.AdReportDetailDto;
import com.hidata.web.util.Pager;

/**
 * 操作广告报告的Service
 * @author xiaoming
 * @date 2014-12-26
 */
public interface AdReportService {
	
	/**
	 * 获取广告报告表格内容（详情）
	 * @param map
	 * @param curPage
	 * @return
	 */
	public Pager getPager(Map<String, String> map, String curPage);
	
	
	/**
	 * 获取每支广告投放详情的 月份
	 * @param adId
	 * @return
	 */
	public List<AdMonthsDto> getMonthsByadId(String adId);
	
	/**
	 * 获取每支广告某个月份的详细
	 * @param adId
	 * @param month
	 * @return
	 */
	public List<AdReportDetailDto> getReportDetailsByMonthAndId(String adId, String month);
	
	/**
	 * 根据广告ID获取广告实体
	 * @param adId
	 * @return
	 */
	public AdInstanceDto getAdInstanceByAdId(String adId);
	
	
	/**
	 * 导出excel
	 * @param map 参数集合
	 * @return
	 */
	public HSSFWorkbook getHWB(Map<String,String> map);


	public Pager getTPager(Map<String, String> map, String curPage);
}
