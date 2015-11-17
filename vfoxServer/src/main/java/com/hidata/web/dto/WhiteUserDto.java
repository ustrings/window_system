package com.hidata.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 用户白名单实体类
 * @author xiaoming
 * @date 2015-1-5
 */
@Table("white_user")
public class WhiteUserDto {
	
	@Column("id")
	private String id;
	
	@Column("user_md5_id")
	private String userMd5Id;
	
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

	public String getUserMd5Id() {
		return userMd5Id;
	}

	public void setUserMd5Id(String userMd5Id) {
		this.userMd5Id = userMd5Id;
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
