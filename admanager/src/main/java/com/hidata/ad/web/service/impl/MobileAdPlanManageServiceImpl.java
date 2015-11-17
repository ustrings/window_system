package com.hidata.ad.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;

import com.hidata.ad.web.dao.AdCategoryDao;
import com.hidata.ad.web.dao.AdMLinkDao;
import com.hidata.ad.web.dao.AdMaterialCacheDao;
import com.hidata.ad.web.dao.AdMaterialDao;
import com.hidata.ad.web.dao.AdPlanManageDao;
import com.hidata.ad.web.dao.MobileAdPlanManageDao;
import com.hidata.ad.web.dto.AdCategoryLinkDto;
import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.dto.AdMLinkDto;
import com.hidata.ad.web.dto.AdMaterialLinkDto;
import com.hidata.ad.web.dto.AdPlanManageInfoDto;
import com.hidata.ad.web.dto.AdSiteDto;
import com.hidata.ad.web.dto.AdTimeFilterDto;
import com.hidata.ad.web.dto.AdTimeFrequencyDto;
import com.hidata.ad.web.dto.AdUserFrequencyDto;
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
import com.hidata.ad.web.dto.mongo.MobileAdPlanManageInfoDto;
import com.hidata.ad.web.model.AdInstance;
import com.hidata.ad.web.model.AdMaterialCache;
import com.hidata.ad.web.model.AdStatBase;
import com.hidata.ad.web.model.AdStatRedisInfo;
import com.hidata.ad.web.model.Crowd;
import com.hidata.ad.web.model.User;
import com.hidata.ad.web.model.VisitorCrowd;
import com.hidata.ad.web.service.MobileAdPlanManageService;
import com.hidata.ad.web.service.TerminalBaseInfoService;
import com.hidata.ad.web.session.SessionContainer;
import com.hidata.ad.web.util.AdConstant;
import com.hidata.ad.web.util.Config;
import com.hidata.framework.cache.redis.JedisPoolWriper;
import com.hidata.framework.util.DateUtil;
import com.hidata.framework.util.JsonUtil;

/**
 * 广告计划维护服务 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author chenjinzhao
 */
@Component
@Transactional(propagation = Propagation.REQUIRED)
public class MobileAdPlanManageServiceImpl implements MobileAdPlanManageService {

    @Autowired
    private MobileAdPlanManageDao mobileAdPlanManageDao;
    
    @Autowired
    private AdPlanManageDao adPlanManageDao;

    @Autowired
    private AdMaterialDao adMaterialDao;

    @Autowired
    private AdCategoryDao adCategoryDao;

    @Autowired
    private AdMaterialCacheDao cache;

    @Autowired
    private TerminalBaseInfoService terminalBaseInfoService;

    @Autowired
    private AdMLinkDao adMLinkDao;
    
    @Resource(name = "jedisPool219")
  	private JedisPoolWriper jedisPool;
    
    private Logger logger = Logger.getLogger(AdPlanManageServiceImpl.class);

