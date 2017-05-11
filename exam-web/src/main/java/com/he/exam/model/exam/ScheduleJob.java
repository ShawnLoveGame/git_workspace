/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-04-21 Created
 */
package com.he.exam.model.exam;

import java.util.Date;

/**
 * 
 * 
 * @author  ${user}
 * @version 1.0 2017-04-21
 */
public class ScheduleJob {

    private Long id;
    /**
     * 定时器名称
     */
    private String jobName;
    /**
     * 定时器组
     */
    private String jobGroup;
    /**
     * 定时器状态
     */
    private Integer jobStatus;
    /**
     * 定时器执行频率
     */
    private String cronExpression;
    /**
     * 定时器描述
     */
    private String jobDesc;
    private String createBy;
    private Date createTime;
    private Date updateTime;
    private String updateBy;
    /**
     * 实现类路径
     */
    private String jobClass;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getJobName() {
        return jobName;
    }
    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }
    public String getJobGroup() {
        return jobGroup;
    }
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup == null ? null : jobGroup.trim();
    }
    public Integer getJobStatus() {
        return jobStatus;
    }
    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }
    public String getCronExpression() {
        return cronExpression;
    }
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression == null ? null : cronExpression.trim();
    }
    public String getJobDesc() {
        return jobDesc;
    }
    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc == null ? null : jobDesc.trim();
    }
    public String getCreateBy() {
        return createBy;
    }
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
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
    public String getUpdateBy() {
        return updateBy;
    }
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }
    public String getJobClass() {
        return jobClass;
    }
    public void setJobClass(String jobClass) {
        this.jobClass = jobClass == null ? null : jobClass.trim();
    }
}