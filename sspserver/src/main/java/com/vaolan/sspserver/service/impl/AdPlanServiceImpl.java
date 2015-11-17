package com.vaolan.sspserver.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.hidata.framework.cache.redis.JedisPoolWriper;
import com.vaolan.sspserver.model.AdFilterElement;
import com.vaolan.sspserver.model.AdMaterial;
import com.vaolan.sspserver.model.AdProperty;
import com.vaolan.sspserver.model.AdShowPara;
import com.vaolan.sspserver.model.AdvPlan;
import com.vaolan.sspserver.model.VarSizeBean;
import com.vaolan.sspserver.service.AdPlanService;
import com.vaolan.sspserver.service.AdRetrievalCommonService;
import com.vaolan.sspserver.timer.DBInfoFresh;
import com.vaolan.sspserver.util.Config;
import com.vaolan.sspserver.util.Constant;

@Service
public class AdPlanServiceImpl implements AdPlanService {

	@Autowired
	AdRetrievalCommonService adRetrievalCommonService;

	@Autowired
	DyAdServiceImpl dyService;

	@Resource(name = "dbinfo")
	private DBInfoFresh dbInfo;

	@Resource(name = "jedisPool_adstat")
	private JedisPoolWriper jedisPool;

	private Logger logger = Logger.getLogger(AdPlanServiceImpl.class);

