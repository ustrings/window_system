package com.hidata.ad.web.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.ReportDao;
import com.hidata.ad.web.dto.AdInstanceHostDto;
import com.hidata.ad.web.dto.AdStatisticsViewDto;
import com.hidata.ad.web.dto.AdUrlHostShowDto;
import com.hidata.ad.web.model.AdHost;
import com.hidata.ad.web.model.AdStatInfo;
import com.hidata.ad.web.model.ChartStatistics;
import com.hidata.ad.web.model.MaterialStatInfo;
import com.hidata.framework.db.DBManager;

@Component
public class ReportDaoImpl implements ReportDao {

	@Autowired
	private DBManager db;
	
//	private static final String AD_MATERIAL_REPORT_SQL = "select view.material_name, view.view_num, view.uv, view.unique_ip, click.click_num, click.click_num / view.view_num * 100 click_rate from (select a.ad_m_id, a.material_name, count(b.ad_ilog_id) view_num, count(distinct b.uid) uv, count(distinct b.visitor_ip) unique_ip from ad_material a left join ad_impress_log b on a.ad_m_id = b.ad_m_id and b.ts >=? and b.ts <= ? where a.userid = ? and a.sts = 'A' group by a.ad_m_id, a.material_name) view inner join (select c.ad_m_id, c.material_name, count(d.ad_click_id) click_num from ad_material c left join ad_clicked_log d on c.ad_m_id = d.ad_m_id and d.click_time >= ? and d.click_time <= ? where c.userid = ? and c.sts = 'A' group by c.ad_m_id, c.material_name) click on view.ad_m_id = click.ad_m_id";
//	public List<AdMaterialReportViewDto> calculateAdMaterialReportByTimeRange(Date start, Date end, int userid, Integer id) {
//		String sql = AD_MATERIAL_REPORT_SQL;
//		Object[] args = new Object[]{start, end, userid, start, end, userid};
//		if (id != null && id > 0){
//			sql += " where view.ad_m_id = " + id.intValue();
//		}
//		return db.queryForListObject(sql, args, AdMaterialReportViewDto.class);
//	}
	
	/**
	 * 广告物料报告
	 * 
	 * 修改：周晓明
	 */
	private static final String AD_MATERIAL_REPORT_SQL_new = 
			"SELECT IFNULL(b.msi_id,0) msi_id,ad_material.ad_m_id,ad_material.material_name,IFNULL(b.pv_num,0) pv_num, IFNULL(b.click_num,0) click_num, IFNULL(b.uv_num,0) uv_num, IFNULL(b.ip_num,0) ip_num, IFNULL(b.mobile_pv_num,0) mobile_pv_num, IFNULL(b.mobile_click_num,0) mobile_click_num  , IFNULL(click_num/pv_num*100,0) click_rate FROM ad_material LEFT JOIN (SELECT msi_id, material_id, SUM(pv_num) pv_num, SUM(click_num) click_num, SUM(uv_num) uv_num, SUM(ip_num) ip_num, SUM(mobile_pv_num) mobile_pv_num, SUM(mobile_click_num) mobile_click_num FROM material_stat_info WHERE num_type = 'D' AND ts >= ? AND ts <= ?  GROUP BY material_id) b ON b.material_id = ad_material.ad_m_id WHERE ad_material.userid=?";
	public List<MaterialStatInfo> calculateAdMaterialReportByTimeRange_new(String start, String end, int userid, Integer id) {
		String sql = AD_MATERIAL_REPORT_SQL_new;
		Object[] args = new Object[]{start, end, userid};
		if (id != null && id > 0){
			sql += " AND ad_material.ad_m_id = " + id.intValue();
		}
		return db.queryForListObject(sql, args, MaterialStatInfo.class);
	}
//	private static final String AD_REPORT_SQL = "select view.ad_id, view.ad_name, view.view_num, view.uv, view.unique_ip, click.click_num, click.click_num / view.view_num * 100 click_rate from (select a.ad_id, a.ad_name, count(b.ad_ilog_id) view_num, count(distinct b.uid) uv, count(distinct b.visitor_ip) unique_ip from ad_instance a left join ad_impress_log b on a.ad_id = b.ad_id and b.ts >=? and b.ts <= ? where a.userid = ? and a.sts = 'A' group by a.ad_id, a.ad_name) view inner join (select c.ad_id, c.ad_name, count(d.ad_click_id) click_num from ad_instance c left join ad_clicked_log d on c.ad_id = d.ad_id and d.click_time >= ? and d.click_time <= ? where c.userid = ? and c.sts = 'A' group by c.ad_id, c.ad_name) click on view.ad_id = click.ad_id";
//	public List<AdReportViewDto> calculateAdReportByTimeRange(Date start,Date end, int userid, Integer adId) {
//		String sql = AD_REPORT_SQL;
//		if (adId != null && adId > 0){
//			sql += " where view.ad_id = " + adId.intValue();
//		}
//		Object[] args = new Object[]{start, end, userid, start, end, userid};
//		return db.queryForListObject(sql, args,  AdReportViewDto.class);
//	}
	/**
	 * 广告报告 周晓明
	 */
	private static final String AD_REPORT_SQL_NEW = 
	"SELECT IFNULL(b.asi_id,0) asi_id,ad_instance.link_type,ad_instance.ad_id,ad_instance.ad_name,IFNULL(b.pv_num,0) pv_num, IFNULL(b.click_num,0) click_num, IFNULL(b.uv_num,0) uv_num, IFNULL(b.ip_num,0) ip_num, IFNULL(b.mobile_pv_num,0) mobile_pv_num, IFNULL(b.mobile_click_num,0) mobile_click_num  , IFNULL(click_num/pv_num*100,0) click_rate,IFNULL(b.close_num,0) close_num, ad_instance.unit_price, IFNULL(ad_instance.unit_price / 1000 * b.pv_num, 0) total_amount FROM ad_instance LEFT JOIN (SELECT asi_id, ad_id, SUM(pv_num) pv_num, SUM(click_num) click_num, SUM(uv_num) uv_num, SUM(ip_num) ip_num, SUM(mobile_pv_num) mobile_pv_num, SUM(mobile_click_num) mobile_click_num,SUM(close_num) close_num FROM ad_stat_info WHERE num_type = 'D' AND ts >= ? AND ts <= ?  GROUP BY ad_id) b ON b.ad_id = ad_instance.ad_id WHERE ad_instance.userid=? AND ad_instance.sts='A' AND ad_instance.ad_useful_type = 'N'";
	
