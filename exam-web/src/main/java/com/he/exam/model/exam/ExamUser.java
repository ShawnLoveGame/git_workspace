/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-04-01 Created
 */
package com.he.exam.model.exam;

/**
 * 考试用户
 * 
 * @author  ${user}
 * @version 1.0 2017-04-01
 */
public class ExamUser {

    private Long id;
    private String userName;
    private String openid;
    private String nickName;
    private Integer sex;
    private String phone;
    private Long departmentId;
    private String headPic;


    private String departmentName;

    private Integer hasExam;

    public Integer getHasExam() {
        return hasExam;
    }

    public void setHasExam(Integer hasExam) {
        this.hasExam = hasExam;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

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
    public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }
    public Integer getSex() {
        return sex;
    }
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
    public Long getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
    public String getHeadPic() {
        return headPic;
    }
    public void setHeadPic(String headPic) {
        this.headPic = headPic == null ? null : headPic.trim();
    }
}