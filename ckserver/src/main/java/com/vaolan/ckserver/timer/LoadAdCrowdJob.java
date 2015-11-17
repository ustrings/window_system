package com.vaolan.ckserver.timer;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vaolan.ckserver.server.LoadAdCrowdService;

/**
 * 定时执行人群总数更新任务 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年8月30日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Component
public class LoadAdCrowdJob {

    private static Logger logger = LoggerFactory.getLogger(LoadAdCrowdJob.class);

    @Autowired
    private LoadAdCrowdService loadAdCrowdService;
    @PostConstruct
    public void initLoadAdCrowd(){
    	 logger.info("initLoadAdCrowd start");
         try {
 			loadAdCrowdService.loadAdCrowd();
 		} catch (Exception e) {
 			e.printStackTrace();
 			logger.error("initLoadAdCrowd exception");
 		}
         logger.info("loadAdCrowd end");
    }
    
    @Scheduled(cron = ("${loadAdCrowdExpress}"))
    public void loadAdCrowd() {
        logger.info("loadAdCrowd start");
        try {
			loadAdCrowdService.loadAdCrowd();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("loadAdCrowdJob exception");
		}
        logger.info("loadAdCrowd end");
    }
    
}