	private static final String AD_REPORT_SINGLE_BY_HOUR = "SELECT IFNULL(b.asi_id,0) asi_id, ts, ad_instance.link_type,ad_instance.ad_id,ad_instance.ad_name,IFNULL(b.pv_num,0) pv_num, IFNULL(b.click_num,0) click_num, IFNULL(b.uv_num,0) uv_num, IFNULL(b.ip_num,0) ip_num, IFNULL(b.mobile_pv_num,0) mobile_pv_num, IFNULL(b.mobile_click_num,0) mobile_click_num  , IFNULL(click_num/pv_num*100,0) click_rate,IFNULL(b.close_num,0) close_num, ad_instance.unit_price, IFNULL(ad_instance.unit_price / 1000 * b.pv_num, 0) total_amount FROM ad_instance INNER JOIN (SELECT asi_id, ad_id, date_format(ts, '%Y-%m-%d %H:%i') ts, SUM(pv_num) pv_num, SUM(click_num) click_num, SUM(uv_num) uv_num, SUM(ip_num) ip_num, SUM(mobile_pv_num) mobile_pv_num, SUM(mobile_click_num) mobile_click_num,SUM(close_num) close_num FROM ad_stat_info WHERE num_type = 'H' AND ts >= ? AND ts <= ?  GROUP BY ad_id, ts) b ON b.ad_id = ad_instance.ad_id WHERE ad_instance.userid=? AND ad_instance.sts='A' AND ad_instance.ad_useful_type = 'N'";
	private static final String AD_REPORT_SINGLE_BY_DAY = "SELECT IFNULL(b.asi_id,0) asi_id, ts, ad_instance.link_type,ad_instance.ad_id,ad_instance.ad_name,IFNULL(b.pv_num,0) pv_num, IFNULL(b.click_num,0) click_num, IFNULL(b.uv_num,0) uv_num, IFNULL(b.ip_num,0) ip_num, IFNULL(b.mobile_pv_num,0) mobile_pv_num, IFNULL(b.mobile_click_num,0) mobile_click_num  , IFNULL(click_num/pv_num*100,0) click_rate,IFNULL(b.close_num,0) close_num, ad_instance.unit_price, IFNULL(ad_instance.unit_price / 1000 * b.pv_num, 0) total_amount FROM ad_instance INNER JOIN (SELECT asi_id, ad_id, date_format(ts, '%Y-%m-%d') ts, SUM(pv_num) pv_num, SUM(click_num) click_num, SUM(uv_num) uv_num, SUM(ip_num) ip_num, SUM(mobile_pv_num) mobile_pv_num, SUM(mobile_click_num) mobile_click_num,SUM(close_num) close_num FROM ad_stat_info WHERE num_type = 'D' AND ts >= ? AND ts <= ?  GROUP BY ad_id, ts) b ON b.ad_id = ad_instance.ad_id WHERE ad_instance.userid=? AND ad_instance.sts='A' AND ad_instance.ad_useful_type = 'N'";

