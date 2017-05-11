package com.he.exam.model.exam.dto;

import com.he.exam.model.BaseDto;

/**
 * Created by he on 2017/3/28.
 */
public class ExamInfoDTO extends BaseDto {

    /**
     * 考试名称
     */
    private String examTitle;


    /**
     * 考试状态 0 启用 1 冻结 2 结束
     */
    private Integer examStatus;

    public String getExamTitle() {
        return examTitle;
    }

    public void setExamTitle(String examTitle) {
        this.examTitle = examTitle;
    }

    public Integer getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(Integer examStatus) {
        this.examStatus = examStatus;
    }
}
