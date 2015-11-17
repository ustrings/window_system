package com.vaolan.sspserver.model;

/**
 * 排序的实体
 * @author ZTD
 *
 */
public class AdSort {
	private String adid;
	private int hitNum;
	
	public AdSort(String adid, int hitNum) {
		super();
		this.adid = adid;
		this.hitNum = hitNum;
	}
	public String getAdid() {
		return adid;
	}
	public void setAdid(String adid) {
		this.adid = adid;
	}
	public int getHitNum() {
		return hitNum;
	}
	public void setHitNum(int hitNum) {
		this.hitNum = hitNum;
	}
	
	public void increase() {
		this.hitNum ++;
	}
}
