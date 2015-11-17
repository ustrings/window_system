package com.hidata.framework.util;

import nl.bitwalker.useragentutils.UserAgent;

public class UserAgentUtil {

	/**
	 * 通过userAgent判断设备是否是移动设备
	 * 
	 * @param userAgent
	 * @return
	 */

	// 从UA中区分出来的手机的
	public static String UA_DEVICE_TYPE_MOBILE = "MOBILE";

	// 从UA中区分出来的平板的
	public static String UA_DEVICE_TYPE_TABLET = "TABLET";

	// 从UA中区分出来的PC的
	public static String UA_DEVICE_TYPE_COMPUTER = "COMPUTER";

	public static boolean isMoveDevice(String userAgent) {
		boolean isMobile = false;
		UserAgent uaInfo = new UserAgent(userAgent);
		String deviceType = uaInfo.getOperatingSystem().getDeviceType()
				.toString();

		if (UA_DEVICE_TYPE_MOBILE.equals(deviceType)
				|| UA_DEVICE_TYPE_TABLET.equals(deviceType)) {
			isMobile = true;
		}
		return isMobile;
	}

}
