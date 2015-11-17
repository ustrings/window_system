package com.hidata.framework.cache.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import redis.clients.jedis.JedisPoolConfig;

public class Configuration {

	private static Logger logger = Logger.getLogger(Configuration.class);
	//默认配置文件路径
	private static String CONFIG_FILE_LOCATION = "/redis.properties";
	private String host;
	private int port;
	private int maxActive;
	private int maxIdle;
	private boolean testOnBorrow;
	private int maxWait;
	private boolean testOnReturn;
	
	public JedisPoolConfig configure(){
		return this.loadPropertiesByLocation(Configuration.CONFIG_FILE_LOCATION);
	}
	private void initParam(Properties properties) throws Exception{
		try{
			this.host = properties.getProperty("host");
			this.port = Integer.parseInt(properties.getProperty("port"));
			this.maxActive = Integer.parseInt(properties.getProperty("maxActive"));
			this.maxIdle = Integer.parseInt(properties.getProperty("maxIdle"));
			this.maxWait = Integer.parseInt(properties.getProperty("maxWait"));
			this.testOnBorrow = "true".equals(properties.getProperty("testOnBorrow"));
			this.testOnReturn = "true".equals(properties.getProperty("testOnReturn"));
		}catch(Exception e){
			logger.error("redis pool parameter error:", e);
			throw e;
		}
	}
	/**
	 * 通过路径加载配置文件
	 * */
	private JedisPoolConfig loadPropertiesByLocation(String location){
		InputStream in = null;
		JedisPoolConfig jPoolConfg = null;
		try {
			jPoolConfg = new JedisPoolConfig();
			Properties properties = new Properties();
			in = this.getClass().getResourceAsStream(location);
			properties.load(in);
			initParam(properties);
			jPoolConfg.setMaxTotal(this.maxActive);
			jPoolConfg.setMaxIdle(this.maxIdle);
			jPoolConfg.setMaxWaitMillis(this.maxWait);
			jPoolConfg.setTestOnBorrow(this.testOnBorrow);
			jPoolConfg.setTestOnReturn(this.testOnReturn);
		} catch (Exception e) {
			logger.error("redis load propertie failed!", e);
		}finally{
			if(null != in){
				try {
					in.close();in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return jPoolConfg;
	}
	
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
	public static void main(String[] args) {
		
	}
}
