package com.vaolan.sspserver.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hidata.framework.cache.redis.JedisPoolWriper;
import com.vaolan.sspserver.filter.AdPlanFilter;
import com.vaolan.sspserver.model.AdFreshInfo;
import com.vaolan.sspserver.model.AdMaterial;
import com.vaolan.sspserver.model.AdvPlan;
import com.vaolan.sspserver.model.NumEntity;
import com.vaolan.sspserver.timer.DBInfoFresh;
import com.vaolan.sspserver.util.Config;
import com.vaolan.sspserver.util.Constant;
import com.vaolan.sspserver.util.DateUtils;
import com.vaolan.sspserver.util.ExcelUtil;
import com.vaolan.sspserver.util.HttpUtil;
import com.vaolan.sspserver.util.OnlineMonitorUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;



@Controller
public class LoadAdvPlanController {

	private static Logger logger = Logger
			.getLogger(LoadAdvPlanController.class);

	@Resource(name = "dbinfo")
	private DBInfoFresh dbInfo;

	@Resource(name = "jedisPool_adstat")
	private JedisPoolWriper jedisPool;

	@Autowired
	private AdPlanFilter adPlanFilter;

	@Autowired
	private OnlineMonitorUtil olm;


	
	/**
	 * 测试关键词查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getCurPvNum", produces = "application/json")
	@ResponseBody
	public String getCurPvNum(HttpServletRequest request,
			HttpServletResponse response) {
		return dbInfo.getPvCountStr();
	}
	
	/**
	 * 展示系统当前的投放信息
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/displayadinfo_new")
	public String displayadinfoNew(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		String ssp_cache_fresh_ipport = Config
				.getProperty("ssp_cache_fresh_ipport");

		Map<String, List<AdFreshInfo>> adFreshMap = new HashMap<String, List<AdFreshInfo>>();

		if (null != ssp_cache_fresh_ipport) {
			String[] sspservers = ssp_cache_fresh_ipport.split(",");
			for (String sspserver : sspservers) {
				String sspUrl = "http://" + sspserver + "/advPlanGet";
				String responseJson = HttpUtil.accessURLByGetMethod(sspUrl);

				List<AdFreshInfo> adFreshInfoList = new ArrayList<AdFreshInfo>();
				if (StringUtils.isNotBlank(responseJson)) {
					JSONArray ja = JSONArray.parseArray(responseJson);

					int allAdCount = 0;
					int allClickCount = 0;
					int allNeedCount = 0;
					for (int i = 0; i < ja.size(); i++) {
						JSONObject jo = (JSONObject) ja.get(i);

						AdFreshInfo adFreshInfo = new AdFreshInfo();

						adFreshInfo.setAdId(jo.getString("adId"));
						adFreshInfo.setAdName(jo.getString("adName"));
						adFreshInfo.setChannel(jo.getString("channel"));
						adFreshInfo.setCrowdOrMt(jo.getString("crowdOrMt"));
						adFreshInfo.setAdCount(jo.getString("adCount"));
						adFreshInfo.setAdStatus(jo.getString("adStatus"));
						adFreshInfo.setSize(jo.getString("size"));
						adFreshInfo.setDayLimitCount(jo
								.getString("dayLimitCount"));
						adFreshInfo.setClickCount(jo.getString("clickCount"));
						adFreshInfo.setClickRate(jo.getString("clickRate"));
						adFreshInfo.setStopTime(jo.getString("stopTime"));
						adFreshInfo.setCloseType(jo.getString("closeType"));
						adFreshInfo.setFinishRate(jo.getString("finishRate"));

						adFreshInfoList.add(adFreshInfo);

						allAdCount += Integer.parseInt(jo.getString("adCount"));
						allClickCount += Integer.parseInt(jo
								.getString("clickCount"));
						allNeedCount += Integer.parseInt(jo
								.getString("dayLimitCount"));

					}

					AdFreshInfo adSum = new AdFreshInfo();
					adSum.setAdId("sum");
					adSum.setAdCount(allAdCount + "");
					adSum.setClickCount(allClickCount + "");
					adSum.setDayLimitCount(allNeedCount + "");

					adFreshInfoList.add(adSum);

				}
				adFreshMap.put(sspserver, adFreshInfoList);

			}

		}

		String vaildOnlineVal = olm.getVaildSspPushOnLineVal();
		
		String allOnlineVal = olm.getAllSspPushOnLineVal();

		String todayVal = olm.getSspPushDaySumVal();
		String todayValJS = olm.getSspPushDaySumValJS();
		String todayValSH = olm.getSspPushDaySumValSH();

		String frameNum = olm.getframeNum();
		String adShowNum = olm.getAdShowNum();
		String blankNum = olm.getBlankNum();

		String frameNumJS = olm.getframeNumJS();
		String adShowNumJS = olm.getAdShowNumJS();
		String blankNumJS = olm.getBlankNumJS();

		String frameNumSH = olm.getframeNumSH();
		String adShowNumSH = olm.getAdShowNumSH();
		String blankNumSH = olm.getBlankNumSH();

		model.addAttribute("adFreshInfoMap", adFreshMap);

		model.addAttribute("vaildOnlineVal", vaildOnlineVal);
		model.addAttribute("allOnlineVal", allOnlineVal);
		
		model.addAttribute("todayVal", todayVal);
		model.addAttribute("todayValJS", todayValJS);
		model.addAttribute("todayValSH", todayValSH);

		model.addAttribute("frameNum", frameNum);
		model.addAttribute("adShowNum", adShowNum);
		model.addAttribute("blankNum", blankNum);

		model.addAttribute("frameNumJS", frameNumJS);
		model.addAttribute("adShowNumJS", adShowNumJS);
		model.addAttribute("blankNumJS", blankNumJS);

		model.addAttribute("frameNumSH", frameNumSH);
		model.addAttribute("adShowNumSH", adShowNumSH);
		model.addAttribute("blankNumSH", blankNumSH);

		return "view/fresh_new";
	}
	
	
	
	 @RequestMapping(value="downloadExcel")
	    public String download(HttpServletRequest request,HttpServletResponse response) throws IOException{
		// 查询数据
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
	        String fileName="excel文件";
	        //填充projects数据
	        List<NumEntity> projects=getNumEntities(startTime, endTime);
	        List<Map<String,Object>> list=createExcelRecord(projects);
	        String columnNames[]={"日期","总数：有效请求量","总数：被封装次数","总数：真实展现次数","总数：打空次数",
	        		"江苏：有效请求量", "江苏：被封装次数","江苏：真实展现次数", "江苏：打空次数", "上海：有效请求量",
	        		"上海：被封装次数", "上海：真实展现次数","上海：打空次数"};//列名
	        String keys[]   =    {"curDate","todayVal","frameNum","adShowNum","blankNum","todayValJS",
	        		"frameNumJS", "adShowNumJS", "blankNumJS", "todayValSH", "frameNumSH","adShowNumSH","blankNumSH"};//map中的key
	        ByteArrayOutputStream os = new ByteArrayOutputStream();
	        try {
	            ExcelUtil.createWorkBook(list,keys,columnNames).write(os);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        byte[] content = os.toByteArray();
	        InputStream is = new ByteArrayInputStream(content);
	        // 设置response参数，可以打开下载页面
	        response.reset();
	        response.setContentType("application/vnd.ms-excel;charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
	        ServletOutputStream out = response.getOutputStream();
	        BufferedInputStream bis = null;
	        BufferedOutputStream bos = null;
	        try {
	            bis = new BufferedInputStream(is);
	            bos = new BufferedOutputStream(out);
	            byte[] buff = new byte[2048];
	            int bytesRead;
	            // Simple read/write loop.
	            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	                bos.write(buff, 0, bytesRead);
	            }
	        } catch (final IOException e) {
	            throw e;
	        } finally {
	            if (bis != null)
	                bis.close();
	            if (bos != null)
	                bos.close();
	        }
	        return null;
	    }
	
	 
	    private List<Map<String, Object>> createExcelRecord(List<NumEntity> projects) {
	        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("sheetName", "sheet1");
	        listmap.add(map);
	        NumEntity project=null;
	        for (int j = 0; j < projects.size(); j++) {
	            project=projects.get(j);
	            Map<String, Object> mapValue = new HashMap<String, Object>();
	            mapValue.put("curDate", project.getCurDate());
	            
	            mapValue.put("todayVal", project.getTodayVal());
	            mapValue.put("frameNum", project.getFrameNum());
	            mapValue.put("adShowNum", project.getAdShowNum());
	            mapValue.put("blankNum", project.getBlankNum());
	            
	            mapValue.put("todayValJS", project.getTodayValJS());
	            mapValue.put("frameNumJS", project.getFrameNumJS());
	            mapValue.put("adShowNumJS", project.getAdShowNumJS());
	            mapValue.put("blankNumJS", project.getBlankNumJS());
	            
	            mapValue.put("todayValSH", project.getTodayValSH());
	            mapValue.put("frameNumSH", project.getFrameNumSH());
	            mapValue.put("adShowNumSH", project.getAdShowNumSH());
	            mapValue.put("blankNumSH", project.getBlankNumSH());
	            listmap.add(mapValue);
	        }
	        return listmap;
	}
	
	
	@RequestMapping(value = "/queryHistoryNum")
	public String queryHistoryNum(HttpServletRequest request,
			HttpServletResponse responsel, Model model) {
		// 查询数据
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
		List<NumEntity> numEntities =  getNumEntities(startTime, endTime);
		
		model.addAttribute("numEntities", numEntities);
		
		return "view/history_num_query";
	}
	
	public List<NumEntity> getNumEntities(String startTime, String endTime) {
		
		List<String> days = DateUtils.getDateArr(startTime, endTime, "yyyy-MM-dd");
		String[] dayArr = new String[days.size()];
		dayArr = days.toArray(dayArr);
		
		List<String> todayVals = olm.getMSspPushDaySumVal(dayArr);
		List<String>  todayValJSs = olm.getMSspPushDaySumValJS(dayArr);
		List<String>  todayValSHs = olm.getMSspPushDaySumValSH(dayArr);

		List<String>  frameNums = olm.getMframeNum(dayArr);
		List<String>  adShowNums = olm.getMAdShowNum(dayArr);
		List<String>  blankNums = olm.getMBlankNum(dayArr);

		List<String>  frameNumJSs = olm.getMframeNumJS(dayArr);
		List<String>  adShowNumJSs = olm.getMAdShowNumJS(dayArr);
		List<String>  blankNumJSs = olm.getMBlankNumJS(dayArr);

		List<String>  frameNumSHs = olm.getMframeNumSH(dayArr);
		List<String>  adShowNumSHs = olm.getMAdShowNumSH(dayArr);
		List<String>  blankNumSHs = olm.getMBlankNumSH(dayArr);
		
		List<NumEntity> numEntities = new ArrayList<NumEntity>();
		
		for(int i=0; i< days.size(); i++) {
			NumEntity entity = new NumEntity();
			entity.setCurDate(days.get(i));
			
			entity.setTodayVal(todayVals.get(i));
			entity.setTodayValJS(todayValJSs.get(i));
			entity.setTodayValSH(todayValSHs.get(i));
			
			entity.setFrameNum(frameNums.get(i));
			entity.setAdShowNum(adShowNums.get(i));
			entity.setBlankNum(blankNums.get(i));
			
			entity.setFrameNumJS(frameNumJSs.get(i));
			entity.setAdShowNumJS(adShowNumJSs.get(i));
			entity.setBlankNumJS(blankNumJSs.get(i));
			
			entity.setFrameNumSH(frameNumSHs.get(i));
			entity.setAdShowNumSH(adShowNumSHs.get(i));
			entity.setBlankNumSH(blankNumSHs.get(i));
			
			numEntities.add(entity);
		}
		
		return numEntities;
	}

	@RequestMapping("/reloadinit_new")
	public String reloadInitNew(HttpServletRequest request,
			HttpServletResponse response) {

		String ipport = request.getParameter("ipport");

		if (StringUtils.isNotBlank(ipport)) {
			String sspUrl = "http://" + ipport + "/reload";
			String ret = HttpUtil.accessURLByGetMethod(sspUrl);

			if ("0".equals(ret)) {
				logger.error("load：" + sspUrl + " ## load fail !!");
			} else {
				logger.info("load：" + sspUrl + " ## load sucess !!");
			}
		}

		return "redirect:/displayadinfo_new";
	}

	
	@RequestMapping(value = "/trafficInfo",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String trafficInfo(HttpServletRequest request,HttpServletResponse response){
		String tInfo = "";
		
		String vaildOnlineVal = olm.getVaildSspPushOnLineVal();
		String allOnlineVal = olm.getAllSspPushOnLineVal();
		String todayVal = olm.getSspPushDaySumVal();
		String frameNum = olm.getframeNum();
		String adShowNum = olm.getAdShowNum();

		
		JSONObject trafficJson = new JSONObject();
		trafficJson.put("vaildOnlineVal", vaildOnlineVal);
		trafficJson.put("allOnlineVal", allOnlineVal);
		trafficJson.put("todayVal", todayVal);
		trafficJson.put("frameNum", frameNum);
		trafficJson.put("adShowNum", adShowNum);
		
		tInfo = trafficJson.toString();
		
		
		return tInfo;
	}
	@RequestMapping(value = "/advPlanGet", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String advPlanGet(HttpServletRequest request,
			HttpServletResponse response) {
		String jaStr = "";
		try {

			Map<String, AdvPlan> advPlanMap2 = dbInfo.getAdvPlanMap2();

			JSONArray ja = new JSONArray();

			for (String adId : advPlanMap2.keySet()) {

				AdvPlan advPlan = advPlanMap2.get(adId);

				String size = "";

				if (Constant.LINK_TYPE_M.equals(advPlan.getAdInstance()
						.getLinkType())) {
					List<AdMaterial> admList = advPlan.getAdMaterials();
					AdMaterial admaterial = admList.get(0);
					size = admaterial.getMaterialSizeValue();
				} else if (Constant.LINK_TYPE_E.equals(advPlan.getAdInstance()
						.getLinkType())) {
					size = advPlan.getAdExtLink().getPicSize();
				} else if (Constant.LINK_TYPE_J.equals(advPlan.getAdInstance().getLinkType())){
					size = advPlan.getAdExtLink().getPicSize();
				} else if (Constant.LINK_TYPE_L.equals(advPlan.getAdInstance().getLinkType())){
					size = advPlan.getAdExtLink().getPicSize();
				}

				Jedis client = jedisPool.getJedis();

				Map<String, String> adstatMap = client.hgetAll("adstat_main_"
						+ advPlan.getAdInstance().getAdId());
				String clickCount = adstatMap.get("today_click_num") == null ? "0"
						: adstatMap.get("today_click_num");

				String adCount = adstatMap.get("today_pv_num") == null ? "0"
						: adstatMap.get("today_pv_num");

				double clickCountDouble = 0;
				if (!"0".equals(adCount)) {
					clickCountDouble = Double.parseDouble(clickCount)
							/ Double.parseDouble(adCount) * 100;
				}
				jedisPool.releaseJedis(client);

				JSONObject jb = new JSONObject();
				if(advPlan.getAdInstance().getAdUsefulType().equalsIgnoreCase("N")) {
					jb.put("channel", "需求平台");
				} else if(advPlan.getAdInstance().getAdUsefulType().equalsIgnoreCase("S")) {
					jb.put("channel", "VFOCUS");
				}
				jb.put("adId", adId);
				jb.put("adName", advPlan.getAdInstance().getAdName());
				jb.put("size", size);
				jb.put("dayLimitCount", advPlan.getDayLimit());
				jb.put("stopTime", advPlan.getAdInstance().getEndTime());
				jb.put("clickCount", clickCount);
				jb.put("adCount", adCount);
				jb.put("clickRate", clickCountDouble + "");

				double finishRate = 0;
				if (!"0".equals(advPlan.getDayLimit())) {
					finishRate = Double.parseDouble(adCount)
							/ Double.parseDouble(advPlan.getDayLimit() + "")
							* 100;
				}
				jb.put("finishRate", finishRate);

				boolean bdl = adPlanFilter.isBeyondDayLimit(advPlan);

				if (bdl) {
					jb.put("adStatus", "正在投放");
				} else {
					jb.put("adStatus", "今日到量停止");
				}

				if ("0".equals(advPlan.getAdInstance().getCloseType())) {
					jb.put("closeType", "上面");
				} else if ("1".equals(advPlan.getAdInstance().getCloseType())) {
					jb.put("closeType", "嵌入");
				} else if ("2".equals(advPlan.getAdInstance().getCloseType())) {
					jb.put("closeType", "死叉");
				} else if ("R".equals(advPlan.getAdInstance().getCloseType())) {
					jb.put("closeType", "混合");
				}

				boolean b = advPlan.isDmpCrowdMatch();
				if (b) {
					jb.put("crowdOrMt", "人群精准");
				} else {

					Set<String> targets = new HashSet<String>();
					if (advPlan.isAdAcctTargetFilter()) {
						targets.add("(ad帐号定向");
					}

					if (advPlan.isSiteFilter()) {
						targets.add("域名定向");
					}
					if (advPlan.isUrlFilter()) {
						targets.add("url定向");
					}
					if (advPlan.getIsPosIpTargetFilter()) {
						targets.add("IP正向定向");
					}
					if (advPlan.getIsNegIpTargetFilter()) {
						targets.add("IP负向定向");
					}
					if (advPlan.isRegionTargetFilter()) {
						targets.add("地域定向");
					}
					if (advPlan.isLabelFilter()) {
						targets.add("标签定向");
					}
					if (advPlan.isKeywordFilter()) {
						if(advPlan.getAdPlanKeyWord().getIsJisoujitou().equals("1")) {
							targets.add("关键词定向-即搜即投");
						} else {
							targets.add("关键词定向-离线关键词");
						}
					}
					if ("1".equals(advPlan.getAdTimeFrequency().getIsUniform())) {
						targets.add("均匀投放["
								+ advPlan.getAdTimeFrequency().getMinuteLimit()
								+ "]");
					}
					jb.put("crowdOrMt", "盲投" + targets.toString());

				}

				ja.add(jb);

			}

			jaStr = ja.toString();

			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		} catch (Exception e) {
			logger.error("刷新advInfo失败：", e);
		}
		return jaStr;
	}

	@RequestMapping("/reload")
	@ResponseBody
	public String reload(HttpServletRequest request,
			HttpServletResponse response) {
		String ret = "";
		try {
			dbInfo.freshAdvInfo();
			ret = "1";
		} catch (Exception e) {
			ret = "0";
			e.printStackTrace();
			logger.error("加载出错", e);
		}
		return ret;
	}

	public static void main(String[] args) {
		Set<String> targets = new HashSet<String>();
		targets.add("ad帐号定向");

		targets.add("域名定向");
		targets.add("url定向");
	}

}
