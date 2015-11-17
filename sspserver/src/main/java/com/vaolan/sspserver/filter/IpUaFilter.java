package com.vaolan.sspserver.filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hidata.framework.util.http.RequestUtil;
import com.vaolan.sspserver.model.AdFilterElement;
import com.vaolan.sspserver.model.AdvPlan;
import com.vaolan.sspserver.model.IPInfoForRedis;
import com.vaolan.sspserver.service.IpUaService;
import com.vaolan.sspserver.util.Constant;
import com.vaolan.sspserver.util.TimeUtil;

/**
 * AD帐号过滤:ad过滤 Date: 2014年6月16日 <br>
 * 
 * @author chenjinzhao
 * */
@Service
public class IpUaFilter {
	@Autowired
	private IpUaService ipUaService;
	
	public boolean doFilter(AdvPlan advPlan, AdFilterElement adFilterElement,HttpServletRequest request,
			HttpServletResponse response) {
		return doCookieNumFilter(advPlan, adFilterElement, request, response) && doIPNumFilter(advPlan, adFilterElement, request);
	}
	
	/**
	 * 过滤 ua
	 * @param advPlan
	 * @param adFilterElement
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean doCookieNumFilter(AdvPlan advPlan, AdFilterElement adFilterElement,HttpServletRequest request,
			HttpServletResponse response) {
		boolean flag = false;
		if(advPlan.isAdUserFrequency()) {
			// =========为 uv 访问数 +1============
			if(!StringUtils.isEmpty(advPlan.getAdUserFrequency().getUidImpressNum())) {
				//CookieName
				String cookieName = Constant.COOKIE_PREFIX + "_" + advPlan.getAdvId();
				Cookie[] cookies = request.getCookies();
				Cookie cookie = null;
				String cookieValue = null;
				if(cookies !=null) {
					for (int i = 0; i < cookies.length; i++) {
						cookie = cookies[i];
						// 存在 cookie 就把 cookie 的值取出来
						if (cookie.getName().equals(cookieName)) {
							cookieValue = cookie.getValue();
							break;
						}
					}
				}

				// 如果 cookie 不为空就为访问数 +1
				if(!StringUtils.isEmpty(cookieValue)) {
					flag = true;
					int oldCookieValue = Integer.parseInt(cookieValue);
					int uidImpressNum =  Integer.parseInt(advPlan.getAdUserFrequency().getUidImpressNum());
					if(oldCookieValue >= uidImpressNum) {
						flag = false;
					} else {
						int newCookieValue = Integer.parseInt(cookieValue) + 1;
						Cookie uvcookie = new Cookie(cookieName, newCookieValue + "");
						//cookie的过期为当天 0 晨
						uvcookie.setMaxAge((int) (TimeUtil.getRemainTime()/1000));
						response.addCookie(uvcookie);
					}
				} else {
					flag = true;
					// 把 cookie 写进去
					Cookie uvcookie = new Cookie(cookieName, "1");
					//cookie的过期为当天 0 晨
					uvcookie.setMaxAge((int) (TimeUtil.getRemainTime()/1000));
					response.addCookie(uvcookie);
				}
			} else {
				flag = true;
			}
			// =========为 uv 访问数 +1============
		} else {
			flag = true;
		}
		return flag;
	}

	/**
	 * 过滤 ip 限制数
	 * @param advPlan
	 * @param adFilterElement
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean doIPNumFilter(AdvPlan advPlan, AdFilterElement adFilterElement,HttpServletRequest request) {
		boolean flag = false;
		if(advPlan.isAdUserFrequency()) {
			if(!StringUtils.isEmpty(advPlan.getAdUserFrequency().getUidIpNum())) {
			String ip = RequestUtil.getClientIp(request);
			// String id  = request.getParameter("id");
			// =========为 ip 访问数 +1============
			// 存放 key 格式 ： ip_adid
			String keyForIp = ip + "_" +  advPlan.getAdvId();
			IPInfoForRedis ipInfo = ipUaService.getIPInfoForRedis(keyForIp);
			// 如果获取到的 IP 记录为空就新增一条记录
			if(ipInfo==null) {
				int remainSecond = (int) (TimeUtil.getRemainTime()/1000);
				ipInfo = new IPInfoForRedis();
				ipInfo.setKey(keyForIp);
				ipInfo.setVisitNum("1");
				// 获取当前过期时间
				try {
					ipUaService.saveIPInfoForRedis(ipInfo, remainSecond);
				} catch (Exception e) {
					e.printStackTrace();
				}
				flag = true;
			} else {
				// 判断当前的ip投放数量是不是超过了指定数量
				
					flag = true;
					int uidIpNum = Integer.parseInt(advPlan.getAdUserFrequency().getUidIpNum());
					// 如果当前的访问量已经超过了指定数量那么就不再投放广告了
					if( Integer.parseInt(ipInfo.getVisitNum()) >= uidIpNum) {
						flag = false;
					} else {
						// 否则就为访问数 +1
						try {
							ipUaService.hincrByOne(keyForIp);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} 
			} else {
				flag = true;
			}
		} else {
			flag = true;
		}
		// =========为 ip 访问数 +1============
		
		return flag;
	}
	

}
