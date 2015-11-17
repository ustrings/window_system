package com.vaolan.sspserver.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.hidata.framework.cache.redis.JedisPoolWriper;
import com.udpwork.ssdb.SSDB;
import com.vaolan.sspserver.filter.AdPlanFilter;
import com.vaolan.sspserver.filter.AdplanKeywordFilter;
import com.vaolan.sspserver.filter.AdplanLabelFilter;
import com.vaolan.sspserver.filter.DeviceFilter;
import com.vaolan.sspserver.filter.IpUaFilter;
import com.vaolan.sspserver.filter.MediaFilter;
import com.vaolan.sspserver.filter.RegionFilter;
import com.vaolan.sspserver.filter.UserFilter;
import com.vaolan.sspserver.model.AdExtLink;
import com.vaolan.sspserver.model.AdFilterElement;
import com.vaolan.sspserver.model.AdMaterial;
import com.vaolan.sspserver.model.AdProperty;
import com.vaolan.sspserver.model.AdSort;
import com.vaolan.sspserver.model.AdTimeFrequency;
import com.vaolan.sspserver.model.AdvPlan;
import com.vaolan.sspserver.model.VarSizeBean;
import com.vaolan.sspserver.service.AdRetrievalCommonService;
import com.vaolan.sspserver.timer.DBInfoFresh;
import com.vaolan.sspserver.util.AdFilterElementUtils;
import com.vaolan.sspserver.util.Config;
import com.vaolan.sspserver.util.Constant;
import com.vaolan.sspserver.util.JavaMd5Util;
import com.vaolan.sspserver.util.DMPUtil;
import com.vaolan.sspserver.util.KeywordUtils;
import com.vaolan.sspserver.filter.FilterType;

@Service
public class AdRetrievalCommonServiceImpl implements AdRetrievalCommonService {

    private static Logger logger = Logger.getLogger(AdRetrievalCommonServiceImpl.class);

    @Resource(name = "dbinfo")
    private DBInfoFresh dbInfo;

    @Autowired
    private AdPlanFilter adPlanFilter;

    @Autowired
    private MediaFilter mediaFilter;

    @Autowired
    private UserFilter userFilter;
    
    @Autowired
    private AdplanLabelFilter  adplanLabelFilter;
    
    @Autowired
    private IpUaFilter  ipUaFilter;
    
    @Autowired
    private DeviceFilter deviceFilter;
    
    @Autowired
    private AdplanKeywordFilter  adplanKeywordFilter;

    @Autowired
    private RegionFilter regionFilter;

    @Resource(name = "jedisPool_adstat")
    private JedisPoolWriper jedisPool;

    @Resource(name = "jedisPool20_6379")
    private JedisPoolWriper jedisPool20_6379;

    @Resource(name = "jedisPool20_6380")
    private JedisPoolWriper jedisPool20_6380;

    @Resource(name = "jedisPool20_6381")
    private JedisPoolWriper jedisPool20_6381;

    @Resource(name = "jedisPool20_6382")
    private JedisPoolWriper jedisPool20_6382;
    
    @Resource(name = "jedisPool20_6383")
    private JedisPoolWriper jedisPool20_6383;

