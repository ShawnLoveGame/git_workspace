/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-04-01 Created
 */
package com.he.exam.model.exam;

/**
 * 
 * 
 * @author  ${user}
 * @version 1.0 2017-04-01
 */
public class ExamDetail {

    private Long id;
    private Integer ruleLevel;
    private Long examId;
    private Long questionId;
    private Integer detailIndex;
    /**
     * 题目得分
     */
    private Integer record;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getRuleLevel() {
        return ruleLevel;
    }
    public void setRuleLevel(Integer ruleLevel) {
        this.ruleLevel = ruleLevel;
    }
    public Long getExamId() {
        return examId;
    }
    public void setExamId(Long examId) {
        this.examId = examId;
    }
    public Long getQuestionId() {
        return questionId;
    }
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
    public Integer getDetailIndex() {
        return detailIndex;
    }
    public void setDetailIndex(Integer detailIndex) {
        this.detailIndex = detailIndex;
    }
    public Integer getRecord() {
        return record;
    }
    public void setRecord(Integer record) {
        this.record = record;
    }
}