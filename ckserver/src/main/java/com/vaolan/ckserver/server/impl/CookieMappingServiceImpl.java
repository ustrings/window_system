package com.vaolan.ckserver.server.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.nutz.ssdb4j.spi.Response;
import org.nutz.ssdb4j.spi.SSDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hidata.framework.util.JsonUtil;
import com.vaolan.ckserver.dao.CookieMappingDao;
import com.vaolan.ckserver.dao.SSDBCache;
import com.vaolan.ckserver.model.CookieMapModel;
import com.vaolan.ckserver.model.CookieUser;
import com.vaolan.ckserver.model.RedirectUserInfo;
import com.vaolan.ckserver.server.CookieMappingService;
import com.vaolan.ckserver.util.AdStatLogUtil;
import com.vaolan.ckserver.util.Config;
import com.vaolan.ckserver.util.Constant;
import com.vaolan.ckserver.util.GenCookieUserUtil;

@Service
public class CookieMappingServiceImpl implements CookieMappingService {
	@Autowired
	private CookieMappingDao cookieMappingDao;

	@Autowired
	private SSDBCache SSDBCache;

	private static Logger logger = LoggerFactory
			.getLogger(CookieMappingServiceImpl.class);

	@Override
	@Transactional
	@Deprecated
	public void saveCookieAndMappingInfo(CookieMapModel cookieMapModel,
			CookieUser cookieUser, boolean isNewCookie) {

		try {
			/**
			 * 1、保存cookie信息 2、去ck表里查询一下，当前用户是否有和当前adxcookie，在用mapping关系，
			 * 如果有，说明当前用户对我们的cookie失效了
			 * ，或者清除了，这时候就要把以前的mapping关系失效掉，重新和当前用户的adxcookie 进行mapping。
			 * 
			 * 3、如果ck表里查询不到，说明这个用户没有和当前adxcookie mapping过，这时候不再Mapping。
			 */
			if (isNewCookie) {

				/**
				 * 新种的cookie信息，保存下来,写磁盘
				 */
				AdStatLogUtil.saveCookieUser(cookieUser);
				// cookieMappingDao.saveCookieInfo(cookieUser);

				List<CookieMapModel> cmmList = cookieMappingDao
						.getVdspCookieInfobyAdxCid(cookieMapModel);

				for (CookieMapModel cmm : cmmList) {
					if (Constant.COOKIEMAPPING_STS_A.equals(cmm.getSts())) {
						CookieMapModel updateModel = new CookieMapModel();
						updateModel.setId(cmm.getId());
						updateModel.setSts(Constant.COOKIEMAPPING_STS_D);
						cookieMappingDao
								.updateCookieMappingRelSts(cookieMapModel);
					}
				}

				cookieMappingDao.saveCookieMappingInfo(cookieMapModel);

			} else {

				/**
				 * 有在用关系就不在mapping，没有的话再mapping
				 */
				boolean isHaveMappingRel = false;
				List<CookieMapModel> cmmList = cookieMappingDao
						.getVdspCookieInfobyAdxCid(cookieMapModel);

				for (CookieMapModel cmm : cmmList) {
					if (Constant.COOKIEMAPPING_STS_A.equals(cmm.getSts())) {
						isHaveMappingRel = true;
					}
				}

				if (!isHaveMappingRel) {
					cookieMappingDao.saveCookieMappingInfo(cookieMapModel);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存cookie，cookiemapping信息出错:" + e.getMessage(), e);
		}
	}

	@Override
	public boolean cookieMapping(HttpServletRequest request,
			HttpServletResponse response) {
		String adxCid = request.getParameter(Config
				.getProperty("tanx_cookie_name")); // tanx_tid
		String vltype = request.getParameter("vl_type"); // 1是直接请求的，2 表示访客找回的
		String vl_vcid = request.getParameter("vl_vcid"); // vaolan自己的人群ID
		String tanx_err = request.getParameter("tanx_err"); // tanx_err错误

		if (StringUtils.isNotBlank(tanx_err)) {
			/**
			 * logger.error("cms_url:"+request.getRequestURL()+request.
			 * getQueryString()); //获取参数、获取URL
			 * 
			 * if("1".equals(tanx_err)){
			 * logger.error("tanx,cookiemapping 出错: tanx_err:" +
			 * tanx_err+".## 1、没有 Tanx Cookie 且 DSP 不允许种植 Tanx Cookie"); }
			 * if("2".equals(tanx_err)){
			 * logger.error("tanx,cookiemapping 出错: tanx_err:" +
			 * tanx_err+".## 2、DSP 没有托管存储的权限"); } if("3".equals(tanx_err)){
			 * logger.error("tanx,cookiemapping 出错: tanx_err:" +
			 * tanx_err+".## 3、无效的指令(比如请求参数中仅有 tanx_nid) "); }
			 * if("4".equals(tanx_err)){
			 * logger.error("tanx,cookiemapping 出错: tanx_err:" +
			 * tanx_err+".## 4、内部错误"); }
			 **/
			return false;
		}

		if (StringUtils.isBlank(adxCid)) {
			logger.warn("adxcookie获取失败，失败原因: 获取adx的cookie为空");
			return false;
		}

		if (Constant.ADEXCHANGE_TANX_COOKIEMPPING_ERROR.equals(adxCid)) {
			logger.warn("adxcookie获取失败，失败原因: 获取adx的cookie为E0，tanx内部错误");
			return false;
		}

		try {
			adxCid = Constant.ADX_COOKIE_FLAG
					+ URLDecoder.decode(adxCid, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.info("CookieMappingServiceImpl URLDecoder.decode exception."
					+ e);
		}

		SSDB ssdb = SSDBCache.getMasterSSDB();
		Response rsp = ssdb.get(adxCid);

		CookieUser cookieUser = new CookieUser();
		boolean isNewCookie = GenCookieUserUtil.assembleCookieUser(cookieUser,
				request, response);

		// 如果是新cookie，则记录
		if (isNewCookie) {
			AdStatLogUtil.saveCookieUser(cookieUser);
		}

		// 新生成的zhaolan dps cookieid,或者是以前种植过带上来的zhaolan dsp cookieid
		String vdspCid = cookieUser.getCookieId();

		if (rsp.notFound()) {

			ssdb.set(adxCid, vdspCid);

		} else {

			// ssdb 存在的dsp cookieId
			String ssdbVdspCid = rsp.asString();

			if (StringUtils.isBlank(ssdbVdspCid)) {

				// SSDB含有adx的cookie id，SSDB中没有诏兰的cookie
				logger.error("SSDB含有adx的cookie id，SSDB中没有诏兰的cookie，adxCid:"
						+ adxCid);

			} else if (!isNewCookie) {

				// 如果用户是带上来的dsp cookie_id，且ssdb中也保存了，
				// 这时候也比对一下，以前保存的和这次带上来的是否一样
				GenCookieUserUtil.judgeSSDBandDspEqual(adxCid, ssdbVdspCid,
						vdspCid);

			} else {
				// 如果是ssdb以前就存在该用户的cookieId,但是这次上来没带上来，又重新种了一个
				// 这种情况，可能是爬虫,记录下，后续分析处理

				logger.error("SSDB含有adx的cookie id，SSDB中也有诏兰的cookie，但是用户没带上来,adxCid:"
						+ adxCid
						+ ", ssdbDspId:"
						+ ssdbVdspCid
						+ ",  新种的cookie:" + vdspCid);

			}
		}

		//AdStatLogUtil.saveCkMapping(request, vdspCid);

		// 记录dsp的人群信息
		/**
		 * 修改
		 * 
		 * @author xiaoming
		 * @date 2014-6-23
		 */
		if (!StringUtils.isBlank(vltype) && !StringUtils.isBlank(vl_vcid)) {
			try {
				// 根据key vdspCid 是否已经存在ssdb中,如果存在则 只改变一下当前时间即可，若不存在则需要进行以下判断
				// 若vltype = 2 则保存，否则不保存
				String filed = vltype + Constant.SEPARATE + vl_vcid;
				Response rsp_vcid = ssdb.hget(vdspCid, filed);
				// 不存在则保存
				if (rsp_vcid.notFound() && vltype.equals("2")) {
					ssdb.hset(vdspCid, filed,
							JsonUtil.beanToJson(new RedirectUserInfo(vl_vcid)));
				} else {
					// 存在则根据Key值获取，修改时间在保存
					JSONObject jo = JSONObject.fromObject(rsp_vcid.asString());
					RedirectUserInfo info = (RedirectUserInfo) JSONObject
							.toBean(jo, RedirectUserInfo.class);
					info.setMappingTime(String.valueOf(System
							.currentTimeMillis()));
					ssdb.hset(vdspCid, filed, JsonUtil.beanToJson(info));
				}
			} catch (IOException e) {
				logger.info("CookieMappingServiceImpl cookieMapping exception"
						+ e);
			} catch (Exception e) {
				e.printStackTrace();
			}

			//AdStatLogUtil.saveRtCrowd(request);
		}

		return true;
	}

}
