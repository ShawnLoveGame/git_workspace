/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-03-30 Created
 */
package com.he.exam.webapp.model.exam;

import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author  ${user}
 * @version 1.0 2017-03-30
 */
public class ExamUserAnswer {

    private Long id;
    private Long userId;
    private Long examId;
    private String answerContent;
    /**
     * 用户得分
     */
    private Integer userRecord;
    /**
     * 用户提交时间
     */
    private Date submitTime;
    private Long departmentId;

    private List<AnswerContentModel> contentModelList;

    public List<AnswerContentModel> getContentModelList() {
        return contentModelList;
    }

    public void setContentModelList(List<AnswerContentModel> contentModelList) {
        this.contentModelList = contentModelList;
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
    public Long getExamId() {
        return examId;
    }
    public void setExamId(Long examId) {
        this.examId = examId;
    }
    public String getAnswerContent() {
        return answerContent;
    }
    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent == null ? null : answerContent.trim();
    }
    public Integer getUserRecord() {
        return userRecord;
    }
    public void setUserRecord(Integer userRecord) {
        this.userRecord = userRecord;
    }
    public Date getSubmitTime() {
        return submitTime;
    }
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }
    public Long getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}