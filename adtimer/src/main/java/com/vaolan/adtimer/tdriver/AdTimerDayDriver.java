package com.vaolan.adtimer.tdriver;

import java.util.Timer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hidata.framework.util.DateUtil;
import com.vaolan.adtimer.task.DayAdStat2MysqlTimerTask;
import com.vaolan.adtimer.util.TimerUtil;

public class AdTimerDayDriver {

	public static void main(String[] args) {

		Timer timer = new Timer();

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		
		DayAdStat2MysqlTimerTask dayAdStat2MysqlTimerTask = (DayAdStat2MysqlTimerTask) ctx
				.getBean("dayAdStat2MysqlTimerTask");

		

		// 每隔一天，把广告的today的信息清零，重新计算，第一次开始时间为下一天
		timer.scheduleAtFixedRate(dayAdStat2MysqlTimerTask,
				DateUtil.parseDateTime(TimerUtil.getNextDayBeginDateTimeStr15S()),
				24 * 60 * 60 * 1000);

		

	}

}
