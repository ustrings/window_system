package com.vaolan.sspserver.model;

/**
 * 广告受众，通过cookie mapping获取该用户属于哪种用户
 * @author fushengli
 *
 */
public class AdvAudience
{
	//用户类别，属于哪个用户群
	private String[] audiencetype;
	
	//用户ID
	private String audienceId;

	//用户转化率
	private double transformWeight;
	
	//用户的广告曝光次数
	private long showNum;
}