    /**
     * 给诏兰自己弹窗的流量选取广告
     */
    @Override
    public AdProperty adRetrieveCommonForVaolanPush(AdFilterElement adFilterElement, 
    		HttpServletRequest request,HttpServletResponse response) {

        AdProperty adProperty = new AdProperty();

        adProperty.setAdAcct(adFilterElement.getAdAcct());
        adProperty.setRef(adFilterElement.getRef());

        // 默认是非精准
        adProperty.setAdThrowType(Constant.AD_THROW_TYPE_FJZ);
        // 诏兰盲投放的广告列表
        List<AdvPlan> vaolanMangtouAdIds = new ArrayList<AdvPlan>();
        // 所有满足投放条件(每天最高投放量未达到，域名满足，时间满足)的诏兰广告
        List<AdvPlan> vaolanAdIds = new ArrayList<AdvPlan>();
        // 诏兰url定向的广告列表
        List<AdvPlan> urlTargetAdIds = new ArrayList<AdvPlan>();
        // 诏兰ad帐号定投的广告列表
        List<AdvPlan> adAcctTargetAdIds = new ArrayList<AdvPlan>();
        // ip定西的广告
        List<AdvPlan> ipTargetAdIds = new ArrayList<AdvPlan>();
        // 域名定向的广告
        List<AdvPlan> hostTargetAdIds = new ArrayList<AdvPlan>();
        //找到对应的标签
        List<AdvPlan>  labelTargetAdplanList = new ArrayList<AdvPlan>();
        //找到对应的关键词广告
        List<AdvPlan>  keywordAdplanList = new ArrayList<AdvPlan>();
        
        long filterStart = System.currentTimeMillis();
        
        this.filterAdForVaolanPush(vaolanMangtouAdIds, vaolanAdIds, urlTargetAdIds,
                adAcctTargetAdIds, ipTargetAdIds, hostTargetAdIds, keywordAdplanList,
                labelTargetAdplanList , adFilterElement, request, response);
        
        long filterEnd = System.currentTimeMillis();
        long filterTime = filterEnd - filterStart;
        
        logger.info("timelog: filterTime，过滤满足条件的广告用时:"+filterTime);
        // ad 账号定向
//        this.filterAdForVaolanPush_enum(adAcctTargetAdIds, FilterType.ADACCTTARGET, adFilterElement, null);
        // 当前访问者的ad帐号命中ad帐号定向
        if (adAcctTargetAdIds != null && adAcctTargetAdIds.size() > 0) {
            Random r = new Random();
            AdvPlan adv = adAcctTargetAdIds.get(r.nextInt(adAcctTargetAdIds.size()));

            String[] wh = this.getAdPlanSize(adv);

            if (wh != null) {
                adProperty.setAdId(adv.getAdInstance().getAdId());
                adProperty.setWidth(wh[0]);
                adProperty.setHeight(wh[1]);
                adProperty.setAdThrowType(Constant.AD_THROW_TYPE_VLJZ);
                adProperty.setHitCause(Constant.HIT_CAUSE_ADTARGET);
                adProperty.setUserId(adv.getAdInstance().getUserId());
                adProperty.setAdName(adv.getAdInstance().getAdName());
                adProperty.setCloseType(adv.getAdInstance().getCloseType());
                adProperty.setLinkType(adv.getAdInstance().getLinkType());
                adProperty.setExtLink(adv.getAdExtLink().getThrowUrl() == null ? "" : adv
                        .getAdExtLink().getThrowUrl());
            }

            return adProperty;
        }

        // url 定向
//        this.filterAdForVaolanPush_enum(urlTargetAdIds, FilterType.URLTARGET, adFilterElement, null);
        // 当前访问的url，有命中url定向的广告，则优先从url定向广告中选取广告
        if (urlTargetAdIds != null && urlTargetAdIds.size() > 0) {
            Random r = new Random();
            AdvPlan adv = urlTargetAdIds.get(r.nextInt(urlTargetAdIds.size()));

            String[] wh = this.getAdPlanSize(adv);

            if (wh != null) {
                adProperty.setAdId(adv.getAdInstance().getAdId());
                adProperty.setWidth(wh[0]);
                adProperty.setHeight(wh[1]);
                adProperty.setAdThrowType(Constant.AD_THROW_TYPE_VLJZ);
                adProperty.setHitCause(Constant.HIT_CAUSE_URLTARGET);
                adProperty.setUserId(adv.getAdInstance().getUserId());
                adProperty.setAdName(adv.getAdInstance().getAdName());
                adProperty.setCloseType(adv.getAdInstance().getCloseType());
                adProperty.setLinkType(adv.getAdInstance().getLinkType());
                adProperty.setExtLink(adv.getAdExtLink().getThrowUrl() == null ? "" : adv
                        .getAdExtLink().getThrowUrl());

            }
            return adProperty;
        }

        // ip 定向
//        this.filterAdForVaolanPush_enum(ipTargetAdIds, FilterType.IPTARGETS, adFilterElement, null);
        // 当前访问者的ip命中ip定向
        if (ipTargetAdIds != null && ipTargetAdIds.size() > 0) {
            Random r = new Random();
            AdvPlan adv = ipTargetAdIds.get(r.nextInt(ipTargetAdIds.size()));

            String[] wh = this.getAdPlanSize(adv);

            if (wh != null) {
                adProperty.setAdId(adv.getAdInstance().getAdId());
                adProperty.setWidth(wh[0]);
                adProperty.setHeight(wh[1]);
                adProperty.setAdThrowType(Constant.AD_THROW_TYPE_VLJZ);
                adProperty.setHitCause(Constant.HIT_CAUSE_IP_TARGET);
                adProperty.setUserId(adv.getAdInstance().getUserId());
                adProperty.setAdName(adv.getAdInstance().getAdName());
                adProperty.setCloseType(adv.getAdInstance().getCloseType());
                adProperty.setLinkType(adv.getAdInstance().getLinkType());
                adProperty.setExtLink(adv.getAdExtLink().getThrowUrl() == null ? "" : adv
                        .getAdExtLink().getThrowUrl());

            }

            return adProperty;
        }

        // 即搜即投的 userid 即搜即投的 userid 不需要加密
        //String jsjt_userid = AdFilterElementUtils.getUserIdWioutMd5(adFilterElement);
        
        
        //即搜即投的 userid 即搜即投的 userid 是ad ua 加密的
        String jsjt_userid = JavaMd5Util.md5Encryp(adFilterElement.getAdAcct(),adFilterElement.getUserAgent());
        
        // vaolan 定向
//        this.filterAdForVaolanPush_enum(vaolanAdIds, FilterType.VAOLAN, adFilterElement, null);
        // 既搜既投，可能命中了多支广告，把所有命中的且 满足投放条件的ad挑出来，随机选择一个投放
        
        
        // 如果 type = 4 说明是即搜即投
        String type = adFilterElement.getType();

        //需要既搜既投的备选广告列表
        Set<String> jsjtStandbyAdIds = null;
        // 如果是即搜即投就走即搜即投的逻辑
        if(!StringUtils.isEmpty(type)) {
        	// 如果是一般即搜即投
        	// 一般的即搜即投需要自己去 redis 查询所有可以投放的 广告id
	        if(type.equals(Constant.JSJT_ADID_FLAGE)) {
	        	long start = System.currentTimeMillis();
	        	jsjtStandbyAdIds = getAdIdsFromRedis(jsjt_userid);
	        	long end = System.currentTimeMillis();
	        	logger.info("搜即投逻辑 账号：" + jsjt_userid + " adIds : " + jsjtStandbyAdIds + "  耗时：" + (end -start) );
	        	
	        	// 如果是百度即搜即投可以直接取出来可以查询出来可以投放的 广告 id
	        } else if(type.equals(Constant.BAIDU_JSJT_ADID_FLAGE)) {
	        	String adId = adFilterElement.getAdId();
	        	if(!StringUtils.isEmpty(adId)) {
	        		jsjtStandbyAdIds = new HashSet<String>();
	        		jsjtStandbyAdIds.add(adId);
	        		logger.info("百度即搜即投逻辑 账号：" + jsjt_userid + " adIds : " + jsjtStandbyAdIds);
	        	}
	        }
        }
        
//        String[] adIds = adIdStr.split("_");
//        List<String> suitableAdIds = new ArrayList<String>();
//
//        for (String adId : adIds) {
//            AdvPlan advPlan = dbInfo.getAdvPlanMap2().get(adId);
//            if (vaolanAdIds.contains(advPlan)) {
//                suitableAdIds.add(adId);
//            }
//        }
        
        

        // 属于诏兰既搜既投精准广告流量，投放诏兰精准广告
        AdvPlan jsjtAdvPlan = null;
        String suitableAdId = null;
        if(jsjtStandbyAdIds!=null && jsjtStandbyAdIds.size() > 0) {
        	// 循环当前的 adId 是不是在投放范围内
        	List<String> canPostAdIds = new ArrayList<String>();
        	for(String subAdId : jsjtStandbyAdIds) {
        		for(AdvPlan plan : vaolanAdIds) {
        			if(plan.getAdInstance().getAdId().equalsIgnoreCase(subAdId)) {
        				canPostAdIds.add(subAdId);
        			}
        		}
        	}
        	if(canPostAdIds.size() > 0) {
        		Random r = new Random();
             	suitableAdId =canPostAdIds.get(r.nextInt(canPostAdIds.size()));
             	jsjtAdvPlan = dbInfo.getAdvPlanMap2().get(suitableAdId);
        	}
        }
        
        if (jsjtAdvPlan != null) {
        	logger.info("即搜即投进入...");
            String[] wh = this.getAdPlanSize(jsjtAdvPlan);
            
            if (wh != null) {
                adProperty.setAdId(suitableAdId);
                adProperty.setWidth(wh[0]);
                adProperty.setHeight(wh[1]);
                adProperty.setAdThrowType(Constant.AD_THROW_TYPE_VLJZ);
                adProperty.setHitCause(Constant.HIT_CAUSE_JSJT);
                adProperty.setUserId(jsjtAdvPlan.getAdInstance().getUserId());
                adProperty.setAdName(jsjtAdvPlan.getAdInstance().getAdName());
                adProperty.setCloseType(jsjtAdvPlan.getAdInstance().getCloseType());
                adProperty.setLinkType(jsjtAdvPlan.getAdInstance().getLinkType());
                adProperty.setExtLink(jsjtAdvPlan.getAdExtLink().getThrowUrl() == null ? "" : jsjtAdvPlan
                        .getAdExtLink().getThrowUrl());

            }
            // 如果是百度即搜即投，且当前的广告不满足投放条件，那么就把这次pv给放弃
        }else if(!StringUtils.isEmpty(type) && type.equals(Constant.BAIDU_JSJT_ADID_FLAGE)) {
        	return null;
        } else {
        	// 域名定向
//        	this.filterAdForVaolanPush_enum(hostTargetAdIds, FilterType.HOSTTARGET, adFilterElement, null);
            // 按域名投放的 优先级比 人群高
            if (hostTargetAdIds != null && hostTargetAdIds.size() > 0) {
                Random r = new Random();
                AdvPlan adv = hostTargetAdIds.get(r.nextInt(hostTargetAdIds.size()));

                String[] wh = this.getAdPlanSize(adv);

                if (wh != null) {
                    adProperty.setAdId(adv.getAdInstance().getAdId());
                    adProperty.setWidth(wh[0]);
                    adProperty.setHeight(wh[1]);
                    adProperty.setAdThrowType(Constant.AD_THROW_TYPE_FJZ);
                    adProperty.setHitCause(Constant.HIT_CAUSE_HOST_TARGET);
                    adProperty.setUserId(adv.getAdInstance().getUserId());
                    adProperty.setAdName(adv.getAdInstance().getAdName());
                    adProperty.setCloseType(adv.getAdInstance().getCloseType());
                    adProperty.setLinkType(adv.getAdInstance().getLinkType());
                    adProperty.setExtLink(adv.getAdExtLink().getThrowUrl() == null ? "" : adv
                            .getAdExtLink().getThrowUrl());

                }
                 // 域名投放中会用到跟 vizury 的交互
                this.processVizury(vaolanAdIds, adFilterElement, adProperty);
                return adProperty;
            }

            
            
           // 获取用户的 userId
            String userId = AdFilterElementUtils.getUserIdByMd5(adFilterElement);
            
            
            /**离线关键词，已经暂时不用了 
             * 
             * 
            // 关键词定向
            long start = System.currentTimeMillis();
            List<String> keywords = DMPUtil.createCurUserKeywordList(userId);
            String keyword_adid = null;
//            keywords = new ArrayList<String>();
//            keywords.add("双领");
//            keywords.add("vivi11");
//            keywords.add("薄无痕");
//            keywords.add("女夏薄");
//            keywords.add("防勾丝细");
            
            // 标签查询结果
            if(keywords != null) {
            	logger.info("用户关键词查询结果：" + keywords.toString());
            	// 获取查询的结果频率
            	Map<String, AdSort> adIdFreq = KeywordUtils.getKeywordAdplanId(dbInfo.getAdsKeywordMap(), keywords);
            	// 如果结果不为空则继续处理
            	if(adIdFreq.size() > 0) {
            		List<AdSort> adSorts = new ArrayList<AdSort>();
                	adSorts.addAll(adIdFreq.values());
                	// 过滤广告
                	filterAdIds(adSorts, adFilterElement);
                	Collections.sort(adSorts, new Comparator<AdSort>() {
                		@Override
            			public int compare(AdSort o1, AdSort o2) {
            				return o2.getHitNum() - o1.getHitNum();
            			}
            		});
                	if(adSorts.size() > 0) {
                		keyword_adid = adSorts.get(0).getAdid();
                	}
            	}
            	
            }
            long end = System.currentTimeMillis();
            logger.info("用户关键词查询时间：" + (end - start));
       
            
//          // 关键词人群精准投放
            if (!StringUtils.isEmpty(keyword_adid)) {
            	logger.info("离线关键词人群投放");
//                Random r = new Random();
//                AdvPlan adv = keywordAdplanList.get(r.nextInt(keywordAdplanList.size()));
            	AdvPlan adv = dbInfo.getAdvPlanMap2().get(keyword_adid);

                String[] wh = this.getAdPlanSize(adv);

                if (wh != null) {
                    adProperty.setAdId(adv.getAdInstance().getAdId());
                    adProperty.setWidth(wh[0]);
                    adProperty.setHeight(wh[1]);
                    adProperty.setAdThrowType(Constant.AD_THROW_TYPE_VLJZ);
                    adProperty.setHitCause(Constant.HIT_CAUSE_KEYWORD_TARGET);
                    adProperty.setUserId(adv.getAdInstance().getUserId());
                    adProperty.setAdName(adv.getAdInstance().getAdName());
                    adProperty.setCloseType(adv.getAdInstance().getCloseType());
                    adProperty.setLinkType(adv.getAdInstance().getLinkType());
                    adProperty.setExtLink(adv.getAdExtLink().getThrowUrl() == null ? "" : adv
                            .getAdExtLink().getThrowUrl());
                }
                // 关键词投放中会用到跟 vizury 的交互
                this.processVizury(vaolanAdIds, adFilterElement, adProperty);
                return adProperty;
            }
            
            **/ 
            
            
            /**
             * 离线标签暂时也不用了
             *
            
            // 标签投处理
            start = System.currentTimeMillis();
            Map<String, String> labelMap = DMPUtil.createCurUserLabelMap(userId);
            // 标签查询结果
//            logger.info("用户标签查询结果：" + labelMap.toString());
            this.filterAdForVaolanPush_enum(labelTargetAdplanList, FilterType.LABELTARGET, adFilterElement, labelMap);
            end = System.currentTimeMillis();
            logger.info("标签查询耗时：" + (end-start));
//            logger.info("标签广告过滤的查询结果：" + labelTargetAdplanList.toString());
          //标签人群精准投放
            if (labelTargetAdplanList != null && labelTargetAdplanList.size() > 0) {
            	logger.info("标签投放...");
                Random r = new Random();
                AdvPlan adv = labelTargetAdplanList.get(r.nextInt(labelTargetAdplanList.size()));

                String[] wh = this.getAdPlanSize(adv);

                if (wh != null) {
                    adProperty.setAdId(adv.getAdInstance().getAdId());
                    adProperty.setWidth(wh[0]);
                    adProperty.setHeight(wh[1]);
                    adProperty.setAdThrowType(Constant.AD_THROW_TYPE_VLJZ);
                    adProperty.setHitCause(Constant.HIT_CAUSE_LABEL_TARGET);
                    adProperty.setUserId(adv.getAdInstance().getUserId());
                    adProperty.setAdName(adv.getAdInstance().getAdName());
                    adProperty.setCloseType(adv.getAdInstance().getCloseType());
                    adProperty.setLinkType(adv.getAdInstance().getLinkType());
                    adProperty.setExtLink(adv.getAdExtLink().getThrowUrl() == null ? "" : adv
                            .getAdExtLink().getThrowUrl());

                }
                // 标签投放中会用到跟 vizury 的交互
                this.processVizury(vaolanAdIds, adFilterElement, adProperty);
                return adProperty;
            }
            */
            
            // 盲头定向
//            this.filterAdForVaolanPush_enum(vaolanMangtouAdIds, FilterType.VAOLANMANGTOU, adFilterElement, null);
            // 获取人群列表
            
            long crowdStart = System.currentTimeMillis();
            Set<String> crowdIds = null;
            //如果开启了离线人群查询，再去查询
            if("ON".equals(Config.getProperty("crowd_switch"))){
            	crowdIds = this.getCrowdIdsByAdUa(userId);
            }
            long crowdEnd = System.currentTimeMillis();
            long crowdTime = crowdEnd-crowdStart;
            
            logger.info("timelog: crowdTime,查询离线人群用时: "+crowdTime);
            
            if (crowdIds == null || crowdIds.size() == 0) {
                this.notVaolanAccuratePv(vaolanMangtouAdIds, hostTargetAdIds, adFilterElement,
                        adProperty);
            } else {

                String adId = this
                        .choiceVaolanAdFromCrowdId(crowdIds, vaolanAdIds, adFilterElement);

                AdvPlan advPlan = dbInfo.getAdvPlanMap2().get(adId);

                // 匹配到了广告
                if (StringUtils.isNotBlank(adId) && vaolanAdIds.contains(advPlan)) {

                    String[] wh = this.getAdPlanSize(dbInfo.getAdvPlanMap2().get(adId));

                    if (wh != null) {
                        adProperty.setAdId(adId);
                        adProperty.setWidth(wh[0]);
                        adProperty.setHeight(wh[1]);
                        adProperty.setAdThrowType(Constant.AD_THROW_TYPE_VLJZ);
                        adProperty.setHitCause(Constant.HIT_CAUSE_OFFCROWD);
                        adProperty.setUserId(dbInfo.getAdvPlanMap2().get(adId).getAdInstance()
                                .getUserId());
                        adProperty.setAdName(dbInfo.getAdvPlanMap2().get(adId).getAdInstance()
                                .getAdName());
                        adProperty.setCloseType(dbInfo.getAdvPlanMap2().get(adId).getAdInstance()
                                .getCloseType());

                        adProperty.setLinkType(advPlan.getAdInstance().getLinkType());
                        adProperty.setExtLink(advPlan.getAdExtLink().getThrowUrl() == null ? ""
                                : advPlan.getAdExtLink().getThrowUrl());

                    }

                    logger.info("根据ad帐号+ua 算出的用户标识，离线去找人群， useId=" + userId + "得到的广告id：" + adId
                            + ",adName:"
                            + dbInfo.getAdvPlanMap2().get(adId).getAdInstance().getAdName());

                } else {
                   
                    this.notVaolanAccuratePv(vaolanMangtouAdIds, hostTargetAdIds, adFilterElement,
                            adProperty);
                }
            }
            
        }

        return adProperty;

    }

