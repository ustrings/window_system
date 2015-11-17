package com.vaolan.sspserver.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.hidata.framework.cache.redis.JedisPoolWriper;
import com.hidata.framework.util.CookieManager;
import com.hidata.framework.util.DateUtil;
import com.hidata.framework.util.http.RequestUtil;

@Service
public class OnlineMonitorUtil {

	@Resource(name = "jedisPool_adstat")
	private JedisPoolWriper jedisPool_adstat;

	/**
	 * 有效在线流量
	 */
	private static String SSP_PUSH_ONLINE_VAILD = "ssp_push_online_vaild";
	/**
	 * 总共在线流量
	 */
	private static String SSP_PUSH_ONLINE_ALL = "ssp_push_online_all";

	/**
	 * 当天接受到的请求总数量
	 */
	private static String SSP_PUSH_DAYSUM = "ssp_push_daysum";
	/**
	 * 上海接受到的请求数量
	 */
	private static String SSP_PUSH_DAYSUM_SH = "ssp_push_daysum_sh";
	/**
	 * 江苏接受到的请求数量
	 */
	private static String SSP_PUSH_DAYSUM_JS = "ssp_push_daysum_js";
	/**
	 * 广告位被加载多少次
	 */
	private static String SSP_AD_IFRAMENUM = "ssp_ad_iframesum";
	/**
	 * 广告展现次数
	 */
	private static String SSP_AD_SHOWNUM = "ssp_ad_showsum";
	/**
	 * 打空次数
	 */
	private static String SSP_AD_BLANKNUM = "ssp_ad_blanksum";
	/**
	 * 上海广告位被加载多少次
	 */
	private static String SSP_AD_IFRAMENUM_SH = "ssp_ad_iframesum_sh";
	/**
	 * 上海广告展现次数
	 */
	private static String SSP_AD_SHOWNUM_SH = "ssp_ad_showsum_sh";
	/**
	 * 上海打空次数
	 */
	private static String SSP_AD_BLANKNUM_SH = "ssp_ad_blanksum_sh";
	/**
	 * 江苏广告位被加载多少次
	 */
	private static String SSP_AD_IFRAMENUM_JS = "ssp_ad_iframesum_js";
	/**
	 * 江苏广告展现次数
	 */
	private static String SSP_AD_SHOWNUM_JS = "ssp_ad_showsum_js";
	/**
	 * 江苏打空次数
	 */
	private static String SSP_AD_BLANKNUM_JS = "ssp_ad_blanksum_js";

	/**
	 * 在线流量
	 */
	private static int SSP_PUSH_ONLINE_TEMP = 0;
	/**
	 * 当天接受到的请求总数量
	 */
	private static int SSP_PUSH_DAYSUM_TEMP = 0;
	/**
	 * 上海接受到的请求数量
	 */
	private static int SSP_PUSH_DAYSUM_SH_TEMP = 0;
	/**
	 * 江苏接受到的请求数量
	 */
	private static int SSP_PUSH_DAYSUM_JS_TEMP = 0;
	/**
	 * 广告位被加载多少次
	 */
	private static int SSP_AD_IFRAMENUM_TEMP = 0;
	/**
	 * 广告展现次数
	 */
	private static int SSP_AD_SHOWNUM_TEMP = 0;
	/**
	 * 打空次数
	 */
	private static int SSP_AD_BLANKNUM_TEMP = 0;
	/**
	 * 上海广告位被加载多少次
	 */
	private static int SSP_AD_IFRAMENUM_SH_TEMP = 0;
	/**
	 * 上海广告展现次数
	 */
	private static int SSP_AD_SHOWNUM_SH_TEMP = 0;
	/**
	 * 上海打空次数
	 */
	private static int SSP_AD_BLANKNUM_SH_TEMP = 0;
	/**
	 * 江苏广告位被加载多少次
	 */
	private static int SSP_AD_IFRAMENUM_JS_TEMP = 0;
	/**
	 * 江苏广告展现次数
	 */
	private static int SSP_AD_SHOWNUM_JS_TEMP = 0;
	/**
	 * 江苏打空次数
	 */
	private static int SSP_AD_BLANKNUM_JS_TEMP = 0;

