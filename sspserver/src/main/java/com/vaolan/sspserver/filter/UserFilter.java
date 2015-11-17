package com.vaolan.sspserver.filter;

import org.springframework.stereotype.Service;

import com.hidata.framework.util.StringUtil;
import com.vaolan.sspserver.model.AdFilterElement;
import com.vaolan.sspserver.model.AdvPlan;

/**
 * AD帐号过滤:ad过滤 Date: 2014年6月16日 <br>
 * 
 * @author chenjinzhao
 * */
@Service
public class UserFilter {

	public boolean doFilter(AdvPlan advPlan, AdFilterElement adFilterElement) {
		//TODO:******重要，测试使用，删除*******
//		return true;
		
		return this.adAcctFilter(advPlan, adFilterElement)
				&& this.ipPosTargetFilter(advPlan, adFilterElement)
				&& this.ipNegTargetFilter(advPlan, adFilterElement);
	}

	/**
	 * ad 帐号定投放，不设置默认是 true
	 * 
	 * @param advPlan
	 * @param adFilterElement
	 * @return
	 */
	public boolean adAcctFilter(AdvPlan advPlan, AdFilterElement adFilterElement) {
		boolean flag = true;
		if (advPlan.isAdAcctTargetFilter()) {
			if (StringUtil.isNotBlank(adFilterElement.getAdAcct())) {

				flag = advPlan.getAdAcctTargetFilters().contains(
						adFilterElement.getAdAcct()) ? true : false;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * ip 定投，只投放指定的ip，不设置默认是 true
	 * 
	 * @param advPlan
	 * @param adFilterElement
	 * @return
	 */
	public boolean ipPosTargetFilter(AdvPlan advPlan,
			AdFilterElement adFilterElement) {

		boolean flag = true;
		if (advPlan.getIsPosIpTargetFilter()) {
			if (StringUtil.isNotBlank(adFilterElement.getIp())) {
				flag = advPlan.getIpPosTargetFilters().contains(
						adFilterElement.getIp()) ? true : false;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * ip 定投，不投给指定的ip，不设置默认是true
	 * 
	 * @param advPlan
	 * @param adFilterElement
	 * @return
	 */
	public boolean ipNegTargetFilter(AdvPlan advPlan,
			AdFilterElement adFilterElement) {

		boolean flag = true;
		if (advPlan.getIsNegIpTargetFilter()) {
			if (StringUtil.isNotBlank(adFilterElement.getIp())) {
				flag = advPlan.getIpNegTargetFilters().contains(
						adFilterElement.getIp()) ? false : true;
			} else {
				flag = false;
			}
		}
		return flag;

	}

}