    /**
     * 过滤不需要投放的广告
     * @param adSorts
     * @param adFilterElement
     */
    private void filterAdIds(List<AdSort> adSorts, AdFilterElement adFilterElement) {
    	Map<String, AdvPlan> advPlanMap2 = dbInfo.getAdvPlanMap2();
    	Iterator<AdSort> adSortIterator = adSorts.iterator();
    	while(adSortIterator.hasNext()) {
    		AdvPlan advPlan = advPlanMap2.get(adSortIterator.next().getAdid());
    		 // 如果有尺寸过滤要求，则过滤尺寸
            if (StringUtils.isNotBlank(adFilterElement.getWidth())
                    && StringUtils.isNotBlank(adFilterElement.getHeight())) {
                if (!this.judageAdPlanSize(advPlan, adFilterElement.getWidth(),
                        adFilterElement.getHeight())) {
                    continue;
                }
            }
    		if (!(adPlanFilter.doFilter(advPlan, adFilterElement)
    				&& mediaFilter.doFilter(advPlan, adFilterElement)
    				&& userFilter.doFilter(advPlan, adFilterElement)
    				&& regionFilter.doFilter(advPlan, adFilterElement))){
    			adSortIterator.remove();
    		}
    	}

	}

	/**
     * 满足上海电信，包括给青稞视频，上海门户的流量选取广告
     */
    
