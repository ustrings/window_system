package com.hidata.ad.web.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    	
    	
	}

}