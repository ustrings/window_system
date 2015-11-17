package com.hidata.web.dao;

import java.util.List;

import com.hidata.web.dto.AdInstanceDto;
import com.hidata.web.dto.AdMonthsDto;
import com.hidata.web.dto.AdReportDetailDto;

/**
 * 操作广告报告的DAO
 * @author xiaoming
 * @date 2014-12-26
 */
public interface AdReportDao {
	
	/**
	 * 获取该 广告的所有统计月份
	 * @param adId
	 * @return
	 */
	public List<AdMonthsDto> qryMonthsByAdId(String adId);
	
	/**
	 * 查询某个广告某个月份投放的详细情况
	 * @param adId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<AdReportDetailDto> qryReportDetailsByMonthAndAdId(String adId, String startTime, String endTime);
	
	/**
	 * 根据主键查询实体
	 * @param adId
	 * @return
	 */
	public List<AdInstanceDto> qryAdInstanceByPkId(String adId);
	
	/**
	 * 获取所导出的报告（统计）
	 * @param adIds
	 * @return
	 */
	public List<AdReportDetailDto> qryAdReportDetailByExcel(String adIds);
	/**
	 * 获取所导出的报告(详情)
	 * @param adIds
	 * @return
	 */
	public List<AdReportDetailDto> qryAdReportDetailByMonthDetail(String adId);
}
