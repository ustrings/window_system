package com.vaolan.sspserver.model;

/**
 * 用来存储广告竞价请求所需信息
 * @author fushengli
 *
 */
public class AvailableAdv
{
	//广告ID
	private String advId;
	//是否人群匹配投放,默认不进行匹配投放
	private boolean isMatch =false;
	
	//回头客匹配投放
	private boolean isVisitorMatch = false;
	//dmp用户投放
	private boolean isDMPCrowdMatch = false;
	
	private void setMatch(boolean match){
		this.isMatch = match?true:this.isMatch;
	}
	/**
	 * 是否需要进行匹配投放
	 * @return true 匹配投放<p> false 盲投
	 * */
	public boolean isMatch(){
		return this.isMatch;
	}
	/**
	 * @return the advId
	 */
	public String getAdvId()
	{
		return advId;
	}

	/**
	 * @param advId the advId to set
	 */
	public void setAdvId(String advId)
	{
		this.advId = advId;
	}
	
	/**
	 * 是否按回头客投放<br>
	 * @return 是 true<p>否 false
	 * */
	public boolean isVisitorMatch() {
		return isVisitorMatch;
	}

	/**
	 * 设置是否按回头访客投放<br>
	 * @param isVisitorPut 按回头客投放 true<p> 否则 false
	 * */
	public void setVisitorMatch(boolean isVisitorMatch) {
		setMatch(isVisitorMatch);
		this.isVisitorMatch = isVisitorMatch;
	}
	/**
	 * 是否按DMP人群投放<br>
	 * @return 是 true<p>否 false
	 * */
	public boolean isDMPCrowdMatch() {
		return isDMPCrowdMatch;
	}
	
	/**
	 * 设置是否按DMP人群投放<br>
	 * @param isDMPCrowdMatch 按DMP人群投放 true<p> 否则 false
	 * */
	public void setDMPCrowdMatch(boolean isDMPCrowdMatch) {
		setMatch(isDMPCrowdMatch);
		this.isDMPCrowdMatch = isDMPCrowdMatch;
	}


}
