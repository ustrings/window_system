package com.vaolan.ckserver.server.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.nutz.ssdb4j.spi.Response;
import org.nutz.ssdb4j.spi.SSDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hidata.framework.util.CookieManager;
import com.hidata.framework.util.DateUtil;
import com.hidata.framework.util.JsonUtil;
import com.vaolan.ckserver.dao.SSDBCache;
import com.vaolan.ckserver.model.RedirectUserInfo;
import com.vaolan.ckserver.server.CrowdDataRefreshServer;
import com.vaolan.ckserver.util.AdStatLogUtil;
import com.vaolan.ckserver.util.Constant;

@Service
public class CrowdDataRefreshServiceImpl implements CrowdDataRefreshServer
{
	@Autowired
	private SSDBCache SSDBCache;

	private static Logger logger = LoggerFactory.getLogger(CrowdDataRefreshServiceImpl.class);

	@Override
	public void updateCrowdData(HttpServletRequest request)
	{
		String vlVcId = request.getParameter("vl_vcid");
		String vlUsers = request.getParameter("vl_dusers");

		if (StringUtils.isBlank(vlVcId) || StringUtils.isBlank(vlUsers))
		{
			logger.warn("CrowdDataRefreshServiceImpl vlVcId or vlUsers is null!");
			return;
		}

		String vdspCid = CookieManager.getValueByName(request, Constant.VAOLAN_COOKIE_NAME);
		if (!StringUtils.isBlank(vdspCid))
		{
			SSDB ssdb = SSDBCache.getMasterSSDB();
			String filed = Constant.YKCROWDTYPE + Constant.SEPARATE + vlVcId;
			Response rsp = ssdb.hget(vdspCid, filed);
			if (!rsp.notFound())
			{
				JSONObject jo= JSONObject.fromObject(rsp.asString());
				RedirectUserInfo info = (RedirectUserInfo)JSONObject.toBean(jo, RedirectUserInfo.class);
				//根据vl_dusers的值判断设置Active 的值
				if(vlUsers.equals("0")){
					info.setActive(false);
				}else if(vlUsers.equals("1")){
					info.setActive(true);
				}
				//改成当前时间
				info.setMappingTime(String.valueOf(System.currentTimeMillis()));
				
				try
				{
					ssdb.hset(vdspCid, filed, JsonUtil.beanToJson(info));
					
				} catch (IOException e)
				{
					logger.error("CrowdDataRefreshServiceImpl JsonUtil.beanToJson exception ", e);
				}
			}
			else
			{
				logger.error("CrowdDataRefreshServiceImpl vdspCid can not get RedirectUserInfo vdspCid = " + vdspCid);
			}
		}
		else
		{
			logger.error("CrowdDataRefreshServiceImpl vdspCid is null.");
		}
		
		//AdStatLogUtil.saveCrowdInfo(request, vdspCid);
	}

}