	/**
	 * 每 vaildTrafficInterval 秒钟的展现数量
	 */
	private static int vaildTrafficVal = 0;
	private static int vaildTrafficInterval = 5;
	private static long vaildTrafficIntervalLater = System.currentTimeMillis()
			+ vaildTrafficInterval * 1000;

	/**
	 * 每 vaildTrafficInterval 秒钟的展现数量
	 */
	private static int allTrafficVal = 0;
	private static int allTrafficInterval = 5;
	private static long allTrafficIntervalLater = System.currentTimeMillis()
			+ allTrafficInterval * 1000;
	
	private static Logger cktimelogger = Logger.getLogger("cktime");

	/**
	 * 更新有效在线统计信息
	 * 
	 * @param area
	 */
	public void vaildSspPushOnlineMontor(String area) {
		String currentDate = DateUtil
				.getCurrentDateStr(DateUtil.C_DATE_PATTON_DEFAULT);

		// 缓存一段时间的方案

		this.SSP_PUSH_DAYSUM_TEMP++;

		if ("1".equals(area)) {
			// 为江苏的请求总量 加 1
			this.SSP_PUSH_DAYSUM_JS_TEMP++;
		} else if ("2".equals(area)) {
			// 为上海的请求总量 加 1
			this.SSP_PUSH_DAYSUM_SH_TEMP++;
		}

		// 量达到1000次 一提交，减少和redis交互
		if (this.SSP_PUSH_DAYSUM_TEMP > 1000) {
			Jedis client = jedisPool_adstat.getJedis();

			Pipeline p = client.pipelined();
			p.hincrBy(SSP_PUSH_DAYSUM, currentDate, this.SSP_PUSH_DAYSUM_TEMP);
			p.hincrBy(SSP_PUSH_DAYSUM_JS, currentDate,
					this.SSP_PUSH_DAYSUM_JS_TEMP);
			p.hincrBy(SSP_PUSH_DAYSUM_SH, currentDate,
					this.SSP_PUSH_DAYSUM_SH_TEMP);

			p.sync();
			this.SSP_PUSH_DAYSUM_TEMP = 0;
			this.SSP_PUSH_DAYSUM_JS_TEMP = 0;
			this.SSP_PUSH_DAYSUM_SH_TEMP = 0;

			jedisPool_adstat.releaseJedis(client);

		}

		/**
		 * 每次请求redis的方案 Jedis client = jedisPool_adstat.getJedis(); // 为当天的请求总量
		 * 加 1 client.hincrBy(SSP_PUSH_DAYSUM, currentDate, 1);
		 * 
		 * if ("1".equals(area)) { // 为江苏的请求总量 加 1
		 * client.hincrBy(SSP_PUSH_DAYSUM_JS, currentDate, 1); } else if
		 * ("2".equals(area)) { // 为上海的请求总量 加 1
		 * client.hincrBy(SSP_PUSH_DAYSUM_SH, currentDate, 1); }
		 * 
		 * jedisPool_adstat.releaseJedis(client);
		 **/

		this.vaildTrafficSpeed();

	}

	/**
	 * 更新所有在线统计信息
	 * 
	 * @param area
	 */
	public void alldSspPushOnlineMontor(String area) {
		this.allTrafficSpeed();

	}

	// 计算有效流量速度的，所谓有效：就是请求的ua 是正常的，非爬虫的
	private void vaildTrafficSpeed() {

		long nowTime = System.currentTimeMillis();
		// 统计每 trafficInterval 秒钟的流量
		vaildTrafficVal++;
		if (nowTime >= this.vaildTrafficIntervalLater) {

			Jedis client = jedisPool_adstat.getJedis();
			client.set(SSP_PUSH_ONLINE_VAILD, this.vaildTrafficVal + "");
			this.vaildTrafficVal = 0;
			this.vaildTrafficIntervalLater = System.currentTimeMillis()
					+ this.vaildTrafficInterval * 1000;

			jedisPool_adstat.releaseJedis(client);

		}

	}

	// 计算所有流量速度的，所谓所有，就是服务器接受到的请求包括爬虫
	private void allTrafficSpeed() {
		long nowTime = System.currentTimeMillis();
		// 统计每 trafficInterval 秒钟的流量
		allTrafficVal++;
		if (nowTime >= allTrafficIntervalLater) {

			Jedis client = jedisPool_adstat.getJedis();
			client.set(SSP_PUSH_ONLINE_ALL, allTrafficVal + "");
			allTrafficVal = 0;
			allTrafficIntervalLater = System.currentTimeMillis()
					+ allTrafficInterval * 1000;

			jedisPool_adstat.releaseJedis(client);

		}

	}

