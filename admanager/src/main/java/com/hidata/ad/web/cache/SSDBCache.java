package com.hidata.ad.web.cache;

import java.util.Set;


public interface SSDBCache {

	/**
	 * 获取adua
	 * @param tid
	 * @return
	 */
	public Set<String> getAdUaByTid(String tid);

}
