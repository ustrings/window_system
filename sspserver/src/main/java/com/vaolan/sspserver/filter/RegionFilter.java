package com.vaolan.sspserver.filter;

import org.springframework.stereotype.Service;

import com.hidata.framework.util.StringUtil;
import com.vaolan.sspserver.model.AdFilterElement;
import com.vaolan.sspserver.model.AdvPlan;

/**
 * 区域过滤 <br>
 * 
 * @author chenjinzhao
 * */
@Service
public class RegionFilter {

	public boolean doFilter(AdvPlan advPlan, AdFilterElement adFilterElement) {
		//TODO: *************测试：重要，上线必须修改************
//		return true;
		return this.regionFilter(advPlan, adFilterElement);
	}

	/**
	 * ad 帐号定投放
	 * 
	 * @param advPlan
	 * @param adFilterElement
	 * @return
	 */
	public boolean regionFilter(AdvPlan advPlan, AdFilterElement adFilterElement) {
		boolean flag = true;
		if (advPlan.isRegionTargetFilter()) {
			if (StringUtil.isNotBlank(adFilterElement.getRegion())) {

				flag = advPlan.getRegionTargetFilters().contains(adFilterElement.getRegion()) ? true
						: false;
			} else {
				flag = false;
			}
		}
		return flag;
	}
}
