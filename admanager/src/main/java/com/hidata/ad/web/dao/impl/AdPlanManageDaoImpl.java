package com.hidata.ad.web.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.dao.AdPlanManageDao;
import com.hidata.ad.web.dto.AdCrowdLinkDto;
import com.hidata.ad.web.dto.AdDeviceLinkDto;
import com.hidata.ad.web.dto.AdExtLinkDto;
import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.dto.AdInstanceShowDto;
import com.hidata.ad.web.dto.AdMLinkDto;
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
import com.hidata.ad.web.dto.mongo.AdDeviceLink;
import com.hidata.ad.web.model.AdInstance;
import com.hidata.ad.web.model.AdIpTargeting;
import com.hidata.ad.web.model.AdMLink;
import com.hidata.ad.web.model.AdMaterial;
import com.hidata.ad.web.model.AdPlanPortrait;
import com.hidata.ad.web.model.AdStatBase;
import com.hidata.ad.web.model.AdTerminalLink;
import com.hidata.ad.web.model.ChannelBase;
import com.hidata.ad.web.model.ChannelSiteRel;
import com.hidata.ad.web.model.Crowd;
import com.hidata.ad.web.model.KeyWordAdPlan;
import com.hidata.ad.web.model.MaterialStatBase;
import com.hidata.ad.web.model.RegionTargeting;
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
public class AdPlanManageDaoImpl implements AdPlanManageDao {

	@Autowired
	private DBManager db;

//	private static final String INSERT_ADINSTANCE_SQL = "insert into ad_instance(ad_name,ad_desc,start_time,end_time,charge_type,all_budget,day_budget,userid,all_budget,day_budget,channel,ad_url,create_time,sts) values(?, ?, ?,?,?,?,?,?,?,?)";

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

	private static final String INSERT_ADSITE_SQL = "insert into ad_site(ad_id,url,match_type,create_time,sts,site_type) values(?, ?, ?, ?, ?, ?)";

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
						DateUtil.C_TIME_PATTON_DEFAULT), adSiteDto.getSts() , adSiteDto.getSiteType()};
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
		String sql = "insert into ad_time_frequency(ad_id,put_interval_unit,put_interval_num,day_limit,create_time,sts,minute_limit,is_uniform) values(?,?,?,?,?,?,?,?)";

		return db.update(
				sql,
				new Object[] { adTimeFrequencyDto.getAdId(),
						adTimeFrequencyDto.getPutIntervalUnit(),
						adTimeFrequencyDto.getPutIntervalNum(),
						adTimeFrequencyDto.getDayLimit(),
						adTimeFrequencyDto.getCreateTime(),
						adTimeFrequencyDto.getSts(),adTimeFrequencyDto.getMinLimit(),adTimeFrequencyDto.getIsUniform() });
	}

	/**
	 * 广告唯一用户展现次数设置
	 */
	@Override
	public int insertAdUserFrequency(AdUserFrequencyDto adUserFrequencyDto) {
		String sql = "insert into ad_user_frequency(ad_id,time_unit,uid_impress_num,create_time,sts,uid_ip_num) values(?,?,?,?,?,?)";

		db.update(
				sql,
				new Object[] { adUserFrequencyDto.getAdId(),
						adUserFrequencyDto.getTimeUnit(),
						adUserFrequencyDto.getUidImpressNum(),
						adUserFrequencyDto.getCreateTime(),
						adUserFrequencyDto.getSts(),
						adUserFrequencyDto.getUidIpNum()});
		return 0;
	}
	
	
	/**
	 * 根据ID查找广告信息 添加字段 第三方统计代码
	 */
	@Override
	public AdInstanceDto getAdInstanceByAdId(AdInstanceDto adInstanceDto) {
		
		String sql = "select ad_id,ad_name,ad_desc,userid,charge_type,date_format(start_time,'%Y-%m-%d') start_time,date_format(end_time,'%Y-%m-%d') end_time,all_budget,day_budget,ad_url,create_time,sts,ad_3stat_code,ad_3stat_code_sub, channel, total,max_cpm_bidprice, link_type,unit_price,ad_type from ad_instance where ad_id = ?";
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
		
		String sql = "select ad_s_id,ad_id,url,match_type,create_time,sts , site_type from ad_site where ad_id = ?";
		Object[] args = new Object[] { adInstanceDto.getAdId() };

		List<AdSiteDto> adSIList = db.queryForListObject(sql, args, AdSiteDto.class);
		return adSIList;
	}