   /**
    @Override
    public AdvPlan adRetrieveForSHDX(AdFilterElement adFilterElement, String channel,
            VarSizeBean vsb) {

        AdvPlan advPlan = null;

        // 盲投放的广告列表
        List<AdvPlan> shdxAdIds = new ArrayList<AdvPlan>();

        // 诏兰url定向的广告列表
        List<AdvPlan> urlTargetAdIds = new ArrayList<AdvPlan>();

        // 诏兰ad帐号定投的广告列表
        List<AdvPlan> adAcctTargetAdIds = new ArrayList<AdvPlan>();

        // ip定西的广告
        List<AdvPlan> ipTargetAdIds = new ArrayList<AdvPlan>();

        // 域名定向的广告
        List<AdvPlan> hostTargetAdIds = new ArrayList<AdvPlan>();

        this.filterAdForSHDX(shdxAdIds, urlTargetAdIds, adAcctTargetAdIds, ipTargetAdIds,
                hostTargetAdIds, adFilterElement, channel, vsb);

        // 当前访问者的ad帐号命中ad帐号定向
        if (adAcctTargetAdIds != null && adAcctTargetAdIds.size() > 0) {
            Random r = new Random();
            advPlan = adAcctTargetAdIds.get(r.nextInt(adAcctTargetAdIds.size()));

            return advPlan;
        }

        // 当前访问的url，有命中url定向的广告，则优先从url定向广告中选取广告
        if (urlTargetAdIds != null && urlTargetAdIds.size() > 0) {
            Random r = new Random();
            advPlan = urlTargetAdIds.get(r.nextInt(urlTargetAdIds.size()));

            return advPlan;
        }

        // 当前访问者的ip命中ip定向
        if (hostTargetAdIds != null && hostTargetAdIds.size() > 0) {
            Random r = new Random();
            advPlan = hostTargetAdIds.get(r.nextInt(hostTargetAdIds.size()));

            return advPlan;
        }

        // 么有定向的
        if (shdxAdIds != null && shdxAdIds.size() > 0) {
            Random r = new Random();
            advPlan = shdxAdIds.get(r.nextInt(shdxAdIds.size()));
        }

        return advPlan;

    }**/

