package com.hidata.ad.web.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.hidata.ad.web.dao.ReportDao;
import com.hidata.ad.web.dto.AdInstanceHostDto;
import com.hidata.ad.web.dto.AdReportViewDto;
import com.hidata.ad.web.dto.AdStatisticsViewDto;
import com.hidata.ad.web.dto.AdUrlHostShowDto;
import com.hidata.ad.web.model.AdHost;
import com.hidata.ad.web.model.AdStatInfo;
import com.hidata.ad.web.model.ChartStatistics;
import com.hidata.ad.web.model.MaterialStatInfo;
import com.hidata.ad.web.service.ReportService;
import com.hidata.ad.web.util.ReadConfigFile;
import com.hidata.framework.util.DateUtil;

@Component
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportDao reportDao;
	
//	public List<AdMaterialReportViewDto> calculateAdMaterialReportByTimeRange(String start, String end, int userid, Integer id) {
//		Date startTime = DateUtil.formatToDate(start, DateUtil.C_TIME_PATTON_DEFAULT);
//		Date endTime = DateUtil.formatToDate(end, DateUtil.C_TIME_PATTON_DEFAULT);
//		return reportDao.calculateAdMaterialReportByTimeRange(startTime, endTime, userid, id);
//	}
	
	/**
	 * 广告物料报告  周晓明
	 */
	public List<MaterialStatInfo> calculateAdMaterialReportByTimeRange_new(String start, String end, int userid, Integer id) {
		//Date startTime = DateUtil.formatToDate(start, DateUtil.C_TIME_PATTON_DEFAULT);
		//Date endTime = DateUtil.formatToDate(end, DateUtil.C_TIME_PATTON_DEFAULT);
		return reportDao.calculateAdMaterialReportByTimeRange_new(start, end, userid, id);
	}

	public List<AdReportViewDto> calculateAdReportByTimeRange(String start, String end, int userid, Integer adId) {
//		Date startTime = DateUtil.formatToDate(start, DateUtil.C_TIME_PATTON_DEFAULT);
//		Date endTime = DateUtil.formatToDate(end, DateUtil.C_TIME_PATTON_DEFAULT);
//		return reportDao.calculateAdReportByTimeRange(startTime, endTime, userid, adId);
		return null;
	}
	
	/**
	 * 广告报告 周晓明
	 */
	public List<AdStatInfo> calculateAdReportByTimeRange_new(String start, String end, int userid, Integer adId) {
		return reportDao.calculateAdReportByTimeRange_new(start, end, userid, adId);
	}
	/**
	 * 首页广告报告
	 * @param start
	 * @param end
	 * @param userid
	 * @param adId
	 * @return
	 */
	@Override
	public List<AdStatInfo> calculateIndexAdReportByTimeRange_new(String start, String end, int userid, Integer adId) {
		return reportDao.calculateIndexAdReportByTimeRange_new(start, end, userid, adId);
	}
	/**
	 * 广告图表
	 */
