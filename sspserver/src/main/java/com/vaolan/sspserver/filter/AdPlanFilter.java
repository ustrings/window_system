package com.vaolan.sspserver.filter;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.vaolan.sspserver.model.AdFilterElement;
import com.vaolan.sspserver.model.AdTimeFilter;
import com.vaolan.sspserver.model.AdvPlan;
import com.vaolan.sspserver.timer.DBInfoFresh;

/**
 * 从广告计划的角度 进行过滤 Date: 2014年6月16日 <br>
 * 
 * @author pj
 * */
@Service
public class AdPlanFilter {

	private static Logger logger = Logger.getLogger(AdPlanFilter.class);

	@Resource(name = "dbinfo")
	private DBInfoFresh advInfo;// 所有广告计划信息

	/**
	 * 过滤当前的广告计划
	 * @param advPlan
	 * @param adFilterElement
	 * @return
	 */
	public boolean doFilter(AdvPlan advPlan,AdFilterElement adFilterElement) {
		return isBeyondDayLimit(advPlan) && putAdTimeFilter(advPlan) && isBeyondMinLimit(advPlan,adFilterElement) && isAdType(advPlan,adFilterElement);
	}

	/**
	 * 判断现在是否是在广告的投放时间段内<br>
	 * 
	 * @param advPlan
	 * @return true 在投放时间内 false 在投放时间段外
	 * */
	private boolean putAdTimeFilter(AdvPlan advPlan) {
		boolean flag = false;
		// 是不是设置了投放时间？
		// 1.果设置了投放时间则判断是不是在投放时间之内
		// 2.没有设置投放时间则不对投放时间进行控制
		if (advPlan.isPutTimeFilter()) {
			Calendar calendar = Calendar.getInstance();
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
			List<AdTimeFilter> adPutTimes = advPlan.getAdTimeFilters();
			for (AdTimeFilter adPutTime : adPutTimes) {
				if (adPutTime.getDaysOfWeek().contains(dayOfWeek + "")) {
					if (adPutTime.getStartHour() <= hourOfDay
							&& adPutTime.getEndHour() >= hourOfDay) {
						flag = true;
						break;
					}
				}
			}
		} else {
			flag = true;// 如不需要时间段控制，说明可任意时间投放
		}
		return flag;
	}

	/**
	 * 1.是否达到每日的pv展示次数
	 * 
	 * @param advPlan
	 * @return
	 */
	public boolean isBeyondDayLimit(AdvPlan advPlan) {
		// 获取当天广告的投放信息
		long pvCount = advInfo.getAdvWareHouse().getPvCount(advPlan.getAdvId());
		advPlan.setPvNumOneDay(pvCount);
		return (advPlan.getDayLimit() > pvCount) ? true : false;
	}

	/**
	 * 判断是否达到每分钟的pv展示次数,true的话还没达到，可以继续投放，flase，已经达到不再投放
	 * 
	 * @param advPlan
	 * @return
	 */
	public boolean isBeyondMinLimit(AdvPlan advPlan,
			AdFilterElement adFilterElement) {
		boolean flag = true;

		int minLimitInt = 0;
		// 每分钟投放量限制
		String minLimitStr = advPlan.getAdTimeFrequency().getMinuteLimit();
		if (StringUtils.isNotBlank(minLimitStr)) {
			minLimitInt = Integer.parseInt(minLimitStr);
		}
		// 均匀投放（每分钟投放量限制的前提是必须是均匀投放）
		// 如果存在每分钟投放量的限制，并且是均匀投放的则判断每分钟的投放数量是不是已经达到
		// 否则不控制投放量
		if (adFilterElement.getAdMinNumMap() != null
				&& "1".equals(advPlan.getAdTimeFrequency().getIsUniform())) {
			String minPvNumStr = adFilterElement.getAdMinNumMap().get(
					"adId_" + advPlan.getAdInstance().getAdId());

			if (StringUtils.isNotBlank(minPvNumStr)) {
				int minPvNumInt = Integer.parseInt(minPvNumStr);
				if (minPvNumInt >= minLimitInt) {
					flag = false;
				}
			}
		}
		return flag;
	}
	
	
	/**
	 * 判断一只广告是否特定的广告类型(PC弹窗，Moible弹窗)
	 * @param advPlan
	 * @param adFilterElement
	 * @return
	 */
	public boolean isAdType(AdvPlan advPlan,
			AdFilterElement adFilterElement) {
		boolean flag;

		String adSrcType = advPlan.getAdInstance().getAdType();
		
		String adNeedType = adFilterElement.getAdType();
		
		//如果传过来的类型不为空，并且和广告所属的类型吻合，就返回true
		if(adNeedType!=null && !"".equals(adNeedType) && adSrcType.equals(adNeedType)){
			flag = true;
		}else{
			flag = false;
		}
		
		return flag;
	}
}
