package com.hidata.web.service.impl;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hidata.framework.util.StringUtil;
import com.hidata.web.dao.AdReportDao;
import com.hidata.web.dao.PagerDao;
import com.hidata.web.dto.AdInstanceDto;
import com.hidata.web.dto.AdMonthsDto;
import com.hidata.web.dto.AdReportDetailDto;
import com.hidata.web.service.AdReportService;
import com.hidata.web.util.Pager;
import com.hidata.web.util.ReadConfigFile;

@Service
public class AdReportServiceImpl implements AdReportService{
	
	@Autowired
	private PagerDao pagerDao;
	
	@Autowired
	private AdReportDao adReportDao;
	
	@Override
	public Pager getPager(Map<String, String> map, String curPage) {
		Pager pager = null;
		StringBuffer sb_sql = new StringBuffer();
		sb_sql.append(
				
				"SELECT " +
					  "ad_instance.ad_id, " +
					  "ad_instance.ad_name, " +
					  "ad_instance.ad_url, " +
					  "ad_instance.start_time, " +
					  "ad_instance.end_time, " +
					  "ad_instance.ad_toufang_sts AS ad_tf_sts, " +
					  "ad_instance.userid  	   user_id, " +
					  "ad_instance.charge_type, " + 
					  "ad_instance.unit_price, " + 
					  "IFNULL(b.pv_nums,0)     pv_nums, " +
					  "IFNULL(b.click_nums,0)    click_nums, " +
					  "IFNULL(b.uv_nums,0)     uv_nums, " +
					  "IFNULL(b.ip_nums,0)     ip_nums, " +
					  "IFNULL(b.mobile_pv_nums,0)    mobile_pv_nums, " +
					  "IFNULL(b.mobile_click_nums,0)    mobile_click_nums, " +
					  "ROUND(IFNULL(click_nums/pv_nums*100,0),2)    click_rates " +
					"FROM ad_instance " +
					  "LEFT JOIN (SELECT " +
					               "ad_id, " +
					               "SUM(pv_num)             pv_nums, " +
					               "SUM(click_num)          click_nums, " +
					               "SUM(uv_num)             uv_nums, " +
					               "SUM(ip_num)             ip_nums, " +
					               "SUM(mobile_pv_num)      mobile_pv_nums, " +
					               "SUM(mobile_click_num)    mobile_click_nums " +
					             "FROM ad_stat_info " +
					             "WHERE num_type = 'D' " +
					             "GROUP BY ad_id) b " +
					    "ON b.ad_id = ad_instance.ad_id " +
					"WHERE " +
					"ad_instance.ad_useful_type = 'N' AND ad_instance.ad_toufang_sts != -5 AND ad_instance.ad_toufang_sts != -1 AND ad_instance.ad_toufang_sts != 0" 
				);
		if(map != null && map.size() > 0){
			String keyword = map.get("keyword");
			if(StringUtil.isNotEmpty(keyword)){
				sb_sql.append(" AND ad_instance.ad_name LIKE '%" + keyword +"%'");
			}
			
			String startTime = map.get("startTime");
			if(StringUtil.isNotEmpty(startTime)){
				sb_sql.append(" AND ad_instance.start_time >= '" + startTime + "' ");
			}
			
			String endTime = map.get("endTime");
			if(StringUtil.isNotEmpty(endTime)){
				sb_sql.append(" AND ad_instance.end_time <= '" + endTime + "' ");
			}
			
			String adTFsts = map.get("stsTF");
			if(StringUtil.isNotEmpty(adTFsts) && !"-2".equals(adTFsts)){
				sb_sql.append(" AND ad_instance.ad_toufang_sts = '" + adTFsts + "' ");
			}
			
			String userId = map.get("userId");
			if(StringUtil.isNotEmpty(userId) && !"-1".equals(userId)){
				sb_sql.append(" AND ad_instance.userid = '" + userId + "' ");
			}
			
			String linkType = map.get("linkType");
			if(StringUtil.isNotEmpty(linkType) && !"-1".equals(linkType)){
				sb_sql.append(" AND ad_instance.ad_toufang_sts = '" + linkType + "' ");
			}
		}
		sb_sql.append(" ORDER BY ad_instance.ad_id DESC");
		pager = pagerDao.getPagerBySql(sb_sql.toString(), Integer.parseInt(curPage), 10, AdReportDetailDto.class);
		if(pager != null && pager.getResult() != null && pager.getResult().size() > 0){
			@SuppressWarnings("unchecked")
			List<AdReportDetailDto> list = (List<AdReportDetailDto>) pager.getResult();
			DecimalFormat df = new DecimalFormat("#.00");
			for(AdReportDetailDto adReportDetail : list){
				String pirceType = adReportDetail.getChargeType();
				Double total = 0.00;
				if("1".equals(pirceType)){
					 total = Double.valueOf(adReportDetail.getPvNums()) / 1000 * Double.valueOf(adReportDetail.getUnitPrice())  ;
				}else if("2".equals(pirceType)){
					 total = Double.valueOf(adReportDetail.getClickNums()) * Double.valueOf(adReportDetail.getUnitPrice());
				}
				String totalMoney = String.valueOf(df.format(total));
				if(".".equals(totalMoney.subSequence(0, 1))){
					StringBuffer sb = new StringBuffer("0");
					totalMoney = sb.append(totalMoney).toString();
				}
				adReportDetail.setTotalMoney(totalMoney);
			}
		}
		return pager;
	}

