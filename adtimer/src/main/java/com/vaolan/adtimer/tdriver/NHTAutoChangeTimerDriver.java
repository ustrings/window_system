package com.vaolan.adtimer.tdriver;

import java.util.Timer;

import javax.annotation.Resource;
import javax.annotation.Resources;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hidata.framework.util.DateUtil;
import com.vaolan.adtimer.task.DayAdStat2MysqlTimerTask;
import com.vaolan.adtimer.task.HourAdStat2MysqlTimerTask;
import com.vaolan.adtimer.task.NHT2NotNHTTimerTask;
import com.vaolan.adtimer.task.NotNHT2NHTTimerTask;
import com.vaolan.adtimer.task.PVCountCollectionTimerTask;
import com.vaolan.adtimer.task.RtAdStat2MysqlTimerTask;
import com.vaolan.adtimer.task.RtIpUv2MainKeyTimerTask;
import com.vaolan.adtimer.util.Config;
import com.vaolan.adtimer.util.TimerUtil;

public class NHTAutoChangeTimerDriver {

	public static void main(String[] args) {
			
		
		Timer timer = new Timer();

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		NotNHT2NHTTimerTask notNHT2NHTTimerTask = (NotNHT2NHTTimerTask) ctx
				.getBean("notNHT2NHTTimerTask");
		
		
		NHT2NotNHTTimerTask NHT2NotNHTTimerTask = (NHT2NotNHTTimerTask) ctx
				.getBean("NHT2NotNHTTimerTask");

		
		

		// 没个十秒检测一次，看看nht流量是否达到预设值，然后变成非NHT流量
		timer.scheduleAtFixedRate(NHT2NotNHTTimerTask, 10000,
				Integer.parseInt(Config.getProperty("nht_cancel_sync_time")));

	
		// 每隔一天，把广告的NHT统计模式重新开始(如果有)，重新计算，第一次开始时间为下一天
		timer.scheduleAtFixedRate(notNHT2NHTTimerTask,
				DateUtil.parseDateTime(TimerUtil.getNextDayBeginDateTimeStr()),
				24 * 60 * 60 * 1000);


	}
	
	
		
}
