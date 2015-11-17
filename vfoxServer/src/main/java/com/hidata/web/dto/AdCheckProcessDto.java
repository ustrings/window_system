package com.hidata.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

@Table("ad_check_process")
public class AdCheckProcessDto {
    
    //记录ID
    @Column("id")
    private String id;

    @Column("ad_id")
    private String adId;
    
    @Column("check_user_id")
    private String  checkUserId ;
    
    
    @Column("check_sts")
    private String checkSts;
    
    
    @Column("check_desc")
    private String checkDesc;
    
    
    @Column("sts")
    private String sts;
    
    
    @Column("sts_date")
    private String stsDate;

    
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


    public String getCheckUserId() {
        return checkUserId;
    }


    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId;
    }


    public String getCheckSts() {
        return checkSts;
    }


    public void setCheckSts(String checkSts) {
        this.checkSts = checkSts;
    }


    public String getCheckDesc() {
        return checkDesc;
    }


    public void setCheckDesc(String checkDesc) {
        this.checkDesc = checkDesc;
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
    
    
    
}