	public List<AdStatInfo> calculateAdReportByTimeRange_new(String start, String end, int userid, Integer adId) {
		String sql = AD_REPORT_SQL_NEW;
		
		if (adId != null && adId > 0){
			
			sql = AD_REPORT_SINGLE_BY_DAY;
			
			int startIndex = start.indexOf(" ");
			int endIndex = end.indexOf(" ");
			
			String startDate = start.substring(0, startIndex);
			String endDate = end.substring(0, endIndex);
			if (startDate.equals(endDate)) {
				sql = AD_REPORT_SINGLE_BY_HOUR;
			}
			
			sql += " AND ad_instance.ad_id = " + adId.intValue() + " order by ts desc";
		}
		
		Object[] args = new Object[]{start, end, userid};
		return db.queryForListObject(sql, args,  AdStatInfo.class);
	}
	private static final String INDEX_AD_REPORT_SQL_NEW = 
		"SELECT IFNULL(b.asi_id,0) asi_id,ad_instance.link_type,ad_instance.ad_id,ad_instance.ad_name,IFNULL(b.pv_num,0) pv_num, IFNULL(b.click_num,0) click_num, IFNULL(b.uv_num,0) uv_num, IFNULL(b.ip_num,0) ip_num, IFNULL(b.mobile_pv_num,0) mobile_pv_num, IFNULL(b.mobile_click_num,0) mobile_click_num  , IFNULL(click_num/pv_num*100,0) click_rate,IFNULL(b.close_num,0) close_num, ad_instance.unit_price, IFNULL(ad_instance.unit_price / 1000 * b.pv_num, 0) total_amount FROM ad_instance LEFT JOIN (SELECT asi_id, ad_id, SUM(pv_num) pv_num, SUM(click_num) click_num, SUM(uv_num) uv_num, SUM(ip_num) ip_num, SUM(mobile_pv_num) mobile_pv_num, SUM(mobile_click_num) mobile_click_num,SUM(close_num) close_num FROM ad_stat_info WHERE num_type = 'D' AND ts >= ? AND ts <= ?  GROUP BY ad_id) b ON b.ad_id = ad_instance.ad_id WHERE ad_instance.userid=? AND ad_instance.sts='A' and ad_instance.end_time>=? AND ad_instance.ad_useful_type = 'N'";
	@Override	
	public List<AdStatInfo> calculateIndexAdReportByTimeRange_new(String start, String end, int userid, Integer adId) {
			String sql = INDEX_AD_REPORT_SQL_NEW;
			if (adId != null && adId > 0){
				sql += " AND ad_instance.ad_id = " + adId.intValue();
			}
			Object[] args = new Object[]{start, end, userid,end};
			return db.queryForListObject(sql, args,  AdStatInfo.class);
		}
		/*
		private static String AD_CLICK_STATISTCIS_FOR_DATE_RANGE = 
		"SELECT ad_instance.ad_name name , click_num num, ts FROM ad_instance INNER JOIN (SELECT ad_id, click_num, DATE_FORMAT(ts, '%Y%m%d') ts FROM ad_stat_info WHERE ts >= ? AND ts <= ? AND num_type = 'D') b ON b.ad_id = ad_instance.ad_id WHERE ad_instance.sts='A' AND ad_instance.userid = ? AND click_num IS NOT NULL";*/
	private static String AD_MATERIAL_VIEW_STATISTICS_FOR_DAY = 
	"SELECT ad_material.material_name, pv_num num, ts FROM ad_material INNER JOIN (SELECT material_id, pv_num, DATE_FORMAT(ts, '%H') ts FROM material_stat_info WHERE ts >= ? AND ts <= ? AND num_type = 'H') b ON b.material_id = ad_material.ad_m_id WHERE ad_material.sts = 'A' AND ad_material.userid = ? AND pv_num IS NOT NULL";
	private static String AD_MATERIAL_VIEW_STATISTICS_FOR_DATE_RANGE = 
	"SELECT ad_material.material_name, pv_num num, ts FROM ad_material INNER JOIN (SELECT material_id, pv_num, DATE_FORMAT(ts, '%Y%m%d') ts FROM material_stat_info WHERE ts >= ? AND ts <= ? AND num_type = 'D') b ON b.material_id = ad_material.ad_m_id WHERE ad_material.sts = 'A' AND ad_material.userid = ? AND pv_num IS NOT NULL";
	private static String AD_MATERIAL_CLICK_STATISTCIS_FOR_DAY = 
	"SELECT ad_material.material_name, click_num num, ts FROM  ad_material INNER JOIN (SELECT material_id, click_num, DATE_FORMAT(ts, '%H') ts FROM material_stat_info WHERE ts >= ? AND ts <= ? AND num_type = 'H') b ON b.material_id = ad_material.ad_m_id AND ad_material.sts = 'A' AND ad_material.userid = ? AND click_num IS NOT NULL";
	private static String AD_MATERIAL_CLICK_STATISTCIS_FOR_DATE_RANGE = 
	"SELECT ad_material.material_name, click_num num, ts FROM  ad_material INNER JOIN (SELECT material_id, click_num, DATE_FORMAT(ts, '%Y%m%d') ts FROM material_stat_info WHERE ts >= ? AND ts <= ? AND num_type = 'D') b ON b.material_id = ad_material.ad_m_id AND ad_material.sts = 'A' AND ad_material.userid = ? AND click_num IS NOT NULL";
	//物料图表
	public List<ChartStatistics> adMaterialViewOrClickChartStatistics_new(String start, String end, int userid, int index, int type, Integer id) {
		String sql = null;
		if (type == ReportDao.CHART_TYPE_VIEW){
			// 按每天小时统计 展现量
			if (index == ReportDao.TIME_TYPE_LAST_DAY || index == ReportDao.TIME_TYPE_CURRENT_DAY){
				sql = AD_MATERIAL_VIEW_STATISTICS_FOR_DAY;
			} else if (index == ReportDao.TIME_TYPE_LAST_WEEK || index == ReportDao.TIME_TYPE_LAST_MONTH
					|| index == ReportDao.TIME_TYPE_CURRENT_WEEK || index == ReportDao.TIME_TYPE_CURRENT_MONTH){
				// 按天统计展现量（每周、每月，时间段内）
				sql = AD_MATERIAL_VIEW_STATISTICS_FOR_DATE_RANGE;
			}
		} else if(type == ReportDao.CHART_TYPE_CLICK){
			// 按每天小时统计 点击量
			if (index == ReportDao.TIME_TYPE_LAST_DAY || index == ReportDao.TIME_TYPE_CURRENT_DAY){
				sql = AD_MATERIAL_CLICK_STATISTCIS_FOR_DAY;
			} else if (index == ReportDao.TIME_TYPE_LAST_WEEK || index == ReportDao.TIME_TYPE_LAST_MONTH
					|| index == ReportDao.TIME_TYPE_CURRENT_WEEK || index == ReportDao.TIME_TYPE_CURRENT_MONTH){
				// 按天统计点击量（每周、每月，时间段内）
				sql = AD_MATERIAL_CLICK_STATISTCIS_FOR_DATE_RANGE;
			}
		}
		StringBuilder builder = new StringBuilder(sql);
		if (id != null && id > 0){
//			int position = builder.lastIndexOf("and");
//			builder.insert(position, "and a.ad_m_id = "+id.intValue() + " ");
			builder.append(" AND b.material_id = " + id.intValue() + " ");
			
		}
		
		Object[] args = new Object[]{start, end, userid};
		return db.queryForListObject(builder.toString(), args, ChartStatistics.class);
	}
//	private static String AD_MATERIAL_VIEW_STATISTICS_FOR_DAY = "select c.material_name name, c.ts, count(c.ad_ilog_id) num from (select material_name, ad_ilog_id, ts from (select a.material_name, b.ad_ilog_id,  date_format(b.ts,'%H') ts from ad_material a inner join ad_impress_log b on a.ad_m_id = b.ad_m_id and b.ts >= ? and b.ts <= ?  where userid = ? and a.sts = 'A') t) c group by c.material_name, c.ts";
//	private static String AD_MATERIAL_VIEW_STATISTICS_FOR_DATE_RANGE = "select c.material_name name, c.ts, count(c.ad_ilog_id) num from (select material_name, ad_ilog_id, ts from (select a.material_name, b.ad_ilog_id,  date_format(b.ts,'%Y%m%d') ts from ad_material a inner join ad_impress_log b on a.ad_m_id = b.ad_m_id and b.ts >= ? and b.ts <= ?  where userid = ? and a.sts = 'A') t) c group by c.material_name, c.ts";
//	private static String AD_MATERIAL_CLICK_STATISTCIS_FOR_DAY = "select c.material_name name, c.ts, count(c.ad_click_id) num from (select material_name, ad_click_id, ts from (select a.material_name, b.ad_click_id,  date_format(b.click_time,'%H') ts from ad_material a inner join ad_clicked_log b on a.ad_m_id = b.ad_m_id and b.click_time >= ? and b.click_time <= ?  where userid = ? and a.sts = 'A') t) c group by c.material_name, c.ts";
//	private static String AD_MATERIAL_CLICK_STATISTCIS_FOR_DATE_RANGE = "select c.material_name name, c.ts, count(c.ad_click_id) num from (select material_name, ad_click_id, ts from (select a.material_name, b.ad_click_id,  date_format(b.click_time,'%Y%m%d') ts from ad_material a inner join ad_clicked_log b on a.ad_m_id = b.ad_m_id and b.click_time >= ? and b.click_time <= ?  where userid = ? and a.sts = 'A') t) c group by c.material_name, c.ts";
//	//物料图表
//	public List<ChartStatistics> adMaterialViewOrClickChartStatistics(Date start, Date end, int userid, int index, int type, Integer id) {
//		String sql = null;
//		if (type == ReportDao.CHART_TYPE_VIEW){
//			// 按每天小时统计 展现量
//			if (index == ReportDao.TIME_TYPE_LAST_DAY || index == ReportDao.TIME_TYPE_CURRENT_DAY){
//				sql = AD_MATERIAL_VIEW_STATISTICS_FOR_DAY;
//			} else if (index == ReportDao.TIME_TYPE_LAST_WEEK || index == ReportDao.TIME_TYPE_LAST_MONTH
//					|| index == ReportDao.TIME_TYPE_CURRENT_WEEK || index == ReportDao.TIME_TYPE_CURRENT_MONTH){
//				// 按天统计展现量（每周、每月，时间段内）
//				sql = AD_MATERIAL_VIEW_STATISTICS_FOR_DATE_RANGE;
//			}
//		} else if(type == ReportDao.CHART_TYPE_CLICK){
//			// 按每天小时统计 点击量
//			if (index == ReportDao.TIME_TYPE_LAST_DAY || index == ReportDao.TIME_TYPE_CURRENT_DAY){
//				sql = AD_MATERIAL_CLICK_STATISTCIS_FOR_DAY;
//			} else if (index == ReportDao.TIME_TYPE_LAST_WEEK || index == ReportDao.TIME_TYPE_LAST_MONTH
//					|| index == ReportDao.TIME_TYPE_CURRENT_WEEK || index == ReportDao.TIME_TYPE_CURRENT_MONTH){
//				// 按天统计点击量（每周、每月，时间段内）
//				sql = AD_MATERIAL_CLICK_STATISTCIS_FOR_DATE_RANGE;
//			}
//		}
//		StringBuilder builder = new StringBuilder(sql);
//		if (id != null && id > 0){
//			int position = builder.lastIndexOf("and");
//			builder.insert(position, "and a.ad_m_id = "+id.intValue() + " ");
//		}
//		
//		Object[] args = new Object[]{start, end, userid};
//		return db.queryForListObject(builder.toString(), args, ChartStatistics.class);
//	}
	
//	private static String AD_VIEW_STATISTICS_FOR_DAY = "select c.ad_name name, c.ts, count(c.ad_ilog_id) num from (select ad_name, ad_ilog_id, ts from (select a.ad_name, b.ad_ilog_id,  date_format(b.ts,'%H') ts from ad_instance a inner join ad_impress_log b on a.ad_id = b.ad_id and b.ts >= ? and b.ts <= ?  where userid = ? and a.sts = 'A') t) c group by c.ad_name, c.ts";
//	private static String AD_VIEW_STATISTICS_FOR_DATE_RANGE = "select c.ad_name name, c.ts, count(c.ad_ilog_id) num from (select ad_name, ad_ilog_id, ts from (select a.ad_name, b.ad_ilog_id,  date_format(b.ts,'%Y%m%d') ts from ad_instance a inner join ad_impress_log b on a.ad_id = b.ad_id and b.ts >= ? and b.ts <= ?  where userid = ? and a.sts = 'A') t) c group by c.ad_name, c.ts";
//	private static String AD_CLICK_STATISTCIS_FOR_DAY = "select c.ad_name name, c.ts, count(c.ad_click_id) num from (select ad_name, ad_click_id, ts from (select a.ad_name, b.ad_click_id,  date_format(b.click_time,'%H') ts from ad_instance a inner join ad_clicked_log b on a.ad_id = b.ad_id and b.click_time >= ? and b.click_time <= ?  where userid = ? and a.sts = 'A') t) c group by c.ad_name, c.ts";
//	private static String AD_CLICK_STATISTCIS_FOR_DATE_RANGE = "select c.ad_name name, c.ts, count(c.ad_click_id) num from (select ad_name, ad_click_id, ts from (select a.ad_name, b.ad_click_id,  date_format(b.click_time,'%Y%m%d') ts from ad_instance a inner join ad_clicked_log b on a.ad_id = b.ad_id and b.click_time >= ? and b.click_time <= ?  where userid = ? and a.sts = 'A') t) c group by c.ad_name, c.ts";
//	
////	//广告图表
//	public List<ChartStatistics> adViewOrClickChartStatistics(Date start, Date end, int userid, int index, int type, Integer id) {
//		String sql = null;
//		if (type == ReportDao.CHART_TYPE_VIEW){
//			// 按每天小时统计 展现量
//			if (index == ReportDao.TIME_TYPE_LAST_DAY || index == ReportDao.TIME_TYPE_CURRENT_DAY){
//				sql = AD_VIEW_STATISTICS_FOR_DAY;
//			} else if (index == ReportDao.TIME_TYPE_LAST_WEEK || index == ReportDao.TIME_TYPE_LAST_MONTH
//					|| index == ReportDao.TIME_TYPE_CURRENT_WEEK || index == ReportDao.TIME_TYPE_CURRENT_MONTH){
//				// 按天统计展现量（每周、每月，时间段内）
//				sql = AD_VIEW_STATISTICS_FOR_DATE_RANGE;
//			}
//		} else if(type == ReportDao.CHART_TYPE_CLICK){
//			// 按每天小时统计 点击量
//			if (index == ReportDao.TIME_TYPE_LAST_DAY || index == ReportDao.TIME_TYPE_CURRENT_DAY){
//				sql = AD_CLICK_STATISTCIS_FOR_DAY;
//			} else if (index == ReportDao.TIME_TYPE_LAST_WEEK || index == ReportDao.TIME_TYPE_LAST_MONTH
//					|| index == ReportDao.TIME_TYPE_CURRENT_WEEK || index == ReportDao.TIME_TYPE_CURRENT_MONTH){
//				// 按天统计点击量（每周、每月，时间段内）
//				sql = AD_CLICK_STATISTCIS_FOR_DATE_RANGE;
//			}
//		}
//		StringBuilder builder = new StringBuilder(sql);
//		if (id != null && id > 0){
//			int position = builder.lastIndexOf("and");
//			builder.insert(position, "and a.ad_id = "+id.intValue() + " ");
//		}
//		
//		Object[] args = new Object[]{start, end, userid};
//		List<ChartStatistics> list = db.queryForListObject(builder.toString(), args, ChartStatistics.class);
//		System.out.println(list);
//		System.out.println(list.get(0).getName());
//		System.out.println(list.get(0).getTs());
//		System.out.println(list.get(0).getNumber());
//		return db.queryForListObject(builder.toString(), args, ChartStatistics.class);
//	}
	//天 展现
	private static String AD_VIEW_STATISTICS_FOR_DAY = 
	"SELECT ad_instance.ad_name name , pv_num num, ts FROM ad_instance INNER JOIN (SELECT ad_id, pv_num, DATE_FORMAT(ts, '%H') ts FROM ad_stat_info WHERE ts >= ? AND ts <= ? AND num_type = 'H') b ON b.ad_id = ad_instance.ad_id WHERE ad_instance.sts='A' AND ad_instance.userid= ? AND pv_num IS NOT NULL";
	//月展现量
	private static String AD_VIEW_STATISTICS_FOR_DATE_RANGE = //"select c.ad_name name, c.ts, count(c.ad_ilog_id) num from (select ad_name, ad_ilog_id, ts from (select a.ad_name, b.ad_ilog_id,  date_format(b.ts,'%Y%m%d') ts from ad_instance a inner join ad_impress_log b on a.ad_id = b.ad_id and b.ts >= ? and b.ts <= ?  where userid = ? and a.sts = 'A') t) c group by c.ad_name, c.ts";
	"SELECT ad_instance.ad_name name , pv_num num, ts FROM ad_instance INNER JOIN (SELECT ad_id, pv_num, DATE_FORMAT(ts, '%Y%m%d') ts FROM ad_stat_info WHERE ts >= ? AND ts <= ? AND num_type = 'D') b ON b.ad_id = ad_instance.ad_id WHERE ad_instance.sts='A' AND ad_instance.userid = ? AND pv_num IS NOT NULL";
	//天 点击
	private static String AD_CLICK_STATISTCIS_FOR_DAY = 
	"SELECT ad_instance.ad_name name , click_num num, ts FROM ad_instance INNER JOIN (SELECT ad_id, click_num, DATE_FORMAT(ts, '%H') ts FROM ad_stat_info WHERE ts >= ? AND ts <= ? AND num_type = 'H') b ON b.ad_id = ad_instance.ad_id WHERE ad_instance.sts='A' AND ad_instance.userid= ? AND click_num IS NOT NULL";
	//月点击量
	private static String AD_CLICK_STATISTCIS_FOR_DATE_RANGE = 
	"SELECT ad_instance.ad_name name , click_num num, ts FROM ad_instance INNER JOIN (SELECT ad_id, click_num, DATE_FORMAT(ts, '%Y%m%d') ts FROM ad_stat_info WHERE ts >= ? AND ts <= ? AND num_type = 'D') b ON b.ad_id = ad_instance.ad_id WHERE ad_instance.sts='A' AND ad_instance.userid = ? AND click_num IS NOT NULL";
	/**
	 * 广告图表
	 * 修改：周晓明
	 * 
	 */
	public List<ChartStatistics> adViewOrClickChartStatistics_new(String start, String end, int userid, int index, int type, Integer id) {
		String sql = null;
		if (type == ReportDao.CHART_TYPE_VIEW){
			// 按每天小时统计 展现量
			if (index == ReportDao.TIME_TYPE_LAST_DAY || index == ReportDao.TIME_TYPE_CURRENT_DAY){
				sql = AD_VIEW_STATISTICS_FOR_DAY;
			} else if (index == ReportDao.TIME_TYPE_LAST_WEEK || index == ReportDao.TIME_TYPE_LAST_MONTH
					|| index == ReportDao.TIME_TYPE_CURRENT_WEEK || index == ReportDao.TIME_TYPE_CURRENT_MONTH){
				// 按天统计展现量（每周、每月，时间段内）
				sql = AD_VIEW_STATISTICS_FOR_DATE_RANGE;
			}
		} else if(type == ReportDao.CHART_TYPE_CLICK){
			// 按每天小时统计 点击量
			if (index == ReportDao.TIME_TYPE_LAST_DAY || index == ReportDao.TIME_TYPE_CURRENT_DAY){
				sql = AD_CLICK_STATISTCIS_FOR_DAY;
			} else if (index == ReportDao.TIME_TYPE_LAST_WEEK || index == ReportDao.TIME_TYPE_LAST_MONTH
					|| index == ReportDao.TIME_TYPE_CURRENT_WEEK || index == ReportDao.TIME_TYPE_CURRENT_MONTH){
				// 按天统计点击量（每周、每月，时间段内）
				sql = AD_CLICK_STATISTCIS_FOR_DATE_RANGE;
			}
		}
		StringBuilder builder = new StringBuilder(sql);
		if (id != null && id > 0){
//			int position = builder.lastIndexOf("and");
//			builder.insert(position, "and ad_id = "+id.intValue() + " ");
			builder.append(" and b.ad_id = " + id.intValue() + " ");
		}
		
		Object[] args = new Object[]{start, end, userid};
		return db.queryForListObject(builder.toString(), args, ChartStatistics.class);
	}
	@Override
	public List<ChartStatistics> adMaterialViewOrClickChartStatistics_new(
			String start, String end, int userid, int index, int type,
			String ids) {
		String sql = null;
		if (type == ReportDao.CHART_TYPE_VIEW){
			// 按每天小时统计 展现量
			if (index == ReportDao.TIME_TYPE_LAST_DAY || index == ReportDao.TIME_TYPE_CURRENT_DAY){
				sql = AD_MATERIAL_VIEW_STATISTICS_FOR_DAY;
			} else if (index == ReportDao.TIME_TYPE_LAST_WEEK || index == ReportDao.TIME_TYPE_LAST_MONTH
					|| index == ReportDao.TIME_TYPE_CURRENT_WEEK || index == ReportDao.TIME_TYPE_CURRENT_MONTH){
				// 按天统计展现量（每周、每月，时间段内）
				sql = AD_MATERIAL_VIEW_STATISTICS_FOR_DATE_RANGE;
			}
		} else if(type == ReportDao.CHART_TYPE_CLICK){
			// 按每天小时统计 点击量
			if (index == ReportDao.TIME_TYPE_LAST_DAY || index == ReportDao.TIME_TYPE_CURRENT_DAY){
				sql = AD_MATERIAL_CLICK_STATISTCIS_FOR_DAY;
			} else if (index == ReportDao.TIME_TYPE_LAST_WEEK || index == ReportDao.TIME_TYPE_LAST_MONTH
					|| index == ReportDao.TIME_TYPE_CURRENT_WEEK || index == ReportDao.TIME_TYPE_CURRENT_MONTH){
				// 按天统计点击量（每周、每月，时间段内）
				sql = AD_MATERIAL_CLICK_STATISTCIS_FOR_DATE_RANGE;
			}
		}
		StringBuilder builder = new StringBuilder(sql);
		if (ids != null && !ids.equals("")){
//			int position = builder.lastIndexOf("and");
//			builder.insert(position, "and a.ad_m_id = "+id.intValue() + " ");
			builder.append(" AND b.material_id in (" + ids + ") ");
			
		}
		
		Object[] args = new Object[]{start, end, userid};
		return db.queryForListObject(builder.toString(), args, ChartStatistics.class);
	}
	@Override
	public List<ChartStatistics> adViewOrClickChartStatistics_new(String start,
			String end, int userid, int index, int type, String ids) {
		String sql = null;
		if (type == ReportDao.CHART_TYPE_VIEW){
			// 按每天小时统计 展现量
			if (index == ReportDao.TIME_TYPE_LAST_DAY || index == ReportDao.TIME_TYPE_CURRENT_DAY){
				sql = AD_VIEW_STATISTICS_FOR_DAY;
			} else if (index == ReportDao.TIME_TYPE_LAST_WEEK || index == ReportDao.TIME_TYPE_LAST_MONTH
					|| index == ReportDao.TIME_TYPE_CURRENT_WEEK || index == ReportDao.TIME_TYPE_CURRENT_MONTH){
				// 按天统计展现量（每周、每月，时间段内）
				sql = AD_VIEW_STATISTICS_FOR_DATE_RANGE;
			}
		} else if(type == ReportDao.CHART_TYPE_CLICK){
			// 按每天小时统计 点击量
			if (index == ReportDao.TIME_TYPE_LAST_DAY || index == ReportDao.TIME_TYPE_CURRENT_DAY){
				sql = AD_CLICK_STATISTCIS_FOR_DAY;
			} else if (index == ReportDao.TIME_TYPE_LAST_WEEK || index == ReportDao.TIME_TYPE_LAST_MONTH
					|| index == ReportDao.TIME_TYPE_CURRENT_WEEK || index == ReportDao.TIME_TYPE_CURRENT_MONTH){
				// 按天统计点击量（每周、每月，时间段内）
				sql = AD_CLICK_STATISTCIS_FOR_DATE_RANGE;
			}
		}
		StringBuilder builder = new StringBuilder(sql);
		if (ids != null && !ids.equals("")){
//			int position = builder.lastIndexOf("and");
//			builder.insert(position, "and ad_id = "+id.intValue() + " ");
			builder.append(" and b.ad_id in ( " + ids + ") ");
		}
		
		Object[] args = new Object[]{start, end, userid};
		return db.queryForListObject(builder.toString(), args, ChartStatistics.class);
	}
	
