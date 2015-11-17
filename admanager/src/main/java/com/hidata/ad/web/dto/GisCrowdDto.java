package com.hidata.ad.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

@Table("gis_crowd")
public class GisCrowdDto {
	// 主键
	@Column("id")
	private String id;
	
	@Column("crowd_id")
	private String crowdId;
	
	@Column("x_position")
	private String xPosition;
	
	@Column("y_position")
	private String yPosition;
	
	@Column("distance_value")
	private String distanceValue;
	
	@Column("fetch_cicle")
	private String fetchCicle;
	
	@Column("centry_addr")
	private String centryAddr;
	
	@Column("sts_date")
	private String stsDate;
	
	@Column("sts")
	private String sts;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCrowdId() {
		return crowdId;
	}

	public void setCrowdId(String crowdId) {
		this.crowdId = crowdId;
	}

	public String getxPosition() {
		return xPosition;
	}

	public void setxPosition(String xPosition) {
		this.xPosition = xPosition;
	}

	public String getyPosition() {
		return yPosition;
	}

	public void setyPosition(String yPosition) {
		this.yPosition = yPosition;
	}

	public String getDistanceValue() {
		return distanceValue;
	}

	public void setDistanceValue(String distanceValue) {
		this.distanceValue = distanceValue;
	}

	public String getFetchCicle() {
		return fetchCicle;
	}

	public void setFetchCicle(String fetchCicle) {
		this.fetchCicle = fetchCicle;
	}

	public String getCentryAddr() {
		return centryAddr;
	}

	public void setCentryAddr(String centryAddr) {
		this.centryAddr = centryAddr;
	}

	public String getStsDate() {
		return stsDate;
	}

	public void setStsDate(String stsDate) {
		this.stsDate = stsDate;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}


}
