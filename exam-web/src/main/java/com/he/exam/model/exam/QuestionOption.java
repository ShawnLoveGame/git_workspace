package com.he.exam.model.exam;

/**
 * 试题选项 对象
 * Created by he on 2017/3/27.
 */
public class QuestionOption {

    private String key;

    private String content;

    private Boolean isChecked;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
