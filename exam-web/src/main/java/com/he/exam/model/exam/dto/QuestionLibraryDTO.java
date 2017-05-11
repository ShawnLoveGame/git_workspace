package com.he.exam.model.exam.dto;

import com.he.exam.model.BaseDto;

/**
 * Created by he on 2017/3/24.
 */
public class QuestionLibraryDTO extends BaseDto {

    private Long id ;

    /**
     * 题库标题
     */
    private String libraryTitle;
    /**
     * 题库状态 0 启用 1 冻结
     */
    private Integer libraryStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibraryTitle() {
        return libraryTitle;
    }

    public void setLibraryTitle(String libraryTitle) {
        this.libraryTitle = libraryTitle;
    }

    public Integer getLibraryStatus() {
        return libraryStatus;
    }

    public void setLibraryStatus(Integer libraryStatus) {
        this.libraryStatus = libraryStatus;
    }
}
