package com.hidata.ad.web.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 广告计划下--人群画像定向表
 * @author ssq
 *
 */
@Table("adplan_portrait")
public class AdPlanPortrait {

	@Column("id")
    private String id;
	
	@Column("ad_id")
	private String adId;
	
	@Column("sex")
	private String sex;
	
	@Column("age_range")
	private String ageRange;

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAgeRange() {
		return ageRange;
	}

	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}

}
