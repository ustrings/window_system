package com.hidata.web.dto;

import com.hidata.framework.annotation.db.Column;
import com.hidata.framework.annotation.db.Table;

/**
 * 广告审核配置
 * Title: HIDATA1.0 <br>
 * Description: <br>
 * Date: 2014年12月23日 <br>
 * Copyright (c) 2014 zhaolansoft <br>
 * 
 * @author zhoubin
 */
@Table("t_ad_check_config")
public class TAdCheckConfigDto {
    //记录ID
    @Column("id")
    private String id;
    
    @Column("check_role_name")
    private String checkRoleName;
    
    @Column("user_name")
    private String userName;
    
    @Column("dept_name")
    private String deptName;


    @Column("user_acct_rel")
    private String userAcctRel;
    
    @Column("tel_nbr")
    private String telNbr;
    

    @Column("email")
    private String email;
    
    
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

    public String getCheckRoleName() {
        return checkRoleName;
    }

    public void setCheckRoleName(String checkRoleName) {
        this.checkRoleName = checkRoleName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUserAcctRel() {
        return userAcctRel;
    }

    public void setUserAcctRel(String userAcctRel) {
        this.userAcctRel = userAcctRel;
    }

    public String getTelNbr() {
        return telNbr;
    }

    public void setTelNbr(String telNbr) {
        this.telNbr = telNbr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
