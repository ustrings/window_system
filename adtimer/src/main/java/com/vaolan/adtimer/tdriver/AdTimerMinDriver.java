package com.vaolan.adtimer.tdriver;

import java.util.Timer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hidata.framework.util.DateUtil;
import com.vaolan.adtimer.task.RtAdStat2MysqlTimerTask;
import com.vaolan.adtimer.util.TimerUtil;

public class AdTimerMinDriver {

	public static void main(String[] args) {

		Timer timer = new Timer();

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		RtAdStat2MysqlTimerTask rtAdStat2MysqlTimerTask = (RtAdStat2MysqlTimerTask) ctx
				.getBean("rtAdStat2MysqlTimerTask");


		// 实时把redis中记录的广告统计信息同步到数据库，1分钟一次
		timer.scheduleAtFixedRate(rtAdStat2MysqlTimerTask,
				DateUtil.parseDateTime(TimerUtil.getNextMin30Str()),
				60000);


	}

}
