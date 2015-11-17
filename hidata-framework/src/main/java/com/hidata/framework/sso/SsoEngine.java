package com.hidata.framework.sso;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.hidata.framework.cache.CacheManager;
import com.hidata.framework.log.LogManager;
import com.hidata.framework.sso.model.LetvUser;
import com.hidata.framework.util.CookieManager;
import com.hidata.framework.util.http.HttpRequester;
import com.hidata.framework.util.http.HttpResponse;

/**
 * sso 引擎，提供letv 大登陆sso的相关接口
 * @author houzhaowei
 *
 */
@Component
public class SsoEngine {
	
	//cookie 中存有token 的key
	private static final String TOKEN  = "sso_tk";
	private static final String NICK_NAME  = "sso_nickname";
	
	//token 验证
	private static final String VALIDATE_URL = "http://api.sso.letv.com/api/checkTicket/tk/";
	
	//处理http请求的对象
	private static HttpRequester requester = new HttpRequester();
	
	private static LogManager logger = LogManager.getLogger(SsoEngine.class);
	
	private static String LOG_KEY = "SsoEngine";
	
	
	private SsoEngine(){}
	
	/**
	 * 获取cookie中的sso_tk 的值
	 * @param request
	 * @return sso_tk 的值
	 */
	public String getTkCookie(HttpServletRequest request){
		return CookieManager.getValueByName(request, TOKEN);
	}
	
	/**
	 * 获取cookie中的nickname
	 * @param request
	 * @return
	 */
	public String getNickNameFromCookie(HttpServletRequest request){
		return CookieManager.getValueByName(request, NICK_NAME);
	}
	
	
	/**
	 * 验票并获取用户信息
	 * @param needExpire
	 * @param needUserName
	 * @return
	 */
	public LetvUser getUserInfo(String tk,boolean needExpire ,boolean needUserName){
		if(null == tk || tk.equals("")){
			logger.error(LOG_KEY, "invalide token");
			return null;
		}
		
		//先从cache 取值，如果有，则直接返回
		String userJson = this.getUserInfo(tk);
		if(userJson != null){
			return JSONParser.parse(userJson);
		}
		
		//若cache没有值，则通过http接口取值，并写入cache
		LetvUser user = null;
		HttpResponse response = null;

		String url = VALIDATE_URL + tk;
		
		//如果需要获得超时时间
		if(needExpire){
			url += "/need_expire/1";
		}
		
		//如果需要获取用户名
		if(needUserName){
			url += "?all=1";
		}
		
		try {
			response = requester.sendGet(url);
		} catch (IOException e) {
			logger.error(LOG_KEY,"send http request to 'sso api' exception : " + e.getMessage());
		}
		
		//获取返回值
		String content = response.getContent();
		//获取letv user 对象
		user = JSONParser.parse(content);
		//获取超时时间
		String expireTime = JSONParser.getExpireTime(content);
		//写入cache
		if(this.writeToCache(tk, content,expireTime)){
			logger.info(LOG_KEY, "write user info to cache ok, user : " + content);
		}
		
		return user;
	}
	
	//写入cache
	private boolean writeToCache(String tk,String content,String sExpireTime){
		int expireTime = Integer.parseInt(sExpireTime);
		//默认超时时间缩短60秒
		if (expireTime >  10){
			expireTime = expireTime - 10;
		} else {
			logger.info(LOG_KEY, "expire time < 10s , will not cache it ! ");
			return true;
		}
		if(CacheManager.set(tk,expireTime,content)){
			return true;
		}
		return false;
	}
	
	//从cache获取信息
	private String getUserInfo(String tk){
		Object result = CacheManager.get(tk);
		if (null != result){
			return result.toString();
		}
		return null;
	}
	
}
