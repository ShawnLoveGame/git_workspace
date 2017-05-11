/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-04-01 Created
 */
package com.he.exam.webapp.model.exam;

import java.util.Date;
import java.util.List;

/**
 * 试题
 * 
 * @author  ${user}
 * @version 1.0 2017-04-01
 */
public class Questions {

    private Long id;
    /**
     * 答题时间
     */
    private String answerTime;
    /**
     * 试题类型 1 单选 2 多选
     */
    private Integer questionType;
    /**
     * 题库ID
     */
    private Long libraryId;
    /**
     * 题干
     */
    private String questionContent;
    /**
     * 选项
     */
    private String questionOptions;
    /**
     * 正确答案
     */
    private String answers;
    /**
     * 状态 0 启用 1 冻结
     */
    private Integer status;
    private Date opTime;
    private Integer isDeleted;

    private List<QuestionOption> optionList;

    public List<QuestionOption> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<QuestionOption> optionList) {
        this.optionList = optionList;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAnswerTime() {
        return answerTime;
    }
    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime == null ? null : answerTime.trim();
    }
    public Integer getQuestionType() {
        return questionType;
    }
    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }
    public Long getLibraryId() {
        return libraryId;
    }
    public void setLibraryId(Long libraryId) {
        this.libraryId = libraryId;
    }
    public String getQuestionContent() {
        return questionContent;
    }
    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent == null ? null : questionContent.trim();
    }
    public String getQuestionOptions() {
        return questionOptions;
    }
    public void setQuestionOptions(String questionOptions) {
        this.questionOptions = questionOptions == null ? null : questionOptions.trim();
    }
    public String getAnswers() {
        return answers;
    }
    public void setAnswers(String answers) {
        this.answers = answers == null ? null : answers.trim();
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
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
}