    /**
     * 广告计划添加 错误：事物没起作用。。。。。
     * 
     * @author chenjinzhao
     */
    @Override
    public void addAdPlan(MobileAdPlanManageInfoDto adDto) {
        String ctStr = DateUtil.getCurrentDateTimeStr();// 创建时间
        User user = SessionContainer.getSession(); // 用户实体
        // 广告基础信息
        String adId = this.addPlanBaisc(adDto, user, ctStr);
        // System.out.println(i);
        // 投放时间定向
        this.addPlanTime(adDto, user, ctStr, adId);
        // 广告物料
        this.addPlanMaterial(null, adDto, user, ctStr, adId);
        // 广告投放策略
        this.addPlanStrategy(adDto, user, ctStr, adId);
        // 广告行业分类
//        addIndustries(adDto, adId);
        this.addAdCategoryLink(adDto, user, ctStr, adId);
        // 广告类型
        addType(adDto, adId);
        // App	分类
        addApps(adDto, adId);
        // 设备类型
        addDevices(adDto, adId);
        // 广告点击类型
        addClick(adDto, adId);
        List<AdMaterialCache> listInCache = adMaterialDao.findMaterialByAdId(adId);
        cache.hset(adId, listInCache);// 广告ID与广告物料的关系
        
        try {
			AdStatBase adStatBaseDay = new AdStatBase();
			adStatBaseDay.setAdId(adId);
			adStatBaseDay.setPvNum("0");
			adStatBaseDay.setClickNum("0");
			adStatBaseDay.setIpNum("0");
			adStatBaseDay.setMobileClickNum("0");
			adStatBaseDay.setMobilePvNum("0");
			adStatBaseDay.setNumType("D");
			adStatBaseDay.setTs(DateUtil.getCurrentDayBeginDateTimeStr());
			adStatBaseDay.setUvNum("0");

			adPlanManageDao.saveAdStatBase(adStatBaseDay);

			AdStatBase adStatBaseHour = new AdStatBase();
			adStatBaseHour.setAdId(adId);
			adStatBaseHour.setPvNum("0");
			adStatBaseHour.setClickNum("0");
			adStatBaseHour.setIpNum("0");
			adStatBaseHour.setMobileClickNum("0");
			adStatBaseHour.setMobilePvNum("0");
			adStatBaseHour.setNumType("H");
			adStatBaseHour.setTs(DateUtil.getCurrentHourBeginDateTimeStr());
			adStatBaseHour.setUvNum("0");

			adPlanManageDao.saveAdStatBase(adStatBaseHour);

			AdStatRedisInfo adStatRedisInfo = new AdStatRedisInfo();

			adStatRedisInfo.setAdId(adId);
			adStatRedisInfo.setTotal_pv_num("0");
			adStatRedisInfo.setTotal_click_num("0");
			adStatRedisInfo.setToday_pv_num("0");
			adStatRedisInfo.setToday_uv_num("0");
			adStatRedisInfo.setToday_ip_num("0");
			adStatRedisInfo.setToday_click_num("0");
			adStatRedisInfo.setToday_mobile_click_num("0");
			adStatRedisInfo.setToday_mobile_pv_num("0");
			adStatRedisInfo.setCurrentHour_pv_num("0");
			adStatRedisInfo.setCurrentHour_uv_num("0");
			adStatRedisInfo.setCurrentHour_ip_num("0");
			adStatRedisInfo.setCurrentHour_click_num("0");
			adStatRedisInfo.setCurrentHour_mobile_click_num("0");
			adStatRedisInfo.setCurrentHour_mobile_pv_num("0");

			Jedis client = jedisPool.getJedis();

			String jsonStr = JsonUtil.beanToJson(adStatRedisInfo);
			JSONObject jsonObj = JSONObject.fromObject(jsonStr);
			Set<String> jsonKeys = jsonObj.keySet();
			for (String field : jsonKeys) {
				String value = jsonObj.getString(field);
				if (StringUtils.isNotBlank(value)) {
					client.hset(AdConstant.AD_STAT_KEY_PREFIX + adId, field,
							value);
				}
			}

			jedisPool.releaseBrokenJedis(client);
		} catch (Exception e) {
			logger.error("添加广告，存入统计信息，存入redis 统计信息出错",e);
		}
    }
    private void addAdCategoryLink(MobileAdPlanManageInfoDto adDto, User user, String ctStr, String adId) {
        /**
         * 添加广告类别link关系
         */
        String adCategoryIds = adDto.getAdCategoryCodes();
        if (StringUtils.isNotBlank(adCategoryIds)) {
            String[] mediaCategoryIdArray = adCategoryIds.split(",");
            for (String adCategoryId : mediaCategoryIdArray) {
                AdCategoryLinkDto adCategoryLinkDto = new AdCategoryLinkDto();
                adCategoryLinkDto.setAd_id(adId);
                adCategoryLinkDto.setAd_category_id(adCategoryId);
                adCategoryDao.insertAdCategoryLink(adCategoryLinkDto);
            }
        }
    }
    private void addClick(MobileAdPlanManageInfoDto adDto, String adId) {
    	AdClick click  = adDto.getAdClick();
    	AdClickLink link = new AdClickLink();
    	link.setAdId(adId);
    	link.setClickId(click.getId());
    	link.setTarget(click.getTarget());
    	mobileAdPlanManageDao.insertAdClick(link);
    }
    
    private void addType(MobileAdPlanManageInfoDto adDto, String adId) {
    	AdType type  = adDto.getAdType();
    	AdTypeLink link = new AdTypeLink();
    	link.setAdId(adId);
    	link.setTypeId(type.getId());
    	mobileAdPlanManageDao.insertAdType(link);
    }
    
