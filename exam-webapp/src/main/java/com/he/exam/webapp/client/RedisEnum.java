package com.he.exam.webapp.client;

/**
 * 
 * REDIS定义
 * 
 * @author daizy
 *
 */
public enum RedisEnum {
	
	/**
	 * 登录用户REDIS缓存 +userId
	 */
	REDIS_LOGIN_USER_INFO_("REDIS_LOGIN_USER_INFO_",24*24*3600),
	
	REDIS_WEIXIN_APP_TOKEN_("REDIS_WEIXIN_APP_TOKEN_",7000),
	
	REDIS_WEIXIN_APP_JSTICKET_("REDIS_WEIXIN_APP_JSTICKET_",7000),
	
	;
	
	
	public String key;
	public int time;
	
	 // 构造方法
    private RedisEnum(String key, int time) {
        this.key = key;
        this.time = time;
    }
    
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	
	

}