	@Override
	public List<AdMonthsDto> getMonthsByadId(String adId) {
		if(StringUtil.isNotEmpty(adId)){
			return adReportDao.qryMonthsByAdId(adId);
		}
		return null;
	}

	@Override
	public List<AdReportDetailDto> getReportDetailsByMonthAndId(String adId,
			String month) {
		if(StringUtil.isNotEmpty(month)){
			String start = month + "-01 00:00:00";
			String end = month + "-31 00:00:00";
			if(StringUtil.isNotEmpty(adId)){
				List<AdReportDetailDto> list = adReportDao.qryReportDetailsByMonthAndAdId(adId, start, end);
				if(list != null && list.size() > 0){
					DecimalFormat df = new DecimalFormat("#.00");
					for(AdReportDetailDto adReportDetail : list){
						String pirceType = adReportDetail.getChargeType();
						Double total = 0.00;
						if("1".equals(pirceType)){
							 total = Double.valueOf(adReportDetail.getPvNums()) / 1000 * Double.valueOf(adReportDetail.getUnitPrice())  ;
						}else if("2".equals(pirceType)){
							 total = Double.valueOf(adReportDetail.getClickNums()) * Double.valueOf(adReportDetail.getUnitPrice());
						}
						String totalMoney = String.valueOf(df.format(total));
						if(".".equals(totalMoney.subSequence(0, 1))){
							StringBuffer sb = new StringBuffer("0");
							totalMoney = sb.append(totalMoney).toString();
						}
						adReportDetail.setTotalMoney(totalMoney);
						
					}
				}
				return list;
			}
		}
		return null;
	}

	@Override
	public AdInstanceDto getAdInstanceByAdId(String adId) {
		if(StringUtil.isNotEmpty(adId)){
			List<AdInstanceDto> list = adReportDao.qryAdInstanceByPkId(adId);
			if(list != null && list.size() == 1){
				return list.get(0);
			}
		}
		return null;
	}

