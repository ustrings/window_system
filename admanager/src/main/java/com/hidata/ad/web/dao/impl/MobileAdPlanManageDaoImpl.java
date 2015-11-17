package com.hidata.ad.web.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.AdPlanManageDao;
import com.hidata.ad.web.dao.MobileAdPlanManageDao;
import com.hidata.ad.web.dto.AdCrowdLinkDto;
import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.dto.AdMaterialLinkDto;
import com.hidata.ad.web.dto.AdMediaCategoryLinkDto;
import com.hidata.ad.web.dto.AdSiteDto;
import com.hidata.ad.web.dto.AdTimeDto;
import com.hidata.ad.web.dto.AdTimeFilterDto;
import com.hidata.ad.web.dto.AdTimeFrequencyDto;
import com.hidata.ad.web.dto.AdUserFrequencyDto;
import com.hidata.ad.web.dto.AdVisitorLinkDto;
import com.hidata.ad.web.dto.MediaCategoryDto;
import com.hidata.ad.web.dto.TerminalBaseInfo;
import com.hidata.ad.web.dto.mongo.AdApp;
import com.hidata.ad.web.dto.mongo.AdAppLink;
import com.hidata.ad.web.dto.mongo.AdClick;
import com.hidata.ad.web.dto.mongo.AdClickLink;
import com.hidata.ad.web.dto.mongo.AdDevice;
import com.hidata.ad.web.dto.mongo.AdDeviceLink;
import com.hidata.ad.web.dto.mongo.AdIndustry;
import com.hidata.ad.web.dto.mongo.AdIndustryLink;
import com.hidata.ad.web.dto.mongo.AdType;
import com.hidata.ad.web.dto.mongo.AdTypeLink;
import com.hidata.ad.web.model.AdInstance;
import com.hidata.ad.web.model.AdMaterial;
import com.hidata.ad.web.model.AdTerminalLink;
import com.hidata.ad.web.model.Crowd;
import com.hidata.ad.web.model.VisitorCrowd;
import com.hidata.framework.db.DBManager;
import com.hidata.framework.util.DateUtil;

