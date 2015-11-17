package com.vaolan.adtimer.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hidata.framework.util.DateUtil;

/**
 * Helper class which read resource boundle
 */
public class Config {

    private static Properties resourceBundle;

    public static HashMap<String, String> IconMap = new HashMap<String, String>();
    private static Logger logger = LoggerFactory.getLogger(Config.class);
    static {
        try {
            InputStream inStream = Config.class.getClassLoader().getResourceAsStream("config.properties") ;
            resourceBundle = new Properties();
            resourceBundle.load(inStream);

        } catch (Exception x) {
            x.printStackTrace();
            logger.error(x.getMessage());
        }
    }

    /**
     * Returns the string from the plugin's resource bundle,
     * or 'key' if not found.
     */
    public static String getProperty(String key) {
    	return resourceBundle.getProperty(key);
    }
    public static void main(String[] args) {
    	System.out.println(Config.getProperty("stat_rt_sync_time"));
    	
    	System.out.println(TimerUtil.getNextMin45Str());
    	
    	Timer timer = new Timer();
    	
    	timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				
				System.out.println(DateUtil.getCurrentDateTimeStr());
				
				
				long s = System.currentTimeMillis();
				
			    try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				
			    long e = System.currentTimeMillis();
			    
			    
				System.out.println("用时: "+(e-s));
				
			}
		}, DateUtil.parseDateTime(TimerUtil.getNextMin45Str()), 60000);
	}

}