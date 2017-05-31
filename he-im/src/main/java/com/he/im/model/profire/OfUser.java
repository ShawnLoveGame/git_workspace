/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-04-24 Created
 */
package com.he.im.model.profire;

import java.util.Date;

/**
 * 
 * 
 * @author  ${user}
 * @version 1.0 2017-04-24
 */
public class OfUser {

    private Long id;
    private String username;
    private String plainpassword;
    private String encryptedpassword;
    private String name;
    private String email;
    private String creationdate;
    private String modificationdate;
    private Date opTime;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }
    public String getPlainpassword() {
        return plainpassword;
    }
    public void setPlainpassword(String plainpassword) {
        this.plainpassword = plainpassword == null ? null : plainpassword.trim();
    }
    public String getEncryptedpassword() {
        return encryptedpassword;
    }
    public void setEncryptedpassword(String encryptedpassword) {
        this.encryptedpassword = encryptedpassword == null ? null : encryptedpassword.trim();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
    public String getCreationdate() {
        return creationdate;
    }
    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate == null ? null : creationdate.trim();
    }
    public String getModificationdate() {
        return modificationdate;
    }
    public void setModificationdate(String modificationdate) {
        this.modificationdate = modificationdate == null ? null : modificationdate.trim();
    }
    public Date getOpTime() {
        return opTime;
    }
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }
}