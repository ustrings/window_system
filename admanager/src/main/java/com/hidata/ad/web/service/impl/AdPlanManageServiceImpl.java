package com.hidata.ad.web.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import redis.clients.jedis.Jedis;

import com.hidata.ad.web.dao.AdCategoryDao;
import com.hidata.ad.web.dao.AdCheckDao;
import com.hidata.ad.web.dao.AdExtLinkDao;
import com.hidata.ad.web.dao.AdIpTargetingDao;
import com.hidata.ad.web.dao.AdMLinkDao;
import com.hidata.ad.web.dao.AdMaterialCacheDao;
import com.hidata.ad.web.dao.AdMaterialDao;
import com.hidata.ad.web.dao.AdPlanManageDao;
import com.hidata.ad.web.dao.IAllLabelAdPlanDao;
import com.hidata.ad.web.dao.MapKvDao;
import com.hidata.ad.web.dao.RegionTargetingDao;
import com.hidata.ad.web.dto.AdCategoryDto;
import com.hidata.ad.web.dto.AdCategoryLinkDto;
import com.hidata.ad.web.dto.AdCrowdLinkDto;
import com.hidata.ad.web.dto.AdDeviceLinkDto;
import com.hidata.ad.web.dto.AdExtLinkDto;
import com.hidata.ad.web.dto.AdInstanceDto;
import com.hidata.ad.web.dto.AdInstanceShowDto;
import com.hidata.ad.web.dto.AdMLinkDto;
import com.hidata.ad.web.dto.AdMaterialLinkDto;
import com.hidata.ad.web.dto.AdMediaCategoryLinkDto;
import com.hidata.ad.web.dto.AdPlanManageInfoDto;
import com.hidata.ad.web.dto.AdRegionLink;
import com.hidata.ad.web.dto.AdSiteDto;
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
import com.hidata.ad.web.model.AdMaterialCache;
import com.hidata.ad.web.model.AdPlanPortrait;
import com.hidata.ad.web.model.AdStatBase;
import com.hidata.ad.web.model.AdStatRedisInfo;
import com.hidata.ad.web.model.AdTerminalLink;
import com.hidata.ad.web.model.AllLabelAdPlan;
import com.hidata.ad.web.model.ChannelBase;
import com.hidata.ad.web.model.ChannelSiteRel;
import com.hidata.ad.web.model.Crowd;
import com.hidata.ad.web.model.KeyWordAdPlan;
import com.hidata.ad.web.model.MapKv;
import com.hidata.ad.web.model.RegionTargeting;
import com.hidata.ad.web.model.User;
import com.hidata.ad.web.model.VisitorCrowd;
import com.hidata.ad.web.service.AdPlanManageService;
import com.hidata.ad.web.service.TerminalBaseInfoService;
import com.hidata.ad.web.session.SessionContainer;
import com.hidata.ad.web.util.AdConstant;
import com.hidata.ad.web.util.Config;
import com.hidata.ad.web.util.FTPUtil;
import com.hidata.ad.web.util.FtpUtils;
import com.hidata.ad.web.util.TimeUtil;
import com.hidata.framework.cache.redis.JedisPoolWriper;
import com.hidata.framework.util.DateUtil;
import com.hidata.framework.util.JsonUtil;
import com.hidata.framework.util.StringUtil;

/**
 * 广告计划维护服务 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年1月13日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author chenjinzhao
 */
@Component
 //@Transactional(propagation = Propagation.REQUIRED)
public class AdPlanManageServiceImpl implements AdPlanManageService {

    public static String AD_ALL_INFO = "ad_all_info_";

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

    @Autowired
    private AdIpTargetingDao adIpTargetingDao;

    @Autowired
    private RegionTargetingDao regionTargetingDao;

    @Autowired
    private IAllLabelAdPlanDao iallLabelAdPlanDao;
    
    @Autowired
    private MapKvDao mapKvdao;
    
    @Autowired
    private AdExtLinkDao adExtLinkdao;
    
    @Autowired
    private AdCheckDao adCheckDao;

    private Logger logger = Logger.getLogger(AdPlanManageServiceImpl.class);
    
    private static Properties props = null;

