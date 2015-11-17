package com.hidata.ad.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;


@Table("ad_device_link")
public class AdDeviceLinkDto {
	
	@Column("id")
	private String Id;
	
	@Column("ad_id")
	private String adId;
	
	@Column("ad_device_id")
	private String adDeviceId;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getAdDeviceId() {
		return adDeviceId;
	}

	public void setAdDeviceId(String adDeviceId) {
		this.adDeviceId = adDeviceId;
	}
	
	

}
