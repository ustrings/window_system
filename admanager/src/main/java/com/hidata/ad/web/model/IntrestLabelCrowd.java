package com.hidata.ad.web.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 兴趣标签定向配置 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年8月27日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Table("intrest_label_crowd")
public class IntrestLabelCrowd {

    @Column("id")
    private String id;

    @Column("label_type")
    private String labelType;
    
    
    @Column("fetch_cicle")
    private String fetchCicle;
    
    @Column("label_value")
    private  String labelValue;
    
    @Column("crowd_id")
    private String crowdId;
    
    @Column("sts")
    private String sts;

    @Column("sts_date")
    private String sts_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    public String getFetchCicle() {
        return fetchCicle;
    }

    public void setFetchCicle(String fetchCicle) {
        this.fetchCicle = fetchCicle;
    }

    public String getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }

    public String getCrowdId() {
        return crowdId;
    }

    public void setCrowdId(String crowdId) {
        this.crowdId = crowdId;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    public String getSts_date() {
        return sts_date;
    }

    public void setSts_date(String sts_date) {
        this.sts_date = sts_date;
    }

}
