package com.vaolan.sspserver.model;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 广告对应标签信息
 * Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年12月15日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Table("all_label_adplan")
public class AdPlanLabelRel {
    // 主键
    @Column("id")
    private String id;
    
    @Column("label_value")
    private String labelValue;
    
    @Column("ad_id")
    private String adId;
    
    @Column("sts")
    private String sts;
    
    @Column("sts_date")
    private String stsDate;
    
    //全路径名称MD5值
    private String  fullPathNameMDVal;
    
    //
    private String labelName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getSts() {
        return sts;
    }

    public void setSts(String sts) {
        this.sts = sts;
    }

    public String getStsDate() {
        return stsDate;
    }

    public void setStsDate(String stsDate) {
        this.stsDate = stsDate;
    }



    public String getFullPathNameMDVal() {
        return fullPathNameMDVal;
    }

    public void setFullPathNameMDVal(String fullPathNameMDVal) {
        this.fullPathNameMDVal = fullPathNameMDVal;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
    
    
}
