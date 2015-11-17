package com.vaolan.adtimer.tdriver;

import java.util.Timer;

import javax.annotation.Resource;
import javax.annotation.Resources;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hidata.framework.util.DateUtil;
import com.vaolan.adtimer.task.DayAdStat2MysqlTimerTask;
import com.vaolan.adtimer.task.HourAdStat2MysqlTimerTask;
import com.vaolan.adtimer.task.PVCountCollectionTimerTask;
import com.vaolan.adtimer.task.RtAdStat2MysqlTimerTask;
import com.vaolan.adtimer.task.RtIpUv2MainKeyTimerTask;
import com.vaolan.adtimer.util.Config;
import com.vaolan.adtimer.util.TimerUtil;

public class AdTimerHourDriver {

	public static void main(String[] args) {

		Timer timer = new Timer();

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");


		HourAdStat2MysqlTimerTask hourAdStat2MysqlTimerTask = (HourAdStat2MysqlTimerTask) ctx
				.getBean("hourAdStat2MysqlTimerTask");

		
		// 每隔一个小时，把广告统计的hour信息清零，重新计算,第一次开始时间为下一个小时
		timer.scheduleAtFixedRate(
				hourAdStat2MysqlTimerTask,
				DateUtil.parseDateTime(TimerUtil.getNextHourBeginDateTimeStr()),
				60 * 60 * 1000);

		

	}

}
