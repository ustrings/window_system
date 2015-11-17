package com.hidata.ad.web.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

@Table("crowd_rule")
public class CrowdRule {

	@Column("cr_id")
	private String crId;

	@Column("crowd_id")
	private String crowdId;

	@Column("kw_num")
	private String kwNum;

	@Column("kw_time_type")
	private String kwTimeType;

	@Column("kw_time_num")
	private String kwTimeNum;

	@Column("url_num")
	private String urlNum;

	@Column("url_time_type")
	private String urlTimeType;

	@Column("url_time_num")
	private String urlTimeNum;

	@Column("url_switch")
	private String urlSwitch;

	@Column("create_time")
	private String createTime;

	@Column("sts")
	private String sts;

	@Column("remark")
	private String remark;

	@Column("kw_switch")
	private String kwSwitch;

	public String getCrId() {
		return crId;
	}

	public void setCrId(String crId) {
		this.crId = crId;
	}

	public String getKwNum() {
		return kwNum;
	}

	public void setKwNum(String kwNum) {
		this.kwNum = kwNum;
	}

	public String getKwTimeType() {
		return kwTimeType;
	}

	public void setKwTimeType(String kwTimeType) {
		this.kwTimeType = kwTimeType;
	}

	public String getKwTimeNum() {
		return kwTimeNum;
	}

	public void setKwTimeNum(String kwTimeNum) {
		this.kwTimeNum = kwTimeNum;
	}

	public String getUrlNum() {
		return urlNum;
	}

	public void setUrlNum(String urlNum) {
		this.urlNum = urlNum;
	}

	public String getUrlTimeType() {
		return urlTimeType;
	}

	public void setUrlTimeType(String urlTimeType) {
		this.urlTimeType = urlTimeType;
	}

	public String getUrlTimeNum() {
		return urlTimeNum;
	}

	public void setUrlTimeNum(String urlTimeNum) {
		this.urlTimeNum = urlTimeNum;
	}

	public String getUrlSwitch() {
		return urlSwitch;
	}

	public void setUrlSwitch(String urlSwitch) {
		this.urlSwitch = urlSwitch;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getSts() {
		return sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getKwSwitch() {
		return kwSwitch;
	}

	public void setKwSwitch(String kwSwitch) {
		this.kwSwitch = kwSwitch;
	}
	
	public String getCrowdId() {
		return crowdId;
	}

	public void setCrowdId(String crowdId) {
		this.crowdId = crowdId;
	}

}
