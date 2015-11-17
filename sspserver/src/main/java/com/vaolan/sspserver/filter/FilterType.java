package com.vaolan.sspserver.filter;

/**
 * 过滤元素类型
 * @author ZTD
 *
 */
public enum FilterType {
	/**
	 * vaolan 盲投
	 */
	VAOLANMANGTOU,
	/**
	 * vaolan 的投放
	 */
	VAOLAN,
	/**
	 * url 定向
	 */
	URLTARGET,
	/**
	 * 账户定向
	 */
	ADACCTTARGET,
	/**
	 * ip 定向
	 */
	IPTARGETS,
	/**
	 * 域名定向
	 */
	HOSTTARGET,
	/**
	 * 关键词定向
	 */
	KEYWORD,
	/**
	 * 标签定向
	 */
	LABELTARGET
}
