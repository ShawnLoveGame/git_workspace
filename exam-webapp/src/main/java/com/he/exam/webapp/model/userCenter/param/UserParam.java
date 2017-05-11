package com.he.exam.webapp.model.userCenter.param;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserParam implements Serializable{
	
	private String distUserId;
	private String openid;
	
	private String userQQ;
	
	private String userName;
	
	private String nickname;
	
	/**
	 * 用户性别 ，1.男 2.女 0.未知
	 */
	private Integer sex;
	
	private String birthday;
	
	private String headpic ;
	
	private String email;
	
	/**
	 * 0.app 1.微信 2.PC
	 */
	private String regType;

	public String getDistUserId() {
		return distUserId;
	}

	public void setDistUserId(String distUserId) {
		this.distUserId = distUserId;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getUserQQ() {
		return userQQ;
	}

	public void setUserQQ(String userQQ) {
		this.userQQ = userQQ;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getHeadpic() {
		return headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}

}
