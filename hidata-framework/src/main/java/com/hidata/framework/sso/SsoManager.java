package com.hidata.framework.sso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hidata.framework.sso.model.LetvUser;
import com.hidata.framework.util.StringUtil;

@Component
public class SsoManager implements ISsoManager{
	
	@Autowired
	private SsoEngine engine;

	public LetvUser sign(String tk,HttpServletRequest request) {
		LetvUser user = null;
		//如果token 为空，则直接返回null
		if(null == tk ){
			return null;
		}
		//登陆比并获取用户信息
	    user = engine.getUserInfo(tk, true, true);
	    //将token写入user，便于验证
	    user.setTk(tk);
	    //写入nickname
	    String nickname = engine.getNickNameFromCookie(request);
	    //若没取到nickname 则用username
	    if(StringUtil.isEmpty(nickname)){
	    	user.setNickname(user.getUsername());
	    } else {
	    	try {
				user.setNickname(URLDecoder.decode(nickname,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				user.setNickname(user.getUsername());
				e.printStackTrace();
			}
	    }
		return user;
	}

}
