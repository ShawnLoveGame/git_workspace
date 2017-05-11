/*
 * BackOperator.java
 * All rights reserved.
 * -----------------------------------------------
 * 2016-12-05 Created
 */
package com.he.exam.model.backoperator;

import java.util.Date;

/**
 * 
 * 
 * @author${user}
 * @version 1.0 2016-12-05
 */
public class BackOperator {

    private Long id;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 用户状态
     */
    private Integer operatorStatus;
    /**
     * 联系号码
     */
    private String phone;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 工号
     */
    private String staffNo;
    /**
     * 是否超级用户
     */
    private Integer isSuperUser;
    /**
     * 登入次数
     */
    private Integer loginTimes;
    /**
     * 最后登入时间
     */
    private Date lastLoginDate;
    /**
     * id
     */
    private String lastIp;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 最近三次密码 已逗号分隔
     */
    private String recentlyPassword;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    private Long createBy;
    private Long updateBy;
    /**
     * 是否删除
     */
    private Integer isDelete;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }
    public Integer getOperatorStatus() {
        return operatorStatus;
    }
    public void setOperatorStatus(Integer operatorStatus) {
        this.operatorStatus = operatorStatus;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
    public String getStaffNo() {
        return staffNo;
    }
    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo == null ? null : staffNo.trim();
    }
    public Integer getIsSuperUser() {
        return isSuperUser;
    }
    public void setIsSuperUser(Integer isSuperUser) {
        this.isSuperUser = isSuperUser;
    }
    public Integer getLoginTimes() {
        return loginTimes;
    }
    public void setLoginTimes(Integer loginTimes) {
        this.loginTimes = loginTimes;
    }
    public Date getLastLoginDate() {
        return lastLoginDate;
    }
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
    public String getLastIp() {
        return lastIp;
    }
    public void setLastIp(String lastIp) {
        this.lastIp = lastIp == null ? null : lastIp.trim();
    }
    public Integer getSex() {
        return sex;
    }
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public String getRecentlyPassword() {
        return recentlyPassword;
    }
    public void setRecentlyPassword(String recentlyPassword) {
        this.recentlyPassword = recentlyPassword == null ? null : recentlyPassword.trim();
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public Long getCreateBy() {
        return createBy;
    }
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
    public Long getUpdateBy() {
        return updateBy;
    }
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }
    public Integer getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}