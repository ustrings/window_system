package com.vaolan.sspserver.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.hidata.framework.util.StringUtil;
import com.vaolan.sspserver.model.AdFilterElement;
import com.vaolan.sspserver.model.AdvPlan;
import com.vaolan.sspserver.util.DomainUtils;

/**
 * 媒体过滤:域名过滤 Date: 2014年6月16日 <br>
 * 
 * @author pj
 * */
@Service
public class MediaFilter {

	public boolean doFilter(AdvPlan advPlan, AdFilterElement adFilterElement) {
		//TODO: 重要测试使用，上线删除
//		return true;
		return this.adSiteUrlFilter(advPlan, adFilterElement);
	}

	/**
	 * 站点过滤
	 * 1.全路径匹配 http://www.baidu.com/1234
	 * 2.顶级域名匹配 baidu.com
	 * 3.负向域名匹配
	 * 
	 * @param advPlan
	 *            广告计划
	 * @param adFilterElement
	 *            push frame信息
	 * @return
	 * */
	public boolean adSiteFilter(AdvPlan advPlan, AdFilterElement adFilterElement) {
		boolean flag = true;
		if (advPlan.isSiteFilter()) {
			// 来源站点为空
			boolean isRefBlank = StringUtil.isBlank(adFilterElement.getRef());
			if(isRefBlank) return false;
			// 正向站点不包括当前的来源站点
			boolean isSiteNotContainsCurRef = 
					!advPlan.getAdSites().contains(
					StringUtil.getHostUrl(adFilterElement.getRef())) &&
					!advPlan.getAdSites().contains(
							DomainUtils.getTopDomainWithoutSubdomain(adFilterElement.getRef()));
			if(isSiteNotContainsCurRef) return false;
			// 如果设置了负向站点
			if(advPlan.isNegSiteFilter()) {
				// 负向站点包括当前来源
				boolean isNegSiteContainsCurRef = advPlan.getNegAdSites().contains(
						StringUtil.getHostUrl(adFilterElement.getRef())) ||
						advPlan.getNegAdSites().contains(
								DomainUtils.getTopDomainWithoutSubdomain(adFilterElement.getRef()));
				if(isNegSiteContainsCurRef) return false;		
			} 
			
		}
		return flag;
	}

	/**
	 * 站点url判断，站点和url 都为空，则认为是全域投放。 站点和url 只要一个不为空，那当前请求，要么匹配上域名，要么匹配上url，否则不投放
	 * 
	 * @param advPlan
	 * @param adFilterElement
	 * @return
	 */
	public boolean adSiteUrlFilter(AdvPlan advPlan,
			AdFilterElement adFilterElement) {
		boolean flag = false;
		if(StringUtils.isBlank(adFilterElement.getRef())){
			return false;
		}

		// 站点和url 都为空则可以投放
		if (!advPlan.isSiteFilter() && !advPlan.isUrlFilter()) {
			flag = true;
		} else {

			// 站点不为空 且 当前域名在投放的站点内，则投放
			if (advPlan.isSiteFilter()
					&& advPlan.getAdSites().contains(
							StringUtil.getHostUrl(adFilterElement.getRef()))) {
				flag = true;
			}

			//url不为空 且 当前url在投放的url内，则投放
			if (advPlan.isUrlFilter()
					&& advPlan.getAdUrls().contains(adFilterElement.getRef())) {
				flag = true;
			}

		}
		return flag;
	}

	/**
	 * url过滤<br>
	 * 
	 * @param advPlan
	 *            广告计划
	 * @param adFilterElement
	 *            push frame信息
	 * @return
	 * */
	public boolean adUrlFilter(AdvPlan advPlan, AdFilterElement adFilterElement) {
		boolean flag = true;
		if (advPlan.isUrlFilter()) {
			if (StringUtil.isBlank(adFilterElement.getRef())
					|| !advPlan.getAdUrls().contains(adFilterElement.getRef())) {
				flag = false;
			}
		}
		return flag;
	}

	
}
