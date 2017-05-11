/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-01-05 Created
 */
package com.he.exam.model.log;

import java.util.Date;

/**
 * 后台操作日志
 * 
 * @author  ${user}
 * @version 1.0 2017-01-05
 */
public class BackOperateLog {

    private Long id;
    /**
     * 操作类型
     */
    private String operationType;
    /**
     * 操作名称
     */
    private String operationName;
    /**
     * 操作人
     */
    private Long operateId;
    private String operateName;
    /**
     * 入参
     */
    private String inputParans;
    /**
     * 创建时间
     */
    private Date createTime;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getOperationType() {
        return operationType;
    }
    public void setOperationType(String operationType) {
        this.operationType = operationType == null ? null : operationType.trim();
    }
    public String getOperationName() {
        return operationName;
    }
    public void setOperationName(String operationName) {
        this.operationName = operationName == null ? null : operationName.trim();
    }
    public Long getOperateId() {
        return operateId;
    }
    public void setOperateId(Long operateId) {
        this.operateId = operateId;
    }
    public String getOperateName() {
        return operateName;
    }
    public void setOperateName(String operateName) {
        this.operateName = operateName == null ? null : operateName.trim();
    }
    public String getInputParans() {
        return inputParans;
    }
    public void setInputParans(String inputParans) {
        this.inputParans = inputParans == null ? null : inputParans.trim();
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}