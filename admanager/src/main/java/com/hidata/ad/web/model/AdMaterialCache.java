package com.hidata.ad.web.model;


import com.hidata.framework.annotation.db.Column;

public class AdMaterialCache {
	
	@Column("ad_m_id")
	private String ad_m_id;
	@Column("m_type")
	private String m_type;
	@Column("width")
	private String width;
	@Column("height")
	private String height;
	@Column("material_name")
	private String material_name;
	@Column("link_url")
	private String link_url;
	@Column("target_url")
	private String target_url;
	@Column("monitor_link")
	private String monitor_link;

	@Column("ad_3stat_code")
    private String ad_3stat_code;
	
	@Column("ad_3stat_code_sub")
    private String ad_3stat_code_sub;
	
	@Column("ad_3stat_code_temp")
    private String ad_3stat_code_temp;
	
	@Column("rich_text")
	private String richText;
	
	@Column("cover_flag")
	private String coverFlag;
	
	
	public String getCoverFlag() {
		return coverFlag;
	}

	public void setCoverFlag(String coverFlag) {
		this.coverFlag = coverFlag;
	}

	public String getAd_3stat_code_temp() {
		return ad_3stat_code_temp;
	}

	public void setAd_3stat_code_temp(String ad_3stat_code_temp) {
		this.ad_3stat_code_temp = ad_3stat_code_temp;
	}

	public String getAd_3stat_code_sub() {
		return ad_3stat_code_sub;
	}

	public void setAd_3stat_code_sub(String ad_3stat_code_sub) {
		this.ad_3stat_code_sub = ad_3stat_code_sub;
	}

	public String getAd_3stat_code() {
		return ad_3stat_code;
	}

	public void setAd_3stat_code(String ad_3stat_code) {
		this.ad_3stat_code = ad_3stat_code;
	}

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

	public String getMaterial_name() {
		return material_name;
	}

	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}

	public String getLink_url() {
		return link_url;
	}

	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}

	public String getTarget_url() {
		return target_url;
	}

	public void setTarget_url(String target_url) {
		this.target_url = target_url;
	}

	public String getMonitor_link() {
		return monitor_link;
	}

	public void setMonitor_link(String monitor_link) {
		this.monitor_link = monitor_link;
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

	public String getRichText() {
		return richText;
	}

	public void setRichText(String richText) {
		this.richText = richText;
	}
	
}