	@Override
	public HSSFWorkbook getHWB(Map<String,String> map) {
		if(map != null && map.size() > 0){
			String adIds = map.get("adIds");
			String type = map.get("type");
			if(StringUtil.isEmpty(adIds) || StringUtil.isEmpty(type)){
				return null;
			}
			HSSFWorkbook workbook = new HSSFWorkbook(); 
			HSSFSheet sheet = workbook.createSheet("广告报告");
			HSSFRow row= sheet.createRow((short)0);  
			HSSFCell cell = null;  
			ReadConfigFile rf = new ReadConfigFile("excel.properties");
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
			
			adIds = adIds.substring(0, adIds.length() - 1);
			System.out.println(adIds);
			List<AdReportDetailDto> list = adReportDao.qryAdReportDetailByExcel(adIds);
			if(list != null && list.size() > 0){
				int iRow = 1;
				//写入各条记录，每条记录对应Excel中的一行  
			 	for(AdReportDetailDto adReportDetail : list){
			 		row= sheet.createRow((short)iRow);
			 		//序列号
			 		cell = row.createCell(0);  
	                cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
	                cell.setCellValue(iRow);
	                //广告名称
	                cell = row.createCell(1);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adReportDetail.getAdName());
		            //投放周期
		            cell = row.createCell(2);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adReportDetail.getStartTime().substring(0,10) + "~" + adReportDetail.getEndTime().substring(0, 10));
		            //展现量
		            cell = row.createCell(3);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adReportDetail.getPvNums());
		            //点击量
		            cell = row.createCell(4);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adReportDetail.getClickNums());
		            //独立访客
		            cell = row.createCell(5);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adReportDetail.getUvNums());
		            //独立IP
		            cell = row.createCell(6);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adReportDetail.getIpNums());
		            //点击率
		            cell = row.createCell(7);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adReportDetail.getRateNums() + "%");
		            //累计消耗金额
		            cell = row.createCell(8);  
		            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
		            cell.setCellValue(adReportDetail.getTotalMoney());
		        	iRow++ ;
		        	
		        	if("1".equals(type)){
		        		List<AdReportDetailDto> detialList = adReportDao.qryAdReportDetailByMonthDetail(adReportDetail.getAdId());
		        		for(AdReportDetailDto adReportDetail_one : detialList){
					 		row= sheet.createRow((short)iRow);
					 		//序列号
					 		cell = row.createCell(0);  
			                cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
			                cell.setCellValue(iRow);
			                //广告名称
			                cell = row.createCell(1);  
				            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
				            cell.setCellValue("");
				            //投放周期
				            cell = row.createCell(2);  
				            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
				            cell.setCellValue(adReportDetail_one.getStartTime().substring(0,10));
				            //展现量
				            cell = row.createCell(3);  
				            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
				            cell.setCellValue(adReportDetail_one.getPvNums());
				            //点击量
				            cell = row.createCell(4);  
				            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
				            cell.setCellValue(adReportDetail_one.getClickNums());
				            //独立访客
				            cell = row.createCell(5);  
				            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
				            cell.setCellValue(adReportDetail_one.getUvNums());
				            //独立IP
				            cell = row.createCell(6);  
				            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
				            cell.setCellValue(adReportDetail_one.getIpNums());
				            //点击率
				            cell = row.createCell(7);  
				            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
				            cell.setCellValue(adReportDetail_one.getRateNums() + "%");
				            //累计消耗金额
				            cell = row.createCell(8);  
				            cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
				            cell.setCellValue(adReportDetail_one.getTotalMoney());
				        	iRow++ ;
		        		}
		        		
		        	}
				
			 	}
			}
			return workbook;  
		}
		return null;
	}

	@Override
	public Pager getTPager(Map<String, String> map, String curPage) {
		Pager pager = null;
		StringBuffer sb_sql = new StringBuffer();
		sb_sql.append(
				
				"SELECT " +
					  "ad_instance.ad_id, " +
					  "ad_instance.ad_name, " +
					  "ad_instance.ad_url, " +
					  "ad_instance.start_time, " +
					  "ad_instance.end_time, " +
					  "ad_instance.ad_toufang_sts AS ad_tf_sts, " +
					  "ad_instance.userid  	   user_id, " +
					  "ad_instance.charge_type, " + 
					  "ad_instance.unit_price, " + 
					  "IFNULL(b.pv_nums,0)     pv_nums, " +
					  "IFNULL(b.click_nums,0)    click_nums, " +
					  "IFNULL(b.uv_nums,0)     uv_nums, " +
					  "IFNULL(b.ip_nums,0)     ip_nums, " +
					  "IFNULL(b.mobile_pv_nums,0)    mobile_pv_nums, " +
					  "IFNULL(b.mobile_click_nums,0)    mobile_click_nums, " +
					  "ROUND(IFNULL(click_nums/pv_nums*100,0),2)    click_rates " +
					"FROM ad_instance " +
					  "LEFT JOIN (SELECT " +
					               "ad_id, " +
					               "SUM(pv_num)             pv_nums, " +
					               "SUM(click_num)          click_nums, " +
					               "SUM(uv_num)             uv_nums, " +
					               "SUM(ip_num)             ip_nums, " +
					               "SUM(mobile_pv_num)      mobile_pv_nums, " +
					               "SUM(mobile_click_num)    mobile_click_nums " +
					             "FROM ad_stat_info " +
					             "WHERE num_type = 'D' " +
					             "GROUP BY ad_id) b " +
					    "ON b.ad_id = ad_instance.ad_id " +
					"WHERE " +
					"ad_instance.ad_useful_type = 'T' AND ad_instance.ad_toufang_sts != -5 AND ad_instance.ad_toufang_sts != -1 AND ad_instance.ad_toufang_sts != 0" 
				);
		if(map != null && map.size() > 0){
			String keyword = map.get("keyword");
			if(StringUtil.isNotEmpty(keyword)){
				sb_sql.append(" AND ad_instance.ad_name LIKE '%" + keyword +"%'");
			}
			
			String startTime = map.get("startTime");
			if(StringUtil.isNotEmpty(startTime)){
				sb_sql.append(" AND ad_instance.start_time >= '" + startTime + "' ");
			}
			
			String endTime = map.get("endTime");
			if(StringUtil.isNotEmpty(endTime)){
				sb_sql.append(" AND ad_instance.end_time <= '" + endTime + "' ");
			}
			
			String adTFsts = map.get("stsTF");
			if(StringUtil.isNotEmpty(adTFsts) && !"-2".equals(adTFsts)){
				sb_sql.append(" AND ad_instance.ad_toufang_sts = '" + adTFsts + "' ");
			}
			
			String userId = map.get("userId");
			if(StringUtil.isNotEmpty(userId) && !"-1".equals(userId)){
				sb_sql.append(" AND ad_instance.userid = '" + userId + "' ");
			}
			
			String linkType = map.get("linkType");
			if(StringUtil.isNotEmpty(linkType) && !"-1".equals(linkType)){
				sb_sql.append(" AND ad_instance.ad_toufang_sts = '" + linkType + "' ");
			}
		}
		sb_sql.append(" ORDER BY ad_instance.ad_id DESC");
		pager = pagerDao.getPagerBySql(sb_sql.toString(), Integer.parseInt(curPage), 10, AdReportDetailDto.class);
		if(pager != null && pager.getResult() != null && pager.getResult().size() > 0){
			@SuppressWarnings("unchecked")
			List<AdReportDetailDto> list = (List<AdReportDetailDto>) pager.getResult();
			DecimalFormat df = new DecimalFormat("#.00");
			for(AdReportDetailDto adReportDetail : list){
				String pirceType = adReportDetail.getChargeType();
				Double total = 0.00;
				if("1".equals(pirceType)){
					 total = Double.valueOf(adReportDetail.getPvNums()) / 1000 * Double.valueOf(adReportDetail.getUnitPrice())  ;
				}else if("2".equals(pirceType)){
					 total = Double.valueOf(adReportDetail.getClickNums()) * Double.valueOf(adReportDetail.getUnitPrice());
				}
				String totalMoney = String.valueOf(df.format(total));
				if(".".equals(totalMoney.subSequence(0, 1))){
					StringBuffer sb = new StringBuffer("0");
					totalMoney = sb.append(totalMoney).toString();
				}
				adReportDetail.setTotalMoney(totalMoney);
			}
		}
		return pager;
	}
}