	/**
	 * 获取 ssp 的有效在线流量
	 * 
	 * @return
	 */
	public String getVaildSspPushOnLineVal() {

		Jedis client = jedisPool_adstat.getJedis();
		String val = client.get(SSP_PUSH_ONLINE_VAILD);
		jedisPool_adstat.releaseJedis(client);
		if (StringUtils.isBlank(val)) {
			val = "0";
		} else {
			int valInt = Integer.parseInt(val) / this.vaildTrafficInterval;
			val = valInt + "";
		}
		return val;
	}

	/**
	 * 获取 ssp 的有效在线流量
	 * 
	 * @return
	 */
	public String getAllSspPushOnLineVal() {

		Jedis client = jedisPool_adstat.getJedis();
		String val = client.get(SSP_PUSH_ONLINE_ALL);
		jedisPool_adstat.releaseJedis(client);
		if (StringUtils.isBlank(val)) {
			val = "0";
		} else {
			int valInt = Integer.parseInt(val) / this.allTrafficInterval;
			val = valInt + "";
		}
		return val;
	}

	/**
	 * 对当前页面展现信息进行记录
	 * 
	 * @param adshowType
	 * @param area
	 */
	public void adshowRecord(String adshowType, String area) {
		String currentDate = DateUtil
				.getCurrentDateStr(DateUtil.C_DATE_PATTON_DEFAULT);
		Jedis client = jedisPool_adstat.getJedis();
		Pipeline p = client.pipelined();
		// 为广告位被加载次数 加 1
		p.hincrBy(SSP_AD_IFRAMENUM, currentDate, 1);

		// 江苏广告位被加载次数
		if ("1".equals(area)) {
			p.hincrBy(SSP_AD_IFRAMENUM_JS, currentDate, 1);
		} else if ("2".equals(area)) {
			// 上海广告位被加载次数
			p.hincrBy(SSP_AD_IFRAMENUM_SH, currentDate, 1);
		}

		// 展示的类型
		if ("1".equals(adshowType)) {
			p.hincrBy(SSP_AD_SHOWNUM, currentDate, 1);

			if ("1".equals(area)) {
				p.hincrBy(SSP_AD_SHOWNUM_JS, currentDate, 1);
			} else if ("2".equals(area)) {
				p.hincrBy(SSP_AD_SHOWNUM_SH, currentDate, 1);
			}

		} else {
			p.hincrBy(SSP_AD_BLANKNUM, currentDate, 1);
			if ("1".equals(area)) {
				p.hincrBy(SSP_AD_BLANKNUM_JS, currentDate, 1);
			} else if ("2".equals(area)) {
				p.hincrBy(SSP_AD_BLANKNUM_SH, currentDate, 1);
			}
		}

		p.sync();
		jedisPool_adstat.releaseJedis(client);
	}

	/**
	 * 获取 ssp 当天的总的流量
	 * 
	 * @return
	 */
	public String getSspPushDaySumVal() {
		String currentDate = DateUtil
				.getCurrentDateStr(DateUtil.C_DATE_PATTON_DEFAULT);
		Jedis client = jedisPool_adstat.getJedis();
		String val = client.hget(SSP_PUSH_DAYSUM, currentDate);
		jedisPool_adstat.releaseJedis(client);

		if (StringUtils.isBlank(val)) {
			val = "0";
		}

		return val;
	}
	
	/**
	 * 获取 ssp 多天的总的流量
	 * 
	 * @return
	 */
	public List<String> getMSspPushDaySumVal(String[] days) {
		List<String> list = new ArrayList<String>();
		Jedis client = jedisPool_adstat.getJedis();
		list = client.hmget(SSP_PUSH_DAYSUM, days);
		jedisPool_adstat.releaseJedis(client);

		List<String> newList = new ArrayList<String>();
		Iterator<String> numIterator = list.iterator();
		while(numIterator.hasNext()) {
			String value = numIterator.next();
			if(StringUtils.isEmpty(value)) {
				newList.add("0");
			} else {
				newList.add(value);
			}
		}
		return newList;
	}

