package com.he.exam.webapp.model.exam;

/**
 * Created by he on 2017/3/30.
 */
public class AnswerContentModel {

    private Long qid;

    private String key;

    private Integer qindex;


    public Long getQid() {
        return qid;
    }

    public void setQid(Long qid) {
        this.qid = qid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getQindex() {
        return qindex;
    }

    public void setQindex(Integer qindex) {
        this.qindex = qindex;
    }
}