    private void addIndustries(MobileAdPlanManageInfoDto adDto, String adId) {
    	String industryIds = adDto.getAdIndustryIds();
    	List<AdIndustryLink> industries = new ArrayList<AdIndustryLink>();
    	if (industryIds != null && !industryIds.equals("")) {
    		String[] ids  = industryIds.split(",");
    		for (String id : ids) {
    			AdIndustryLink link = new AdIndustryLink();
    			link.setAdId(adId);
    			link.setIndustryId(id);
    			industries.add(link);
    		}
    	}
    	mobileAdPlanManageDao.insertAdIndustryList(industries);
    }
    
    private void addApps(MobileAdPlanManageInfoDto adDto, String adId) {
    	String appIds = adDto.getAdAppIds();
    	List<AdAppLink> appLinks = new ArrayList<AdAppLink>();
    	if (appIds != null && !appIds.equals("")) {
    		String[] ids  = appIds.split(",");
    		for (String id : ids) {
    			AdAppLink link = new AdAppLink();
    			link.setAdId(adId);
    			link.setAppId(id);
    			appLinks.add(link);
    		}
    	}
    	mobileAdPlanManageDao.insertAdAppList(appLinks);
    }

   
    private void addDevices(MobileAdPlanManageInfoDto adDto, String adId) {
    	String deviceIds = adDto.getAdDeviceIds();
    	List<AdDeviceLink> deviceLinks = new ArrayList<AdDeviceLink>();
    	if (deviceIds != null && !deviceIds.equals("")) {
    		String[] ids  = deviceIds.split(",");
    		for (String id : ids) {
    			AdDeviceLink link = new AdDeviceLink();
    			link.setAdId(adId);
    			link.setDeviceId(id);
    			deviceLinks.add(link);
    		}
    	}
    	mobileAdPlanManageDao.insertAdDevice(deviceLinks);
    }
    
    /**
     * 添加广告信息 （修改） 日期：2014-4-18
     * 
     * @author xiaoming
     * @param adDto
     * @param user
     * @param ctStr
     * @return
     */
    @Transactional
    private String addPlanBaisc(MobileAdPlanManageInfoDto adDto, User user, String ctStr) {
        AdInstanceDto adInstanceDto = new AdInstanceDto();
        BeanUtils.copyProperties(adDto.getAdInstanceDto(), adInstanceDto);//
        adInstanceDto.setCreateTime(ctStr);
        adInstanceDto.setUserId(user.getUserId() + "");
        adInstanceDto.setSts(AdConstant.AD_STS_A);
        String adId = mobileAdPlanManageDao.insertAdInstance(adInstanceDto) + "";

        /**
         * 回显添加AdUrl
         */
        String adUrl = Config.getProperty("adurl_prefix") + "/" + user.getUserId() + "/" + adId;
        String adTanxUrl = Config.getProperty("ad_tanxurl_prefix") + "/" + user.getUserId() + "/"
                + adId;
        adInstanceDto.setAdTanxUrl(adTanxUrl);
        adInstanceDto.setAdId(adId + "");
        adInstanceDto.setAdUrl(adUrl);

        mobileAdPlanManageDao.updateAdUrl(adInstanceDto);

        return adId;
    }

    /**
     * 添加投放时间
     * 
     * @param adDto
     * @param user
     * @param ctStr
     * @author chenjinzhao
     */
    private void addPlanTime(MobileAdPlanManageInfoDto adDto, User user, String ctStr, String adId) {
        /**
         * 添加投放时间
         */
        // 数据格式 workDay:true%start:1#end:2;weekendDay:true%start:1#end:2
        // workDay:true%start:1#end:23;weekendDay:true%start:3#end:15

        String putHours = adDto.getPutHours();
        if (StringUtils.isNotBlank(putHours)) {
            String[] daysOfWeeks = putHours.split(";");
            for (String daysOfWeek : daysOfWeeks) {
                AdTimeFilterDto adTimeFilterDto = assembleAdTimeFilterFromStr(daysOfWeek);
                if (adTimeFilterDto != null) {
                    adTimeFilterDto.setAdId(adId);
                    adTimeFilterDto.setUpdateTime(ctStr);
                    mobileAdPlanManageDao.insertAdTimeFilter(adTimeFilterDto);
                }
            }
        }
    }