	/**
	 * 获取 江苏当天的总的 push 量
	 * 
	 * @return
	 */
	public String getSspPushDaySumValJS() {
		String currentDate = DateUtil
				.getCurrentDateStr(DateUtil.C_DATE_PATTON_DEFAULT);
		Jedis client = jedisPool_adstat.getJedis();
		String val = client.hget(SSP_PUSH_DAYSUM_JS, currentDate);
		jedisPool_adstat.releaseJedis(client);

		if (StringUtils.isBlank(val)) {
			val = "0";
		}

		return val;
	}
	
	/**
	 * 获取 江苏多天的总的 push 量
	 * 
	 * @return
	 */
	public List<String> getMSspPushDaySumValJS(String[] days) {
		List<String> list = new ArrayList<String>();
		Jedis client = jedisPool_adstat.getJedis();
		list = client.hmget(SSP_PUSH_DAYSUM_JS, days);
		jedisPool_adstat.releaseJedis(client);

		List<String> newList = new ArrayList<String>();
		Iterator<String> numIterator = list.iterator();
		while(numIterator.hasNext()) {
			String value = numIterator.next();
			if(StringUtils.isEmpty(value)) {
				newList.add("0");
			} else {
				newList.add(value);
			}
		}
		return newList;
	}
	
	


	/**
	 * 获取 上海 当天的总的 push 量
	 * 
	 * @return
	 */
	public String getSspPushDaySumValSH() {
		String currentDate = DateUtil
				.getCurrentDateStr(DateUtil.C_DATE_PATTON_DEFAULT);
		Jedis client = jedisPool_adstat.getJedis();
		String val = client.hget(SSP_PUSH_DAYSUM_SH, currentDate);
		jedisPool_adstat.releaseJedis(client);

		if (StringUtils.isBlank(val)) {
			val = "0";
		}

		return val;
	}
	
	/**
	 * 获取 上海 多天的总的 push 量
	 * 
	 * @return
	 */
	public List<String> getMSspPushDaySumValSH(String[] days) {
		List<String> list = new ArrayList<String>();
		Jedis client = jedisPool_adstat.getJedis();
		list = client.hmget(SSP_PUSH_DAYSUM_SH, days);
		jedisPool_adstat.releaseJedis(client);

		List<String> newList = new ArrayList<String>();
		Iterator<String> numIterator = list.iterator();
		while(numIterator.hasNext()) {
			String value = numIterator.next();
			if(StringUtils.isEmpty(value)) {
				newList.add("0");
			} else {
				newList.add(value);
			}
		}
		return newList;
	}

	/**
	 * 获取当天 总的 iframe 封装量
	 * 
	 * @return
	 */
	public String getframeNum() {
		String currentDate = DateUtil
				.getCurrentDateStr(DateUtil.C_DATE_PATTON_DEFAULT);
		Jedis client = jedisPool_adstat.getJedis();
		String val = client.hget(SSP_AD_IFRAMENUM, currentDate);
		jedisPool_adstat.releaseJedis(client);

		if (StringUtils.isBlank(val)) {
			val = "0";
		}

		return val;
	}
	
	/**
	 * 获取 江苏多天的总的 push 量
	 * 
	 * @return
	 */
	public List<String> getMframeNum(String[] days) {
		List<String> list = new ArrayList<String>();
		Jedis client = jedisPool_adstat.getJedis();
		list = client.hmget(SSP_AD_IFRAMENUM, days);
		jedisPool_adstat.releaseJedis(client);

		List<String> newList = new ArrayList<String>();
		Iterator<String> numIterator = list.iterator();
		while(numIterator.hasNext()) {
			String value = numIterator.next();
			if(StringUtils.isEmpty(value)) {
				newList.add("0");
			} else {
				newList.add(value);
			}
		}
		return newList;
	}

	/**
	 * 获取当天 江苏 总的 iframe 封装量
	 * 
	 * @return
	 */
	public String getframeNumJS() {
		String currentDate = DateUtil
				.getCurrentDateStr(DateUtil.C_DATE_PATTON_DEFAULT);
		Jedis client = jedisPool_adstat.getJedis();
		String val = client.hget(SSP_AD_IFRAMENUM_JS, currentDate);
		jedisPool_adstat.releaseJedis(client);

		if (StringUtils.isBlank(val)) {
			val = "0";
		}
		return val;
	}
	
