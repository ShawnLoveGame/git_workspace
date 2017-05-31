/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-04-25 Created
 */
package com.he.im.model.profire;

public class OfHistoryKey {

    private String username;
    private Integer messageid;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }
    public Integer getMessageid() {
        return messageid;
    }
    public void setMessageid(Integer messageid) {
        this.messageid = messageid;
    }
}