package com.hidata.framework.sso;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.hidata.framework.log.LogManager;
import com.hidata.framework.sso.model.LetvUser;

public class JSONParser {

	private static LogManager logger = LogManager.getLogger(SsoEngine.class);
	
	private static String LOG_KEY = "JSONParser";
	
	/**
	 * 解析json串
	 * @param content
	 * @return
	 */
	public static LetvUser parse(String content){
		LetvUser user = null;
		try {
			JSONObject object = JSONObject.fromObject(content);
			if(null != object){
				//将数据写入cache
				JSONObject uinfo = object.getJSONObject("bean");
				if(null != uinfo){
					user = new LetvUser();
					user.setUserid(uinfo.getInt("ssouid"));
					user.setUsername(uinfo.getString("username"));
				}
			} 
		} catch (JSONException e) {
			logger.error(LOG_KEY, "json data error : " + e.getMessage());
		}
		return user;
	}
	
	/**
	 * 获取过期时间
	 * @param content
	 * @return 过期时间（单位 s）
	 */
	public static String getExpireTime(String content){
		String expireTime = null;
		try {
			JSONObject object = JSONObject.fromObject(content);
			if(null != object){
				//将数据写入cache
				expireTime = object.getString("expire");
			} 
		} catch (JSONException e) {
			logger.error(LOG_KEY, "json data error : " + e.getMessage());
		}
		return expireTime;
	}
}