	/**
	 * push广告检索，检索规则：
	 *  1、优先诏兰精准广告，根据带来的人群信息(既搜既投的adId，宽带adAcct）
	 * 挑选匹配当前人群信息的精准广告投放。
	 *  2、如果第一步匹配不上诏兰的精准广告，则进入非诏兰精准流量分配:
	 * 倒给vizury(vizury也是精准人群匹配，有passback)
	 * 3、如果vizury不要，则在诏兰盲投广告和网盟(AG等)盲投广告之间选择一支进行投放。 
	 * 4、如果都不要，则放空此次流量。
	 */
	@Override
	public AdShowPara adRetrieveForVaolanPush(AdFilterElement adFilterElement, HttpServletRequest request,
			HttpServletResponse response) {
		String adTag = "";

		logger.info("accept *****  advId=" + adFilterElement.getAdId()
				+ ",  radius=" + adFilterElement.getAdAcct() + ",ref="
				+ adFilterElement.getRef() + ",region:"
				+ adFilterElement.getRegion() + ",type:" 
				+ adFilterElement.getType()+",adType:"+adFilterElement.getAdType());
		logger.info("ua=" + adFilterElement.getUserAgent());

		
		long minStart = System.currentTimeMillis();
		
		adFilterElement.setAdMinNumMap(dbInfo.getAdvWareHouse().getAdMinMap());
		
		long minEnd = System.currentTimeMillis();
		
		long minTime = minEnd-minStart;
		
		logger.info("timelog: minTime,每次请求获取minNumMap用时:"+minTime);
		
		AdProperty adProperty = adRetrievalCommonService
				.adRetrieveCommonForVaolanPush(adFilterElement, request, response);

		
		AdShowPara adShowPara = new AdShowPara();
		
		adShowPara.setAdAcct(this.Null2Empt(adProperty.getAdAcct()));
		adShowPara.setAdId(this.Null2Empt(adProperty.getAdId()));
		adShowPara.setAdName(this.Null2Empt(adProperty.getAdName()));
		adShowPara.setAdThrowType(this.Null2Empt(adProperty.getAdThrowType()));
		adShowPara.setAdType(adFilterElement.getAdType());
		adShowPara.setCloseType(this.Null2Empt(adProperty.getCloseType()));
		adShowPara.setExtLink(this.Null2Empt(adProperty.getExtLink()));
		adShowPara.setHeight(this.Null2Empt(adProperty.getHeight()));
		adShowPara.setLinkType(this.Null2Empt(adProperty.getLinkType()));
		adShowPara.setRef(this.Null2Empt(adProperty.getRef()));
		adShowPara.setUserId(this.Null2Empt(adProperty.getUserId()));
		adShowPara.setWidth(this.Null2Empt(adProperty.getWidth()));
		
		if(Constant.HIT_CAUSE_NO.equals(adProperty.getHitCause())){
			adShowPara.setShowFlag("0");
		}else{
			adShowPara.setShowFlag("1");
		}
		
		
		if (Constant.HIT_CAUSE_NO.equals(adProperty.getHitCause())) {
			logger.warn("广告类型: ref:"
					+ adFilterElement.getRef() + ",region:"
					+ adFilterElement.getRegion()
					+ "没有可选的广告投放，请检查##################");
		} else {
			logger.info("广告类型: ref:"
					+ adFilterElement.getRef() + ",region:"
					+ adFilterElement.getRegion() + ",adThrowType:"
					+ adProperty.getAdThrowType() + ",hit_cause:"
					+ adProperty.getHitCause() + ",adId:"
					+ adProperty.getAdId() + ",adName:"
					+ adProperty.getAdName() );
		}
		
		
		String impId = dyService.genImpressionUUID(adFilterElement.getIp(),
				adFilterElement.getHost(), adFilterElement.getRef(),
				adFilterElement.getUserAgent());
		
		adShowPara.setImpId(impId);
		return adShowPara;
		
		
		
		/**
		String vizuryAsk = "";
		if ("1".equals(adProperty.getVizuryTag())) {
			vizuryAsk = "询问vizury";
		} else {
			vizuryAsk = "不询问vizury";
		}
		if (Constant.HIT_CAUSE_NO.equals(adProperty.getHitCause())) {
			logger.warn("广告类型: " + vizuryAsk + ",ref:"
					+ adFilterElement.getRef() + ",region:"
					+ adFilterElement.getRegion()
					+ "没有可选的广告投放，请检查##################");
		} else {
			logger.info("广告类型: " + vizuryAsk + "ref:"
					+ adFilterElement.getRef() + ",region:"
					+ adFilterElement.getRegion() + ",adThrowType:"
					+ adProperty.getAdThrowType() + ",hit_cause:"
					+ adProperty.getHitCause() + ",adId:"
					+ adProperty.getAdId() + ",adName:"
					+ adProperty.getAdName() );
		}
		adTag = this.assmblyAdInfo(adProperty, adFilterElement);
		return adTag;
		**/
	}
	

	
	/**
	 * push广告检索，检索规则：
	 *  1、优先诏兰精准广告，根据带来的人群信息(既搜既投的adId，宽带adAcct）
	 * 挑选匹配当前人群信息的精准广告投放。
	 *  2、如果第一步匹配不上诏兰的精准广告，则进入非诏兰精准流量分配:
	 * 倒给vizury(vizury也是精准人群匹配，有passback)
	 * 3、如果vizury不要，则在诏兰盲投广告和网盟(AG等)盲投广告之间选择一支进行投放。 
	 * 4、如果都不要，则放空此次流量。
	 */
	@Override
	public String adRetrieveForVaolanPushWH(AdFilterElement adFilterElement, HttpServletRequest request,
			HttpServletResponse response) {
		String adTag = "";

		logger.info("accept *****  advId=" + adFilterElement.getAdId()
				+ ",  radius=" + adFilterElement.getAdAcct() + ",ref="
				+ adFilterElement.getRef() + ",region:"
				+ adFilterElement.getRegion() + ",width:"
				+ adFilterElement.getWidth() + ",height:"
				+ adFilterElement.getHeight());
		logger.info("ua=" + adFilterElement.getUserAgent());

		
		long minStart = System.currentTimeMillis();
		
		/**
		Jedis client = jedisPool.getJedis();
		// 当前分钟 pv 量，每分钟每一个广告的当前投放量
		Map<String, String> adMinNumMap = client.hgetAll(Constant.AD_MIN_NUM);
		//jedisPool.releaseJedis(client);
		 **/
		
		adFilterElement.setAdMinNumMap(dbInfo.getAdvWareHouse().getAdMinMap());
		
		long minEnd = System.currentTimeMillis();
		
		long minTime = minEnd-minStart;
		
		logger.info("timelog: minTime,每次请求获取minNumMap用时:"+minTime);
		
		AdProperty adProperty = adRetrievalCommonService.adRetrieveCommonForVaolanPush(
				adFilterElement, request, response);

		String vizuryAsk = "";
		if ("1".equals(adProperty.getVizuryTag())) {
			vizuryAsk = "询问vizury";
		} else {
			vizuryAsk = "不询问vizury";
		}

		if (Constant.HIT_CAUSE_NO.equals(adProperty.getHitCause())) {
			logger.warn("广告类型: " + vizuryAsk + ",ref:"
					+ adFilterElement.getRef() + ",region:"
					+ adFilterElement.getRegion()
					+ "没有可选的广告投放，请检查##################");
		} else {
			logger.info("广告类型: " + vizuryAsk + "ref:"
					+ adFilterElement.getRef() + ",region:"
					+ adFilterElement.getRegion() + ",adType:"
					+ adProperty.getAdThrowType() + ",hit_cause:"
					+ adProperty.getHitCause() + ",adId:"
					+ adProperty.getAdId() + ",adName:"
					+ adProperty.getAdName());
		}

		adTag = this.assmblyAdInfoWithWH(adProperty, adFilterElement);

		return adTag;
	}
	
	
	/**
	 * 
	 * @param src
	 * @return
	 */
	public String Null2Empt(String src){
		if(null == src){
			return "";
		}else{
			return src;
		}
	}

	

