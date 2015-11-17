package com.hidata.ad.web.model;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 终端广告相关表
 * @author xiaoming
 *
 */
@Table("ad_t_link")
public class AdTerminalLink implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column("id")
	private String id;
	@Column("ad_id")
	private String adId;
	@Column("t_id")
	private String tId;
	@Column("t_value")
	private String tValue;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAdId() {
		return adId;
	}
	public void setAdId(String adId) {
		this.adId = adId;
	}
	public String gettId() {
		return tId;
	}
	public void settId(String tId) {
		this.tId = tId;
	}
	public String gettValue() {
		return tValue;
	}
	public void settValue(String tValue) {
		this.tValue = tValue;
	}
}