    /**
     * 询问当前的广告 vizury 是不是要
     * @param vaolanAdIds
     * @param adFilterElement
     * @param adProperty
     */
    private void processVizury(List<AdvPlan> vaolanAdIds, AdFilterElement adFilterElement,
            AdProperty adProperty) {
        boolean isHasVizury = false;

        adProperty.setAdThrowType(Constant.AD_THROW_TYPE_FJZ);

        // 询问vizury之后，如果vizury不要流量，要找一个备选广告填充，
        // 同时把vizury的adid 从广告id列表中去除掉。
        for (AdvPlan adv : vaolanAdIds) {
            if (Constant.VIZURY_ADID.equals(adv.getAdInstance().getAdId())) {
                isHasVizury = true;
                vaolanAdIds.remove(adv);
                break;
            }
        }

        // vizury 在投放条件中，则去询问vizury
        if (isHasVizury) {

            // 这是给vizury封装的adid 和 userid，因为vizury 比较特殊，他是挑流量的。所以所有的流量先询问一下他
            String[] wh = this.getAdPlanSize(dbInfo.getAdvPlanMap2().get(Constant.VIZURY_ADID));
            adProperty.setVizuryAdId(Constant.VIZURY_ADID);
            adProperty.setVizuryUserId(Constant.VIZURY_USERID);
            adProperty.setVizuryTag("1");
            adProperty.setVizuryWidth(wh[0]);
            adProperty.setVizuryHeight(wh[1]);
            adProperty.setVizuryCloseType((dbInfo.getAdvPlanMap2().get(Constant.VIZURY_ADID)
                    .getAdInstance().getCloseType()));

        } else {
            // 当前流量不满足vizury
            // 的投放条件(时间不在范围内，域名不在vizury挑选的范围内，达到了投放量)，则不再去询问vizury，直接把流量给盲投广告
            adProperty.setVizuryTag("0");
        }
    }

    /**
     * 非诏兰精准流量分配规则：先去请求vizury，如果vizury 不要则选择诏兰盲投或网盟盲投，如果在不要放空
     * 
     * @param vaolanMangtouAdIds
     */
    private void notVaolanAccuratePv(List<AdvPlan> vaolanMangtouAdIds,
            List<AdvPlan> hostTargetAdIds, AdFilterElement adFilterElement, AdProperty adProperty) {

        adProperty.setAdThrowType(Constant.AD_THROW_TYPE_FJZ);

        this.processVizury(vaolanMangtouAdIds, adFilterElement, adProperty);

        // 如果诏兰没有盲投广告
        if (vaolanMangtouAdIds.size() == 0) {
            adProperty.setHitCause(Constant.HIT_CAUSE_NO);

            // 诏兰有盲投广告，用诏兰盲投广告
        } else {
            adProperty.setHitCause(Constant.HIT_CAUSE_VL_MT);
            Random random = new Random();
            AdvPlan adv = vaolanMangtouAdIds.get(random.nextInt(vaolanMangtouAdIds.size()));

            String[] wh = this.getAdPlanSize(adv);

            if (wh != null) {
                adProperty.setAdId(adv.getAdvId());
                adProperty.setWidth(wh[0]);
                adProperty.setHeight(wh[1]);
                adProperty.setUserId(adv.getAdInstance().getUserId());
                adProperty.setAdName(adv.getAdInstance().getAdName());
                adProperty.setCloseType(adv.getAdInstance().getCloseType());
                adProperty.setLinkType(adv.getAdInstance().getLinkType());
                adProperty.setExtLink(adv.getAdExtLink().getThrowUrl() == null ? "" : adv
                        .getAdExtLink().getThrowUrl());

            }
        }
    }