	private String assmblyAdInfo(AdProperty adProperty,
			AdFilterElement adFilterElement) {
		
		StringBuffer adCode = new StringBuffer("");

		if (Constant.AD_THROW_TYPE_VLJZ.equals(adProperty.getAdThrowType())) {
			adCode.append("var adType='1';");
			adCode.append("\n");
			adCode.append("var w='" + adProperty.getWidth() + "';");
			adCode.append("\n");
			adCode.append("var h='" + adProperty.getHeight() + "';");
			adCode.append("\n");
			adCode.append("var adId = '" + adProperty.getAdId() + "'");
			adCode.append("\n");
			adCode.append("var userId = '" + adProperty.getUserId() + "'");
			adCode.append("\n");
			adCode.append("var closeType='" + adProperty.getCloseType() + "'");
			adCode.append("\n");

			adCode.append("var linkType='" + adProperty.getLinkType() + "'");
			adCode.append("\n");

			adCode.append("var extLink='" + adProperty.getExtLink() + "'");
			adCode.append("\n");

		} else {
			adCode.append("var adType='2'");
			adCode.append("\n");
			if ("0".equals(adProperty.getVizuryTag())) {
				adCode.append("var vizury='0';");
				adCode.append("\n");
			} else {
				adCode.append("var vizury='1';");
				adCode.append("\n");
				adCode.append("var vizuryAdid='" + adProperty.getVizuryAdId()
						+ "';");
				adCode.append("\n");
				adCode.append("var vizuryUserid='"
						+ adProperty.getVizuryUserId() + "';");
				adCode.append("\n");
				adCode.append("var vizuryWidth='" + adProperty.getVizuryWidth()
						+ "';");
				adCode.append("\n");
				adCode.append("var vizuryHeight='"
						+ adProperty.getVizuryHeight() + "';");
				adCode.append("\n");
				adCode.append("var vizuryCloseType='"
						+ adProperty.getVizuryCloseType() + "'");
				adCode.append("\n");
			}

			if (Constant.HIT_CAUSE_NO.equals(adProperty.getHitCause())) {
				adCode.append("var mangtou='0';");
				adCode.append("\n");
			} else {

				if (Constant.HIT_CAUSE_3_MT.equals(adProperty.getHitCause())) {
					adCode.append("var mangtou='2';");
					adCode.append("\n");
				} else {
					adCode.append("var mangtou='1';");
					adCode.append("\n");
				}
				adCode.append("var w='" + adProperty.getWidth() + "';");
				adCode.append("\n");
				adCode.append("var h='" + adProperty.getHeight() + "';");
				adCode.append("\n");
				adCode.append("var adId = '" + adProperty.getAdId() + "';");
				adCode.append("\n");
				adCode.append("var userId = '" + adProperty.getUserId() + "';");
				adCode.append("\n");
				adCode.append("var closeType='" + adProperty.getCloseType()
						+ "';");
				adCode.append("\n");

				adCode.append("var linkType='" + adProperty.getLinkType() + "'");
				adCode.append("\n");

				adCode.append("var extLink='" + adProperty.getExtLink() + "'");
				adCode.append("\n");
			}
		}

		if (!"radius".equals(adProperty.getAdAcct())
				&& !"null".equals(adProperty.getAdAcct())) {

			adCode.append("var adAcct='" + adProperty.getAdAcct() + "';");
			adCode.append("\n");

			// 如果radius为空，则为空
		} else {
			adCode.append("var adAcct='';");
			adCode.append("\n");
		}

		adCode.append("var ref='" + adProperty.getRef() + "';");
		adCode.append("\n");

		String impId = dyService.genImpressionUUID(adFilterElement.getIp(),
				adFilterElement.getHost(), adFilterElement.getRef(),
				adFilterElement.getUserAgent());

		adCode.append("var impId='" + impId + "';");
		adCode.append("\n");

		long hourTs = System.currentTimeMillis() / 1000 / 60 / 60 / 24;

		String vlsspAddr = Config.getProperty("vlssp_addr");

		adCode.append("document.write(unescape(\"%3Cscript src='" + vlsspAddr
				+ "?_=" + hourTs
				+ "' type='text/javascript'%3E%3C/script%3E\"));");
		adCode.append("\n");

		return adCode.toString();
	}
	
