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

public class AdTimerOhterDriver {

	public static void main(String[] args) {

		Timer timer = new Timer();

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		PVCountCollectionTimerTask pVCountCollectionTask = (PVCountCollectionTimerTask) ctx
				.getBean("PVCountCollectionTimerTask");

		RtIpUv2MainKeyTimerTask rtIpUv2MainKeyTimerTask = (RtIpUv2MainKeyTimerTask) ctx
				.getBean("rtIpUv2MainKeyTimerTask");

		// 每隔1分钟，把每支广告的当天pv量给adtarget
		timer.scheduleAtFixedRate(pVCountCollectionTask, 10000,
				Integer.parseInt(Config.getProperty("pvcount_sync_time")));
		
		// 实时计算广告的ip数量，uv数量，到广告信息统计中
		timer.scheduleAtFixedRate(rtIpUv2MainKeyTimerTask, 5000, 1000);

	}

}
