package com.hidata.ad.web.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hidata.ad.web.model.User;
import com.hidata.ad.web.session.SessionContainer;

public class HttpServletRequestListener implements ServletRequestListener {

	public void requestDestroyed(ServletRequestEvent arg0) {
		SessionContainer.destoryed();
	}

	public void requestInitialized(ServletRequestEvent event) {
		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
		HttpSession session = request.getSession(true);
		User sessionUser = (User)session.getAttribute("sessionUser");
		if (sessionUser != null){
			SessionContainer.setSession(sessionUser);
		}
	}

}