    static {
        try {
            props = PropertiesLoaderUtils.loadAllProperties("ftp-config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 广告计划添加 错误：事物没起作用。。。。。
     * 
     * @author chenjinzhao
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAdPlan(HttpServletRequest request,AdPlanManageInfoDto adDto) throws Exception {
        String ctStr = DateUtil.getCurrentDateTimeStr();// 创建时间
        User user = SessionContainer.getSession(); // 用户实体
        // 广告基础信息
        String adId = this.addPlanBaisc(adDto, user, ctStr);
        //广告 计划
        AdInstanceDto adInstance = adDto.getAdInstanceDto();
        String linkType = adInstance.getLinkType();//投放类型
               
        this.addPlanKeyword(request,adDto, ctStr, adId);        
        /**********************新增人群画像定向**ssq*4************************/
        AdPlanPortrait portrait = adDto.getAdPlanPortrait();
        if(portrait != null){
        	 portrait.setAdId(adId);
             adPlanManageDao.insertAdPlanPortrait(portrait);
        } 
        /**************************************************************/
        /******************域名频道定向***ssq***********************************/
//        String channelIds = adDto.getChannelUrlIds();
//        String[] ids = channelIds.split(",");
//        List<ChannelSiteRel> channelSiteRel = adPlanManageDao.findChannelSiteRelByIds(channelIds);
//        for(int i=0;i<channelSiteRel.size();i++){
//        	ChannelSiteRel channel = channelSiteRel.get(i); 
//        	AdSiteDto adSiteDto = new AdSiteDto();
//        	adSiteDto.setAdId(adId);
//        	adSiteDto.setChannelId(String.valueOf(channel.getChannelId()));
//            adSiteDto.setCreateTime(TimeUtil.dateLongToMMHHssString((new Date()).getTime()));
//        	adSiteDto.setMatchType("R");
//        	adSiteDto.setSiteDesc(channel.getSiteDesc());
//        	adSiteDto.setSts("A");
//        	adSiteDto.setUrl(channel.getSiteUrl());
//        	
//        	adPlanManageDao.insertAdPlanSite(adSiteDto);
//        }
        /*******************************************************************/
        // 投放时间定向 1
        this.addPlanTime(adDto, user, ctStr, adId);
        // 广告受众人群定向8
       // this.addPlanCrowd(adDto, user, ctStr, adId);
        // 广告人群重定向
//        this.addPlanVisitor(adDto, ctStr, adId);
        // 终端定向
//        this.addPlanTerminal(adDto, adId);
        if(AdConstant.AD_TYPE_M.equals(adInstance.getLinkType())){
        	 // 广告物料
            this.addPlanMaterial(null, adDto, user, ctStr, adId);
        }else{
        	this.addAdExtLink(adDto, ctStr, adId);
        }
       
        // 站点定向5
        this.addPlanSites(adDto, user, ctStr, adId);
        // 添加背景url定向
        //this.addBackUrlSites(adDto, user, ctStr, adId);
        // 广告投放策略(频次控制)
        this.addPlanStrategy(adDto, user, ctStr, adId);
        
      //广告物料的一些信息
        List<AdMaterialCache> listInCache = null; 
        if(StringUtil.isNotEmpty(linkType) && "M".equals(linkType)){
        	listInCache = adMaterialDao.findMaterialByAdId(adId);
        }else{
        	listInCache = adMaterialDao.findExtLinkByAdId(adId);
        }
        cache.hset(AD_ALL_INFO + adId, listInCache);// 关高ID与广告物料的关系
        // 广告媒体
      //  this.addAdMediaCategoryLink(adDto, user, ctStr, adId);
        // 广告类别
       // this.addAdCategoryLink(adDto, user, ctStr, adId);
        //IP定向 6
        this.addAdIpTargeting(adDto, ctStr, adId);
        //区域定向 7
        this.addAdRegionLink(adDto, user, ctStr, adId);
        // 新增兴趣标签 2
        this.addAdAllLabel(adDto, user, ctStr, adId);
        
        //是移动端广告再添加
        if("1".equals(adDto.getAdInstanceDto().getAdType())){
        	this.addDeviceLink(adId,adDto.getAdDeviceIds());
        }
        
        
/************************将新建的广告插入的审核表中****ad_check_process********************************/
        
    }

    /**
     * 添加广告基本信息
     * 
     * @param adDto
     * @param user
     * @param ctStr
     * @author chenjinzhao
     */
    // private String addPlanBaisc(AdPlanManageInfoDto adDto, User user,
    // String ctStr) {
    // /**
    // *
    // */
    // AdInstanceDto adInstanceDto = new AdInstanceDto();
    // BeanUtils.copyProperties(adDto.getAdInstanceDto(), adInstanceDto);
    // adInstanceDto.setCreateTime(ctStr);
    // adInstanceDto.setUserId(user.getUserId() + "");
    // adInstanceDto.setSts(AdConstant.AD_STS_A);
    // String adId = adPlanManageDao.insertAdInstance(adInstanceDto) + "";
    //
    // /**
    // * 回显添加AdUrl
    // */
    // String adUrl = Config.getProperty("adurl_prefix") + "/"
    // + user.getUserId() + "/" + adId;
    // adInstanceDto.setAdId(adId + "");
    // adInstanceDto.setAdUrl(adUrl);
    //
    // adPlanManageDao.updateAdUrl(adInstanceDto);
    //
    // return adId;
    // }
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
    private String addPlanBaisc(AdPlanManageInfoDto adDto, User user, String ctStr) {
        /**
		 *  
		 */
        AdInstanceDto adInstanceDto = new AdInstanceDto();
        BeanUtils.copyProperties(adDto.getAdInstanceDto(), adInstanceDto);//
        adInstanceDto.setCreateTime(ctStr);
        adInstanceDto.setUserId(user.getUserId() + "");
        adInstanceDto.setSts(AdConstant.AD_STS_A);
        String linkType = adDto.getAdInstanceDto().getLinkType();
        if("3".equals(user.getUsertype())){
        	adInstanceDto.setAdUsefulType("T");
        }
        if("J".equals(linkType)){
        	 String jsType = adDto.getJsType();
             if(StringUtils.isNotEmpty(jsType)){
             	adInstanceDto.setLinkType(jsType);
             }
        }
        String ad3StatCode = adInstanceDto.getAd3statCode();
        String adId="";
        if(!"".equals(ad3StatCode)){
        	adInstanceDto.setAd3statCode("");
        	adId = adPlanManageDao.insertAdInstance(adInstanceDto) + "";
        	adInstanceDto.setAd3statCode(ad3StatCode);
        	adInstanceDto.setAdId(adId);
        	adPlanManageDao.updateAdInstance(adInstanceDto);
        }else{
        	adId = adPlanManageDao.insertAdInstance(adInstanceDto) + "";
        }   
        	 /**
             * 回显添加AdUrl
             */
            String adUrl = Config.getProperty("adurl_prefix") + "/" + user.getUserId() + "/" + adId;
            String adTanxUrl = Config.getProperty("ad_tanxurl_prefix") + "/" + user.getUserId() + "/"
                    + adId;
            adInstanceDto.setAdTanxUrl(adTanxUrl);
            adInstanceDto.setAdId(adId + "");
            adInstanceDto.setAdUrl(adUrl);

            adPlanManageDao.updateAdUrl(adInstanceDto);
       
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
                    client.hset(AdConstant.AD_STAT_KEY_PREFIX + adId, field, value);
                }
            }

            jedisPool.releaseBrokenJedis(client);
        } catch (Exception e) {
            logger.error("添加广告，存入统计信息，存入redis 统计信息出错", e);
        }

        return adId;
    }
    
    
    private void addDeviceLink(String adId,String adDevices){
    	if(StringUtils.isNotBlank(adDevices)){
    		String[] adDeviceArry = adDevices.split(",");
    		if(adDeviceArry!=null && adDeviceArry.length>0){
    			for (int i = 0; i < adDeviceArry.length; i++) {
					String adDeviceId = adDeviceArry[i];
					AdDeviceLinkDto adDeviceLinkDto = new AdDeviceLinkDto();
					adDeviceLinkDto.setAdId(adId);
					adDeviceLinkDto.setAdDeviceId(adDeviceId);
					adPlanManageDao.insertAdDeviceLlink(adDeviceLinkDto);
				}
    		}
    	}
    	
    }