//	@Override
//	public List<Crowd> getAdCrowdsByAdId(AdInstanceDto adInstanceDto) {
//		
//		String sql = "select b.crowd_id,b.crowd_name,b.crowd_desc,b.create_time,b.sts,b.user_id from ad_crowd_link a,crowd b where a.crowd_id = b.crowd_id and a.ad_id = ?";
//		Object[] args = new Object[] { adInstanceDto.getAdId() };
//
//		List<Crowd> adCIList = db.queryForListObject(sql, args, Crowd.class);
//		return adCIList;
//	}
	@Override
	public List<Crowd> getAdCrowdsByAdId(AdInstanceDto adInstanceDto) {
		
		String sql = "select b.crowd_id,b.crowd_name,b.create_date,b.crowd_sts as sts,b.user_id from ad_crowd_link a,crowd_find_info b where a.crowd_id = b.crowd_id and a.ad_id = ?";
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
		
		String sql = "select ad_tf_id,ad_id,put_interval_unit,put_interval_num,day_limit,create_time,sts,minute_limit,is_uniform from ad_time_frequency a where a.ad_id = ?";
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
		String sql = "select ad_uf_id,ad_id,time_unit,uid_impress_num,create_time,sts,uid_ip_num from ad_user_frequency a where a.ad_id = ?";
		Object[] args = new Object[] { adInstanceDto.getAdId() };

		List<AdUserFrequencyDto> adUFIList = db.queryForListObject(sql, args, AdUserFrequencyDto.class);
		return adUFIList;
	}

	@Override
	public void updateAdInstance(AdInstanceDto adDto) {
		
		String sql = "update ad_instance set ad_name= ?,ad_desc= ?,charge_type = ?,start_time = ?,end_time = ?,unit_price = ?,"
				+ "all_budget = ?,day_budget = ? ,ad_3stat_code= ?,ad_3stat_code_sub= ?, channel = ?, total=?,max_cpm_bidprice=?, link_type=? where ad_id = ?";
		if(StringUtils.isEmpty(adDto.getMaxCpmBidprice())){
			adDto.setMaxCpmBidprice(null);
		}
		db.update(sql, new Object[]{adDto.getAdName(),adDto.getAdDesc(),adDto.getChargeType(),adDto.getStartTime(),adDto.getEndTime(),adDto.getUnitPrice(),adDto.getAllBudget(), adDto.getDayBudget(), adDto.getAd3statCode(),adDto.getAd3statCodeSub(),adDto.getChannel(), adDto.getTotal(),adDto.getMaxCpmBidprice(),adDto.getLinkType(),adDto.getAdId()});
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
	
	// 查询广告：去掉移动端的类型
	private static final String QUERY_AD_LIST_BY_USER = "select ad_id, ad_name, ad_desc, date_format(start_time,'%Y-%m-%d %H:%i:%s') start_time, date_format(end_time,'%Y-%m-%d %H:%i:%s') end_time, all_budget, day_budget, sts, ad_url,ad_toufang_sts from ad_instance where userid = ? and sts = 'A' and channel !=3 and ad_useful_type = 'N'";
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
	
	
	private static final String QUERY_REGION_LIST_BY_PARENDCODE = "select code, name, parent_code  from region_targeting where parent_code = ? ";

	@Override
	public List<RegionTargeting> findRegionListByParendCode(String parentcode){
		Object[] args = new Object[]{parentcode};
		return db.queryForListObject(QUERY_REGION_LIST_BY_PARENDCODE, args, RegionTargeting.class);
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
	@Override
	public void delAdRegionLink(AdInstanceDto adDto) {
		String sql= "delete from ad_region_link where ad_id = ?";
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
	
	@Override
	public void saveAdStatBase(AdStatBase adStatBase) {
		db.insertObject(adStatBase);
		
	}

	@Override
	public void saveMaterialStatBase(MaterialStatBase materialStatBase) {
		db.insertObject(materialStatBase);
		
	}
	
	@Override
	public int delAdIpTargetingByAdId(Integer adId) throws Exception{
		StringBuilder sql = new StringBuilder("delete from ad_ip_targeting where ad_id=?");
		
		return db.update(sql.toString(), new Object[]{adId});
	}
	
	@Override
	public int addAdIpTargeting(AdIpTargeting adIpTargeting) throws Exception{
		return db.insertObjectAndGetAutoIncreaseId(adIpTargeting);
	}

	@Override
	public Integer insertAdExtLink(AdExtLinkDto adExtLink) {
		try {
			Integer id = db.insertObjectAndGetAutoIncreaseId(adExtLink);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int insertAdPlanKeyWord(KeyWordAdPlan keyWordAdPlan){
		String sql="insert into adplan_keyword(key_wd,fetch_cicle,is_jisoujitou,search_type,ad_id,sts,sts_date,match_type,kw_type,kw_file_path) values(?,?,?,?,?,?,?,?,?,?)";
		Object[] args = new Object[]{keyWordAdPlan.getKeyWd(),keyWordAdPlan.getFetchCicle(),keyWordAdPlan.getIsJisoujitou(),keyWordAdPlan.getSearchType(),
				keyWordAdPlan.getAdId(),keyWordAdPlan.getSts(),keyWordAdPlan.getSearchDate(),keyWordAdPlan.getMatchType(),keyWordAdPlan.getKwType(),keyWordAdPlan.getKwFilePath()};
		return db.update(sql,args);

	}

	@Override
	public KeyWordAdPlan getAdPlanKeyWordByAdId(String adId) {
	    String sql="select * from adplan_keyword where ad_id=?";
	    Object[] args = new Object[]{adId};
		return db.queryForObject(sql, args,KeyWordAdPlan.class);
	}

	@Override
	public int updateAdPlanKeyword(KeyWordAdPlan keyWordAdPlan) {
		String sql="update adplan_keyword set key_wd=?,fetch_cicle=?,is_jisoujitou=?,search_type=?,search_date=?,"
				+ "search_num=?,sts_date=?,match_type=? where ad_id=?";
		Object[] args = new Object[]{keyWordAdPlan.getKeyWd(),keyWordAdPlan.getFetchCicle(),keyWordAdPlan.getIsJisoujitou(),keyWordAdPlan.getSearchType(),
				keyWordAdPlan.getSearchDate(),keyWordAdPlan.getSearchNum(),keyWordAdPlan.getStsDate(),keyWordAdPlan.getMatchType(),keyWordAdPlan.getAdId()};
		return db.update(sql,args);
	}

	@Override
	public int insertAdPlanPortrait(AdPlanPortrait portrait) {
		String sql="insert into adplan_portrait(ad_id,sex,age_range) values(?,?,?)";
		Object[] args = new Object[]{portrait.getAdId(),portrait.getSex(),portrait.getAgeRange()};
		return db.update(sql,args);
	}

	@Override
	public AdPlanPortrait getAdPlanPortraitByAdId(String adId) {
		String sql="select * from adplan_portrait where ad_id=?";
	    Object[] args = new Object[]{adId};
		return db.queryForObject(sql, args,AdPlanPortrait.class);
	}

	@Override
	public int updateAdPlanPortrait(AdPlanPortrait portrait) {
		String sql="update adplan_portrait set sex=?,age_range=? where ad_id=?";
		Object[] args = new Object[]{portrait.getSex(),portrait.getAgeRange(),portrait.getAdId()};
		return db.update(sql,args);	
	}

	@Override
	public List<ChannelBase> getChannelBaseInfo() {
		String sql="select * from channel_base";
		return db.queryForListObject(sql, ChannelBase.class);
	}

	@Override
	public List<ChannelSiteRel> findChannelSiteRelByChannelIds(String channels) {						
		StringBuffer sqlBuilder = new StringBuffer();
		sqlBuilder.append("select csr.*,cb.channel_name as channel_name from channel_base cb,channel_site_rel csr where (");
		if(!channels.equals("")){
			String[] channelId = channels.split(",");
		    for(int i=0;i<channelId.length;i++){
		    	sqlBuilder.append("csr.channel_id='"+channelId[i]+"'");
		        if(i<channelId.length-1){
		        	sqlBuilder.append(" or ");
		        }
		    }
		    sqlBuilder.append(") and cb.id = csr.channel_id");
		}
		return db.queryForListObject(sqlBuilder.toString(),ChannelSiteRel.class);				
	}

	@Override
	public List<AdSiteDto> findAdSiteDtoByAdId(String adId) {
		String sql ="select * from ad_site where ad_id = ?";
		Object[] args = new Object[]{adId};
		return db.queryForListObject(sql,args,AdSiteDto.class);
	}

	@Override
	public ChannelBase findChannelBaseByChannelId(String channelId){
		String sql ="select * from channel_base where id = ?";
		Object[] args = new Object[]{channelId};
		return db.queryForObject(sql,args,ChannelBase.class);
	}

	@Override
	public KeyWordAdPlan findAdPlanKeywordByAdId(String adId) {
		String sql="select * from adplan_keyword where ad_id=?";
		Object[] args = new Object[]{adId};
		return db.queryForObject(sql, args,KeyWordAdPlan.class);
	}

	@Override
	public AdPlanPortrait findAdPortraitByAdId(String adId) {
		String sql="select * from adplan_portrait where ad_id=?";
		Object[] args = new Object[]{adId};
		return db.queryForObject(sql, args,AdPlanPortrait.class);
	}

	@Override
	public int deletePortraitByAdId(String adId) {
		String sql = "delete from adplan_portrait where ad_id =?";
		Object[] args = new Object[]{adId};
		return db.update(sql,args);
	}

	@Override
	public int deleteKeywordByAdId(String adId) {
		String sql = "delete from adplan_keyword where ad_id=?";
		Object[] args = new Object[]{adId};
		return db.update(sql,args);
	}

	@Override
	public List<ChannelSiteRel> findChannelSiteRelByIds(String channels) {
		StringBuffer sqlBuilder = new StringBuffer();
		sqlBuilder.append("select csr.*,cb.channel_name as channel_name from channel_base cb,channel_site_rel csr where (");
		if(!channels.equals("")){
			String[] channelId = channels.split(",");
		    for(int i=0;i<channelId.length;i++){
		    	sqlBuilder.append("csr.id='"+channelId[i]+"'");
		        if(i<channelId.length-1){
		        	sqlBuilder.append(" or ");
		        }
		    }
		    sqlBuilder.append(") and cb.id = csr.channel_id");
		}
		return db.queryForListObject(sqlBuilder.toString(),ChannelSiteRel.class);
	}

	@Override
	public int insertAdPlanSite(AdSiteDto adSiteDto) {
	
		return db.insertObjectAndGetAutoIncreaseId(adSiteDto);
	}

	@Override
	public AdExtLinkDto findAdExtLinkByAdId(String adId) {
		String sql="select * from ad_ext_link where ad_instance_id=?";
		Object[] args = new Object[]{adId};
		return db.queryForObject(sql,args,AdExtLinkDto.class);
	}

	@Override
	public AdInstanceDto getAdInstanceDtoByAdId(String adId) {
		String sql="select * from ad_instance where ad_id =?";
		Object[] args = new Object[]{adId};
		return db.queryForObject(sql,args,AdInstanceDto.class);
	}

	@Override
	public List<AdMLink> getAdMaterialsByAdId(String adId) {
		String sql="select * from ad_m_link where ad_id=?";
		Object[] args = new Object[]{adId};
		return db.queryForListObject(sql,args,AdMLink.class);
	}

	@Override
	public List<AdInstanceShowDto> getListAdShow(int userId) {
		String sql = "SELECT m.* , n.throw_url FROM (SELECT a.ad_id, a.ad_name, a.ad_desc, a.link_type , DATE_FORMAT(a.start_time,'%Y-%m-%d') start_time, DATE_FORMAT(a.end_time,'%Y-%m-%d') end_time, a.sts, a.ad_url, a.remark, a.ad_toufang_sts,b.day_limit FROM ad_instance a ,ad_time_frequency b WHERE a.userid = " + userId + " AND a.sts = 'A' AND a.channel !=3 AND (a.ad_useful_type='N' or a.ad_useful_type='T') AND a.ad_id = b.ad_id ) m LEFT JOIN ad_ext_link n ON n.ad_instance_id = m.ad_id";
		try {
			List<AdInstanceShowDto> list = db.queryForListObject(sql, AdInstanceShowDto.class);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateAdExtLink(AdExtLinkDto adExtLink) {
		String sql="update ad_ext_link set throw_url=? where id=?";
		Object[] args = new Object[]{adExtLink.getThrowUrl(),adExtLink.getId()};
		db.update(sql,args);
		
	}

	@Override
	public int insertAdDeviceLlink(AdDeviceLinkDto adDeviceLinkDto) {
		db.insertObject(adDeviceLinkDto);
		return 0;
	}

	@Override
	public void DelAdDeviceLlink(AdInstanceDto adInstance) {
		String sql="delete from ad_device_link where ad_id=? ";
		Object[] args = new Object[]{adInstance.getAdId()};
		db.update(sql,args);
		
	}

	@Override
	public List<AdDeviceLinkDto> getListAdDeviceLink(AdDeviceLinkDto adDeviceLinkDto) {
		String sql="select * from ad_device_link where ad_id=?";
		Object[] args = new Object[]{adDeviceLinkDto.getAdId()};
		return db.queryForListObject(sql,args,AdDeviceLinkDto.class);
	}
}