	private String assmblyAdInfoWithWH(AdProperty adProperty,
			AdFilterElement adFilterElement) {

		StringBuffer adCode = new StringBuffer("");

		if (Constant.AD_THROW_TYPE_VLJZ.equals(adProperty.getAdThrowType())) {
			adCode.append("var adType='1';");
			adCode.append("\n");
			adCode.append("var w='" + adProperty.getWidth() + "';");
			adCode.append("\n");
			adCode.append("var h='" + adProperty.getHeight() + "';");
			adCode.append("\n");
			adCode.append("var adId = '" + adProperty.getAdId() + "'");
			adCode.append("\n");
			adCode.append("var userId = '" + adProperty.getUserId() + "'");
			adCode.append("\n");
			adCode.append("var closeType='" + adProperty.getCloseType() + "'");
			adCode.append("\n");

			adCode.append("var linkType='" + adProperty.getLinkType() + "'");
			adCode.append("\n");

			adCode.append("var extLink='" + adProperty.getExtLink() + "'");
			adCode.append("\n");

		} else {
			adCode.append("var adType='2'");
			adCode.append("\n");
			if ("0".equals(adProperty.getVizuryTag())) {
				adCode.append("var vizury='0';");
				adCode.append("\n");
			} else {
				adCode.append("var vizury='1';");
				adCode.append("\n");
				adCode.append("var vizuryAdid='" + adProperty.getVizuryAdId()
						+ "';");
				adCode.append("\n");
				adCode.append("var vizuryUserid='"
						+ adProperty.getVizuryUserId() + "';");
				adCode.append("\n");
				adCode.append("var vizuryWidth='" + adProperty.getVizuryWidth()
						+ "';");
				adCode.append("\n");
				adCode.append("var vizuryHeight='"
						+ adProperty.getVizuryHeight() + "';");
				adCode.append("\n");
				adCode.append("var vizuryCloseType='"
						+ adProperty.getVizuryCloseType() + "'");
				adCode.append("\n");
			}

			if (Constant.HIT_CAUSE_NO.equals(adProperty.getHitCause())) {
				adCode.append("var mangtou='0';");
				adCode.append("\n");
			} else {

				if (Constant.HIT_CAUSE_3_MT.equals(adProperty.getHitCause())) {
					adCode.append("var mangtou='2';");
					adCode.append("\n");
				} else {
					adCode.append("var mangtou='1';");
					adCode.append("\n");
				}
				adCode.append("var w='" + adProperty.getWidth() + "';");
				adCode.append("\n");
				adCode.append("var h='" + adProperty.getHeight() + "';");
				adCode.append("\n");
				adCode.append("var adId = '" + adProperty.getAdId() + "';");
				adCode.append("\n");
				adCode.append("var userId = '" + adProperty.getUserId() + "';");
				adCode.append("\n");
				adCode.append("var closeType='" + adProperty.getCloseType()
						+ "';");
				adCode.append("\n");

				adCode.append("var linkType='" + adProperty.getLinkType() + "'");
				adCode.append("\n");

				adCode.append("var extLink='" + adProperty.getExtLink() + "'");
				adCode.append("\n");
			}
		}

		if (!"radius".equals(adProperty.getAdAcct())
				&& !"null".equals(adProperty.getAdAcct())) {

			adCode.append("var adAcct='" + adProperty.getAdAcct() + "';");
			adCode.append("\n");

			// 如果radius为空，则为空
		} else {
			adCode.append("var adAcct='';");
			adCode.append("\n");
		}

		adCode.append("var ref='" + adProperty.getRef() + "';");
		adCode.append("\n");

		String impId = dyService.genImpressionUUID(adFilterElement.getIp(),
				adFilterElement.getHost(), adFilterElement.getRef(),
				adFilterElement.getUserAgent());

		adCode.append("var impId='" + impId + "';");
		adCode.append("\n");

		long hourTs = System.currentTimeMillis() / 1000 / 60 / 60 / 24;

		String vlsspWHAddr = Config.getProperty("vlssp_wh_addr");

		adCode.append("document.write(unescape(\"%3Cscript src='" + vlsspWHAddr
				+ "?_=" + hourTs
				+ "' type='text/javascript'%3E%3C/script%3E\"));");
		adCode.append("\n");

		return adCode.toString();
	}

	

