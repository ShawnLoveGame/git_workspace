package com.he.exam.model.exam.dto;

import com.he.exam.model.BaseDto;

/**
 * Created by he on 2017/4/21.
 */
public class UserWinDTO extends BaseDto {

    private String startTime;

    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