	/**
	 * 获取当天 江苏 多天总的 iframe 封装量
	 * 
	 * @return
	 */
	public List<String> getMframeNumJS(String[] days) {
		List<String> list = new ArrayList<String>();
		Jedis client = jedisPool_adstat.getJedis();
		list = client.hmget(SSP_AD_IFRAMENUM_JS, days);
		jedisPool_adstat.releaseJedis(client);

		List<String> newList = new ArrayList<String>();
		Iterator<String> numIterator = list.iterator();
		while(numIterator.hasNext()) {
			String value = numIterator.next();
			if(StringUtils.isEmpty(value)) {
				newList.add("0");
			} else {
				newList.add(value);
			}
		}
		return newList;
	}

	/**
	 * 获取当天 上海 总的 iframe 封装量
	 * 
	 * @return
	 */
	public String getframeNumSH() {
		String currentDate = DateUtil
				.getCurrentDateStr(DateUtil.C_DATE_PATTON_DEFAULT);
		Jedis client = jedisPool_adstat.getJedis();
		String val = client.hget(SSP_AD_IFRAMENUM_SH, currentDate);
		jedisPool_adstat.releaseJedis(client);

		if (StringUtils.isBlank(val)) {
			val = "0";
		}

		return val;
	}
	
	/**
	 * 获取当天 上海 多天总的 iframe 封装量
	 * 
	 * @return
	 */
	public List<String> getMframeNumSH(String[] days) {
		List<String> list = new ArrayList<String>();
		Jedis client = jedisPool_adstat.getJedis();
		list = client.hmget(SSP_AD_IFRAMENUM_SH, days);
		jedisPool_adstat.releaseJedis(client);

		List<String> newList = new ArrayList<String>();
		Iterator<String> numIterator = list.iterator();
		while(numIterator.hasNext()) {
			String value = numIterator.next();
			if(StringUtils.isEmpty(value)) {
				newList.add("0");
			} else {
				newList.add(value);
			}
		}
		return newList;
	}
	

	/**
	 * 获取当天广告 总 的展现次数
	 * 
	 * @return
	 */
	public String getAdShowNum() {
		String currentDate = DateUtil
				.getCurrentDateStr(DateUtil.C_DATE_PATTON_DEFAULT);
		Jedis client = jedisPool_adstat.getJedis();
		String val = client.hget(SSP_AD_SHOWNUM, currentDate);
		jedisPool_adstat.releaseJedis(client);

		if (StringUtils.isBlank(val)) {
			val = "0";
		}

		return val;
	}
	
	/**
	 * 获取当天 上海 多天总的 iframe 封装量
	 * 
	 * @return
	 */
	public List<String> getMAdShowNum(String[] days) {
		List<String> list = new ArrayList<String>();
		Jedis client = jedisPool_adstat.getJedis();
		list = client.hmget(SSP_AD_SHOWNUM, days);
		jedisPool_adstat.releaseJedis(client);

		List<String> newList = new ArrayList<String>();
		Iterator<String> numIterator = list.iterator();
		while(numIterator.hasNext()) {
			String value = numIterator.next();
			if(StringUtils.isEmpty(value)) {
				newList.add("0");
			} else {
				newList.add(value);
			}
		}
		return newList;
	}

	/**
	 * 获取当天江苏广告 总 的展现次数
	 * 
	 * @return
	 */
	public String getAdShowNumJS() {
		String currentDate = DateUtil
				.getCurrentDateStr(DateUtil.C_DATE_PATTON_DEFAULT);
		Jedis client = jedisPool_adstat.getJedis();
		String val = client.hget(SSP_AD_SHOWNUM_JS, currentDate);
		jedisPool_adstat.releaseJedis(client);

		if (StringUtils.isBlank(val)) {
			val = "0";
		}

		return val;
	}
	
	/**
	 *  获取多天江苏广告 总 的展现次数
	 * 
	 * @return
	 */
	public List<String> getMAdShowNumJS(String[] days) {
		List<String> list = new ArrayList<String>();
		Jedis client = jedisPool_adstat.getJedis();
		list = client.hmget(SSP_AD_SHOWNUM_JS, days);
		jedisPool_adstat.releaseJedis(client);

		List<String> newList = new ArrayList<String>();
		Iterator<String> numIterator = list.iterator();
		while(numIterator.hasNext()) {
			String value = numIterator.next();
			if(StringUtils.isEmpty(value)) {
				newList.add("0");
			} else {
				newList.add(value);
			}
		}
		return newList;
	}