	@Override
	public List<AdInstanceHostDto> calculateAdHostReportByTimeRange_new(String start, String end, String adId) {
		String sql = "SELECT " +
				  "ad_instance.ad_id, " +
				  "ad_instance.ad_name, " +				 
				  "url_host, " +
				  "IFNULL(b.pv_num,0)     pv_num, " +
				  "IFNULL(b.click_num,0)    click_num, " +
				  "ROUND(IFNULL(click_num/pv_num*100,0),2)    click_rate, " +
				  "IFNULL(b.uv_num,0)       uv_num, " +
				  "IFNULL(b.ip_num,0)       ip_num "  +
				  "FROM ad_instance " +
				  "LEFT JOIN (SELECT " +
				               "ad_id, " +
				               "url_host, " +
				               "SUM(pv_num)             pv_num, " +
				               "SUM(click_num)          click_num, " +
				               "SUM(uv_num)             uv_num, " +
				               "SUM(ip_num)             ip_num "+
				             "FROM ad_host " +
				             "WHERE ad_id= " + adId +
				             " AND ts between '"+start +
				             "' and '"+ end+
				             "' GROUP BY ad_id,url_host) b " +
				    "ON b.ad_id = ad_instance.ad_id " +
				"WHERE " +
				"ad_instance.sts = 'A' AND "+
				"ad_instance.ad_id ="  + adId;
	   try {
		   List<AdInstanceHostDto> list = db.queryForListObject(sql, AdInstanceHostDto.class);
		   return list;
	   } catch (DataAccessException e) {
		   e.printStackTrace();
	   }
	   return null;
	}
	@Override
	public List<AdUrlHostShowDto> findAdHostByUrlHost(String start,String end,String urlHost,String adId) {
		String sql="select a.ad_id,a.url_host,IFNULL(a.pv_num,0) pv_num,IFNULL(a.click_num,0) click_num,ROUND(IFNULL(click_num/pv_num*100,0),2) click_rate,IFNULL(a.uv_num,0) uv_num,IFNULL(a.ip_num,0) ip_num,a.ts " 
                   +" from( select ad_id,url_host,SUM(pv_num) pv_num,SUM(click_num) click_num,SUM(uv_num) uv_num,SUM(ip_num) ip_num,ts " 
		           +" from ad_host " 
		           +" where url_host= '"+urlHost+"' AND ad_Id ="+adId+" AND ts between '"+start+"' and '"+end+"' "
                   +" group by substring(ts,1,11)) a";
		return db.queryForListObject(sql,AdUrlHostShowDto.class);
	}
	@Override
	public List<AdHost> findAdHostByAdId(String adId) {
		String sql="select distinct(url_host) from ad_host where ad_id=?";
		Object[] args = new Object[]{adId};
		return db.queryForListObject(sql,args,AdHost.class);
	}
	
