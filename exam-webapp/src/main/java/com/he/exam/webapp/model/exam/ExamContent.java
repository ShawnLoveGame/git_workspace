package com.he.exam.webapp.model.exam;

/**
 * Created by he on 2017/3/28.
 */
public class ExamContent {

    private Integer quetionType;

    private Integer totalNum;

    private Integer singleScore;

    public Integer getQuetionType() {
        return quetionType;
    }

    public void setQuetionType(Integer quetionType) {
        this.quetionType = quetionType;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getSingleScore() {
        return singleScore;
    }

    public void setSingleScore(Integer singleScore) {
        this.singleScore = singleScore;
    }
}