    /**
     * 根据特定的人群id，选取一支匹配这些人群的广告id
     * 
     * @param crowdIds
     * @return
     */
    private String choiceVaolanAdFromCrowdId(Set<String> crowdIds, List<AdvPlan> vaolanAdIds,
            AdFilterElement adFilterElement) {

        String adId = null;

        Map<String, AdvPlan> advPlanMap2 = dbInfo.getAdvPlanMap2();

        // 匹配当前人群的所有广告id集合
        List<String> crowdAdIds = new ArrayList<String>();

        for (String key : advPlanMap2.keySet()) {
            AdvPlan advPlan = advPlanMap2.get(key);

            // 如果有尺寸过滤要求，则过滤尺寸
            if (StringUtils.isNotBlank(adFilterElement.getWidth())
                    && StringUtils.isNotBlank(adFilterElement.getHeight())) {
                if (!this.judageAdPlanSize(advPlan, adFilterElement.getWidth(),
                        adFilterElement.getHeight())) {
                    continue;
                }
            }

            if (advPlan.isDmpCrowdMatch() && vaolanAdIds.contains(advPlan)) {
                List<String> adCrowdIds = advPlan.getDmpCrowds();

                for (String adCrowdId : adCrowdIds) {
                    if (crowdIds.contains(adCrowdId)) {
                        crowdAdIds.add(key);
                    }
                }

            }
        }

        if (crowdAdIds.size() > 0) {
            Random r = new Random();
            adId = crowdAdIds.get(r.nextInt(crowdAdIds.size()));
        }
        return adId;
    }

    /**
     * 判断一只广告是否某个尺寸的，要判断所有物料
     * 
     * @param advPlan
     * @param width
     * @param height
     * @return
     */
    private boolean judageAdPlanSize(AdvPlan advPlan, String width, String height) {
        boolean b = true;
        List<AdMaterial> adMaterialList = advPlan.getAdMaterials();
        if (adMaterialList != null && adMaterialList.size() > 0) {
            for (AdMaterial admaterial : adMaterialList) {
                String reqwh = width + "x" + height;
                String wh = admaterial.getMaterialSizeValue();
                if (!reqwh.equals(wh)) {
                    b = false;
                }
            }
        }
        return b;
    }

    /**
     * 判断一只广告是否有这个尺寸的物料
     * 
     * @param advPlan
     * @param width
     * @param height
     * @return
     */
    private boolean judageAdPlanIncludeSize(AdvPlan advPlan, String width, String height) {
        boolean b = false;
        List<AdMaterial> adMaterialList = advPlan.getAdMaterials();
        if (adMaterialList != null && adMaterialList.size() > 0) {
            for (AdMaterial admaterial : adMaterialList) {
                String reqwh = width + "x" + height;
                String wh = admaterial.getMaterialSizeValue();
                if (reqwh.equals(wh)) {
                    b = true;
                    break;
                }
            }
        }
        return b;
    }

    /**
     * 得到一只广告的尺寸,仅限于这支广告的所有物料尺寸一样
     * 
     * @param advPlan
     * @param width
     * @param height
     * @return
     */
    private String[] getAdPlanSize(AdvPlan advPlan) {

        String[] wh = null;
        if (Constant.LINK_TYPE_M.equals(advPlan.getAdInstance().getLinkType())) {
            List<AdMaterial> adMaterialList = advPlan.getAdMaterials();
            if (adMaterialList != null && adMaterialList.size() > 0) {
                for (AdMaterial admaterial : adMaterialList) {
                    wh = admaterial.getMaterialSizeValue().split("x");
                    break;
                }
            }
        } else if (Constant.LINK_TYPE_E.equals(advPlan.getAdInstance().getLinkType())) {

            AdExtLink adExtLink = advPlan.getAdExtLink();
            wh = new String[2];
            wh[0] = adExtLink.getWidth();
            wh[1] = adExtLink.getHeight();
        }else if (Constant.LINK_TYPE_J.equals(advPlan.getAdInstance().getLinkType())){
			AdExtLink adExtLink = advPlan.getAdExtLink();
			wh = new String[2];
			wh[0]=adExtLink.getWidth();
			wh[1]=adExtLink.getHeight();
		}else if (Constant.LINK_TYPE_L.equals(advPlan.getAdInstance().getLinkType())){
			AdExtLink adExtLink = advPlan.getAdExtLink();
			wh = new String[2];
			wh[0]=adExtLink.getWidth();
			wh[1]=adExtLink.getHeight();
		}
        
        return wh;
    }

