package com.hidata.ad.web.session;

import com.hidata.ad.web.model.User;

public class SessionContainer {
	
	private static ThreadLocal<User> sessionLocal = new ThreadLocal<User>();// 私有静态变量

	public static User getSession() {
		return sessionLocal.get();
	}

	public static void setSession(User User) {
		sessionLocal.set(User);
	}
	
	public static void destoryed(){
		sessionLocal.remove();
	}
}