    public AdTimeFilterDto assembleAdTimeFilterFromStr(String str) {
        // 数据格式
        // workDay:true%start:1#end:2;
        // weekendDay:true%start:1#end:2;
        AdTimeFilterDto adTimeFilterDto = null;
        String[] str1 = str.split("%");
        String[] str2 = str1[0].split(":");
        if (str2[1].equals("true")) {
            adTimeFilterDto = new AdTimeFilterDto();
            // 如果是工作日就设置日期 为 2,3,4,5,6
            if (str2[0].equals("workDay")) {
                adTimeFilterDto.setDaysOfWeek("2,3,4,5,6");
            } else {
                adTimeFilterDto.setDaysOfWeek("1,7");
            }
            // 获取时间 start:1 和 end:2
            String[] str3 = str1[1].split("#");
            int start = Integer.parseInt(str3[0].split(":")[1]);
            int end = Integer.parseInt(str3[1].split(":")[1]);
            adTimeFilterDto.setStartHour(start);
            adTimeFilterDto.setEndHour(end);
        }
        return adTimeFilterDto;
    }


    private void addPlanMaterial(List<AdMLinkDto> list, MobileAdPlanManageInfoDto adDto, User user,
            String ctStr, String adId) {
        /**
         * 添加广告物料link关系
         */
        String adMaterialIds = adDto.getAdMaterialIds();
        if (StringUtils.isNotBlank(adMaterialIds)) {
            String[] adMaterialIdsArray = adMaterialIds.split(",");
            for (String adMaterialId : adMaterialIdsArray) {
                AdMaterialLinkDto adMaterialLink = new AdMaterialLinkDto();
                adMaterialLink.setAdId(adId);
                adMaterialLink.setAdMaterId(adMaterialId);
                adMaterialLink.setCreateTime(ctStr);
                adMaterialLink.setSts(AdConstant.AD_STS_A);

                // 处理原来的认证信息
                if (list != null && list.size() > 0) {
                    for (AdMLinkDto link : list) {
                        if (adMaterialId.equals(String.valueOf(link.getAdMId()))) {
                            adMaterialLink.setCheckStatus(link.getCheckStatus());
                            adMaterialLink.setComment(link.getComment());
                        }
                    }
                }

                mobileAdPlanManageDao.insertAdMaterialLink(adMaterialLink);
                // 更新广告物料状态
                // adMaterialDao.updateAdMaterialStatus(user.getUserId(),
                // Integer.valueOf(adMaterialId));
            }
        }

    }

    /**
     * 广告投放策略
     * 
     * @param adDto
     * @param user
     * @param ctStr
     * @param adId
     * @author chenjinzhao
     */
    private void addPlanStrategy(MobileAdPlanManageInfoDto adDto, User user, String ctStr, String adId) {
        /**
         * 添加广告投放频率
         */
        AdTimeFrequencyDto adTimeFrequencyDto = new AdTimeFrequencyDto();
        BeanUtils.copyProperties(adDto.getAdTimeFrequencyDto(), adTimeFrequencyDto);

        adTimeFrequencyDto.setAdId(adId);
        adTimeFrequencyDto.setSts(AdConstant.AD_STS_A);
        adTimeFrequencyDto.setCreateTime(ctStr);
        mobileAdPlanManageDao.insertAdTimeFrequency(adTimeFrequencyDto);

        /**
         * 添加广告唯一访客展现次数
         */
        AdUserFrequencyDto adUserFrequencyDto = new AdUserFrequencyDto();
        BeanUtils.copyProperties(adDto.getAdUserFrequencyDto(), adUserFrequencyDto);

        adUserFrequencyDto.setAdId(adId);
        adUserFrequencyDto.setCreateTime(ctStr);
        adUserFrequencyDto.setSts(AdConstant.AD_STS_A);
        mobileAdPlanManageDao.insertAdUserFrequency(adUserFrequencyDto);
    }