	private static final String AD_STATISTICS_SQL = "select IFNULL(sum(pv_num),0) total_impress_num, IFNULL(sum(click_num),0) total_click_num, IFNULL(sum(click_num)/sum(pv_num) * 100,0) click_rate, IFNULL(sum(total_amount),0) total_amount,  IFNULL(sum(total_amount) / sum(pv_num) * 1000, 0) cpm_price from (SELECT IFNULL(b.pv_num,0) pv_num, IFNULL(b.click_num,0) click_num, IFNULL(b.uv_num,0) uv_num, IFNULL(b.ip_num,0) ip_num, IFNULL(b.mobile_pv_num,0) mobile_pv_num, IFNULL(b.mobile_click_num,0) mobile_click_num  , IFNULL(click_num/pv_num*100,0) click_rate,IFNULL(b.close_num,0) close_num, ad_instance.unit_price, ad_instance.unit_price / 1000 * b.pv_num total_amount FROM ad_instance INNER JOIN (SELECT asi_id, ad_id, SUM(pv_num) pv_num, SUM(click_num) click_num, SUM(uv_num) uv_num, SUM(ip_num) ip_num, SUM(mobile_pv_num) mobile_pv_num, SUM(mobile_click_num) mobile_click_num,SUM(close_num) close_num FROM ad_stat_info WHERE num_type = 'D' AND ts >= ? AND ts <= ? ${parameter}  GROUP BY ad_id) b ON b.ad_id = ad_instance.ad_id WHERE ad_instance.userid=? AND ad_instance.sts='A' AND ad_instance.ad_useful_type = 'N') sts";
	@Override
	public AdStatisticsViewDto calculateAdStatistics(String start, String end, int userid, String adId) {
		String parameter = "";
		if (!StringUtils.isEmpty(adId) && !"0".equals(adId)){
			parameter = " AND ad_id = " + adId;
		}
		
		String sql = AD_STATISTICS_SQL.replace("${parameter}", parameter);
		
		Object[] args = new Object[]{start, end, userid};
		return db.queryForObject(sql, args, AdStatisticsViewDto.class);
	}
	
