/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-04-25 Created
 */
package com.he.im.model.profire;

/**
 * 
 * 
 * @author  ${user}
 * @version 1.0 2017-04-25
 */
public class OfHistory extends OfHistoryKey {

    private String creationdate;
    private Integer messagesize;
    private String stanza;
    private String toname;

    public String getCreationdate() {
        return creationdate;
    }
    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate == null ? null : creationdate.trim();
    }
    public Integer getMessagesize() {
        return messagesize;
    }
    public void setMessagesize(Integer messagesize) {
        this.messagesize = messagesize;
    }
    public String getStanza() {
        return stanza;
    }
    public void setStanza(String stanza) {
        this.stanza = stanza == null ? null : stanza.trim();
    }
    public String getToname() {
        return toname;
    }
    public void setToname(String toname) {
        this.toname = toname == null ? null : toname.trim();
    }
}