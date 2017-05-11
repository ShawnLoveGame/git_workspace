package com.he.exam.model.exam.dto;

import com.he.exam.model.BaseDto;

/**
 * Created by he on 2017/3/28.
 */
public class UserGroupDTO extends BaseDto {

    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