    /**
     * 通过adid查询一个广告计划的所有信息
     * 
     * @author chenjinzhao
     */
    @Override
    @Transactional
    public MobileAdPlanManageInfoDto getOneAdplanInfo(String adId) {
        MobileAdPlanManageInfoDto mobileAdPlanManageInfo = new MobileAdPlanManageInfoDto();

        AdInstanceDto adInstanceQry = new AdInstanceDto();
        adInstanceQry.setAdId(adId);

        // 广告基本信息
        AdInstanceDto adInstance = mobileAdPlanManageDao.getAdInstanceByAdId(adInstanceQry);
        mobileAdPlanManageInfo.setAdInstanceDto(adInstance);

        // 数据格式 workDay:true%start:1#end:2;weekendDay:true%start:1#end:2
        List<AdTimeFilterDto> adTFList = mobileAdPlanManageDao.getAdTimeFiltersByAdId(adInstanceQry);

        StringBuffer timeStr = new StringBuffer();
        for (AdTimeFilterDto adTimeFilter : adTFList) {
            // 判断
            if (adTimeFilter.getDaysOfWeek().contains("1")) {
                timeStr.append("weekendDay:true%start:").append(adTimeFilter.getStartHour())
                        .append("#end:").append(adTimeFilter.getEndHour()).append(";");
            } else {
                timeStr.append("workDay:true%start:").append(adTimeFilter.getStartHour())
                        .append("#end:").append(adTimeFilter.getEndHour()).append(";");
            }
        }

        if (timeStr.length() > 0) {
        	mobileAdPlanManageInfo.setPutHours(timeStr.deleteCharAt(timeStr.length() - 1).toString());
        }
        
        // 设置广告行业
        List<AdIndustry> adIndustries = this.getAdIndustryListByAdId(adId);
        // 拼接industryIds 字符串
        StringBuffer bufIndustryIds = new StringBuffer();
        if(adIndustries.size() > 0) {
      	  for (AdIndustry industry : adIndustries) {
      		  bufIndustryIds.append(industry.getId() + ",");
      	  }
        }
        if (bufIndustryIds.length() > 0) {
      	  mobileAdPlanManageInfo.setAdIndustryIds(bufIndustryIds.toString());
        }
        
        // 设置广告
        List<AdApp> adApps =this.getAdAppListByAdId(adId);
        StringBuffer bufadApps= new StringBuffer();
        if(adApps.size() > 0) {
      	  for (AdApp adApp : adApps) {
      		  bufadApps.append(adApp.getId() + ",");
      	  }
        }
        if (bufadApps.length() > 0) {
      	  mobileAdPlanManageInfo.setAdAppIds(bufadApps.toString());
        }
        
        // 广告类型
        AdType adType = this.getAdTypeByAdId(adId);
        mobileAdPlanManageInfo.setAdType(adType);
        // 设备类型
        List<AdDevice> adDevices = this.getAdDeviceListByAdId(adId);
        StringBuffer bufDevices = new StringBuffer();
        for (AdDevice device : adDevices) {
        	bufDevices.append(device.getId() + ",");
        }
        mobileAdPlanManageInfo.setAdDeviceIds(bufDevices.toString());
        
        // 广告点击类型
        AdClick adClick = this.getAdClickByAdId(adId);
        mobileAdPlanManageInfo.setAdClick(adClick);

        // 广告投放频率
        AdTimeFrequencyDto adTimeFrequency = mobileAdPlanManageDao
                .getAdTimeFrequencyByAdId(adInstanceQry);
        mobileAdPlanManageInfo.setAdTimeFrequencyDto(adTimeFrequency);

        List<AdUserFrequencyDto> adUFList = mobileAdPlanManageDao.getAdUserFrequencyByAdId(adInstanceQry);

        // 唯一访客投放次数目前只支持一种限制，所以先返回一个对象
        if (adUFList != null && adUFList.size() > 0) {
            AdUserFrequencyDto adUserFrequency = adUFList.get(0);
            mobileAdPlanManageInfo.setAdUserFrequencyDto(adUserFrequency);
        }

        return mobileAdPlanManageInfo;
    }

    @Override
    @Transactional
    public void editAdPlan(MobileAdPlanManageInfoDto adDto, String adId) {

    	 AdInstanceDto updateAdDto = new AdInstanceDto();
         BeanUtils.copyProperties(adDto.getAdInstanceDto(), updateAdDto);
         updateAdDto.setAdId(adId);

         // 先修改，删除以前的广告信息
         mobileAdPlanManageDao.updateAdInstance(updateAdDto);
         mobileAdPlanManageDao.delAdTimeFilters(updateAdDto);
         List<AdMLinkDto> links = adMLinkDao.findAdMLinkDtoByAdId(Integer.parseInt(updateAdDto
                 .getAdId()));

         mobileAdPlanManageDao.delAdMaterialLinks(updateAdDto);
         mobileAdPlanManageDao.delAdTimeFrequency(updateAdDto);
         mobileAdPlanManageDao.delAdUserFrequency(updateAdDto);
         mobileAdPlanManageDao.delAdMediaCategoryLinks(updateAdDto);
         mobileAdPlanManageDao.deleteAdType(updateAdDto.getAdId());
         mobileAdPlanManageDao.deleteAdAppList(updateAdDto.getAdId());
         mobileAdPlanManageDao.deleteAdDevice(updateAdDto.getAdId());
         mobileAdPlanManageDao.deleteAdClick(updateAdDto.getAdId());
         
         adPlanManageDao.delAdCategoryLinks(updateAdDto);//广告分类
         
         // 再添加新的
         String ctStr = DateUtil.getCurrentDateTimeStr();
         User user = SessionContainer.getSession();

         this.addPlanTime(adDto, user, ctStr, adId);
         this.addPlanMaterial(links, adDto, user, ctStr, adId);
         this.addPlanStrategy(adDto, user, ctStr, adId);
         this.addApps(adDto, adId);
         this.addClick(adDto, adId);
         this.addDevices(adDto, adId);
         this.addType(adDto, adId);
         //广告分类
         this.addAdCategoryLink(adDto, user, ctStr, adId);
         List<AdMaterialCache> listInCache = adMaterialDao.findMaterialByAdId(adId);
         cache.del(adId);
         cache.hset(adId, listInCache);
    }

