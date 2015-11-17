package com.hidata.ad.web.dao;

import java.util.Date;
import java.util.List;

import com.hidata.ad.web.dto.AdInstanceHostDto;
import com.hidata.ad.web.dto.AdStatisticsViewDto;
import com.hidata.ad.web.dto.AdUrlHostShowDto;
import com.hidata.ad.web.model.AdHost;
import com.hidata.ad.web.model.AdStatInfo;
import com.hidata.ad.web.model.ChartStatistics;
import com.hidata.ad.web.model.MaterialStatInfo;

public interface ReportDao {
	
	// 类别展现量
	public static final int CHART_TYPE_VIEW = 1;
	// 类别点击量
	public static final int CHART_TYPE_CLICK = 2;
	
	// 时间范围
	public static final int TIME_TYPE_LAST_DAY = 1;
	public static final int TIME_TYPE_CURRENT_DAY = 2;
	public static final int TIME_TYPE_LAST_WEEK = 3;
	public static final int TIME_TYPE_CURRENT_WEEK = 4;
	public static final int TIME_TYPE_LAST_MONTH = 5;
	public static final int TIME_TYPE_CURRENT_MONTH = 6;
	
	//List<AdMaterialReportViewDto> calculateAdMaterialReportByTimeRange(Date start, Date end, int userid, Integer id);
	
	//广告物料报告
	List<MaterialStatInfo> calculateAdMaterialReportByTimeRange_new(String start, String end, int userid, Integer id);
	
	//List<AdReportViewDto> calculateAdReportByTimeRange(Date start, Date end, int userid, Integer adId);
	
	//报告 周晓明
	List<AdStatInfo> calculateAdReportByTimeRange_new(String start, String end, int userid, Integer adId);
	
	//List<ChartStatistics>  adMaterialViewOrClickChartStatistics(Date start, Date end, int userid, int index, int type, Integer id);
	List<ChartStatistics>  adMaterialViewOrClickChartStatistics_new(String start, String end, int userid, int index, int type, Integer id);
	List<ChartStatistics>  adMaterialViewOrClickChartStatistics_new(String start, String end, int userid, int index, int type, String ids);
	
	//List<ChartStatistics>  adViewOrClickChartStatistics(Date start, Date end, int userid, int index, int type, Integer id);
	
	//报告图表 周晓明
	List<ChartStatistics>  adViewOrClickChartStatistics_new(String start, String end, int userid, int index, int type, Integer id);
	List<ChartStatistics>  adViewOrClickChartStatistics_new(String start, String end, int userid, int index, int type, String ids);

	/**
	 * 首页广告报告
	 * @param start
	 * @param end
	 * @param userid
	 * @param adId
	 * @return
	 */
	public List<AdStatInfo> calculateIndexAdReportByTimeRange_new(String start,
			String end, int userid, Integer adId);
	
	/**
     * 查找该用户下广告的投放网址信息
     * @param start
     * @param end
     * @param userId
     * @param id
     * @return
     */
	List<AdInstanceHostDto> calculateAdHostReportByTimeRange_new(String start,String end, String adId);
    
	/**
	 * 根据域名查找
	 * @param urlHost
	 * @param urlHost2 
	 * @param end 
	 * @param adId 
	 * @return
	 */
	List<AdUrlHostShowDto> findAdHostByUrlHost(String start, String end, String urlHost, String adId);

	List<AdHost> findAdHostByAdId(String adId); 
	
	AdStatisticsViewDto calculateAdStatistics(String start, String end, int userid, String adId);
	
	AdStatisticsViewDto calculateCurrentAdStatistics(String start, String end, int userid);
}
