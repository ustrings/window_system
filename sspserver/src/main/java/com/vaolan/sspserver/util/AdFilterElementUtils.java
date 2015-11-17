package com.vaolan.sspserver.util;

import com.vaolan.sspserver.model.AdFilterElement;

public class AdFilterElementUtils {
	/**
	 * 根据 AdFilterElement 获取 md5 之后的 userid
	 * @param adFilterElement
	 * @return
	 */
	public static String getUserIdByMd5(AdFilterElement adFilterElement) {
		  String adAcct = adFilterElement.getAdAcct();
	        String userAgent = adFilterElement.getUserAgent();
	        String adAcct16 = JavaMd5Util.md5EncrypFirst16(adAcct);
	        String userAgent16 = JavaMd5Util.md5EncrypFirst16(userAgent);
	        // 生成用户ID
	        String userId = adAcct16 + userAgent16;
	        return userId.toLowerCase();
	}
	
	/**
	 * 根据 AdFilterElement 获取 没有经过 md5 之后的 userid
	 * @param adFilterElement
	 * @return
	 */
	public static String getUserIdWioutMd5(AdFilterElement adFilterElement) {
		String adAcct = adFilterElement.getAdAcct();
		String userAgent = adFilterElement.getUserAgent();
		return adAcct + userAgent;
	}
}
