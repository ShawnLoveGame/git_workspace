/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-03-29 Created
 */
package com.he.exam.webapp.model.exam;

import java.util.Date;
import java.util.List;

/**
 * 考试
 * 
 * @author  ${user}
 * @version 1.0 2017-03-29
 */
public class ExamInfo {

    private Long id;
    /**
     * 考试名称
     */
    private String examTitle;
    /**
     * 开始时间
     */
    private Date examStartTime;
    /**
     * 结束时间
     */
    private Date examEndTime;
    /**
     * 考试时长
     */
    private Integer examTimes;
    /**
     * 考试类型 1 普通 2 随机
     */
    private Integer examType;
    /**
     * 总分
     */
    private Integer totalScore;
    /**
     * 单题得分
     */
    private Integer passScore;
    private String examContent;
    private Date opTime;
    /**
     * 是否删除 0否1是
     */
    private Integer isDeleted;
    /**
     * 考试状态 0 启用 1 冻结 2 结束
     */
    private Integer examStatus;

    private List<ExamContent> contentList;

    public List<ExamContent> getContentList() {
        return contentList;
    }

    public void setContentList(List<ExamContent> contentList) {
        this.contentList = contentList;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getExamTitle() {
        return examTitle;
    }
    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle == null ? null : examTitle.trim();
    }
    public Date getExamStartTime() {
        return examStartTime;
    }
    public void setExamStartTime(Date examStartTime) {
        this.examStartTime = examStartTime;
    }
    public Date getExamEndTime() {
        return examEndTime;
    }
    public void setExamEndTime(Date examEndTime) {
        this.examEndTime = examEndTime;
    }
    public Integer getExamTimes() {
        return examTimes;
    }
    public void setExamTimes(Integer examTimes) {
        this.examTimes = examTimes;
    }
    public Integer getExamType() {
        return examType;
    }
    public void setExamType(Integer examType) {
        this.examType = examType;
    }
    public Integer getTotalScore() {
        return totalScore;
    }
    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }
    public Integer getPassScore() {
        return passScore;
    }
    public void setPassScore(Integer passScore) {
        this.passScore = passScore;
    }
    public String getExamContent() {
        return examContent;
    }
    public void setExamContent(String examContent) {
        this.examContent = examContent == null ? null : examContent.trim();
    }
    public Date getOpTime() {
        return opTime;
    }
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }
    public Integer getIsDeleted() {
        return isDeleted;
    }
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
    public Integer getExamStatus() {
        return examStatus;
    }
    public void setExamStatus(Integer examStatus) {
        this.examStatus = examStatus;
    }
}