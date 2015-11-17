package com.hidata.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 投放控制实体类	
 * @author xiaoming
 * @date 2015-1-5
 */

@Table("put_control")
public class AdControlDto {
	
	@Column("id")
	private String id;
	
	@Column("frequency_day")
	private String frequencyDay;
	
	@Column("spacing_min")
	private String spacingMin;
	
	@Column("pv_total")
	private String pvTotal;
	
	@Column("sts_date")
	private String stsDate;
	
	@Column("remark")
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFrequencyDay() {
		return frequencyDay;
	}

	public void setFrequencyDay(String frequencyDay) {
		this.frequencyDay = frequencyDay;
	}

	public String getSpacingMin() {
		return spacingMin;
	}

	public void setSpacingMin(String spacingMin) {
		this.spacingMin = spacingMin;
	}

	public String getPvTotal() {
		return pvTotal;
	}

	public void setPvTotal(String pvTotal) {
		this.pvTotal = pvTotal;
	}


	public String getStsDate() {
		return stsDate;
	}

	public void setStsDate(String stsDate) {
		this.stsDate = stsDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
