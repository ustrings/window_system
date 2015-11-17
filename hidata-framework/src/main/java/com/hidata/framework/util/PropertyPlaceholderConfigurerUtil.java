package com.hidata.framework.util;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


public class PropertyPlaceholderConfigurerUtil extends PropertyPlaceholderConfigurer {

	@Override
	protected String resolvePlaceholder(String placeholder, Properties props) {
		String result = super.resolvePlaceholder(placeholder, props);
		if (placeholder.indexOf("password") > 0){
			try {
				result = DesUtil.decrypt(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
