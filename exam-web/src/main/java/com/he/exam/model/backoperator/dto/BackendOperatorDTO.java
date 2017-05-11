package com.he.exam.model.backoperator.dto;


import com.he.exam.model.BaseDto;

public class BackendOperatorDTO extends BaseDto {

    // <code>serialVersionUID</code> 的注释
    private static final long serialVersionUID = -3374837026853598434L;

    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 最近三次密码 已逗号分隔
     */
    private String recentlyPassword;
    /**
     * 新密码
     */
    private String newPassword;
    @Override
	public String toString() {
		return "BackendOperatorDTO [userName=" + userName + ", password="
				+ password + ", recentlyPassword=" + recentlyPassword
				+ ", newPassword=" + newPassword + ", confirmNewPassword="
				+ confirmNewPassword + ", validateCode=" + validateCode
				+ ", sessionValidateCode=" + sessionValidateCode + ", lastIp="
				+ lastIp + ", updateBy=" + updateBy + ", autoLogin="
				+ autoLogin + ", realName=" + realName + ", staffNo=" + staffNo
				+ ", operatorStatus=" + operatorStatus + ", mobile=" + mobile
				+ ", oldStatus=" + oldStatus + ", cityId=" + cityId
				+ ", cityName=" + cityName + ", countyId=" + countyId
				+ ", countyName=" + countyName + ", businessHall="
				+ businessHall + ", department=" + department + ", company="
				+ company + ", id=" + id + ", operatorRoleDTOs="
				+ powerStatus + "]";
	}

	/**
     * 新密码确认
     */
    private String confirmNewPassword;
    /**
     * 验证码
     */
    private String validateCode;

    /**
     * 缓存中的验证码
     */
    private String sessionValidateCode;

    /**
     * 最后一次登录IP
     */
    private String lastIp;
    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 是否7天自动登录，0：否；1：是
     */
    private Integer autoLogin;

    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 员工编号
     */
    private String staffNo;

    /**
     * 用户状态
     */
    private Integer operatorStatus;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 更新前状态
     */
    private Integer oldStatus;
    /**
     * 归属城市ID
     */
    private Integer cityId;
    /**
     * 归属城市名称
     */
    private String cityName;
    /**
     * 归属地区ID
     */
    private Integer countyId;
    /**
     * 归属地区名称
     */
    private String countyName;
    /**
     * 营业厅
     */
    private String businessHall;
    /**
     * 所属部门
     */
    private String department;

    /**
     * 所属公司
     */
    private String company;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色授权状态，1：本角色用户；0：非本角色用户
     */
    private Integer powerStatus;

    public Integer getPowerStatus() {
        return powerStatus;
    }

    public void setPowerStatus(Integer powerStatus) {
        this.powerStatus = powerStatus;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(Integer autoLogin) {
        this.autoLogin = autoLogin;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public String getSessionValidateCode() {
        return sessionValidateCode;
    }

    public void setSessionValidateCode(String sessionValidateCode) {
        this.sessionValidateCode = sessionValidateCode;
    }

    public String getUserName() {
        return userName == null ? "" : userName.trim();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password == null ? "" : password.trim();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidateCode() {
        return validateCode == null ? "" : validateCode.trim();
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getRealName() {
        return realName == null ? "" : realName.trim();
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStaffNo() {
        return staffNo == null ? "" : staffNo.trim();
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public Integer getOperatorStatus() {
        return operatorStatus;
    }

    public void setOperatorStatus(Integer operatorStatus) {
        this.operatorStatus = operatorStatus;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCompany() {
        return company == null ? "" : company.trim();
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(Integer oldStatus) {
        this.oldStatus = oldStatus;
    }


    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getBusinessHall() {
        return businessHall;
    }

    public void setBusinessHall(String businessHall) {
        this.businessHall = businessHall;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRecentlyPassword() {
        return recentlyPassword;
    }

    public void setRecentlyPassword(String recentlyPassword) {
        this.recentlyPassword = recentlyPassword;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