//	public Map<String,Object> adMaterialViewOrClickChartStatistics(String start, String end, int userid, int index, int type, int reportType, Integer id) {
//		Date startTime = DateUtil.formatToDate(start, DateUtil.C_TIME_PATTON_DEFAULT);
//		Date endTime = DateUtil.formatToDate(end, DateUtil.C_TIME_PATTON_DEFAULT);
//
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		
//		List<ChartStatistics> stList = null;
//		if (reportType == ReportService.REPORT_TYPE_MATERIAL){
//			// 广告物料图表
//			stList = reportDao.adMaterialViewOrClickChartStatistics(startTime, endTime, userid, index, type, id);
//		} else if (reportType == ReportService.REPORT_TYPE_AD){
//			// 广告图表
//			stList = reportDao.adViewOrClickChartStatistics(startTime, endTime, userid, index, type, id);
//		}
//		
//		Map<String, TreeMap<String, Integer>> tempMap = new HashMap<String, TreeMap<String, Integer>>();
//		for (ChartStatistics s : stList){
//			TreeMap<String, Integer> temp = tempMap.get(s.getName());
//			if (temp == null){
//				temp = new TreeMap<String, Integer>();
//			}
//			temp.put(s.getTs(), s.getNumber());
//			tempMap.put(s.getName(), temp);
//		}
//		
//		if (stList == null || stList.isEmpty() || tempMap.isEmpty()){
//			return resultMap;
//		}
//		
//		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
//		
//		// 时间范围取值
//		int range = 0;
//		boolean isHour = true; // 按小时
//		if (index == ReportDao.TIME_TYPE_CURRENT_DAY || index == ReportDao.TIME_TYPE_LAST_DAY){
//			range = 24;
//		} else if (index == ReportDao.TIME_TYPE_CURRENT_WEEK || index == ReportDao.TIME_TYPE_LAST_WEEK){
//			range = 7;
//			isHour = false;
//		} else if (index == ReportDao.TIME_TYPE_CURRENT_MONTH || index == ReportDao.TIME_TYPE_LAST_MONTH){
//			range = DateUtil.daysBetween(startTime, endTime)+1;
//			isHour = false;
//		}
//		
//		int max = 0;
//		for (Entry<String, TreeMap<String, Integer>>  entry: tempMap.entrySet()){
//			String name = entry.getKey();
//			Map<String, Integer> m = entry.getValue();
//			for (Integer v : m.values()){
//				// 最大值
//				if (max < v){
//					max = v;
//				}
//			}
//			
//			StringBuilder dataBuilder = new StringBuilder();
//			dataBuilder.append("[");
//			for (int i = 0; i < range; ++i){
//				dataBuilder.append("[").append(i+1).append(",");
//				Integer val = null;
//				if (isHour){
//					// 按天每小时
//					val = m.get(i < 10 ? ("0"+i) : String.valueOf(i));
//				} else{
//					// 按天
//					String key = DateUtil.format(DateUtil.addDays(startTime, i), DateUtil.C_DATA_PATTON_YYYYMMDD);
//					val = m.get(key);
//				}
//				dataBuilder.append(val == null ? 0 : val).append("]");
//				if (i + 1 < range){
//					dataBuilder.append(",");
//				}
//			}
//			dataBuilder.append("]");
//			HashMap<String, String> one = new HashMap<String, String>();
//			one.put("labelName", name);
//			one.put("data", dataBuilder.toString());
//			resultList.add(one);
//		}
//		
//		// y坐标最大值
//		resultMap.put("yMax", max);
//		resultMap.put("result", resultList);
//		return resultMap;
//	}
	public Map<String,Object> adMaterialViewOrClickChartStatistics(String start, String end, int userid, int index, int type, int reportType, Integer id) {
		Date startTime = DateUtil.formatToDate(start, DateUtil.C_TIME_PATTON_DEFAULT);
		Date endTime = DateUtil.formatToDate(end, DateUtil.C_TIME_PATTON_DEFAULT);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<ChartStatistics> stList = null;
		if (reportType == ReportService.REPORT_TYPE_MATERIAL){
			// 广告物料图表
			stList = reportDao.adMaterialViewOrClickChartStatistics_new(start, end, userid, index, type, id);
		} else if (reportType == ReportService.REPORT_TYPE_AD){
			// 广告图表
			stList = reportDao.adViewOrClickChartStatistics_new(start, end, userid, index, type, id);
		}
		
		Map<String, TreeMap<String, Integer>> tempMap = new HashMap<String, TreeMap<String, Integer>>();
		for (ChartStatistics s : stList){
			TreeMap<String, Integer> temp = tempMap.get(s.getName());
			if (temp == null){
				temp = new TreeMap<String, Integer>();
			}
			temp.put(s.getTs(), s.getNumber());
			tempMap.put(s.getName(), temp);
		}
		
		if (stList == null || stList.isEmpty() || tempMap.isEmpty()){
			return resultMap;
		}
		
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		
		// 时间范围取值
		int range = 0;
		boolean isHour = true; // 按小时
		if (index == ReportDao.TIME_TYPE_CURRENT_DAY || index == ReportDao.TIME_TYPE_LAST_DAY){
			range = 24;
		} else if (index == ReportDao.TIME_TYPE_CURRENT_WEEK || index == ReportDao.TIME_TYPE_LAST_WEEK){
			range = 7;
			isHour = false;
		} else if (index == ReportDao.TIME_TYPE_CURRENT_MONTH || index == ReportDao.TIME_TYPE_LAST_MONTH){
			range = DateUtil.daysBetween(startTime, endTime)+1;
			isHour = false;
		}
		
		int max = 0;
		for (Entry<String, TreeMap<String, Integer>>  entry: tempMap.entrySet()){
			String name = entry.getKey();
			Map<String, Integer> m = entry.getValue();
			for (Integer v : m.values()){
				// 最大值
				if (max < v){
					max = v;
				}
			}
			
			StringBuilder dataBuilder = new StringBuilder();
			dataBuilder.append("[");
			for (int i = 0; i < range; ++i){
				dataBuilder.append("[").append(i+1).append(",");
				Integer val = null;
				if (isHour){
					// 按天每小时
					val = m.get(i < 10 ? ("0"+i) : String.valueOf(i));
				} else{
					// 按天
					String key = DateUtil.format(DateUtil.addDays(startTime, i), DateUtil.C_DATA_PATTON_YYYYMMDD);
					val = m.get(key);
				}
				dataBuilder.append(val == null ? 0 : val).append("]");
				if (i + 1 < range){
					dataBuilder.append(",");
				}
			}
			dataBuilder.append("]");
			HashMap<String, String> one = new HashMap<String, String>();
			one.put("labelName", name);
			one.put("data", dataBuilder.toString());
			resultList.add(one);
		}
		
		// y坐标最大值
		resultMap.put("yMax", max);
		resultMap.put("result", resultList);
		return resultMap;
	}
	
	public Map<String,Object> adMaterialViewOrClickChartStatistics(String start, String end, int userid, int index, int type, int reportType, String ids) {
		Date startTime = DateUtil.formatToDate(start, DateUtil.C_TIME_PATTON_DEFAULT);
		Date endTime = DateUtil.formatToDate(end, DateUtil.C_TIME_PATTON_DEFAULT);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<ChartStatistics> stList = null;
		if (reportType == ReportService.REPORT_TYPE_MATERIAL){
			// 广告物料图表
			stList = reportDao.adMaterialViewOrClickChartStatistics_new(start, end, userid, index, type, ids);
		} else if (reportType == ReportService.REPORT_TYPE_AD){
			// 广告图表
			stList = reportDao.adViewOrClickChartStatistics_new(start, end, userid, index, type, ids);
		}
		
		Map<String, TreeMap<String, Integer>> tempMap = new HashMap<String, TreeMap<String, Integer>>();
		for (ChartStatistics s : stList){
			TreeMap<String, Integer> temp = tempMap.get(s.getName());
			if (temp == null){
				temp = new TreeMap<String, Integer>();
			}
			temp.put(s.getTs(), s.getNumber());
			tempMap.put(s.getName(), temp);
		}
		
		if (stList == null || stList.isEmpty() || tempMap.isEmpty()){
			return resultMap;
		}
		
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		
		// 时间范围取值
		int range = 0;
		boolean isHour = true; // 按小时
		if (index == ReportDao.TIME_TYPE_CURRENT_DAY || index == ReportDao.TIME_TYPE_LAST_DAY){
			range = 24;
		} else if (index == ReportDao.TIME_TYPE_CURRENT_WEEK || index == ReportDao.TIME_TYPE_LAST_WEEK){
			range = 7;
			isHour = false;
		} else if (index == ReportDao.TIME_TYPE_CURRENT_MONTH || index == ReportDao.TIME_TYPE_LAST_MONTH){
			range = DateUtil.daysBetween(startTime, endTime)+1;
			isHour = false;
		}
		
		int max = 0;
		for (Entry<String, TreeMap<String, Integer>>  entry: tempMap.entrySet()){
			String name = entry.getKey();
			Map<String, Integer> m = entry.getValue();
			for (Integer v : m.values()){
				// 最大值
				if (max < v){
					max = v;
				}
			}
			
			StringBuilder dataBuilder = new StringBuilder();
			dataBuilder.append("[");
			for (int i = 0; i < range; ++i){
				dataBuilder.append("[").append(i+1).append(",");
				Integer val = null;
				if (isHour){
					// 按天每小时
					val = m.get(i < 10 ? ("0"+i) : String.valueOf(i));
				} else{
					// 按天
					String key = DateUtil.format(DateUtil.addDays(startTime, i), DateUtil.C_DATA_PATTON_YYYYMMDD);
					val = m.get(key);
				}
				dataBuilder.append(val == null ? 0 : val).append("]");
				if (i + 1 < range){
					dataBuilder.append(",");
				}
			}
			dataBuilder.append("]");
			HashMap<String, String> one = new HashMap<String, String>();
			one.put("labelName", name);
			one.put("data", dataBuilder.toString());
			resultList.add(one);
		}
		
		// y坐标最大值
		resultMap.put("yMax", max);
		resultMap.put("result", resultList);
//		resultMap = indexMaterialViewOrClickChartStatistics(start, end, userid, index, type, reportType, ids);
		return resultMap;
	}
	
	@Override
	public Map<String,Object> indexMaterialViewOrClickChartStatistics(String start, String end, int userid, int index, int type, int reportType, String ids) {
		Date startTime = DateUtil.formatToDate(start, DateUtil.C_TIME_PATTON_DEFAULT);
		Date endTime = DateUtil.formatToDate(end, DateUtil.C_TIME_PATTON_DEFAULT);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<ChartStatistics> stList = null;
		if(!StringUtils.isEmpty(ids)){
			if (reportType == ReportService.REPORT_TYPE_MATERIAL){
				// 广告物料图表
				stList = reportDao.adMaterialViewOrClickChartStatistics_new(start, end, userid, index, type, ids);
			} else if (reportType == ReportService.REPORT_TYPE_AD){
				// 广告图表
				stList = reportDao.adViewOrClickChartStatistics_new(start, end, userid, index, type, ids);
			}
		}
		
		
	   TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>();

		for (ChartStatistics s : stList){
			
			Integer val = treeMap.get(s.getTs());
			
			if(val==null){
				System.out.println(s.getTs());
				treeMap.put(s.getTs(), s.getNumber());
			}else{
				treeMap.put(s.getTs(), s.getNumber()+val);
			}
		}
		
		if (stList == null || stList.isEmpty() || treeMap.isEmpty()){
			return resultMap;
		}
		
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		
		int max = 0;
		StringBuilder dataBuilder = new StringBuilder();
		dataBuilder.append("[");
		
	    for(int i=0;i<24;i++){
			dataBuilder.append("[").append(i+1).append(",");
			Integer val = treeMap.get(i < 10 ? ("0"+i) : String.valueOf(i));
			// 最大值
			if(val!=null){
				if (max < val){
					max = val;
				}
			}
			dataBuilder.append(val == null ? 0 : val).append("]");
			if (i + 1 < 24){
				dataBuilder.append(",");
			}
	    }
	    
		dataBuilder.append("]");
		HashMap<String, String> one = new HashMap<String, String>();
		one.put("labelName", "");
		one.put("data", dataBuilder.toString());
		resultList.add(one);
		
		// y坐标最大值
		resultMap.put("yMax", max);
		resultMap.put("result", resultList);

		return resultMap;
	}

	@Override
	public HSSFWorkbook getHWB(List<AdStatInfo> list) {
		HSSFWorkbook workbook = new HSSFWorkbook(); 
		HSSFSheet sheet = workbook.createSheet("广告报告");
		HSSFRow row= sheet.createRow((short)0);  
		HSSFCell cell = null;  
		ReadConfigFile rf = new ReadConfigFile("config.properties");
		String columnNames = rf.getValue("column_name");
		if(columnNames != null && !"".equals(columnNames)){
			String[] columns = columnNames.split(",");
					int nColumn = columns.length;
			          
			        System.out.println("nColumn: " + nColumn);  
			          
			        //写入各个字段的名称  
			        for(int i=0;i < nColumn;i++) {  
			              cell = row.createCell((i));  
			              cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
			              cell.setCellValue(columns[i]);  
			        }  
			        
		}
		if(list != null && list.size() > 0){
			 int iRow=1;  
		        //写入各条记录，每条记录对应Excel中的一行  
			 	for(AdStatInfo adStatInfo : list){
			 		row= sheet.createRow((short)iRow);
			 		//序列号
			 		cell = row.createCell(0);  
	                cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
	                cell.setCellValue(iRow);
	                //广告名称
	                cell = row.createCell(1);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getAdName());
		            //展现量
		            cell = row.createCell(2);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getPvNum());
		            //独立访客
		            cell = row.createCell(3);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getUvNum());
		            //独立IP
		            cell = row.createCell(4);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getIpNum());
		            //点击量
		            cell = row.createCell(5);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getClickNum());
		            //点击率
		            cell = row.createCell(6);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getClickRate());
		            //移动端展现量
		            cell = row.createCell(7);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getMobilePvNum());
		            //移动端点击量
		            cell = row.createCell(8);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getMobileClickNum());
		            //关闭按钮点击量
		            cell = row.createCell(9);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getCloseNum());
		            //CPM单价
		            cell = row.createCell(10);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getUnitPrice());
		            //总消耗金额
		            cell = row.createCell(11);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getTotalAmount());
		        	iRow++ ;
			 	}
		}
		 return workbook;  
	}
	
	@Override
	public HSSFWorkbook getHWB2(List<AdStatInfo> list) {
		HSSFWorkbook workbook = new HSSFWorkbook(); 
		HSSFSheet sheet = workbook.createSheet("广告报告");
		HSSFRow row= sheet.createRow((short)0);  
		HSSFCell cell = null;  
		ReadConfigFile rf = new ReadConfigFile("config.properties");
		String columnNames = rf.getValue("column_name2");
		if(columnNames != null && !"".equals(columnNames)){
			String[] columns = columnNames.split(",");
					int nColumn = columns.length;
			          
			        System.out.println("nColumn: " + nColumn);  
			          
			        //写入各个字段的名称  
			        for(int i=0;i < nColumn;i++) {  
			              cell = row.createCell((i));  
			              cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
			              cell.setCellValue(columns[i]);  
			        }  
			        
		}
		if(list != null && list.size() > 0){
			 int iRow=1;  
		        //写入各条记录，每条记录对应Excel中的一行  
			 	for(AdStatInfo adStatInfo : list){
			 		row= sheet.createRow((short)iRow);
			 		//序列号
			 		cell = row.createCell(0);  
	                cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
	                cell.setCellValue(iRow);
	                //时间
	                cell = row.createCell(1);  
	                cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
	                cell.setCellValue(adStatInfo.getTs());
	                //广告名称
	                cell = row.createCell(2);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getAdName());
		            //展现量
		            cell = row.createCell(3);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getPvNum());
		            //独立访客
		            cell = row.createCell(4);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getUvNum());
		            //独立IP
		            cell = row.createCell(5);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getIpNum());
		            //点击量
		            cell = row.createCell(6);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getClickNum());
		            //点击率
		            cell = row.createCell(7);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getClickRate());
		            //移动端展现量
		            cell = row.createCell(8);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getMobilePvNum());
		            //移动端点击量
		            cell = row.createCell(9);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getMobileClickNum());
		            //关闭按钮点击量
		            cell = row.createCell(10);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getCloseNum());
		            //CPM单价
		            cell = row.createCell(11);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getUnitPrice());
		            //总消耗金额
		            cell = row.createCell(12);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adStatInfo.getTotalAmount());
		        	iRow++ ;
			 	}
		}
		 return workbook;  
	}
	
	@Override
	public List<AdInstanceHostDto> calculateAdHostReportByTimeRange_new(String start,String end, String adId) {
		
		return reportDao.calculateAdHostReportByTimeRange_new(start, end, adId);
	}

	@Override
	public List<AdUrlHostShowDto> findAdHostByUrlHost(String start,String end,String urlHost,String adId) {
		
		return reportDao.findAdHostByUrlHost(start,end,urlHost,adId);
	}

	@Override
	public List<AdHost> findAdHostByAdId(String adId) {
		
		return reportDao.findAdHostByAdId(adId);
	}

	@Override
	public HSSFWorkbook getUrlBook(List<AdInstanceHostDto> urllist) {
		HSSFWorkbook workbook = new HSSFWorkbook(); 
		HSSFSheet sheet = workbook.createSheet("网址报告");
		HSSFRow row= sheet.createRow((short)0);  
		HSSFCell cell = null;  
		ReadConfigFile rf = new ReadConfigFile("config.properties");
		String columnNames = rf.getValue("url_column_name");
		if(columnNames != null && !"".equals(columnNames)){
			String[] columns = columnNames.split(",");
					int nColumn = columns.length;		          
			        //System.out.println("nColumn: " + nColumn);  		          
			        //写入各个字段的名称  
			        for(int i=0;i < nColumn;i++) {  
			              cell = row.createCell((i));  
			              cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
			              cell.setCellValue(columns[i]);  
			        }  			        
		}
		
		if(urllist != null && urllist.size() > 0){
			 int iRow=1;  
		        //写入各条记录，每条记录对应Excel中的一行  
			 	for(AdInstanceHostDto adInstanceHost : urllist){
			 		row= sheet.createRow((short)iRow);
			 		//序列号
			 		cell = row.createCell(0);  
	                cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
	                cell.setCellValue(iRow);
	                //host
	                cell = row.createCell(1);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adInstanceHost.getUrlHost());
		            //时间
		            cell = row.createCell(2);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(/*adInstanceHost.getTs().substring(0,10)*/" "); 
		            //展现量
		            cell = row.createCell(3);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adInstanceHost.getPvNum());		  
		            //点击量
		            cell = row.createCell(4);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adInstanceHost.getClickNum());
		            //点击率
		            cell = row.createCell(5);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adInstanceHost.getClickRate());
		        	//独立访客
		            cell = row.createCell(6);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adInstanceHost.getUvNum());
		            //独立IP
		            cell = row.createCell(7);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adInstanceHost.getIpNum());
		            iRow++ ;
			 	}
			 }
		return workbook;
	}

	@Override
	public HSSFWorkbook getUrlHostBook(List<AdUrlHostShowDto> hostlist) {
		HSSFWorkbook workbook = new HSSFWorkbook(); 
		HSSFSheet sheet = workbook.createSheet("网址报告");
		HSSFRow row= sheet.createRow((short)0);  
		HSSFCell cell = null;  
		ReadConfigFile rf = new ReadConfigFile("config.properties");
		String columnNames = rf.getValue("url_column_name");
		if(columnNames != null && !"".equals(columnNames)){
			String[] columns = columnNames.split(",");
					int nColumn = columns.length;		          
			        System.out.println("nColumn: " + nColumn);  		          
			        //写入各个字段的名称  
			        for(int i=0;i < nColumn;i++) {  
			              cell = row.createCell((i));  
			              cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
			              cell.setCellValue(columns[i]);  
			        }  			        
		}
		
		if(hostlist != null && hostlist.size() > 0){
			 int iRow=1;  
		        //写入各条记录，每条记录对应Excel中的一行  
			 	for(AdUrlHostShowDto adUrlHostShow : hostlist){
			 		row= sheet.createRow((short)iRow);
			 		//序列号
			 		cell = row.createCell(0);  
	                cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
	                cell.setCellValue(iRow);
	                //host
	                cell = row.createCell(1);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adUrlHostShow.getUrlHost());
		            //时间
		            cell = row.createCell(2);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adUrlHostShow.getTs().substring(0,10)); 
		            //展现量
		            cell = row.createCell(3);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adUrlHostShow.getPvNum());		  
		            //点击量
		            cell = row.createCell(4);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adUrlHostShow.getClickNum());
		            //点击率
		            cell = row.createCell(5);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adUrlHostShow.getClickRate());
		            //独立访客
		            cell = row.createCell(6);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adUrlHostShow.getUvNum());
		            //独立IP
		            cell = row.createCell(7);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adUrlHostShow.getIpNum());
		        	iRow++ ;
			 	}
			 }
		return workbook;
	}

	@Override
	public AdStatisticsViewDto calculateAdStatistics(String start, String end, int userid, String adId) {
		AdStatisticsViewDto dto = reportDao.calculateAdStatistics(start, end, userid, adId);
		BigDecimal clickRate = new BigDecimal(dto.getClickRate()).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal cpmPrice = new BigDecimal(dto.getCpm_price()).setScale(2, BigDecimal.ROUND_HALF_UP);
		dto.setClickRate(clickRate.toString());
		dto.setCpm_price(cpmPrice.toString());
		return dto;
	}

	@Override
	public AdStatisticsViewDto calculateCurrentAdStatistics(String start, String end, int userid) {
		AdStatisticsViewDto dto = reportDao.calculateCurrentAdStatistics(start, end, userid);
		BigDecimal clickRate = new BigDecimal(dto.getClickRate()).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal cpmPrice = new BigDecimal(dto.getCpm_price()).setScale(2, BigDecimal.ROUND_HALF_UP);
		dto.setClickRate(clickRate.toString());
		dto.setCpm_price(cpmPrice.toString());
		return dto;
	}
}
