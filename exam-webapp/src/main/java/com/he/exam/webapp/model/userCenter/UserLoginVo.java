package com.he.exam.webapp.model.userCenter;


/**
 * 
 * Class Name		: UserLoginVo
 * Description		: 登录信息  cookie 记录字段<br>
 *
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
     * 昵称
     */
    private String nickName;
    
    /**
     * 头像
     */
    private String headpic;

    /**
     * 登录时间
     */
    private Long loginTime;
    
    private String openid;
    
    private String uuid;
    

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public String getHeadpic() {
		return headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}

}
