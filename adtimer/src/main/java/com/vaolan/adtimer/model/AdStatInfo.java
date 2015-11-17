package com.vaolan.adtimer.model;



/**
 * 一个广告的所有统计信息
 * @author chenjinzhao
 *
 */
public class AdStatInfo {
	
	//广告id
	
	private String adId;
	
	//物料id
	private String materialId;
	
	//总共的pv量
	private String total_pv_num;
	
	//总共的点击量
	private String total_click_num;
	
	//今天的pv量
	private String today_pv_num;
	
	
	//今天的点击量
	private String today_click_num;
	
    //今天的uv量
	private String today_uv_num;
	
	//今天的ip量
	private String today_ip_num;
	
	
	//今天移动端的pv量
	private String today_mobile_pv_num;
	
	//今天移动端的点击量
	private String today_mobile_click_num;
	
	//当前小时的pv量
	private String currentHour_pv_num;
	
	//当前小时的点击量
	private String currentHour_click_num;
	
	//当前小时的uv量
	private String currentHour_uv_num;
	
	//当前小时的ip量
	private String currentHour_ip_num;
	
	//当前小时移动端的pv量
	private String currentHour_mobile_pv_num;
	
	//当前小时移动端的点击量
	private String currentHour_mobile_click_num;
	
	
	//当天点击关闭按钮的量
	private String today_close_num;
		
	//当前小时点击关闭按钮的量
	private String currentHour_close_num;
	
	
	
	public String getToday_close_num() {
		return today_close_num;
	}

	public void setToday_close_num(String today_close_num) {
		this.today_close_num = today_close_num;
	}

	public String getCurrentHour_close_num() {
		return currentHour_close_num;
	}

	public void setCurrentHour_close_num(String currentHour_close_num) {
		this.currentHour_close_num = currentHour_close_num;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getTotal_pv_num() {
		return total_pv_num;
	}

	public void setTotal_pv_num(String total_pv_num) {
		this.total_pv_num = total_pv_num;
	}

	public String getTotal_click_num() {
		return total_click_num;
	}

	public void setTotal_click_num(String total_click_num) {
		this.total_click_num = total_click_num;
	}

	public String getToday_pv_num() {
		return today_pv_num;
	}

	public void setToday_pv_num(String today_pv_num) {
		this.today_pv_num = today_pv_num;
	}

	public String getToday_click_num() {
		return today_click_num;
	}

	public void setToday_click_num(String today_click_num) {
		this.today_click_num = today_click_num;
	}

	public String getToday_uv_num() {
		return today_uv_num;
	}

	public void setToday_uv_num(String today_uv_num) {
		this.today_uv_num = today_uv_num;
	}

	public String getToday_ip_num() {
		return today_ip_num;
	}

	public void setToday_ip_num(String today_ip_num) {
		this.today_ip_num = today_ip_num;
	}

	public String getToday_mobile_pv_num() {
		return today_mobile_pv_num;
	}

	public void setToday_mobile_pv_num(String today_mobile_pv_num) {
		this.today_mobile_pv_num = today_mobile_pv_num;
	}

	public String getToday_mobile_click_num() {
		return today_mobile_click_num;
	}

	public void setToday_mobile_click_num(String today_mobile_click_num) {
		this.today_mobile_click_num = today_mobile_click_num;
	}

	public String getCurrentHour_pv_num() {
		return currentHour_pv_num;
	}

	public void setCurrentHour_pv_num(String currentHour_pv_num) {
		this.currentHour_pv_num = currentHour_pv_num;
	}

	public String getCurrentHour_click_num() {
		return currentHour_click_num;
	}

	public void setCurrentHour_click_num(String currentHour_click_num) {
		this.currentHour_click_num = currentHour_click_num;
	}

	public String getCurrentHour_uv_num() {
		return currentHour_uv_num;
	}

	public void setCurrentHour_uv_num(String currentHour_uv_num) {
		this.currentHour_uv_num = currentHour_uv_num;
	}

	public String getCurrentHour_ip_num() {
		return currentHour_ip_num;
	}

	public void setCurrentHour_ip_num(String currentHour_ip_num) {
		this.currentHour_ip_num = currentHour_ip_num;
	}

	public String getCurrentHour_mobile_pv_num() {
		return currentHour_mobile_pv_num;
	}

	public void setCurrentHour_mobile_pv_num(String currentHour_mobile_pv_num) {
		this.currentHour_mobile_pv_num = currentHour_mobile_pv_num;
	}

	public String getCurrentHour_mobile_click_num() {
		return currentHour_mobile_click_num;
	}

	public void setCurrentHour_mobile_click_num(String currentHour_mobile_click_num) {
		this.currentHour_mobile_click_num = currentHour_mobile_click_num;
	}
	
	
	public String toString(){
		StringBuffer statInfo = new StringBuffer("");
		statInfo.append("[adId="+this.getAdId()+",");
		statInfo.append("materialId="+this.getMaterialId()+",");
		statInfo.append("total_pv_num="+this.getTotal_pv_num()+",");
		statInfo.append("total_click_num="+this.getTotal_click_num()+",");
		statInfo.append("today_pv_num="+this.getToday_pv_num()+",");
		statInfo.append("today_uv_num="+this.getToday_uv_num()+",");
		statInfo.append("today_ip_num="+this.getToday_ip_num()+",");
		statInfo.append("today_pv_num="+this.getToday_mobile_pv_num()+",");
		statInfo.append("today_click_num="+this.getToday_click_num()+",");
		statInfo.append("currentHour_click_num="+this.getCurrentHour_click_num()+",");
		statInfo.append("currentHour_ip_num="+this.getCurrentHour_ip_num()+",");
		statInfo.append("currentHour_mobile_click_num="+this.getCurrentHour_mobile_click_num()+",");
		statInfo.append("currentHour_mobile_pv_num="+this.getCurrentHour_mobile_pv_num()+",");
		statInfo.append("currentHour_pv_num="+this.getCurrentHour_pv_num()+",");
		statInfo.append("currentHour_close_num="+this.currentHour_close_num + ",");
		statInfo.append("today_close_num=" + this.today_close_num+",");
		statInfo.append("currentHour_uv_num="+this.getCurrentHour_uv_num()+"]");
		return statInfo.toString();
	}
	
}
