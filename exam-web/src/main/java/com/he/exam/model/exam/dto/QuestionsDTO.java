package com.he.exam.model.exam.dto;

import com.he.exam.model.BaseDto;

/**
 * Created by he on 2017/3/24.
 */
public class QuestionsDTO extends BaseDto {

    private Integer questionType;

    private Integer status;

    /**
     * 题干
     */
    private String questionContent;

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
