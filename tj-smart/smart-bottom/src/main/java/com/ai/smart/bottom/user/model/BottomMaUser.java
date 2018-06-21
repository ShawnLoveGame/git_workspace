package com.ai.smart.bottom.user.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * 
 * <br>
 * <b>功能：</b>BottomMaUser 实体类<br>
 * <b>日期：</b> 2018-05-04 15:12:16 <br>
 */
@Data
@SuppressWarnings("serial")
public class BottomMaUser implements Serializable {
	
	/**
	 * 
	 */
	private Long id;
	
	/**
	 * 用户昵称
	 */
	@ApiModelProperty(notes = "用户昵称")
	private String nickName;
	
	/**
	 * 用户opneid
	 */
	@ApiModelProperty(notes = "用户opneid")
	private String openId;
	
	/**
	 * 用户头像
	 */
	@ApiModelProperty(notes = "用户头像")
	private String headImg;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 用户手机号码
	 */
	private String serialNum;
	
	/**
	 * 用户性别
	 */
	private Integer gender;
	
	/**
	 * 语言
	 */
	private String language;
	
	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 省份
	 */
	private String province;
	
	/**
	 * 国家
	 */
	private String country;
	
	/**
	 * unionid
	 */
	private String unionid;

	/**
	 * 绑定时间
	 */
	private Date bindTime;

}

