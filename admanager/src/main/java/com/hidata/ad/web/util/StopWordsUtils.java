package com.hidata.ad.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 停用词工具类，验证一个词是不是停用词
 * @author ZTD
 *
 */
public class StopWordsUtils {
	private static Logger logger = Logger.getLogger(StopWordsUtils.class);
	private static Map<String, String> stopWrodsMap = new HashMap<String, String>();
	
	// 初始化停用词
	static{
//		E:/eclipse-ee-projects/sspserver/src/main/webapp/WEB-INF/classes/
		String projectPath = PathUtils.getRootPath();
		init(projectPath + "stopword.txt");
	}
	
	public static void init(String filePath) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(new File(filePath)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		String keyValueStr = null;
		try {
			while ((keyValueStr = reader.readLine()) != null) {
				String trimStr = keyValueStr.trim();
				// 如果字符串长度小于等于 0 则跳过
				if(trimStr.length() <= 0) {
					continue;
				}
				// 如果字符串以 # 开头也跳过
				if(trimStr.startsWith("#")) {
					continue;
				}
				// 如果两者都不是就开始处理
				stopWrodsMap.put(new String(trimStr.getBytes("GBK")), "");
			}
		} catch (Exception e) {
			logger.error("File : " + filePath + " is not found.");
			logger.error(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}
	
	/**
	 * 查看当前词是不是停用词
	 * @param key
	 * @return
	 */
	public static boolean containsKey(String key) {
		return stopWrodsMap.containsKey(key);
	}
}