	/**
	 * 获取当天上海广告 总 的展现次数
	 * 
	 * @return
	 */
	public String getAdShowNumSH() {
		String currentDate = DateUtil
				.getCurrentDateStr(DateUtil.C_DATE_PATTON_DEFAULT);
		Jedis client = jedisPool_adstat.getJedis();
		String val = client.hget(SSP_AD_SHOWNUM_SH, currentDate);
		jedisPool_adstat.releaseJedis(client);

		if (StringUtils.isBlank(val)) {
			val = "0";
		}

		return val;
	}
	
	/**
	 *  获取多天上海广告 总 的展现次数
	 * 
	 * @return
	 */
	public List<String> getMAdShowNumSH(String[] days) {
		List<String> list = new ArrayList<String>();
		Jedis client = jedisPool_adstat.getJedis();
		list = client.hmget(SSP_AD_SHOWNUM_SH, days);
		jedisPool_adstat.releaseJedis(client);

		List<String> newList = new ArrayList<String>();
		Iterator<String> numIterator = list.iterator();
		while(numIterator.hasNext()) {
			String value = numIterator.next();
			if(StringUtils.isEmpty(value)) {
				newList.add("0");
			} else {
				newList.add(value);
			}
		}
		return newList;
	}

	/**
	 * 获取总打空的的量
	 * 
	 * @return
	 */
	public String getBlankNum() {
		String currentDate = DateUtil
				.getCurrentDateStr(DateUtil.C_DATE_PATTON_DEFAULT);
		Jedis client = jedisPool_adstat.getJedis();
		String val = client.hget(SSP_AD_BLANKNUM, currentDate);
		jedisPool_adstat.releaseJedis(client);

		if (StringUtils.isBlank(val)) {
			val = "0";
		}

		return val;
	}
	
	/**
	 *   获取多天总打空的的量
	 * 
	 * @return
	 */
	public List<String> getMBlankNum(String[] days) {
		List<String> list = new ArrayList<String>();
		Jedis client = jedisPool_adstat.getJedis();
		list = client.hmget(SSP_AD_BLANKNUM, days);
		jedisPool_adstat.releaseJedis(client);

		List<String> newList = new ArrayList<String>();
		Iterator<String> numIterator = list.iterator();
		while(numIterator.hasNext()) {
			String value = numIterator.next();
			if(StringUtils.isEmpty(value)) {
				newList.add("0");
			} else {
				newList.add(value);
			}
		}
		return newList;
	}

	/**
	 * 获取江苏总打空的的量
	 * 
	 * @return
	 */
	public String getBlankNumJS() {
		String currentDate = DateUtil
				.getCurrentDateStr(DateUtil.C_DATE_PATTON_DEFAULT);
		Jedis client = jedisPool_adstat.getJedis();
		String val = client.hget(SSP_AD_BLANKNUM_JS, currentDate);
		jedisPool_adstat.releaseJedis(client);

		if (StringUtils.isBlank(val)) {
			val = "0";
		}

		return val;
	}
	
	/**
	 *   获取多天总打空的的量
	 * 
	 * @return
	 */
	public List<String> getMBlankNumJS(String[] days) {
		List<String> list = new ArrayList<String>();
		Jedis client = jedisPool_adstat.getJedis();
		list = client.hmget(SSP_AD_BLANKNUM_JS, days);
		jedisPool_adstat.releaseJedis(client);

		List<String> newList = new ArrayList<String>();
		Iterator<String> numIterator = list.iterator();
		while(numIterator.hasNext()) {
			String value = numIterator.next();
			if(StringUtils.isEmpty(value)) {
				newList.add("0");
			} else {
				newList.add(value);
			}
		}
		return newList;
	}

