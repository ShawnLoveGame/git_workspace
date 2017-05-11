/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-03-28 Created
 */
package com.he.exam.model.exam;

import java.util.List;

/**
 * 用户分组
 * 
 * @author  ${user}
 * @version 1.0 2017-03-28
 */
public class UserGroup {

    private Long id;
    private String groupName;


    private List<Department> departmentList;

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }
}