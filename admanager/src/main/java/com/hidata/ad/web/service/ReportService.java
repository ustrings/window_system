package com.hidata.ad.web.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hidata.ad.web.dto.AdInstanceHostDto;
import com.hidata.ad.web.dto.AdReportViewDto;
import com.hidata.ad.web.dto.AdStatisticsViewDto;
import com.hidata.ad.web.dto.AdUrlHostShowDto;
import com.hidata.ad.web.model.AdHost;
import com.hidata.ad.web.model.AdStatInfo;
import com.hidata.ad.web.model.MaterialStatInfo;

public interface ReportService {
	
	// 图表类型：广告物料
	public static final int REPORT_TYPE_MATERIAL = 1;
	// 图表类型：广告
	public static final int REPORT_TYPE_AD = 2;
	
	//public List<AdMaterialReportViewDto> calculateAdMaterialReportByTimeRange(String start, String end, int userid, Integer id);
	
	//周晓明 广告物料报告
	public List<MaterialStatInfo> calculateAdMaterialReportByTimeRange_new(String start, String end, int userid, Integer id);
	
	public List<AdReportViewDto> calculateAdReportByTimeRange(String start, String end, int userid, Integer adId);
	//周晓明  报告表格
	public List<AdStatInfo> calculateAdReportByTimeRange_new(String start, String end, int userid, Integer adId);
	
	public Map<String,Object> adMaterialViewOrClickChartStatistics(String start, String end, int userid, int index, int type, int reportType, Integer id);
	// 
	public Map<String,Object> adMaterialViewOrClickChartStatistics(String start, String end, int userid, int index, int type, int reportType, String ids);
	
	//导出excel报告
	public HSSFWorkbook getHWB(List<AdStatInfo> list);
	
	public HSSFWorkbook getHWB2(List<AdStatInfo> list);

	public Map<String, Object> indexMaterialViewOrClickChartStatistics(String start,
			String end, int userid, int index, int type, int reportType,
			String ids);

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
     * 统计广告投放网址的信息
     * @param string
     * @param string2
     * @param userId
     * @param id
     * @return
     */
	public List<AdInstanceHostDto> calculateAdHostReportByTimeRange_new(String string,
			String string2, String adId);
    
	/**
	 * 根据域名查找
	 * @param urlHost
	 * @param adId 
	 * @param urlHost2 
	 * @param string 
	 * @return
	 */
	public List<AdUrlHostShowDto> findAdHostByUrlHost(String start, String end,String urlHost, String adId);
    
	/**
	 * 根据广告Id查找该广告投放的网址
	 * @param adId
	 * @return
	 */
	public List<AdHost> findAdHostByAdId(String adId);
	
	//广告的展示和点击地址信息
    public HSSFWorkbook getUrlBook(List<AdInstanceHostDto> list);
    //单个网址的展示
	public HSSFWorkbook getUrlHostBook(List<AdUrlHostShowDto> hostlist);
	
	public AdStatisticsViewDto calculateAdStatistics(String start, String end, int userid, String adId);
	
	public AdStatisticsViewDto calculateCurrentAdStatistics(String start, String end, int userid);
		
}
