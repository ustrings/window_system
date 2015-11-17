package com.hidata.ad.web.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 兴趣标签树 Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年8月27日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Table("intrest_label_tree")
public class IntrestLabelTree {

    @Column("id")
    private String id;

    @Column("label_name")
    private String labelName;

    @Column("label_val")
    private String labelValue;

    @Column("parent_label_val")
    private String parentLabelValue;

    @Column("label_count")
    private String labelCount;
    

    @Column("label_type")
    private String labelType;

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

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }

    public String getParentLabelValue() {
        return parentLabelValue;
    }

    public void setParentLabelValue(String parentLabelValue) {
        this.parentLabelValue = parentLabelValue;
    }

    public String getLabelCount() {
        return labelCount;
    }

    public void setLabelCount(String labelCount) {
        this.labelCount = labelCount;
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

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }
    
    

}
