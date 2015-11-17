package com.vaolan.adserver.model;


import com.hidata.framework.annotation.db.Column;

public class MobileMaterial {
	
	@Column("ad_m_id")
	private String ad_m_id;
	@Column("m_type")
	private String m_type;
	@Column("width")
	private String width;
	@Column("height")
	private String height;
	@Column("click_id")
	private String click_id;
	@Column("target")
	private String target;
	@Column("link_url")
	private String link_url;
	
	public String getAd_m_id() {
		return ad_m_id;
	}
	public void setAd_m_id(String ad_m_id) {
		this.ad_m_id = ad_m_id;
	}
	public String getM_type() {
		return m_type;
	}
	public void setM_type(String m_type) {
		this.m_type = m_type;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getClick_id() {
		return click_id;
	}
	public void setClick_id(String click_id) {
		this.click_id = click_id;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getLink_url() {
		return link_url;
	}
	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}
	
	
	
	
	
	
}