/**
 * 广告计划管理数据操作 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Component
public class MobileAdPlanManageDaoImpl implements MobileAdPlanManageDao {

	@Autowired
	private DBManager db;

	//private static final String INSERT_ADINSTANCE_SQL = "insert into ad_instance(ad_name,ad_desc,userid,start_time,end_time,all_budget,day_budget,ad_url,create_time,sts) values(?, ?, ?,?,?,?,?,?,?,?)";

	/**
	 * 新增广告信息
	 */
	@Override
	public int insertAdInstance(AdInstanceDto adInstanceDto) {
		return db.insertObjectAndGetAutoIncreaseId(adInstanceDto);
	}

	/**
	 * 回显修改adUrl，因为adurl中需要知道ad_id
	 */
	@Override
	public void updateAdUrl(AdInstanceDto adInstanceDto) {
		String sql = "update ad_instance set ad_url=? where ad_id = ?";

		db.update(
				sql,
				new Object[] { adInstanceDto.getAdUrl(),
						adInstanceDto.getAdId() });
	}

	private static final String INSERT_ADSITE_SQL = "insert into ad_site(ad_id,url,match_type,create_time,sts) values(?, ?, ?, ?, ?)";

	/**
	 * 新增广告投放站点信息
	 */
	@Override
	public int insertAdSite(AdSiteDto adSiteDto) {
		Object[] args = new Object[] {
				adSiteDto.getAdId(),
				adSiteDto.getUrl(),
				adSiteDto.getMatchType(),
				DateUtil.formatToDate(adSiteDto.getCreateTime(),
						DateUtil.C_TIME_PATTON_DEFAULT), adSiteDto.getSts() };
		return db.update(INSERT_ADSITE_SQL, args);
	}

	private static final String INSERT_ADTIME_SQL = "insert into ad_time(ad_id,week,hour,create_time,sts) values(?, ?, ?, ?, ?)";

	/**
	 * 广告时间信息
	 */
	@Override
	public int insertAdTime(AdTimeDto adTimeDto) {
		Object[] args = new Object[] { adTimeDto.getAdId(),
				adTimeDto.getWeek(), adTimeDto.getHour(),
				adTimeDto.getCreateTime(), adTimeDto.getSts() };
		return db.update(INSERT_ADTIME_SQL, args);
	}
	
	private static final String INSERT_ADTIME_FILTER_SQL = "insert into ad_time_filter(ad_id, days_of_week, start_hour, end_hour, update_time) values(?, ?, ?, ?, ?)";

	/**
	 * 广告时间信息
	 */
	@Override
	public int insertAdTimeFilter(AdTimeFilterDto adTimeFilterDto) {
		Object[] args = new Object[] {
				adTimeFilterDto.getAdId(), adTimeFilterDto.getDaysOfWeek(), 
				adTimeFilterDto.getStartHour(), adTimeFilterDto.getEndHour(), adTimeFilterDto.getUpdateTime()};
		return db.update(INSERT_ADTIME_FILTER_SQL, args);
	}

	private static final String INSERT_ADCROWDlINK_SQL = "insert into ad_crowd_link(ad_id,crowd_id,create_time,sts) values(?, ?, ?, ?)";

	/**
	 * 广告受众人群关系新增
	 */
	@Override
	public int insertAdCrowdLink(AdCrowdLinkDto adCrowdLinkDto) {
		Object[] args = new Object[] { adCrowdLinkDto.getAdId(),
				adCrowdLinkDto.getCrowdId(), adCrowdLinkDto.getCreateTime(),
				adCrowdLinkDto.getSts() };
		return db.update(INSERT_ADCROWDlINK_SQL, args);
	}

	private static final String INSERT_ADMaterlINK_SQL = "insert into ad_m_link(ad_id, ad_m_id, create_time, sts, check_status, comment) values(?, ?, ?, ?, ?, ?)";

	/**
	 * 广告物料关系新增
	 */
	@Override
	public int insertAdMaterialLink(AdMaterialLinkDto adMaterialLinkDto) {
		Object[] args = new Object[] { 
				adMaterialLinkDto.getAdId(), adMaterialLinkDto.getAdMaterId(),
				adMaterialLinkDto.getCreateTime(), adMaterialLinkDto.getSts(),
				adMaterialLinkDto.getCheckStatus(), adMaterialLinkDto.getComment()
				};
		return db.update(INSERT_ADMaterlINK_SQL, args);
	}

	/**
	 * 广告投放频率新增
	 */
	@Override
	public int insertAdTimeFrequency(AdTimeFrequencyDto adTimeFrequencyDto) {
		String sql = "insert into ad_time_frequency(ad_id,put_interval_unit,put_interval_num,day_limit,create_time,sts) values(?,?,?,?,?,?)";

		return db.update(
				sql,
				new Object[] { adTimeFrequencyDto.getAdId(),
						adTimeFrequencyDto.getPutIntervalUnit(),
						adTimeFrequencyDto.getPutIntervalNum(),
						adTimeFrequencyDto.getDayLimit(),
						adTimeFrequencyDto.getCreateTime(),
						adTimeFrequencyDto.getSts() });
	}

	/**
	 * 广告唯一用户展现次数设置
	 */
	@Override
	public int insertAdUserFrequency(AdUserFrequencyDto adUserFrequencyDto) {
		String sql = "insert into ad_user_frequency(ad_id,time_unit,uid_impress_num,create_time,sts) values(?,?,?,?,?)";

		db.update(
				sql,
				new Object[] { adUserFrequencyDto.getAdId(),
						adUserFrequencyDto.getTimeUnit(),
						adUserFrequencyDto.getUidImpressNum(),
						adUserFrequencyDto.getCreateTime(),
						adUserFrequencyDto.getSts() });
		return 0;
	}
	
	
	/**
	 * 根据ID查找广告信息 添加字段 第三方统计代码
	 */
	@Override
	public AdInstanceDto getAdInstanceByAdId(AdInstanceDto adInstanceDto) {
		
		String sql = "select ad_id,ad_name,ad_desc,userid,charge_type,date_format(start_time,'%Y-%m-%d') start_time,date_format(end_time,'%Y-%m-%d') end_time,all_budget,day_budget,ad_url,create_time,sts, ad_3stat_code,ad_3stat_code_sub, channel, total,max_cpm_bidprice from ad_instance where ad_id = ?";
		Object[] args = new Object[] { adInstanceDto.getAdId() };

		List<AdInstanceDto> adIList = db.queryForListObject(sql, args, AdInstanceDto.class);
		AdInstanceDto adInstanceRes = adIList.get(0);
		return adInstanceRes;
	}

	@Override
	public List<AdTimeDto> getAdTimesByAdId(AdInstanceDto adInstanceDto) {
		
		String sql = "select ad_t_id,ad_id,week,hour,create_time,sts from ad_time where ad_id = ?";
		Object[] args = new Object[] { adInstanceDto.getAdId() };

		List<AdTimeDto> adTIList = db.queryForListObject(sql, args, AdTimeDto.class);
		return adTIList;
		
	}
	
	@Override
	public List<AdTimeFilterDto> getAdTimeFiltersByAdId(AdInstanceDto adInstanceDto) {
		
		String sql = "select id, ad_id, days_of_week, start_hour, end_hour from ad_time_filter where ad_id = ?";
		Object[] args = new Object[] { adInstanceDto.getAdId() };

		List<AdTimeFilterDto> adTIList = db.queryForListObject(sql, args, AdTimeFilterDto.class);
		return adTIList;
	}

	@Override
	public List<AdSiteDto> getAdSitesByAdId(AdInstanceDto adInstanceDto) {
		
		String sql = "select ad_s_id,ad_id,url,match_type,create_time,sts from ad_site where ad_id = ?";
		Object[] args = new Object[] { adInstanceDto.getAdId() };

		List<AdSiteDto> adSIList = db.queryForListObject(sql, args, AdSiteDto.class);
		return adSIList;
	}

	@Override
	public List<Crowd> getAdCrowdsByAdId(AdInstanceDto adInstanceDto) {
		
		String sql = "select b.crowd_id,b.crowd_name,b.crowd_desc,b.create_time,b.sts,b.user_id from ad_crowd_link a,crowd b where a.crowd_id = b.crowd_id and a.ad_id = ?";
		Object[] args = new Object[] { adInstanceDto.getAdId() };

		List<Crowd> adCIList = db.queryForListObject(sql, args, Crowd.class);
		return adCIList;
	}
	
	/**
	 * 根据广告ID查询重定向人群
	 */
	@Override
	public List<VisitorCrowd> getVisitorsByAdId(AdInstanceDto adInstanceDto){
		String sql = "SELECT visitor.vc_id, visitor.vc_name, visitor.vc_site_type, visitor.vc_site_desc,visitor.vc_site_host,visitor.vc_code,visitor.vc_userid FROM visitor_crowd visitor , ad_vc_link ad_vc  WHERE visitor.vc_id = ad_vc.vc_id AND ad_vc.ad_id = ?  AND vc_sts = 'A'";
		Object[] args = new Object[]{
			adInstanceDto.getAdId()	
		};
		return db.queryForListObject(sql, args, VisitorCrowd.class);
	}

	@Override
	public List<AdMaterial> getAdMaterialsByAdId(AdInstanceDto adInstanceDto) {
		
		String sql = "select b.ad_m_id, b.m_type, b.material_name, b.material_size, b.link_url, b.target_url, b.material_desc, "
				+ "b.third_monitor, b.monitor_link, b.rich_text, b.material_type from ad_m_link a,"
				+ "ad_material b where a.ad_m_id = b.ad_m_id and a.ad_id = ?";
		Object[] args = new Object[] { adInstanceDto.getAdId() };

		List<AdMaterial> adAIList = db.queryForListObject(sql, args, AdMaterial.class);
		return adAIList;
	}

	@Override
	public AdTimeFrequencyDto getAdTimeFrequencyByAdId(
			AdInstanceDto adInstanceDto) {
		
		String sql = "select ad_tf_id,ad_id,put_interval_unit,put_interval_num,day_limit,create_time,sts from ad_time_frequency a where a.ad_id = ?";
		Object[] args = new Object[] { adInstanceDto.getAdId() };

		List<AdTimeFrequencyDto> adTFIList = db.queryForListObject(sql, args, AdTimeFrequencyDto.class);
		if(adTFIList !=null && adTFIList.size() >0){
			return adTFIList.get(0);
		}else{
			return null;
		}
		
	}

	@Override
	public List<AdUserFrequencyDto> getAdUserFrequencyByAdId(
			AdInstanceDto adInstanceDto) {
		String sql = "select ad_uf_id,ad_id,time_unit,uid_impress_num,create_time,sts from ad_user_frequency a where a.ad_id = ?";
		Object[] args = new Object[] { adInstanceDto.getAdId() };

		List<AdUserFrequencyDto> adUFIList = db.queryForListObject(sql, args, AdUserFrequencyDto.class);
		return adUFIList;
	}

	@Override
	public void updateAdInstance(AdInstanceDto adDto) {
		
		String sql = "update ad_instance set ad_name= ?,ad_desc= ?,charge_type = ?,start_time = ?,end_time = ?,"
				+ "all_budget = ?,day_budget = ? ,ad_3stat_code= ?,ad_3stat_code_sub= ?, channel = ?, total=?,max_cpm_bidprice=? where ad_id = ?";
		if(StringUtils.isEmpty(adDto.getMaxCpmBidprice())){
			adDto.setMaxCpmBidprice(null);
		}
		db.update(sql, new Object[]{adDto.getAdName(),adDto.getAdDesc(),adDto.getChargeType(),adDto.getStartTime(),adDto.getEndTime(),adDto.getAllBudget(), adDto.getDayBudget(), adDto.getAd3statCode(),adDto.getAd3statCodeSub(),adDto.getChannel(), adDto.getTotal(),adDto.getMaxCpmBidprice(),adDto.getAdId()});
	}

	@Override
	public void delAdTimes(AdInstanceDto adDto) {
		
		String sql= "delete from ad_time where ad_id = ?";
		db.update(sql,new Object[]{adDto.getAdId()});
		
	}
	
	@Override
	public void delAdTimeFilters(AdInstanceDto adDto) {
		
		String sql= "delete from ad_time_filter where ad_id = ?";
		db.update(sql,new Object[]{adDto.getAdId()});
	}
	
	

	@Override
	public void delAdSites(AdInstanceDto adDto) {
		
		String sql= "delete from ad_site where ad_id = ?";
		db.update(sql,new Object[]{adDto.getAdId()});
	}

	@Override
	public void delAdCrowdLinks(AdInstanceDto adDto) {
		
		String sql= "delete from ad_crowd_link where ad_id = ?";
		db.update(sql,new Object[]{adDto.getAdId()});
		
	}
	
	/**
	 * 重定向人群  修改
	 * @author xiaoming
	 * @date 2014年5月30日
	 */
	@Override
	public void delAdVisitorLinks(AdInstanceDto adDto) {
		
		String sql= "DELETE FROM ad_vc_link WHERE ad_id = ?";
		db.update(sql,new Object[]{adDto.getAdId()});
		
	}
	@Override
	public void delAdTerminalLinks(AdInstanceDto adDto) {
		
		String sql= "DELETE FROM ad_t_link WHERE ad_id = ?";				
		db.update(sql,new Object[]{adDto.getAdId()});
	}
	
	

	@Override
	public void delAdMaterialLinks(AdInstanceDto adDto) {
		String sql= "delete from ad_m_link where ad_id = ?";
		db.update(sql,new Object[]{adDto.getAdId()});
		
	}

	@Override
	public void delAdTimeFrequency(AdInstanceDto adDto) {
		
		String sql= "delete from ad_time_frequency where ad_id = ?";
		db.update(sql,new Object[]{adDto.getAdId()});
		
		
	}

	@Override
	public void delAdUserFrequency(AdInstanceDto adDto) {
		
		String sql= "delete from ad_user_frequency where ad_id = ?";
		db.update(sql,new Object[]{adDto.getAdId()});
		
	}
	
	// 查询广告：查询移动端的类型
	private static final String QUERY_AD_LIST_BY_USER = "select ad_id, ad_name, ad_desc, date_format(start_time,'%Y-%m-%d %H:%i:%s') start_time, date_format(end_time,'%Y-%m-%d %H:%i:%s') end_time, all_budget, day_budget, sts, ad_url from ad_instance where userid = ? and sts = 'A' and ad_type =1";
	@Override
	public List<AdInstance> findAdInstanceListByUser(int userid) {
		Object[] args = new Object[]{userid};
		return db.queryForListObject(QUERY_AD_LIST_BY_USER, args, AdInstance.class);
	}
	
	private static final String QUERY_AD_LIST_BY_USER_CHANNEL = 
			"select ad_id, ad_name, ad_desc, date_format(start_time,'%Y-%m-%d %H:%i:%s') start_time, date_format(end_time,'%Y-%m-%d %H:%i:%s') end_time, "
			+ "all_budget, day_budget, sts, ad_url from ad_instance where sts = 'A' and userid = ? and channel=?";
	@Override
	public List<AdInstance> findAdInstanceListByChannle(int userid,int channel) {
		Object[] args = new Object[]{userid, channel};
		return db.queryForListObject(QUERY_AD_LIST_BY_USER_CHANNEL, args, AdInstance.class);
	}
	
	private static final String QUERY_MEDIA_CATEGORYDTO_LIST_BY_PARENDCODE = "select code, name, parent_media_category_code  from media_category where parent_media_category_code = ? and sts = 'A'";

	@Override
	public List<MediaCategoryDto> findMediaCategoryDtoListByParendCode(String parentcode){
		Object[] args = new Object[]{parentcode};
		return db.queryForListObject(QUERY_MEDIA_CATEGORYDTO_LIST_BY_PARENDCODE, args, MediaCategoryDto.class);
	}
	private static final String QUERY_MEDIA_CATEGORYDTO_LIST_BY_ADID = "select b.code, b.name, b.parent_media_category_code  from ad_media_category_link a ,media_category b where b.code=a.media_category_code and  a.ad_id = ? and a.sts = 'A'";

	@Override
	public List<MediaCategoryDto> findMediaCategoryDtoListByAdId(String adId){
		Object[] args = new Object[]{adId};
		return db.queryForListObject(QUERY_MEDIA_CATEGORYDTO_LIST_BY_ADID, args, MediaCategoryDto.class);
	}
	
	private static final String QUERY_MEDIA_CATEGORYDTO_BY_CODE = "select code, name, parent_media_category_code  from media_category where code = ? and sts = 'A'";

	@Override
	public MediaCategoryDto findMediaCategoryDtoByCode(String code){
		Object[] args = new Object[]{code};
		return db.queryForBean(QUERY_MEDIA_CATEGORYDTO_BY_CODE, args, MediaCategoryDto.class);
	}
	//private static final String QUERY_MEDIA_CATEGORY_NUMS_BY_PARENTCODE = "select code, name, parent_media_category_code  from media_category where parent_media_category_code = ? and sts = 'A'";

	private static final String UPDATE_AD_STS_BY_ID = "update ad_instance set sts = 'D' where userid = ? and ad_id = ?";
	@Override
	public int updateAdInstanceByIdAndUser(int userid, int adId) {
		Object[] args = new Object[]{userid, adId};
		return db.update(UPDATE_AD_STS_BY_ID, args);
	}

	@Override
	public int insertAdMediaCategoryLink(AdMediaCategoryLinkDto adMediaCategoryLinkDto) {
		return db.insertObject(adMediaCategoryLinkDto);
	}

	@Override
	public int insertMediaCategory(
			MediaCategoryDto mediaCategoryDto) {
		return db.insertObject(mediaCategoryDto);
	}
	
	@Override
	public void delAdMediaCategoryLinks(AdInstanceDto adDto) {
		String sql= "delete from ad_media_category_link where ad_id = ?";
		db.update(sql,new Object[]{adDto.getAdId()});
	}
	
	@Override
	public void delAdCategoryLinks(AdInstanceDto adDto) {
		String sql= "delete from ad_category_link where ad_id = ?";
		db.update(sql,new Object[]{adDto.getAdId()});
	}
	
	
	/**
	 * 广告重定向人群对应关系新增
	 * @author xiaoming
	 * @date 2014年5月29日（修改）
	 */
	@Override
	public int insertAdVisitorLink(AdVisitorLinkDto adVisitorLinkDto) {
		String sql = "INSERT INTO ad_vc_link (ad_id, vc_id, create_time) VALUES (?, ? , ?)";
		Object[] args = new Object[]{
				adVisitorLinkDto.getAdId(), adVisitorLinkDto.getVcId(), adVisitorLinkDto.getCreateTime()
		};
		return db.update(sql, args);
	}

	@Override
	public int inserAdTerminalLink(AdTerminalLink adTerminalLink) {
		try {
			String sql = "INSERT INTO ad_t_link (ad_id,t_id,t_value) VALUES (?,?,?)";
			Object[] args = new Object[]{
					adTerminalLink.getAdId(),adTerminalLink.gettId(),
					adTerminalLink.gettValue()
			};
			return db.update(sql, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	//设备
	public List<TerminalBaseInfo> getdevicesByAdId(AdInstanceDto adInstanceDto) {
		try {
			String sql = "SELECT terminal_base_info.tbi_id, terminal_base_info.t_type, terminal_base_info.t_name, terminal_base_info.t_value FROM terminal_base_info INNER JOIN ad_t_link WHERE ad_t_link.t_id = terminal_base_info.tbi_id AND ad_id = ? AND terminal_base_info.t_type =1";
			Object[] args = new Object[]{
					adInstanceDto.getAdId()
			};
			return db.queryForListObject(sql, TerminalBaseInfo.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	//系统
	public List<TerminalBaseInfo> getsystemsByAdId(AdInstanceDto adInstanceDto) {
		try {
			String sql = "SELECT terminal_base_info.tbi_id, terminal_base_info.t_type, terminal_base_info.t_name, terminal_base_info.t_value FROM terminal_base_info INNER JOIN ad_t_link WHERE ad_t_link.t_id = terminal_base_info.tbi_id AND ad_id = ? AND terminal_base_info.t_type =2";
			Object[] args = new Object[]{
					adInstanceDto.getAdId()
			};
			return db.queryForListObject(sql, TerminalBaseInfo.class, args);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	//浏览器
	public List<TerminalBaseInfo> getbrowsersByAdId(AdInstanceDto adInstanceDto) {
		try {
			String sql = "SELECT terminal_base_info.tbi_id, terminal_base_info.t_type, terminal_base_info.t_name, terminal_base_info.t_value FROM terminal_base_info INNER JOIN ad_t_link WHERE ad_t_link.t_id = terminal_base_info.tbi_id AND ad_id = ? AND terminal_base_info.t_type =3";
			Object[] args = new Object[]{
					adInstanceDto.getAdId()
			};
			return db.queryForListObject(sql, TerminalBaseInfo.class, args);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ===============移动分类查询=========================
	private static final String QUERY_AD_APP_LIST = "select id, category from ad_mongo_app";
	@Override
	public List<AdApp> getAdAppList() {
		return db.queryForListObject(QUERY_AD_APP_LIST, AdApp.class);
	}

	private static final String QUERY_AD_CLICK_LIST = "select id, type from ad_mongo_click";
	@Override
	public List<AdClick> getAdClickList() {
		return db.queryForListObject(QUERY_AD_CLICK_LIST, AdClick.class);
	}

	private static final String QUERY_AD_DEVICE_LIST = "select id, type from ad_mongo_device";
	@Override
	public List<AdDevice> getAdDeviceList() {
		return db.queryForListObject(QUERY_AD_DEVICE_LIST, AdDevice.class);
	}

	private static final String QUERY_AD_INDUSTRY_LIST = "select id, description from ad_mongo_industry";
	@Override
	public List<AdIndustry> getAdIndustryList() {
		return db.queryForListObject(QUERY_AD_INDUSTRY_LIST, AdIndustry.class);
	}

	private static final String QUERY_AD_TYPE_LIST = "select id, description from ad_mongo_type";
	@Override
	public List<AdType> getAdTypeList() {
		return db.queryForListObject(QUERY_AD_TYPE_LIST, AdType.class);
	}
	
	// ===============移动分类查询=========================
	
	
	// ===============移动分类查询 by adId=========================
	private static final String QUERY_AD_APP_LIST_BY_ADID = "select id,category from ad_mongo_app where  id in (SELECT link.app_id from ad_mongo_app_link link where link.ad_id=?)";
	@Override
	public List<AdApp> getAdAppListByAdId(String adId) {
		Object[] params = new Object[]{adId};
		return db.queryForListObject(QUERY_AD_APP_LIST_BY_ADID, AdApp.class, params);
	}

	private static final String QUERY_AD_CLICK_BY_ADID = "select click.id, type, linktb.tar as target from ad_mongo_click click, (SELECT link.click_id as id, link.target as tar from ad_mongo_click_link link where link.ad_id=?) linktb where  click.id = linktb.id";
	@Override
	public AdClick getAdClickByAdId(String adId) {
		Object[] params = new Object[]{adId};
		return db.queryForObject(QUERY_AD_CLICK_BY_ADID, AdClick.class, params);
	}

	private static final String QUERY_AD_DEVICE_LIST_BY_ADID = "select id,type from ad_mongo_device where  id in (SELECT link.device_id from ad_mongo_device_link link where link.ad_id=?)";
	@Override
	public List<AdDevice> getAdDeviceListByAdId(String adId) {
		Object[] params = new Object[]{adId};
		return db.queryForListObject(QUERY_AD_DEVICE_LIST_BY_ADID, AdDevice.class, params);
	}

	private static final String QUERY_AD_INDUSTRY_LIST_BY_ADID = "select id,description from ad_mongo_industry where  id in (SELECT link.industry_id from ad_mongo_industry_link link where link.ad_id=?)";
	@Override
	public List<AdIndustry> getAdIndustryListByAdId(String adId) {
		Object[] params = new Object[]{adId};
		return db.queryForListObject(QUERY_AD_INDUSTRY_LIST_BY_ADID, AdIndustry.class, params);
	}

	private static final String QUERY_AD_TYPE_BY_ADID = "select id,description from ad_mongo_type where  id = (SELECT link.type_id from ad_mongo_type_link link where link.ad_id=?)";
	
	@Override
	public AdType getAdTypeByAdId(String adId) {
		Object[] params = new Object[]{adId};
		return db.queryForObject(QUERY_AD_TYPE_BY_ADID, AdType.class, params);
	}
	
	// ===============移动分类查询 by adId=========================


	// ===============新增 移动分类信息=========================
	private static final String INSERT_AD_APP_LINK = "insert into ad_mongo_app_link(ad_id, app_id) values(?,?)";
	@Override
	public void insertAdAppList(List<AdAppLink> adAppLinks) {
		Object[] args = null;
		for (AdAppLink link : adAppLinks) {
			args = new Object[]{link.getAdId(), link.getAppId()};
			db.update(INSERT_AD_APP_LINK, args);
		}
	}
	
	private static final String INSERT_AD_CLICK_LINK = "insert into ad_mongo_click_link(ad_id, click_id,target) values(?,?,?)";
	@Override
	public void insertAdClick(AdClickLink adClickLink) {
		Object[] args = new Object[]{adClickLink.getAdId(), adClickLink.getClickId(), adClickLink.getTarget()};
		db.update(INSERT_AD_CLICK_LINK, args);
	}
	
	private static final String INSERT_AD_DEVICE_LINK = "insert into ad_mongo_device_link(ad_id, device_id) values(?,?)";
	@Override
	public void insertAdDevice(List<AdDeviceLink> adDeviceLinks) {
		Object[] args = null;
		for (AdDeviceLink link : adDeviceLinks) {
			args = new Object[]{link.getAdId(), link.getDeviceId()};
			db.update(INSERT_AD_DEVICE_LINK, args);
		}
	}

	private static final String INSERT_AD_INDUSTRY_LINK = "insert into ad_mongo_industry_link(ad_id, industry_id) values(?,?)";
	@Override
	public void insertAdIndustryList(List<AdIndustryLink> adIndustriLinks) {
		Object[] args = null;
		for (AdIndustryLink link : adIndustriLinks) {
			args = new Object[]{link.getAdId(), link.getIndustryId()};
			db.update(INSERT_AD_INDUSTRY_LINK, args);
		}
	}
	
	private static final String INSERT_AD_TYPE_LINK = "insert into ad_mongo_type_link(ad_id, type_id) values(?,?)";
	@Override
	public void insertAdType(AdTypeLink adTypeLink) {
		Object[] args = new Object[]{adTypeLink.getAdId(),adTypeLink.getTypeId()};
		db.update(INSERT_AD_TYPE_LINK, args);
	}

	private static final String DELETE_AD_APP_LINK = "delete from ad_mongo_app_link where ad_id=?";
	@Override
	public void deleteAdAppList(String adId) {
		Object[] args = new Object[]{adId};
		db.update(DELETE_AD_APP_LINK, args);
	}
	
	// ===============新增 移动分类信息=========================


	// ===============删除 移动分类信息=========================
	private static final String DELETE_AD_CLICK_LINK = "delete from ad_mongo_click_link where ad_id=?";
	@Override
	public void deleteAdClick(String adId) {
		Object[] args = new Object[]{adId};
		db.update(DELETE_AD_CLICK_LINK, args);
	}

	private static final String DELETE_AD_DEVICE_LINK = "delete from ad_mongo_device_link where ad_id=?";
	@Override
	public void deleteAdDevice(String adId) {
		Object[] args = new Object[]{adId};
		db.update(DELETE_AD_DEVICE_LINK, args);
	}

	private static final String DELETE_AD_INDUSTRY_LINK = "delete from ad_mongo_industry_link where ad_id=?";
	@Override
	public void deleteAdIndustryList(String adId) {
		Object[] args = new Object[]{adId};
		db.update(DELETE_AD_INDUSTRY_LINK, args);
	}

	private static final String DELETE_AD_TYPE_LINK = "delete from ad_mongo_type_link where ad_id=?";
	@Override
	public void deleteAdType(String adId) {
		Object[] args = new Object[]{adId};
		db.update(DELETE_AD_TYPE_LINK, args);
	}
	// ===============删除 移动分类信息=========================

}