    /**
     * 根绝ad+ua 离线人群方式，根据当前人的信息去匹配广告
     * 
     * @param adFilterElement
     * @return
     */
    private Set<String> getCrowdIdsByAdUa(String userId) {

        Set<String> crowdIds = null;

        char c = userId.charAt(0);
        int accii = (int) c;

        int model = accii % 4;

        switch (model) {
        case 0:
            Jedis client_6379 = jedisPool20_6379.getJedis();
            crowdIds = client_6379.smembers(Constant.ADUA + userId);
            jedisPool20_6379.releaseBrokenJedis(client_6379);
            break;
        case 1:
            Jedis client_6380 = jedisPool20_6380.getJedis();
            crowdIds = client_6380.smembers(Constant.ADUA + userId);
            jedisPool20_6380.releaseBrokenJedis(client_6380);
            break;

        case 2:
            Jedis client_6381 = jedisPool20_6381.getJedis();
            crowdIds = client_6381.smembers(Constant.ADUA + userId);
            jedisPool20_6381.releaseBrokenJedis(client_6381);
            break;
        case 3:
            Jedis client_6382 = jedisPool20_6382.getJedis();
            crowdIds = client_6382.smembers(Constant.ADUA + userId);
            jedisPool20_6382.releaseBrokenJedis(client_6382);
            break;

        default:
            break;
        }

        return crowdIds;

    }
    
    /**
     * 根绝ad+ua, 查询当前用户对应的 既搜既投的广告列表
     * 
     * @param adFilterElement
     * @return
     */
    private Set<String> getAdIdsFromRedis(String userId) {
    	Set<String> adIds = null;
        Jedis client= jedisPool20_6383.getJedis();
        adIds = client.smembers(userId);
        jedisPool20_6383.releaseBrokenJedis(client);
        return adIds;
    }

    /**
     * 判读一支广告是否均匀投放，如果是均匀投放，是否已经达到当前分钟的量
     * 
     * @param advPlan
     * @return
     */
    private boolean isAchieveMinLimit(AdvPlan advPlan) {
        boolean b = false;

        AdTimeFrequency atf = advPlan.getAdTimeFrequency();

        // 说明要均匀投放
        if ("1".equals(atf.getIsUniform())) {

        }

        return b;
    }

    /**
     * 过滤广告(把通过诏兰弹窗投放的广告进行过滤)
     * 
     * @param vaolanMangtouAdIds
     * @param vaolanAdIds
     * @param thirdAdIds
     * @param adFilterElement
     */
    private void filterAdForVaolanPush(List<AdvPlan> vaolanMangtouAdIds, List<AdvPlan> vaolanAdIds,
            List<AdvPlan> urlTargetAdIds, List<AdvPlan> adAcctTargetAdIds,
            List<AdvPlan> ipTargetsAdIds, List<AdvPlan> hostTargetAdIds, 
            List<AdvPlan>  keywordAdplanList, List<AdvPlan>  labelTargetAdplanList,
            AdFilterElement adFilterElement, HttpServletRequest request,
			HttpServletResponse response) {
        // 诏兰push广告
        Map<String, AdvPlan> adPlanMap2 = dbInfo.getAdvPlanMap2();
        
        for (String adId : adPlanMap2.keySet()) {
            AdvPlan advPlan = adPlanMap2.get(adId);
            
            // 过滤指定 id 的广告
            if(adFilterElement.getExcludeAdIds().size()>0) {
            	if(adFilterElement.getExcludeAdIds().contains(adId)) {
            		logger.info("过滤广告 adId：" + adId);
            		continue;
            	}
            }
            
            // 如果有尺寸过滤要求，则过滤尺寸
            if (StringUtils.isNotBlank(adFilterElement.getWidth())
                    && StringUtils.isNotBlank(adFilterElement.getHeight())) {
                if (!this.judageAdPlanSize(advPlan, adFilterElement.getWidth(),
                        adFilterElement.getHeight())) {
                    continue;
                }
            }
            
            if (adPlanFilter.doFilter(advPlan, adFilterElement)
                    && mediaFilter.doFilter(advPlan, adFilterElement)
                    && userFilter.doFilter(advPlan, adFilterElement)
                    && regionFilter.doFilter(advPlan, adFilterElement)
                    && ipUaFilter.doFilter(advPlan, adFilterElement, request, response)
                    && deviceFilter.doFilter(advPlan, adFilterElement)) {
            	
                vaolanAdIds.add(advPlan);

                // 匹配当前url的所有 设定url定向的广告
                if (advPlan.isUrlFilter() && mediaFilter.adUrlFilter(advPlan, adFilterElement)) {
                    urlTargetAdIds.add(advPlan);

                }

                if (advPlan.isAdAcctTargetFilter()
                        && userFilter.adAcctFilter(advPlan, adFilterElement)) {
                    adAcctTargetAdIds.add(advPlan);
                }

                if (advPlan.getIsPosIpTargetFilter()
                        && userFilter.ipPosTargetFilter(advPlan, adFilterElement)) {
                    ipTargetsAdIds.add(advPlan);

                }
                
                /**
                关键词定向
                if (advPlan.isKeywordFilter()
                        && adplanKeywordFilter.adKeywrodFilter(advPlan, adFilterElement, (String[])param)) {
                	keywordAdplanList.add(advPlan);
                } */
                
                // 盲投中按域名投放的
                if (advPlan.isSiteFilter()
                        && mediaFilter.adSiteFilter(advPlan, adFilterElement)) {
                    hostTargetAdIds.add(advPlan);
                }
                
                /**
                if (!advPlan.isDmpCrowdMatch()) {
                    vaolanMangtouAdIds.add(advPlan);
                }
                */
                
                // 不按人群投放的即是 盲投广告
                if (!advPlan.isDmpCrowdMatch()&&!advPlan.isLabelFilter()&&!advPlan.isKeywordFilter()) {
                    vaolanMangtouAdIds.add(advPlan);
                }
                
            }
        }
    }
    