    /**
     * 添加投放时间
     * 
     * @param adDto
     * @param user
     * @param ctStr
     * @author chenjinzhao
     */
    private void addPlanTime(AdPlanManageInfoDto adDto, User user, String ctStr, String adId) {
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
                    adPlanManageDao.insertAdTimeFilter(adTimeFilterDto);
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

    /**
     * 添加投放站点
     * 
     * @param adDto
     * @param user
     * @param ctStr
     * @param adId
     * @author chenjinzhao
     */
    /*private void addPlanSites(AdPlanManageInfoDto adDto, User user, String ctStr, String adId) {
        *//**
         * 添加广告投放站点
         *//*
        String adSites = adDto.getAdSites();
        if (StringUtils.isNotBlank(adSites)) {
            String[] adSiteArray = adSites.split("\r\n");

            for (String iterAdSite : adSiteArray) {

                AdSiteDto adSiteDto = new AdSiteDto();
                if (iterAdSite.toLowerCase().contains("http://")) {
                    // 完全匹配
                    adSiteDto.setMatchType(AdConstant.AD_MATCHTYPE_F);
                } else {
                    // 模糊匹配
                    adSiteDto.setMatchType(AdConstant.AD_MATCHTYPE_R);
                }
                adSiteDto.setUrl(iterAdSite);
                adSiteDto.setSts(AdConstant.AD_STS_A);
                adSiteDto.setCreateTime(ctStr);

                // 广告主键信息
                adSiteDto.setAdId(adId);
                adPlanManageDao.insertAdSite(adSiteDto);
            }
        }
    }*/
    private void addPlanSites(AdPlanManageInfoDto adDto, User user, String ctStr, String adId) {
        /**
         * 添加广告投放站点
         */
        String whiteAdSites = adDto.getWhiteAdSites();
        if (StringUtils.isNotBlank(whiteAdSites)) {
            String[] adSiteArray = whiteAdSites.split("\r\n");

            for (String iterAdSite : adSiteArray) {

                AdSiteDto adSiteDto = new AdSiteDto();
                if (iterAdSite.toLowerCase().contains("http://")) {
                    // 完全匹配
                    adSiteDto.setMatchType(AdConstant.AD_MATCHTYPE_F);
                } else {
                    // 模糊匹配
                    adSiteDto.setMatchType(AdConstant.AD_MATCHTYPE_R);
                }
                adSiteDto.setSiteType(AdIpTargeting.TYPE_WHITE);
                adSiteDto.setUrl(iterAdSite);
                adSiteDto.setSts(AdConstant.AD_STS_A);
                adSiteDto.setCreateTime(ctStr);

                // 广告主键信息
                adSiteDto.setAdId(adId);
                adPlanManageDao.insertAdSite(adSiteDto);
            }
        }
        
        String blackAdSites = adDto.getBlackAdSites();
        if (StringUtils.isNotBlank(blackAdSites)) {
            String[] adSiteArray = blackAdSites.split("\r\n");

            for (String iterAdSite : adSiteArray) {

                AdSiteDto adSiteDto = new AdSiteDto();
                if (iterAdSite.toLowerCase().contains("http://")) {
                    // 完全匹配
                    adSiteDto.setMatchType(AdConstant.AD_MATCHTYPE_F);
                } else {
                    // 模糊匹配
                    adSiteDto.setMatchType(AdConstant.AD_MATCHTYPE_R);
                }
                adSiteDto.setSiteType(AdIpTargeting.TYPE_BLACK);
                adSiteDto.setUrl(iterAdSite);
                adSiteDto.setSts(AdConstant.AD_STS_A);
                adSiteDto.setCreateTime(ctStr);

                // 广告主键信息
                adSiteDto.setAdId(adId);
                adPlanManageDao.insertAdSite(adSiteDto);
            }
        }
    }

    /**
     * 添加投放IP定向
     * 
     * @param adDto
     * @param user
     * @param ctStr
     * @param adId
     * @author chenjinzhao
     */
    private void addAdIpTargeting(AdPlanManageInfoDto adDto, String ctStr, String adId)
            throws Exception {
        try {
            String whiteIps = adDto.getWhiteIps();
            if (StringUtils.isNotBlank(whiteIps)) {
                String[] whiteIpArray = whiteIps.split("\r\n");
                for (String ip : whiteIpArray) {

                    AdIpTargeting adIpTargeting = new AdIpTargeting();
                    adIpTargeting.setAdId(Integer.valueOf(adId));
                    adIpTargeting.setIp(ip);
                    adIpTargeting.setCreateTime(ctStr);
                    adIpTargeting.setType(AdIpTargeting.TYPE_WHITE);
                    adIpTargetingDao.addAdIpTargeting(adIpTargeting);
                }
            }
            String blackIps = adDto.getBlackIps();
            if (StringUtils.isNotBlank(blackIps)) {
                String[] blackIpsArray = blackIps.split("\r\n");
                for (String ip : blackIpsArray) {

                    AdIpTargeting adIpTargeting = new AdIpTargeting();
                    adIpTargeting.setAdId(Integer.valueOf(adId));
                    adIpTargeting.setIp(ip);
                    adIpTargeting.setCreateTime(ctStr);
                    adIpTargeting.setType(AdIpTargeting.TYPE_BLACK);
                    adIpTargetingDao.addAdIpTargeting(adIpTargeting);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    /**
     * 添加背景URL定向
     * 
     * @param adDto
     * @param user
     * @param ctStr
     * @param adId
     * @author zhoubin
     */
    private void addBackUrlSites(AdPlanManageInfoDto adDto, User user, String ctStr, String adId) {
        /**
         * 添加广告投放站点
         */
        String adSites = adDto.getBackUrlSite();
        if (StringUtils.isNotBlank(adSites)) {
            String[] adSiteArray = adSites.split("\r\n");

            for (String iterAdSite : adSiteArray) {

                AdSiteDto adSiteDto = new AdSiteDto();
                adSiteDto.setUrl(iterAdSite);
                adSiteDto.setSts(AdConstant.AD_STS_A);
                adSiteDto.setCreateTime(ctStr);
                // 模糊匹配
                adSiteDto.setMatchType(AdConstant.AD_MATCHTYPE_A);
                // 广告主键信息
                adSiteDto.setAdId(adId);
                adPlanManageDao.insertAdSite(adSiteDto);
            }
        }
    }

    /**
     * 广告受众人群
     * 
     * @param adDto
     * @param user
     * @param ctStr
     * @param adId
     * @author chenjinzhao
     */
    private void addPlanCrowd(AdPlanManageInfoDto adDto, User user, String ctStr, String adId) {
        /**
         * 添加人群link关系
         */
        String crowdSeledIds = adDto.getCrowdSeledIds();
        if (StringUtils.isNotBlank(crowdSeledIds)) {
            String[] crowdIdsArray = crowdSeledIds.split(",");
            for (String crowdId : crowdIdsArray) {
                AdCrowdLinkDto adCrowdLink = new AdCrowdLinkDto();
                adCrowdLink.setAdId(adId);
                adCrowdLink.setCreateTime(ctStr);
                adCrowdLink.setCrowdId(crowdId);
                adCrowdLink.setSts(AdConstant.AD_STS_A);

                adPlanManageDao.insertAdCrowdLink(adCrowdLink);
            }
        }

    }

    /**
     * 广告终端定向
     * 
     * @param adDto
     * @param user
     * @param ctStr
     * @param adId
     * @author 周晓明
     * @date 2014年5月29日 修改
     */
    private void addPlanTerminal(AdPlanManageInfoDto adDto, String adId) {
        // boolean flag = cache.isExists("ad_term_black_" + adId);
        // System.out.println(flag);
        String key = "ad_term_black_" + adId;
        // 浏览器
        String browserSeledIds = adDto.getBrowserSeledIds();
        if (StringUtils.isNotBlank(browserSeledIds)) {
            String[] browserIdsArray = browserSeledIds.split(",");
            String type = "3";
            StringBuffer value = new StringBuffer();
            for (String browserId : browserIdsArray) {
                AdTerminalLink adTerminalLink = new AdTerminalLink();
                adTerminalLink.setAdId(adId);
                adTerminalLink.settId(browserId);
                // 设置浏览器的值
                String browserValue = terminalBaseInfoService.getValueById(browserId).get(0)
                        .gettValue();
                value.append(browserValue + ",");
                adTerminalLink.settValue(browserValue);
                adPlanManageDao.inserAdTerminalLink(adTerminalLink);
            }
            cache.add(key, type, value.toString().substring(0, value.toString().length() - 1));
            // System.out.println(value.toString().substring(0, value.toString(·).length()-1));
        }
        // 设备
        String deviceSeledIds = adDto.getDeviceSeledIds();
        if (StringUtils.isNotBlank(deviceSeledIds)) {
            String[] deviceIdsArray = deviceSeledIds.split(",");
            String type = "1";
            StringBuffer value = new StringBuffer();
            for (String deviceId : deviceIdsArray) {
                AdTerminalLink adTerminalLink = new AdTerminalLink();
                adTerminalLink.setAdId(adId);
                adTerminalLink.settId(deviceId);
                // 设置设备的值
                String deviceValue = terminalBaseInfoService.getValueById(deviceId).get(0)
                        .gettValue();
                value.append(deviceValue + ",");
                adTerminalLink.settValue(deviceValue);
                adPlanManageDao.inserAdTerminalLink(adTerminalLink);
            }
            cache.add(key, type, value.toString().substring(0, value.toString().length() - 1));
            // System.out.println( value.toString().substring(0, value.toString().length()-1));
        }
        // 系统
        String systemSeledIds = adDto.getSystemSeledIds();
        if (StringUtils.isNotBlank(systemSeledIds)) {
            String[] sysytemIdsArray = systemSeledIds.split(",");
            String type = "2";
            StringBuffer value = new StringBuffer();
            for (String sysytemId : sysytemIdsArray) {
                AdTerminalLink adTerminalLink = new AdTerminalLink();
                adTerminalLink.setAdId(adId);
                adTerminalLink.settId(sysytemId);
                // 设置系统的值的值
                String systemValue = terminalBaseInfoService.getValueById(sysytemId).get(0)
                        .gettValue();
                value.append(systemValue + ",");
                adTerminalLink.settValue(systemValue);
                adPlanManageDao.inserAdTerminalLink(adTerminalLink);
            }
            cache.add(key, type, value.toString().substring(0, value.toString().length() - 1));
            // System.out.println(value.toString().substring(0, value.toString().length()-1));
        }

        // System.out.println(cache.isExists(key));
    }

    /**
     * 广告重定向人群
     * 
     * @param adDto
     * @param user
     * @param ctStr
     * @param adId
     * @author 周晓明
     * @date 2014年5月29日 修改
     */
    private void addPlanVisitor(AdPlanManageInfoDto adDto, String ctStr, String adId) {
        /**
         * 添加人群link关系
         */
        String visitorSeledIds = adDto.getVisitorSeledIds();
        if (StringUtils.isNotBlank(visitorSeledIds)) {
            String[] visitorIdsArray = visitorSeledIds.split(",");
            for (String vcId : visitorIdsArray) {
                AdVisitorLinkDto adVisitorLink = new AdVisitorLinkDto();
                adVisitorLink.setAdId(adId);
                adVisitorLink.setCreateTime(ctStr);
                adVisitorLink.setVcId(vcId);
                adPlanManageDao.insertAdVisitorLink(adVisitorLink);
            }
        }

    }

    private void addPlanMaterial(List<AdMLinkDto> list, AdPlanManageInfoDto adDto, User user,
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

                adPlanManageDao.insertAdMaterialLink(adMaterialLink);
                // 更新广告物料状态
                // adMaterialDao.updateAdMaterialStatus(user.getUserId(),
                // Integer.valueOf(adMaterialId));
            }
        }

    }
    /**
     * 广告投放链接添加
     * @param adDto
     * @param ctStr
     * @param adId
     */
    private void addAdExtLink(AdPlanManageInfoDto adDto,String ctStr, String adId){
    	AdExtLinkDto adExtLink = adDto.getAdExtLink();
    	adExtLink.setAdInstanceId(adId);
    	adExtLink.setStsDate(ctStr);
    	String picSize = "";
    	if("E".equals(adDto.getAdInstanceDto().getLinkType())){
    		 picSize = adExtLink.getPicSize();
    	}else if("J".equals(adDto.getAdInstanceDto().getLinkType())){
    		 picSize = adDto.getJsSize();
//    		 adExtLink.setThrowUrl(adDto.getJsContent());
             adExtLink.setThrowUrl("");
    	}
    	if(StringUtils.isNotEmpty(picSize)){
    		MapKv mapKv = mapKvdao.findMapKvByAttrCode(picSize).get(0);
        	adExtLink.setPicSize(mapKv.getAttrValue());
        	adExtLink.setWidth(mapKv.getWidth());
        	adExtLink.setHeight(mapKv.getHeight());
    	}
    	int id = adPlanManageDao.insertAdExtLink(adExtLink);
    	if("J".equals(adDto.getAdInstanceDto().getLinkType())&&!"".equals(adDto.getJsContent())){
    		adExtLink.setId(String.valueOf(id));
    		adExtLink.setThrowUrl(adDto.getJsContent());
    		adPlanManageDao.updateAdExtLink(adExtLink);
    	}
    }
    private void addAdMediaCategoryLink(AdPlanManageInfoDto adDto, User user, String ctStr,
            String adId) {
        /**
         * 添加广告媒体类别link关系
         */
        String mediaCategoryIds = adDto.getMediaCategoryCodes();
        if (StringUtils.isNotBlank(mediaCategoryIds)) {
            String[] mediaCategoryIdArray = mediaCategoryIds.split(",");
            for (String mediaCategoryId : mediaCategoryIdArray) {
                AdMediaCategoryLinkDto adMediaCategoryLinkDto = new AdMediaCategoryLinkDto();
                adMediaCategoryLinkDto.setAdId(adId);
                adMediaCategoryLinkDto.setCreateTime(ctStr);
                adMediaCategoryLinkDto.setMedia_category_code(mediaCategoryId);
                adMediaCategoryLinkDto.setSts(AdConstant.AD_STS_A);

                adPlanManageDao.insertAdMediaCategoryLink(adMediaCategoryLinkDto);
            }
        }
    }

    private void addAdCategoryLink(AdPlanManageInfoDto adDto, User user, String ctStr, String adId) {
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

    private void addAdRegionLink(AdPlanManageInfoDto adDto, User user, String ctStr, String adId) {
        /**
         * 区域 和广告关联
         */
        String regionCodes = adDto.getRegionCodes();
        if (StringUtils.isNotBlank(regionCodes)) {
            String[] regionCodesIdArray = regionCodes.split(",");
            for (String regionCode : regionCodesIdArray) {
                AdRegionLink adRegionLink = new AdRegionLink();
                adRegionLink.setAdId(adId);
                adRegionLink.setRegionCode(regionCode);
                RegionTargeting paramObj = new RegionTargeting();
                paramObj.setCode(regionCode);
                List<RegionTargeting> regionTargetingList = regionTargetingDao
                        .findAdRegionListByCondition(paramObj);
                String regionName = "";
                if (regionTargetingList != null && regionTargetingList.size() > 0) {
                    RegionTargeting regionTargeting = regionTargetingList.get(0);
                    RegionTargeting parentParam = new RegionTargeting();
                    parentParam.setCode(regionTargeting.getParentCode());
                    List<RegionTargeting> parentList = regionTargetingDao
                            .findAdRegionListByCondition(parentParam);
                    if (parentList != null && parentList.size() > 0) {
                        if (!regionTargeting.getName().equals(parentList.get(0).getName())) {
                            regionName = parentList.get(0).getName() + regionTargeting.getName();
                        } else {
                            regionName = regionTargeting.getName();
                        }
                    }
                }
                adRegionLink.setRegionName(regionName);
                adRegionLink.setCreateTime(ctStr);
                regionTargetingDao.insertAdRegionLink(adRegionLink);
            }
        }
    }

    /**
     * 添加广告计划对应的兴趣标签
     * 
     * @param adDto
     * @param user
     * @param ctStr
     * @param adId
     * @author zhoubin
     */
    private void addAdAllLabel(AdPlanManageInfoDto adDto, User user, String ctStr, String adId) {
        /**
         * 区域 和广告关联
         */
        String allLabelCodes = adDto.getAllLabelCodes();

        if (StringUtils.isNotBlank(allLabelCodes)) {
            String[] LabelCodeArray = allLabelCodes.split(",");
            for (String iterLbelCode : LabelCodeArray) {

                if (StringUtils.isEmpty(iterLbelCode)) {
                    continue;
                }

                AllLabelAdPlan allLabelAdPlan = new AllLabelAdPlan();

                allLabelAdPlan.setAdId(adId);

                allLabelAdPlan.setLabelValue(iterLbelCode);

                allLabelAdPlan.setStsDate(ctStr);

                allLabelAdPlan.setSts(AdConstant.AD_STS_A);

                iallLabelAdPlanDao.addAllLabelAdPlan(allLabelAdPlan);

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
    private void addPlanStrategy(AdPlanManageInfoDto adDto, User user, String ctStr, String adId) {
        /**
         * 添加广告投放频率
         */
        AdTimeFrequencyDto adTimeFrequencyDto = new AdTimeFrequencyDto();
        BeanUtils.copyProperties(adDto.getAdTimeFrequencyDto(), adTimeFrequencyDto);

        adTimeFrequencyDto.setAdId(adId);
        adTimeFrequencyDto.setSts(AdConstant.AD_STS_A);
        adTimeFrequencyDto.setCreateTime(ctStr);
        adPlanManageDao.insertAdTimeFrequency(adTimeFrequencyDto);

        /**
         * 添加广告唯一访客展现次数
         */
        AdUserFrequencyDto adUserFrequencyDto = new AdUserFrequencyDto();
        BeanUtils.copyProperties(adDto.getAdUserFrequencyDto(), adUserFrequencyDto);

        adUserFrequencyDto.setAdId(adId);
        adUserFrequencyDto.setCreateTime(ctStr);
        adUserFrequencyDto.setSts(AdConstant.AD_STS_A);
        adPlanManageDao.insertAdUserFrequency(adUserFrequencyDto);
    }

    /**
     * 通过adid查询一个广告计划的所有信息
     * 
     * @author chenjinzhao
     */
    @Override
    @Transactional
    public AdPlanManageInfoDto getOneAdplanInfo(String adId) {
        AdPlanManageInfoDto adPlanManageInfo = new AdPlanManageInfoDto();

        AdInstanceDto adInstanceQry = new AdInstanceDto();
        adInstanceQry.setAdId(adId);

        // 广告基本信息
        AdInstanceDto adInstance = adPlanManageDao.getAdInstanceByAdId(adInstanceQry);
        adPlanManageInfo.setAdInstanceDto(adInstance);

        // 数据格式 workDay:true%start:1#end:2;weekendDay:true%start:1#end:2
        List<AdTimeFilterDto> adTFList = adPlanManageDao.getAdTimeFiltersByAdId(adInstanceQry);

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
            adPlanManageInfo.setPutHours(timeStr.deleteCharAt(timeStr.length() - 1).toString());
        }

        // 广告投放受众人群
      //  List<Crowd> adCList = adPlanManageDao.getAdCrowdsByAdId(adInstanceQry);
       // adPlanManageInfo.setAdCList(adCList);

        // 广告投放重定向人群
        /**
         * @date 2014年5月30日 修改
         * @author xiaoming
         */
       // List<VisitorCrowd> viList = adPlanManageDao.getVisitorsByAdId(adInstanceQry);
       // adPlanManageInfo.setVisitorList(viList);

        /**
         * 广告终端定向相关
         */
        Map<String, List<TerminalBaseInfo>> map = new HashMap<String, List<TerminalBaseInfo>>();
        List<TerminalBaseInfo> browserList = adPlanManageDao.getbrowsersByAdId(adInstanceQry);
        List<TerminalBaseInfo> deviceList = adPlanManageDao.getdevicesByAdId(adInstanceQry);
        List<TerminalBaseInfo> systemList = adPlanManageDao.getsystemsByAdId(adInstanceQry);
        map.put("browserList", browserList);
        map.put("deviceList", deviceList);
        map.put("systemList", systemList);
        adPlanManageInfo.setMap(map);

        // 广告投放站点
        List<AdSiteDto> adSList = adPlanManageDao.getAdSitesByAdId(adInstanceQry);

//        String adSites = "";
        String whiteAdSites = "";
        String blackAdSites = "";
        //String backUrlSites = "";

        /*for (AdSiteDto adSite : adSList) {
            if (AdConstant.AD_MATCHTYPE_R.equals(adSite.getMatchType())) {
                adSites += adSite.getUrl() + "\n";
            } else if (AdConstant.AD_MATCHTYPE_F.equals(adSite.getMatchType())) {
                adSites += adSite.getUrl() + "\n";
            }
        }*/
        for (AdSiteDto adSite : adSList) {
            if (AdIpTargeting.TYPE_WHITE.equals(adSite.getSiteType())) {
            	whiteAdSites += adSite.getUrl() + "\n";
            } else if (AdIpTargeting.TYPE_BLACK.equals(adSite.getSiteType())) {
            	blackAdSites += adSite.getUrl() + "\n";
            }
        }
        adPlanManageInfo.setWhiteAdSites(whiteAdSites);
        adPlanManageInfo.setBlackAdSites(blackAdSites);

//        adPlanManageInfo.setAdSites(adSites);

        //adPlanManageInfo.setBackUrlSite(backUrlSites);

        List<AdIpTargeting> adIpTargetingList = adIpTargetingDao
                .queryAdIpTargetingListByAdId(Integer.valueOf(adId));
        String whiteIps = "";

        String blackIps = "";
        for (AdIpTargeting adIpTargeting : adIpTargetingList) {
            if (AdIpTargeting.TYPE_WHITE.equals(adIpTargeting.getType())) {
                whiteIps += adIpTargeting.getIp() + "\n";
            } else if (AdIpTargeting.TYPE_BLACK.equals(adIpTargeting.getType())) {
                blackIps += adIpTargeting.getIp() + "\n";
            }
        }
        adPlanManageInfo.setWhiteIps(whiteIps);
        adPlanManageInfo.setBlackIps(blackIps);
        // 广告投放频率

        AdTimeFrequencyDto adTimeFrequency = adPlanManageDao
                .getAdTimeFrequencyByAdId(adInstanceQry);
        adPlanManageInfo.setAdTimeFrequencyDto(adTimeFrequency);

        List<AdUserFrequencyDto> adUFList = adPlanManageDao.getAdUserFrequencyByAdId(adInstanceQry);
        
        
        AdDeviceLinkDto adDeviceLinkDto = new AdDeviceLinkDto();
        adDeviceLinkDto.setAdId(adId);
        List<AdDeviceLinkDto> adDeviceLinkList = adPlanManageDao.getListAdDeviceLink(adDeviceLinkDto);
        
        adPlanManageInfo.setAdDeviceLinkList(adDeviceLinkList);
        
       

        // 唯一访客投放次数目前只支持一种限制，所以先返回一个对象
        if (adUFList != null && adUFList.size() > 0) {
            AdUserFrequencyDto adUserFrequency = adUFList.get(0);
            adPlanManageInfo.setAdUserFrequencyDto(adUserFrequency);
        }

        return adPlanManageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editAdPlan(HttpServletRequest request,AdPlanManageInfoDto adDto, String adId) throws Exception {
        //查看物料是否改变   	    	 	
    	Boolean flag = checkAdUrl(adDto,adId);
        AdInstanceDto updateAdDto = new AdInstanceDto();
        
        BeanUtils.copyProperties(adDto.getAdInstanceDto(), updateAdDto);
        updateAdDto.setAdId(adId);
        String linkType = updateAdDto.getLinkType();
        //如果是J 则要改变类型，L或者J
        if("J".equals(linkType)){
        	updateAdDto.setLinkType(adDto.getJsType());
        }
        // 先修改，删除以前的广告信息
        adPlanManageDao.updateAdInstance(updateAdDto);
        // adPlanManageDao.delAdTimes(updateAdDto);
        adPlanManageDao.delAdTimeFilters(updateAdDto);
        adPlanManageDao.delAdSites(updateAdDto);
       // adPlanManageDao.delAdCrowdLinks(updateAdDto);
        // 删除重定向人群
        /**
         * @author xiaoming 修改
         * @date 2014年5月30日
         */
     //   adPlanManageDao.delAdVisitorLinks(updateAdDto);
        adPlanManageDao.delAdIpTargetingByAdId(Integer.valueOf(adId));// 删除ip定向
        /***************关键词定向修改**ssq********************/
        adPlanManageDao.deleteKeywordByAdId(adId); 
        
        /****************人群素描定向的修改**ssq*****************/
      //  AdPlanPortrait portrait = adDto.getAdPlanPortrait();
       // if(portrait!=null){
      //    portrait.setAdId(adId); 
        //  adPlanManageDao.deletePortraitByAdId(adId);          
        //  adPlanManageDao.insertAdPlanPortrait(portrait);        
       // }
        
        /**************************************************/
        /**
         * 删除终端定向
         */
       adPlanManageDao.delAdTerminalLinks(updateAdDto);
        String key = "ad_term_black_" + adId;
        cache.del(key);
        // System.out.println(cache.isExists(key));
        List<AdMLinkDto> links = adMLinkDao.findAdMLinkDtoByAdId(Integer.parseInt(updateAdDto
                .getAdId()));
        
        adPlanManageDao.DelAdDeviceLlink(updateAdDto);
        
        adPlanManageDao.delAdMaterialLinks(updateAdDto);
        adExtLinkdao.deleteAdExtLinkByAdId(adId);
        adPlanManageDao.delAdTimeFrequency(updateAdDto);
        adPlanManageDao.delAdUserFrequency(updateAdDto);
//        adPlanManageDao.delAdMediaCategoryLinks(updateAdDto);
//        adPlanManageDao.delAdCategoryLinks(updateAdDto);
        adPlanManageDao.delAdRegionLink(updateAdDto);
        //删除标签广告
        iallLabelAdPlanDao.delAllLabelAdPlan(adId);
        
        
        
        // 再添加新的
        String ctStr = DateUtil.getCurrentDateTimeStr();
        User user = SessionContainer.getSession();
        
        this.addPlanKeyword(request,adDto, ctStr, adId);
        this.addPlanTime(adDto, user, ctStr, adId);
        this.addPlanCrowd(adDto, user, ctStr, adId);
        // 重定向人群 添加
//        this.addPlanVisitor(adDto, ctStr, adId);
        // 终端定向添加
//        this.addPlanTerminal(adDto, adId);
        if(AdConstant.AD_TYPE_M.equals(linkType)){
       	 // 广告物料
        	this.addPlanMaterial(links, adDto, user, ctStr, adId);
       }else {
    	   this.addAdExtLink(adDto, ctStr, adId);
       }
        this.addPlanSites(adDto, user, ctStr, adId);
        this.addBackUrlSites(adDto, user, ctStr, adId);
        this.addPlanStrategy(adDto, user, ctStr, adId);
 //     this.addAdMediaCategoryLink(adDto, user, ctStr, adId);
 //     this.addAdCategoryLink(adDto, user, ctStr, adId);
        this.addAdIpTargeting(adDto, ctStr, adId);
        this.addAdRegionLink(adDto, user, ctStr, adId);
        //添加标签信息
        this.addAdAllLabel(adDto, user, ctStr, adId);
        
        //设备信息
        this.addDeviceLink(adId,adDto.getAdDeviceIds());
        List<AdMaterialCache> listInCache = null;
        if(StringUtil.isNotEmpty(linkType) && "M".equals(linkType)){
        	listInCache = adMaterialDao.findMaterialByAdId(adId);
        }else{
        	listInCache = adMaterialDao.findExtLinkByAdId(adId);
        }
        
        
        cache.del(AD_ALL_INFO + adId);
        cache.hset(AD_ALL_INFO + adId, listInCache);
        
/****************************修改广告后将广告的审核状态改为待审核状态******************************/   
        AdInstanceDto adInstance = adPlanManageDao.getAdInstanceDtoByAdId(adId);
        String adToufangSts = adInstance.getAdToufangSts();
        if(flag&&!"-1".equals(adToufangSts)&&"2".equals(user.getUsertype())){ //如果用户修改了物料
           adCheckDao.deleteAdCheckProcessByAdId(Integer.parseInt(adId));   
           AdInstanceDto adInstanceDto = new AdInstanceDto();
           adInstanceDto.setAdToufangSts("-1");
           adInstanceDto.setAdId(adId);
           adCheckDao.updateAdInstanceByAdId(adInstanceDto);
        }else if(flag&&!"-1".equals(adToufangSts)&&"3".equals(user.getUsertype())){
        	adCheckDao.deleteTAdCheckProcessByAdId(Integer.parseInt(adId));   
            AdInstanceDto adInstanceDto = new AdInstanceDto();
            adInstanceDto.setAdToufangSts("-1");
            adInstanceDto.setAdId(adId);
            adCheckDao.updateAdInstanceByAdId(adInstanceDto);
        } 
/*************************************************************************************/
    }

    @Override
    public List<AdInstance> findAdInstanceListByUser(int userid) {
        return adPlanManageDao.findAdInstanceListByUser(userid);
    }

    public List<AdInstance> findAdInstanceListByChannle(int userid, int channel) {
        return adPlanManageDao.findAdInstanceListByChannle(userid, channel);
    }

    @Override
    public int updateAdInstanceByIdAndUser(int userid, int adId) {
        return adPlanManageDao.updateAdInstanceByIdAndUser(userid, adId);
    }

    @Override
    public AdInstanceDto getAdplanById(String adId) {
        AdInstanceDto adInstanceQry = new AdInstanceDto();
        adInstanceQry.setAdId(adId);
        return adPlanManageDao.getAdInstanceByAdId(adInstanceQry);
    }

    @Override
    public List<MediaCategoryDto> findMediaCategoryDtoListByParendCode(String code) {
        return adPlanManageDao.findMediaCategoryDtoListByParendCode(code);
    }

    @Override
    public List<RegionTargeting> findRegionTargetingListByParendCode(String code) {
        return adPlanManageDao.findRegionListByParendCode(code);
    }

    @Override
    public List<RegionTargeting> findRegionListByParendCode(String code) {
        return adPlanManageDao.findRegionListByParendCode(code);
    }

    /**
     * 根据code查询媒体分类
     * 
     * @param code
     * @return
     */
    public MediaCategoryDto findMediaCategoryDtoByCode(String code) {
        return adPlanManageDao.findMediaCategoryDtoByCode(code);
    }

    /**
     * 根据广告id查询媒体分类
     * 
     * @param adId
     * @return
     */
    public List<MediaCategoryDto> findMediaCategoryDtoListByAdId(String adId) {
        return adPlanManageDao.findMediaCategoryDtoListByAdId(adId);
    }

    @Override
    public List<AdCategoryDto> findAdCategoryDtoListByParentCode(String parentCode) {
        AdCategoryDto paramObj = new AdCategoryDto();
        paramObj.setParentCode(parentCode);
        return adCategoryDao.findAdCategoryDtoListByCondition(paramObj);
    }

    @Override
    public List<RegionTargeting> findRegionTargetingListByParentCode(String parentCode) {
        RegionTargeting paramObj = new RegionTargeting();
        paramObj.setParentCode(parentCode);
        return regionTargetingDao.findAdRegionListByCondition(paramObj);
    }

    @Override
    public List<AdCategoryDto> findAdCategoryDtoListByAdId(String adId) {
        return adCategoryDao.findAdCategoryDtoListByAdId(adId);
    }

    @Override
    public List<RegionTargeting> findAdRegtionListByAdId(String adId) {
        return regionTargetingDao.findAdRegionListByAdId(adId);
    }

	@Override
	public KeyWordAdPlan getAdPlanKeyWordByAdId(String adId) {
	
		return adPlanManageDao.getAdPlanKeyWordByAdId(adId);
	}

	@Override
	public int updateAdPlanKeyword(KeyWordAdPlan keyWordAdPlan) {
		
		return adPlanManageDao.updateAdPlanKeyword(keyWordAdPlan);
	}

	@Override
	public AdPlanPortrait getAdPlanPortraitByAdId(String adId) {
		
		return adPlanManageDao.getAdPlanPortraitByAdId(adId);
	}

	@Override
	public List<ChannelBase> getChannelBaseInfo() {
		
		return adPlanManageDao.getChannelBaseInfo();
	}

	@Override
	public List<ChannelSiteRel> findChannelSiteRelByChannelIds(String channels) {
		
		return adPlanManageDao.findChannelSiteRelByChannelIds(channels);
	}

	@Override
	public List<AdSiteDto> findAdSiteDtoByAdId(String adId) {
		
		return adPlanManageDao.findAdSiteDtoByAdId(adId);
	}

	@Override
	public ChannelBase findChannelBaseByChannelId(String channelId) {
		
		return adPlanManageDao.findChannelBaseByChannelId(channelId);
	}

	@Override
	public KeyWordAdPlan findAdPlanKeywordByAdId(String adId) {
		
		return adPlanManageDao.findAdPlanKeywordByAdId(adId);
	}

	@Override
	public AdPlanPortrait findAdPortraitByAdId(String adId) {
		
		return adPlanManageDao.findAdPortraitByAdId(adId);
	}

	@Override
	public int deleteKeywordByAdId(String adId) {
		return adPlanManageDao.deleteKeywordByAdId(adId);
	}

	@Override
	public int deletePortraitByAdId(String adId) {
		
		return adPlanManageDao.deletePortraitByAdId(adId);
	}

	@Override
	public List<ChannelSiteRel> findChannelSiteRelByIds(String channelIds) {
		
		return adPlanManageDao.findChannelSiteRelByIds(channelIds);
	}

	@Override
	public int insertAdPlanSite(AdSiteDto adSiteDto) {
		
		return adPlanManageDao.insertAdSite(adSiteDto);
	}
	
	/**
	 * 查看物料是否已更改
	 * @param adDto
	 * @param adId
	 * @return
	 */
	public Boolean checkAdUrl(AdPlanManageInfoDto adDto, String adId){		
	   AdInstanceDto adInstanceDto = adDto.getAdInstanceDto();
	   AdInstanceDto adInstance = adPlanManageDao.getAdInstanceDtoByAdId(adId);
	   String linktype = adInstance.getLinkType();   //以前的
	   String linkType = adInstanceDto.getLinkType(); //现在的
       Boolean flag = false;
       if("L".equals(linktype)){
    	   linktype="J";
       }
       if(linktype.equals(linkType)){
    	 if(AdConstant.AD_TYPE_M.equals(linkType)){
    		String adMaterialIds = adDto.getAdMaterialIds();
    		String[] adMIds = adMaterialIds.split(",");
    		//旧的物料
    		List<AdMLink> adMLinkList = adPlanManageDao.getAdMaterialsByAdId(adId);
    	    if(adMIds.length!=adMLinkList.size()){
    	    	flag = true;
    	    }else{
    	    	for(int i=0;i<adMLinkList.size();i++){
    	    		AdMLink adMLink = adMLinkList.get(i);
    	    		int adMId = adMLink.getAdMId(); 
        		    for(int j=0;j<adMIds.length;j++){
        		    	if(adMId==Integer.parseInt(adMIds[j])){
        		    	    flag = false;
        		    		break;
        		    	}else{
        		    		flag = true;
        		    	}
        		    }
        		}
    	    }    		
    	 }else{
    		AdExtLinkDto adExtLink = adPlanManageDao.findAdExtLinkByAdId(adId);
    		String throwUrl = adExtLink.getThrowUrl();   //以前的物料
    		if(linkType.equals("E")){
    			AdExtLinkDto adExtLinkDto = adDto.getAdExtLink();    
    			String throwurl = adExtLinkDto.getThrowUrl();   //新的
    			if(throwUrl.equals(throwurl)){
    				flag = false;
    			}else{
    				flag = true;
    			}
    		}else if(linkType.equals("J")){
    			String jsContent = adDto.getJsContent();
    			if(throwUrl.equals(jsContent)){
    				flag = false;
    			}else{
    				flag = true;
    			}
    		}
    	 }
       }else{
    	   flag = true;
       }
		return flag;
	}
	
	/**
     * 通过广告Id查找广告物料
     * @param adId
     * @return
     */
	@Override
	public AdExtLinkDto findAdExtLinkByAdId(String adId){
		return adPlanManageDao.findAdExtLinkByAdId(adId);
	}

	@Override
	public AdInstanceDto getAdInstanceDtoByAdId(String adId) {
		
		return adPlanManageDao.getAdInstanceDtoByAdId(adId);
	}

	@Override
	public List<AdMLink> getAdMaterialsByAdId(String adId) {
		
		return adPlanManageDao.getAdMaterialsByAdId(adId);
	}

	@Override
	public List<AdInstanceShowDto> getListAdShow(int userId) {
		if(userId > 0){
			return adPlanManageDao.getListAdShow(userId);
		}
		return null;
	}
	
	/**
	 * 插入广告计划关键词
	 * @param adDto
	 * @param ctStr
	 * @param adId
	 */
	public void addPlanKeyword(HttpServletRequest request,AdPlanManageInfoDto adDto,String ctStr, String adId){		
		try{		
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
    	    MultipartFile multipartfile = multipartRequest.getFile("kwfilepath");
			//关键词的上传保存
			if(!"".equals(adDto.getKwFileName())&&multipartfile.getSize()>0){	    		
	            String filePath = request.getSession().getServletContext().getRealPath("/upload_kw");
	            File dirPath = new File(filePath);   
	            if (!dirPath.exists()) {   
	               dirPath.mkdir();   
	            }         
	            String fileName = adDto.getKwFileName();
	            String[] filenames = fileName.split("\\\\"); 
	            if(filenames.length==3){
	            	fileName=filenames[2];
	            }
	            String[] s1 = fileName.split(".txt");
	            String[] s2 = s1[0].split("_");
	            if(s2.length>1){
	            	String id = s2[1];
	            	if(!id.equals(adId)){
	            	   fileName=s1[0]+"_"+adId+".txt";
	            	}
	            }else{
	            	fileName=s1[0]+"_"+adId+".txt";
	            }
	            
	    	    File file = new File(filePath+"/"+fileName);		    	    	    	
	    	    multipartfile.transferTo(file);	   //保存文件到本地 	    		    	    	        
	            uploadFtp(new File(filePath+"/"+fileName),fileName);//上传至ftp
	            deleteKwFile(filePath+"/"+fileName);
	    	    
	    	    adDto.setKwFileName(fileName);
	    	}
			//关键词信息的插入
			KeyWordAdPlan keyWord = adDto.getKeyWordAdPlan();		
	        if(!keyWord.getKeyWd().equals("")){
	          String[] keyword =  (keyWord.getKeyWd()).split("\\r\\n");
	          String keywords="";
	          if(keyword.length>0){
	            for(int i=0;i<keyword.length;i++){
	        	   if(!keyword[i].equals("")){
	        	      keywords +=keyword[i]+",";
	        	   }
	            }
	          }
	          if(keywords.length()>0){
	        	 keywords = keywords.trim().substring(0,keywords.length()-1);
	        	 keyWord.setAdId(adId);
	             keyWord.setStsDate(ctStr);
	             keyWord.setSts("A");
	             keyWord.setKwType("1");
	             keyWord.setKeyWd(keywords);    
	             adPlanManageDao.insertAdPlanKeyWord(keyWord); 
	          }                
	        }
	        if(!"".equals(adDto.getKwFileName())){
	       	    keyWord.setAdId(adId);
	            keyWord.setStsDate(ctStr);
	            keyWord.setSts("A");
	            keyWord.setKwType("2");
	            keyWord.setKeyWd(adDto.getKwFileName());
	            adDto.getKeyWordAdPlan().setKwFilePath(props.getProperty("kw.directory"));
	            adPlanManageDao.insertAdPlanKeyWord(keyWord);
	       }
		}catch(Exception e){
			e.printStackTrace();
		}								
	}	
	
	/**
     * 将关键字文件上传至ftp
     * @param file
     * @param filename
     * @return
	 * @throws FileNotFoundException 
     */
    public Boolean uploadFtp(File file,String filename) throws FileNotFoundException{
    	   	
    	String hostname = props.getProperty("kw_host");
    	String port = props.getProperty("kw_port");
    	String username = props.getProperty("kw_username");
    	String password = props.getProperty("kw_password");
    	String remotePath = props.getProperty("kw.directory");  
    	InputStream input = new FileInputStream(file);
        boolean result = FtpUtils.uploadFile(hostname,Integer.parseInt(port),username,password,remotePath,filename,input);  
        return result;
    }
    
    /**
     * 删除本地保存的关键词文件
     * @param adId
     * @return
     */
    public Boolean deleteKwFile(String filepath){   	
		File file1 = new File(filepath);
	    if(file1.exists()){
	    	file1.delete();
	    }
		return null;
    }
}