	/**
	 * 根据cookie，判断一个用户一天是否达到 每日最高 允许的广告展示
	 * 
	 * @return
	 */
	public boolean isdisplayadbyck(HttpServletRequest request, HttpServletResponse response) {
		// 标识用户频次，是否达到最大频次，如果达到则不再展示广告
		boolean userFreNeed = false;

		
		String refer = RequestUtil.getRefererUrl(request);
		
		
		//这样限制的话，一个终端只会展示设定的次数，那我们测试的话也就看不到，所以测试的话加个特定的标志，以防影响测试
		if(StringUtils.isBlank(refer) || refer.contains("from=testshow")){
			return true;
		}
		// 一个用户允许最大频次
		int userMaxFre = Integer.parseInt(Config.getProperty("user_fre"));

		String jspush = CookieManager.getValueByName(request, "_uj");

		// 用户没有cookie 说明第一次，展示广告,同时种植一个cookie
		if (StringUtils.isBlank(jspush)) {
			userFreNeed = true;

			Cookie cookie = new Cookie("_uj", UUID.randomUUID().toString()
					+ "_1");
			cookie.setPath("/");

			Date nextDayBegindate = DateUtil.parseDateTime(DateUtil
					.getNextDayBeginDateTimeStr());
			long nextDayBegindateLong = nextDayBegindate.getTime();
			// 到第二天凌晨的时候失效
			long expireMills = nextDayBegindateLong
					- System.currentTimeMillis();
			long expireSec = expireMills / 1000;

			cookie.setMaxAge((int) expireSec);
			response.addCookie(cookie);

		} else {

			String[] strs = jspush.split("_");
			int uf = Integer.parseInt(strs[1]);

			// 如果大与两次则不在展示广告
			if (uf >= userMaxFre) {
				userFreNeed = false;
				cktimelogger.info("cktime:"+DateUtil.getCurrentDateTimeStr()+",一个cookie 展示超过设定次数的访问,ck:"+jspush+",ref:"+RequestUtil.getRefererUrl(request));
				
				uf++;

				Cookie cookie = new Cookie("_uj", UUID.randomUUID().toString()
						+ "_" + uf);
				cookie.setPath("/");

				Date nextDayBegindate = DateUtil.parseDateTime(DateUtil
						.getNextDayBeginDateTimeStr());
				long nextDayBegindateLong = nextDayBegindate.getTime();
				// 到第二天凌晨的时候失效
				long expireMills = nextDayBegindateLong
						- System.currentTimeMillis();
				long expireSec = expireMills / 1000;

				cookie.setMaxAge((int) expireSec);
				response.addCookie(cookie);
				
			} else {

				// 如果没有达到每天最高量，继续可以投，同事cookie计个数
				userFreNeed = true;
				uf++;

				Cookie cookie = new Cookie("_uj", UUID.randomUUID().toString()
						+ "_" + uf);
				cookie.setPath("/");

				Date nextDayBegindate = DateUtil.parseDateTime(DateUtil
						.getNextDayBeginDateTimeStr());
				long nextDayBegindateLong = nextDayBegindate.getTime();
				// 到第二天凌晨的时候失效
				long expireMills = nextDayBegindateLong
						- System.currentTimeMillis();
				long expireSec = expireMills / 1000;

				cookie.setMaxAge((int) expireSec);
				response.addCookie(cookie);

			}

		}
		
		response.setHeader(
				"P3P",
				"CP=CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR");
		
		return userFreNeed;
	}

	/**
	 * 获取 上海 总打空的的量
	 * 
	 * @return
	 */
	public String getBlankNumSH() {
		String currentDate = DateUtil
				.getCurrentDateStr(DateUtil.C_DATE_PATTON_DEFAULT);
		Jedis client = jedisPool_adstat.getJedis();
		String val = client.hget(SSP_AD_BLANKNUM_SH, currentDate);
		jedisPool_adstat.releaseJedis(client);
		if (StringUtils.isBlank(val)) {
			val = "0";
		}
		return val;
	}
	
	/**
	 *   获取多天 上海 总打空的的量
	 * 
	 * @return
	 */
	public List<String> getMBlankNumSH(String[] days) {
		List<String> list = new ArrayList<String>();
		Jedis client = jedisPool_adstat.getJedis();
		list = client.hmget(SSP_AD_BLANKNUM_SH, days);
		jedisPool_adstat.releaseJedis(client);

		List<String> newList = new ArrayList<String>();
		Iterator<String> numIterator = list.iterator();
		while(numIterator.hasNext()) {
			String value = numIterator.next();
			if(StringUtils.isEmpty(value)) {
				newList.add("0");
			} else {
				newList.add(value);
			}
		}
		return newList;
	}

}