    /**
     * 过滤广告(把通过诏兰弹窗投放的广告进行过滤),按需过滤
     * 
     * @param vaolanMangtouAdIds
     * @param vaolanAdIds
     * @param thirdAdIds
     * @param adFilterElement
     */
    @SuppressWarnings("unchecked")
	private void filterAdForVaolanPush_enum(List<AdvPlan> adIds, 
    		FilterType filterType, AdFilterElement adFilterElement, Object param) {
        // 诏兰push广告
        Map<String, AdvPlan> adPlanMap2 = dbInfo.getAdvPlanMap2();

        for (String adId : adPlanMap2.keySet()) {
            AdvPlan advPlan = adPlanMap2.get(adId);
            
            // 如果有尺寸过滤要求，则过滤尺寸
            if (StringUtils.isNotBlank(adFilterElement.getWidth())
                    && StringUtils.isNotBlank(adFilterElement.getHeight())) {
                if (!this.judageAdPlanSize(advPlan, adFilterElement.getWidth(),
                        adFilterElement.getHeight())) {
                    continue;
                }
            }
            
//            logger.info("标签过滤开始...");
            if (adPlanFilter.doFilter(advPlan, adFilterElement)
                    && mediaFilter.doFilter(advPlan, adFilterElement)
                    && userFilter.doFilter(advPlan, adFilterElement)
                    && regionFilter.doFilter(advPlan, adFilterElement)) {
//            	logger.info("标签过滤进入...");
            	switch (filterType) {
				case VAOLAN:
					adIds.add(advPlan);
					break;
				case URLTARGET:
					 // 匹配当前url的所有 设定url定向的广告
	                if (advPlan.isUrlFilter() && mediaFilter.adUrlFilter(advPlan, adFilterElement)) {
	                	adIds.add(advPlan);
	                }
	                break;
				case ADACCTTARGET:
					 if (advPlan.isAdAcctTargetFilter()
		                        && userFilter.adAcctFilter(advPlan, adFilterElement)) {
						 adIds.add(advPlan);
		                }
					 break;
				case IPTARGETS:
					if (advPlan.getIsPosIpTargetFilter()
	                        && userFilter.ipPosTargetFilter(advPlan, adFilterElement)) {
						adIds.add(advPlan);
	                }
					break;
				case KEYWORD:
					if (advPlan.isKeywordFilter()
	                        && adplanKeywordFilter.adKeywrodFilter(advPlan, adFilterElement, (List<String>)param)) {
						adIds.add(advPlan);
	                }
					break;
				case LABELTARGET:
					  if (advPlan.isLabelFilter()
		                        && adplanLabelFilter.adLabelFilter(advPlan, adFilterElement, (Map<String, String>)param)) {
						  adIds.add(advPlan);
		                }
					  break;
				case HOSTTARGET:
					if (!advPlan.isDmpCrowdMatch()) {
	                    // 盲投中按域名投放的
	                    if (advPlan.isSiteFilter()
	                            && mediaFilter.adSiteFilter(advPlan, adFilterElement)) {
	                    	adIds.add(advPlan);
	                    }
					}
					break;
				case VAOLANMANGTOU:
                    	  // 不按人群投放的即是 盲投广告
                    	  if (!advPlan.isDmpCrowdMatch()&&!advPlan.isLabelFilter()&&!advPlan.isKeywordFilter()) {
                              adIds.add(advPlan);
                          }
                    break;
				default:
					break;
				}
              
            }
        }
    }
    

    /**
     * 过滤广（把通过青稞视频，上海门户push投放的广告过滤）
     * 
     * @param vaolanMangtouAdIds
     * @param vaolanAdIds
     * @param thirdAdIds
     * @param adFilterElement
     */
    
    /**
    private void filterAdForSHDX(List<AdvPlan> shdxAdIds, List<AdvPlan> urlTargetAdIds,
            List<AdvPlan> adAcctTargetAdIds, List<AdvPlan> ipTargetsAdIds,
            List<AdvPlan> hostTargetAdIds, AdFilterElement adFilterElement, String channel,
            VarSizeBean vsb) {
        // shdx 扩展广告
        Map<String, AdvPlan> adPlanMap = null;

        if (channel.equals(DBInfoFresh.QINGKOO_VIDEO_CHANNEL)) {
            adPlanMap = dbInfo.getAdvPlanMap6();
        } else if (channel.equals(DBInfoFresh.SH_PROTAL_CHANNEL)) {
            adPlanMap = dbInfo.getAdvPlanMap7();
        }

        for (String adId : adPlanMap.keySet()) {
            AdvPlan advPlan = adPlanMap.get(adId);

            // 如果有尺寸过滤要求，则过滤尺寸
            if (StringUtils.isNotBlank(adFilterElement.getWidth())
                    && StringUtils.isNotBlank(adFilterElement.getHeight())) {
                if (!this.judageAdPlanSize(advPlan, adFilterElement.getWidth(),
                        adFilterElement.getHeight())) {
                    continue;
                }
            }

            // 对于有伸缩需求的广告，这支广告必须包含大尺寸的物料 和小尺寸的物料
            if (vsb != null) {
                if (this.judageAdPlanIncludeSize(advPlan, vsb.getSizeBigWidth(),
                        vsb.getSizeBigHeight())
                        && this.judageAdPlanIncludeSize(advPlan, vsb.getSizeSmallWidth(),
                                vsb.getSizeSmallHeight())) {

                } else {
                    continue;
                }
            }

            if (adPlanFilter.doFilter(advPlan, adFilterElement)
                    && mediaFilter.doFilter(advPlan, adFilterElement)
                    && userFilter.doFilter(advPlan, adFilterElement)) {

                shdxAdIds.add(advPlan);

                // 匹配当前url的所有 设定url定向的广告
                if (advPlan.isUrlFilter() && mediaFilter.adUrlFilter(advPlan, adFilterElement)) {
                    urlTargetAdIds.add(advPlan);
                }

                if (advPlan.isAdAcctTargetFilter()
                        && userFilter.adAcctFilter(advPlan, adFilterElement)) {
                    adAcctTargetAdIds.add(advPlan);
                }

                if (advPlan.getIsPosIpTargetFilter()
                        && userFilter.ipPosTargetFilter(advPlan, adFilterElement)) {
                    ipTargetsAdIds.add(advPlan);

                }

            }
        }

    }**/
    
    public static void main(String[] args) {
		System.out.println(JavaMd5Util.md5Encryp("MTkyLjE2OC4xLjEwNwo=","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36"));
	}
   
}