    @Override
    public List<AdInstance> findAdInstanceListByUser(int userid) {
        return mobileAdPlanManageDao.findAdInstanceListByUser(userid);
    }

    public List<AdInstance> findAdInstanceListByChannle(int userid, int channel) {
        return mobileAdPlanManageDao.findAdInstanceListByChannle(userid, channel);
    }

    @Override
    public int updateAdInstanceByIdAndUser(int userid, int adId) {
        return mobileAdPlanManageDao.updateAdInstanceByIdAndUser(userid, adId);
    }

    @Override
    public AdInstanceDto getAdplanById(String adId) {
        AdInstanceDto adInstanceQry = new AdInstanceDto();
        adInstanceQry.setAdId(adId);
        return mobileAdPlanManageDao.getAdInstanceByAdId(adInstanceQry);
    }


	@Override
	public List<AdApp> getAdAppList() {
		return mobileAdPlanManageDao.getAdAppList();
	}

	@Override
	public List<AdClick> getAdClickList() {
		return mobileAdPlanManageDao.getAdClickList();
	}

	@Override
	public List<AdDevice> getAdDeviceList() {
		return mobileAdPlanManageDao.getAdDeviceList();
	}

	@Override
	public List<AdIndustry> getAdIndustryList() {
		return mobileAdPlanManageDao.getAdIndustryList();
	}

	@Override
	public List<AdType> getAdTypeList() {
		return mobileAdPlanManageDao.getAdTypeList();
	}

	@Override
	public List<AdApp> getAdAppListByAdId(String adId) {
		return mobileAdPlanManageDao.getAdAppListByAdId(adId);
	}

	@Override
	public AdClick getAdClickByAdId(String adId) {
		return mobileAdPlanManageDao.getAdClickByAdId(adId);
	}

	@Override
	public List<AdDevice> getAdDeviceListByAdId(String adId) {
		return mobileAdPlanManageDao.getAdDeviceListByAdId(adId);
	}

	@Override
	public List<AdIndustry> getAdIndustryListByAdId(String adId) {
		return mobileAdPlanManageDao.getAdIndustryListByAdId(adId);
	}

	@Override
	public AdType getAdTypeByAdId(String adId) {
		return mobileAdPlanManageDao.getAdTypeByAdId(adId);
	}

	@Override
	public void insertAdAppList(List<AdAppLink> adAppLinks) {
		mobileAdPlanManageDao.insertAdAppList(adAppLinks);
	}

	@Override
	public void insertAdClick(AdClickLink adClickLink) {
		mobileAdPlanManageDao.insertAdClick(adClickLink);
	}

	@Override
	public void insertAdDevice(List<AdDeviceLink> adDeviceLinks) {
		mobileAdPlanManageDao.insertAdDevice(adDeviceLinks);
	}

	@Override
	public void insertAdIndustryList(List<AdIndustryLink> adIndustriLinks) {
		mobileAdPlanManageDao.insertAdIndustryList(adIndustriLinks);
	}

	@Override
	public void insertAdType(AdTypeLink adTypeLink) {
		mobileAdPlanManageDao.insertAdType(adTypeLink);
	}

	@Override
	public void deleteAdAppList(String adId) {
		mobileAdPlanManageDao.deleteAdAppList(adId);
	}

	@Override
	public void deleteAdClick(String adId) {
		mobileAdPlanManageDao.deleteAdClick(adId);
	}

	@Override
	public void deleteAdDevice(String adId) {
		 mobileAdPlanManageDao.deleteAdDevice(adId);
	}

	@Override
	public void deleteAdIndustryList(String adId) {
		mobileAdPlanManageDao.deleteAdIndustryList(adId);
	}

	@Override
	public void deleteAdType(String adId) {
		mobileAdPlanManageDao.deleteAdType(adId);
	}
}
