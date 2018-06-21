package com.ai.smart.bottom.user.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.util.Date;

/**
 * 
 * <br>
 * <b>功能：</b>BottomActivityUserRel 实体类<br>
 * <b>日期：</b> 2018-06-07 12:40:34 <br>
 */
@Data
@SuppressWarnings("serial")
public class BottomActivityUserRel implements Serializable {
	
	/**
	 * 
	 */
	private Long id;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 上级用户id
	 */
	private Long parentUserId;
	
	/**
	 * 状态 0：待生效  1：已生效
	 */
	private Integer status;
	
	/**
	 * 绑定时间
	 */
	private Date bindTime;
	

}

