package com.vaolan.ckserver.util;

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
    	System.out.println(Config.getProperty("cookie_expire_time"));
    	System.out.print(com.ideal.encode.SelfBase64Test.getAd("123123123"));
	}

}