	private static final String CURRENT_AD_STATISTICS_SQL = "select IFNULL(sum(pv_num),0) total_impress_num, IFNULL(sum(click_num),0) total_click_num, IFNULL(sum(click_num)/sum(pv_num) * 100,0) click_rate, IFNULL(sum(total_amount),0) total_amount,  IFNULL(sum(total_amount) / sum(pv_num) * 1000, 0) cpm_price from (SELECT IFNULL(b.pv_num,0) pv_num, IFNULL(b.click_num,0) click_num, IFNULL(b.uv_num,0) uv_num, IFNULL(b.ip_num,0) ip_num, IFNULL(b.mobile_pv_num,0) mobile_pv_num, IFNULL(b.mobile_click_num,0) mobile_click_num  , IFNULL(click_num/pv_num*100,0) click_rate,IFNULL(b.close_num,0) close_num, ad_instance.unit_price, ad_instance.unit_price / 1000 * b.pv_num total_amount FROM ad_instance INNER JOIN (SELECT asi_id, ad_id, SUM(pv_num) pv_num, SUM(click_num) click_num, SUM(uv_num) uv_num, SUM(ip_num) ip_num, SUM(mobile_pv_num) mobile_pv_num, SUM(mobile_click_num) mobile_click_num,SUM(close_num) close_num FROM ad_stat_info WHERE num_type = 'D' AND ts >= ? AND ts <= ? GROUP BY ad_id) b ON b.ad_id = ad_instance.ad_id WHERE ad_instance.userid=? AND ad_instance.sts='A' and ad_instance.end_time>=? AND ad_instance.ad_useful_type = 'N') sts";
	@Override
	public AdStatisticsViewDto calculateCurrentAdStatistics(String start, String end, int userid) {
		Object[] args = new Object[]{start, end, userid, end};
		return db.queryForObject(CURRENT_AD_STATISTICS_SQL, args, AdStatisticsViewDto.class);
	}
}
