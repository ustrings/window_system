package com.project.dsp.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringApplicationContext implements ApplicationContextAware {

	private static ApplicationContext appContext;

	private SpringApplicationContext() {
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		appContext = applicationContext;
	}

	public static ApplicationContext getAppContext() {
		return appContext;
	}

	public static Object getBean(String beanName) {
		return appContext.getBean(beanName);
	}
}
