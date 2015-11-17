package com.vaolan.sspserver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class HttpUtil {
	private static int QUERY_FROM_URL_ERROR_NUM = 0;
	private static Logger logger = Logger.getLogger(HttpUtil.class);
	
	/**
	 * 解析 json 数据
	 * @param content 要解析内容
	 * @param resultType 解析的类型
	 * @return
	 */
	private static List<String> convertJson(String content, DataQueryType queryType) {
		JSONObject jsObj = JSONObject.fromObject(content);
		List<String> result = null;
		String msg = jsObj.getString("msg");
		
		if(!msg.equals("ok")) {
			return result;
		}
		
		JSONArray resultArray = null;
		result = new ArrayList<String>();
		switch (queryType) {
		case KEYWORD:
			// 判断是不存在 search.kw
			if(jsObj.containsKey("search.kw")) {
				resultArray = jsObj.getJSONArray("search.kw");
				assembleValue(null,result, resultArray);
			}
			break;
		case LABEL:
			// 判断是不存在 host
			if(jsObj.containsKey("host")) {
				resultArray = jsObj.getJSONArray("host");
				assembleValue("host", result, resultArray);
//				for(Object obj : resultArray) {
//					logger.info("host result : " + obj.toString());
//				}
			}
			// 判断是不存在 interest
			if(jsObj.containsKey("interest")) {
				resultArray = jsObj.getJSONArray("interest");
				assembleValue("interest", result, resultArray);
//				for(Object obj : resultArray) {
//					logger.info("interest result : " + obj.toString());
//				}
			}

			// 判断是不存在 basic
			if(jsObj.containsKey("basic")) {
				resultArray = jsObj.getJSONArray("basic");
				assembleValue("basic", result, resultArray);
			}

			// 判断是不存在 pay
			if(jsObj.containsKey("pay")) {
				resultArray = jsObj.getJSONArray("pay");
				assembleValue("pay", result, resultArray);
			}
			break;
		default:
			break;
		}
		
		return result;
	}
	
	
	public static void assembleValue(String prefix, List<String> targetList, JSONArray jsonArray) {
		for(Object obj : jsonArray) {
			if(obj!=null) {
				if(prefix != null) {
					targetList.add(prefix + "/" + obj.toString());
				} else {
					targetList.add(obj.toString());
				}
			}
		}
	}
	
	/**
	 * 从DMP 查询用户对应的数据信息
	 * @param userid
	 * @param resultType
	 * @return
	 */
	public static List<String> getResultFromDMPService(String userid, DataQueryType queryType) {
		logger.info("query userid : " + userid);
		String url = Constant.KEYWORD_LABLE_QUERY_URL + "&adua=" + userid;
		List<String> resultList = null;
		String resultStr = null;
		try {
			resultStr = getResultFromUrl(url);
//			resultStr = "{\"status\": \"0\", \"msg\": \"ok\", \"basic\": [\"年龄/35~49岁/0\", \"性别/女/0\"],\"pay\": [\"电商付费/淘宝/1\", \"性别/女/0\"], \"uid\": \"00004de92c0ff06d948233e171cea2c6\", \"ad\": \"sec814_cmMteQPqSrEeb8LttVWi0xZJq\"}";
		} catch (Exception e) {
			QUERY_FROM_URL_ERROR_NUM ++;
			logger.error(e);
			logger.info("数据查询出错 : url：" + url);
			logger.info("数据查询出错 : 当前查询出错总数：" + QUERY_FROM_URL_ERROR_NUM);
			try {
				resultStr = getResultFromUrl(url);
			} catch (Exception e1) {
				QUERY_FROM_URL_ERROR_NUM ++;
				logger.error( e1);
				logger.info("数据查询出错 : url：" + url);
				logger.info("再次数据查询出错 : 当前查询出错总数：" + QUERY_FROM_URL_ERROR_NUM);
			}
		}
		if(!StringUtils.isEmpty(resultStr)) {
			resultList = convertJson(resultStr, queryType);
		}
		return resultList;
	}
	
	
	/**
	 * 从 url 获取查询结果
	 * @param url
	 * @return
	 * @throws Exception 
	 * @throws Exception 
	 */
	private static String getResultFromUrl(String url) throws Exception  {
		StringBuffer sb = new StringBuffer();
		URL _url = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) _url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.connect();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(connection.getInputStream(),"utf-8"));
		String line = null;
		while((line = reader.readLine())!=null) {
			sb.append(line);
		}
		reader.close();
		connection.disconnect();

		return sb.toString();
	}
	
	/**
	 * 把 unicode 转换为 utf-8
	 * @param utfString
	 * @return
	 */
	public static String convert(String utfString){
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;
		
		while((i=utfString.indexOf("\\u", pos)) != -1){
			sb.append(utfString.substring(pos, i));
			if(i+5 < utfString.length()){
				pos = i+6;
				sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));
			}
		}
		
		return sb.toString();
	}

	public static String accessURLByGetMethod(String urls) {
		URL url;
		String line = "";
		try {
			url = new URL(urls);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(),"utf-8"));
			line = reader.readLine();
			reader.close();
			connection.disconnect();
		} catch (Exception e) {
			logger.error("url访问异常：" + urls, e);
		}
		return line;
	}
	
	/**
	 * 通过HTTP方式获取远程 txt 文件内容
	 * 
	 * @param strUrl
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private static String getRemoteFileContent(String strUrl)
			throws IOException {
		StringBuffer sb = new StringBuffer();
		URL url = new URL(strUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		InputStreamReader isr = new InputStreamReader(conn.getInputStream());
		BufferedReader reader = new BufferedReader(isr);
		String line = null;
		while ((line = reader.readLine())!=null) {
			if(line.trim().length()<=0) {
				continue;
			}
			sb.append(line).append("\n");
			System.out.println(line);
		}
		isr.close();
		reader.close();
		return sb.toString();
	}

}