	public static void main(String[] args) {
		
		
		try {
			System.out.println(URLDecoder.decode("MTkyLjE2OC4xLjEwNwo%253D", "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	@Override
	public String adRetrieveForQingKooVideo(AdFilterElement adFilterElement,
			String template, VarSizeBean vsb) {
	
		
		/**
		StringBuffer adCode = new StringBuffer("");

		AdvPlan advPlan = adRetrievalCommonService.adRetrieveForSHDX(
				adFilterElement, DBInfoFresh.QINGKOO_VIDEO_CHANNEL, vsb);

		if (null != advPlan) {

			String bigUrl = "http://ad.adtina.com/ad/"
					+ advPlan.getAdInstance().getUserId() + "/"
					+ advPlan.getAdInstance().getAdId() + "?w="
					+ vsb.getSizeBigWidth() + "&h=" + vsb.getSizeBigHeight()
					+ "&adforcerReferer=" + adFilterElement.getRef();

			String smallUrl = "http://ad.adtina.com/ad/"
					+ advPlan.getAdInstance().getUserId() + "/"
					+ advPlan.getAdInstance().getAdId() + "?w="
					+ vsb.getSizeSmallWidth() + "&h="
					+ vsb.getSizeSmallHeight() + "&type=view&adforcerReferer="
					+ adFilterElement.getRef();

			adCode.append("var url1 = document.createElement('input');");
			adCode.append("url1.type='hidden'; ");
			adCode.append("url1.id='ad1'; ");
			adCode.append("url1.value='" + bigUrl + "'; ");
			adCode.append("var url2 = document.createElement('input');");
			adCode.append("url2.type='hidden';");
			adCode.append("url2.id='ad2'; ");
			adCode.append("url2.value='" + smallUrl + "';");
			adCode.append("");
			adCode.append("var dialog = document.getElementById('ad_id'); ");
			adCode.append("dialog.appendChild(url1); ");
			adCode.append("dialog.appendChild(url2);");
			adCode.append("");
			adCode.append("var scriptShow=document.createElement('script');");
			adCode.append("scriptShow.type='text/javascript';");
			adCode.append("scriptShow.src = document.getElementById('dt').value; ");
			adCode.append("var headShow = document.getElementsByTagName('head'); ");
			adCode.append("if(headShow && headShow[0]) {");
			adCode.append("	headShow[0].appendChild(scriptShow);");
			adCode.append("}");

		} else {
			adCode.append("");
		}

		return adCode.toString();
		*/
		
		return "";
	}
	
	

	private int maxValue(Set<Integer> setInt) {
		int max = Integer.MIN_VALUE;
		for (int v : setInt) {
			if (v > max) {
				max = v;
			}
		}

		return max;
	}

	private int minValue(Set<Integer> setInt) {
		int min = Integer.MAX_VALUE;
		for (int v : setInt) {
			if (v < min) {
				min = v;
			}
		}

		return min;
	}

	
	
	@Override
	public String adRetrieveForQingKooVideoDemo(
			AdFilterElement adFilterElement, String template) {
		StringBuffer adCode = new StringBuffer("");
		/**
		AdvPlan advPlan = adRetrievalCommonService.adRetrieveForSHDX(
				adFilterElement, DBInfoFresh.QINGKOO_VIDEO_CHANNEL, null);

		if (null != advPlan) {
			List<AdMaterial> admList = advPlan.getAdMaterials();

			if (admList != null && admList.size() > 0) {
				Map<Integer, String[]> sizeMap = new HashMap<Integer, String[]>();
				for (AdMaterial adm : admList) {
					String[] wh = adm.getMaterialSizeValue().split("x");

					int sizeSum = Integer.parseInt(wh[0])
							+ Integer.parseInt(wh[1]);

					sizeMap.put(sizeSum, wh);
				}

				int minSize = this.minValue(sizeMap.keySet());
				int maxSize = this.maxValue(sizeMap.keySet());

				String[] bigWh = sizeMap.get(maxSize);
				String[] smallWh = sizeMap.get(minSize);

				adCode.append("var bigw='" + bigWh[0] + "';");
				adCode.append("\n");
				adCode.append("var bigh='" + bigWh[1] + "';");
				adCode.append("\n");
				adCode.append("var smallw = '" + smallWh[0] + "'");
				adCode.append("\n");
				adCode.append("var smallh = '" + smallWh[1] + "'");
				adCode.append("\n");
				adCode.append("var userId = '"
						+ advPlan.getAdInstance().getUserId() + "'");
				adCode.append("\n");
				adCode.append("var adId = '"
						+ advPlan.getAdInstance().getAdId() + "'");
				adCode.append("\n");
				adCode.append("var closeType='"
						+ advPlan.getAdInstance().getCloseType() + "'");
				adCode.append("\n");
				adCode.append("var display='1'");
				adCode.append("\n");

			} else {
				adCode.append("");

				adCode.append("var display='0'");
				adCode.append("\n");

			}

			adCode.append("var ref='" + adFilterElement.getRef() + "'");
			adCode.append("\n");
			if (!"radius".equals(adFilterElement.getAdAcct())
					&& !"null".equals(adFilterElement.getAdAcct())) {

				adCode.append("var adAcct='" + adFilterElement.getAdAcct()
						+ "'");
				adCode.append("\n");

				// 如果radius为空，则为空
			} else {
				adCode.append("var adAcct=''");
				adCode.append("\n");
			}

			long dayTs = System.currentTimeMillis() / 1000 / 60 / 60;

			String vlsspAddr = Config.getProperty("vlssp_addr_4shdx");

			adCode.append("document.write(unescape(\"%3Cscript src='"
					+ vlsspAddr + "?_=" + dayTs
					+ "' type='text/javascript'%3E%3C/script%3E\"));");
			adCode.append("\n");

		}
		*/
		return adCode.toString();
	}

	

	@Override
	public String getAdTargetUrl(String adId) {

		String targetUrl = "";

		AdvPlan advPlan = dbInfo.getAdvPlanMap2().get(adId);

		if (null != advPlan) {
			List<AdMaterial> adMaterialList = advPlan.getAdMaterials();

			for (AdMaterial adm : adMaterialList) {
				targetUrl = adm.getTargetUrl();
				break;
			}
		}

		return targetUrl;

	}



	@Override
	public String adRetrieveForShProtal(AdFilterElement adFilterElement) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
