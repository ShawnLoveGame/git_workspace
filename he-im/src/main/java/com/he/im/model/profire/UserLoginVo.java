package com.he.im.model.profire;

/**
 * Created by he on 2017/4/24.
 */
public class UserLoginVo {


    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 登录账号
     */
    private String userName;

    /**
     * 登录时间
     */
    private Long loginTime;

    private String uuid;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
