package com.hidata.ad.web.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 域名频道基础表
 * @author ssq
 *
 */
@Table("channel_base")
public class ChannelBase {

	@Column("id")
	private Integer id;
	
	@Column("channel_name")
	private String channelName;
	
	@Column("sts")
	private String sts;
	
	@Column("sts_date")
	private String stsDate;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getStsDate() {
		return stsDate;
	}

	public void setStsDate(String stsDate) {
		this.stsDate = stsDate;
	}
}
