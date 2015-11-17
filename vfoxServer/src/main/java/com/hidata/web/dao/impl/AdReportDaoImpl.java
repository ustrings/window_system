package com.hidata.web.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.hidata.framework.db.DBManager;
import com.hidata.web.dao.AdReportDao;
import com.hidata.web.dto.AdInstanceDto;
import com.hidata.web.dto.AdMonthsDto;
import com.hidata.web.dto.AdReportDetailDto;

@Component
public class AdReportDaoImpl implements AdReportDao{
	
	private static Logger logger = LoggerFactory.getLogger(AdReportDaoImpl.class);
	@Autowired
	private DBManager db;
	
	@Override
	public List<AdMonthsDto> qryMonthsByAdId(String adId) {
		String sql = "SELECT ad_id , DATE_FORMAT(ts,'%Y-%m') yuefen FROM ad_stat_info WHERE ad_id = ? AND num_type = 'D' GROUP BY yuefen ORDER BY yuefen ";
		try {
			Object[] args = new Object[]{
				adId
			}; 
			List<AdMonthsDto> list = db.queryForListObject(sql, AdMonthsDto.class, args);
			return list;
		} catch (DataAccessException e) {
			logger.error("AdReportDaoImpl qryMonthsByAdId error",e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AdReportDetailDto> qryReportDetailsByMonthAndAdId(String adId,
			String startTime, String endTime) {
		String sql = "SELECT "+
					  "a.ad_id, " +
					  "a.ad_name, " +
					  "a.ad_url, " +
					  "a.ad_toufang_sts      ad_tf_sts, " +
					  "a.charge_type, " +
					  "a.unit_price, " +
					  "b.ts                  start_time, " +
					  "a.userid              user_id, " +
					  "b.pv_num              pv_nums, " +
					  "b.click_num           click_nums, " +
					  "b.uv_num              uv_nums, " +
					  "b.ip_num              ip_nums, " +
					  "b.mobile_click_num    mobile_click_nums, " +
					  "b.mobile_pv_num       mobile_pv_nums, " +
					  "IFNULL(ROUND((b.click_num/b.pv_num)*100,2),0.00)       click_rates " +
					"FROM ad_instance a, " +
					  "ad_stat_info b " +
					"WHERE a.ad_id = b.ad_id " +
					    "AND a.ad_id = ? " +
					    "AND b.ts >= ? " +
					    "AND b.ts <= ? " +
					    "AND b.num_type = 'D'" ;
		try {
			Object[] args = new Object[]{
				adId, startTime, endTime	
			};
			List<AdReportDetailDto> list = db.queryForListObject(sql, args, AdReportDetailDto.class);
			return list;
		} catch (DataAccessException e) {
			logger.error("AdReportDaoImpl qryReportDetailsByMonthAndAdId error",e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AdInstanceDto> qryAdInstanceByPkId(String adId) {
		String sql = "SELECT * FROM ad_instance WHERE ad_id = ?";
		try {
			Object[] args = new Object[]{
				adId	
			};
			List<AdInstanceDto> list = db.queryForListObject(sql, AdInstanceDto.class, args);
			return list;
		} catch (DataAccessException e) {
			logger.error("AdReportDaoImpl qryAdInstanceByPkId error",e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AdReportDetailDto> qryAdReportDetailByExcel(String adIds) {
		String sql = "SELECT " +
				  "ad_instance.ad_id, " +
				  "ad_instance.ad_name, " +
				  "ad_instance.ad_url, " +
				  "ad_instance.start_time, " +
				  "ad_instance.end_time, " +
				  "ad_instance.ad_toufang_sts AS ad_tf_sts, " +
				  "ad_instance.userid  	   user_id, " +
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
				             "AND ad_id IN ( " + adIds + " ) " +
				             "GROUP BY ad_id) b " +
				    "ON b.ad_id = ad_instance.ad_id " +
				"WHERE " +
				"ad_instance.sts = 'A' AND "+
				"ad_instance.ad_id IN (" + adIds + ") " +
				"ORDER BY ad_instance.ad_id DESC";
		try {
			List<AdReportDetailDto> list = db.queryForListObject(sql, AdReportDetailDto.class);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<AdReportDetailDto> qryAdReportDetailByMonthDetail(String adId) {
		String sql = "SELECT "+
				  "a.ad_id, " +
				  "a.ad_name, " +
				  "a.ad_url, " +
				  "a.ad_toufang_sts      ad_tf_sts, " +
				  "b.ts                  start_time, " +
				  "a.userid              user_id, " +
				  "b.pv_num              pv_nums, " +
				  "b.click_num           click_nums, " +
				  "b.uv_num              uv_nums, " +
				  "b.ip_num              ip_nums, " +
				  "b.mobile_click_num    mobile_click_nums, " +
				  "b.mobile_pv_num       mobile_pv_nums, " +
				  "IFNULL(ROUND((b.click_num/b.pv_num)*100,2),0.00)       click_rates " +
				"FROM ad_instance a, " +
				  "ad_stat_info b " +
				"WHERE a.ad_id = b.ad_id " +
				    "AND a.ad_id = ? " +
				    "AND b.num_type = 'D'" ;
	try {
		Object[] args = new Object[]{
			adId
		};
		List<AdReportDetailDto> list = db.queryForListObject(sql, args, AdReportDetailDto.class);
		return list;
	} catch (DataAccessException e) {
		logger.error("AdReportDaoImpl qryReportDetailsByMonthAndAdId error",e);
		e.printStackTrace();
	}
	return null;
	}
}
