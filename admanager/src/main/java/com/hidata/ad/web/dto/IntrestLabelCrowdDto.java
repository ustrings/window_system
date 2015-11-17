package com.hidata.ad.web.dto;

import java.io.Serializable;

public class IntrestLabelCrowdDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2072824063427730137L;
	
	private String label;
	
	private String labelName;
	
	private String objid;
	
	private String fetchCicle;
	
	public String getFetchCicle() {
		return fetchCicle;
	}

	public void setFetchCicle(String fetchCicle) {
		this.fetchCicle = fetchCicle;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getObjid() {
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	

}
