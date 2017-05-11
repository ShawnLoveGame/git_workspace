/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-04-21 Created
 */
package com.he.exam.model.exam;

import java.util.Date;

/**
 * 用户中奖
 * 
 * @author  ${user}
 * @version 1.0 2017-04-21
 */
public class UserWin {

    private Long id;
    private Long userId;
    private String winTime;
    private Date opTime;

    private String userName;

    private String departmentName;
    private String groupName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getWinTime() {
        return winTime;
    }
    public void setWinTime(String winTime) {
        this.winTime = winTime == null ? null : winTime.trim();
    }
    public Date getOpTime() {
        return opTime;
    }
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }
}