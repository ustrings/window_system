package com.hidata.ad.web.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hidata.ad.web.service.IIntrestsCrowdService;

/**
 * 定时执行人群总数更新任务 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年8月30日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Component
public class CrowdNumSumScheduleJob {

    private static Logger logger = LoggerFactory.getLogger(CrowdNumSumScheduleJob.class);

    @Autowired
    private IIntrestsCrowdService iIntrestsCrowdService;

//    @Scheduled(cron = ("${cronExpress}"))
    public void executeUpdateCrowdSum() {
    	// 获取昨天日期
    	String date = new SimpleDateFormat("yyyyMMdd").format(new Date(new Date().getTime() - 60*60*24*1000L));
        iIntrestsCrowdService.fixExecuteUpdateCrowdNum(date);
        System.out.println("更新人群总数任务正在执行。。。");
    }
    
    public int executeUpdateCrowdSumByHand(String date) {
    	try {
    		iIntrestsCrowdService.fixExecuteUpdateCrowdNum(date);
    	} catch(Exception e) {
    		e.printStackTrace();
    		return -1;
    	}
        System.out.println("更新人群总数任务正在执行。。。");
        return 1;
    }
}
