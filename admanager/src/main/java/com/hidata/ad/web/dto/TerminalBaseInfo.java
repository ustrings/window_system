package com.hidata.ad.web.dto;

import java.io.Serializable;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 终端定向实体对应表
 * @author xiaoming
 * @date 2014-6-13
 */
@Table("terminal_base_info")
public class TerminalBaseInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column("tbi_id")
	private String tbiId;
	@Column("t_type")
	private String tType;
	@Column("t_name")
	private String tName;
	@Column("t_Value")
	private String tValue;
	public String getTbiId() {
		return tbiId;
	}
	public void setTbiId(String tbiId) {
		this.tbiId = tbiId;
	}
	public String gettType() {
		return tType;
	}
	public void settType(String tType) {
		this.tType = tType;
	}
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}
	public String gettValue() {
		return tValue;
	}
	public void settValue(String tValue) {
		this.tValue = tValue;
	}
	
}
