package com.vaolan.sspserver.util;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * config.properties 文件的工具类
 */
public class Config {

	/**
	 * 资源文件
	 */
	private static Properties resourceBundle;

	private static Logger logger = LoggerFactory.getLogger(Config.class);
	static {
		try {
			InputStream inStream = Config.class.getClassLoader()
					.getResourceAsStream("config.properties");
			resourceBundle = new Properties();
			resourceBundle.load(inStream);
		} catch (Exception x) {
			x.printStackTrace();
			logger.error(x.getMessage());
		}
	}

	/**
	 * 获取指定 key 对应的值
	 */
	public static String getProperty(String key) {
		return resourceBundle.getProperty(key);
	}

	public static void main(String[] args) {
		System.out.println(Config.getProperty("pv_collect_url"));

	}

}