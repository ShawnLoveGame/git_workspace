/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-03-24 Created
 */
package com.he.exam.model.exam;

import java.util.Date;

/**
 * 题库
 * 
 * @author  ${user}
 * @version 1.0 2017-03-24
 */
public class QuestionLibrary {

    private Long id;
    /**
     * 题库标题
     */
    private String libraryTitle;
    /**
     * 题库状态 0 启用 1 冻结
     */
    private Integer libraryStatus;
    private String libraryImg;
    private Date createTime;
    private Date updateTime;
    private Integer isDeleted;

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
        this.libraryTitle = libraryTitle == null ? null : libraryTitle.trim();
    }
    public Integer getLibraryStatus() {
        return libraryStatus;
    }
    public void setLibraryStatus(Integer libraryStatus) {
        this.libraryStatus = libraryStatus;
    }
    public String getLibraryImg() {
        return libraryImg;
    }
    public void setLibraryImg(String libraryImg) {
        this.libraryImg = libraryImg == null ? null : libraryImg.trim();
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public Integer getIsDeleted() {
        return isDeleted;
    }
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}