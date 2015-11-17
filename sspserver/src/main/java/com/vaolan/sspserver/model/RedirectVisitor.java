package com.vaolan.sspserver.model;
/**
 * 重定向用户信息<br>
 * 继承Comparable接口，支持排序，按照mappingTime倒排
 *
 */
public class RedirectVisitor implements Comparable<RedirectVisitor>
{
	//用户是否激活的
	private boolean isActive;
	
	//用户映射时间
	private long mappingTime;
	
	//人群Id
	private String crowId;

	@Override
	public int compareTo(RedirectVisitor o) {
		if(this.getMappingTime() !=  o.getMappingTime()){
			return this.getMappingTime()<o.getMappingTime()?1:-1;
		}else{
			return 0;
		}
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public long getMappingTime() {
		return mappingTime;
	}

	public void setMappingTime(long mappingTime) {
		this.mappingTime = mappingTime;
	}

	public String getCrowId() {
		return crowId;
	}

	public void setCrowId(String crowId) {
		this.crowId = crowId;
	}

}
