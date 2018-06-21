package com.ai.smart.bottom.user.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * <br>
 * <b>功能：</b> 实体类<br>
 * <b>日期：</b> 2018-06-07 17:30:46 <br>
 */
@Data
@SuppressWarnings("serial")
public class BottomActivitySo implements Serializable {
	
	/**
	 * 
	 */
	private Long id;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 办理套餐的ncode
	 */
	private String ncode;
	
	/**
	 * 红包奖励金额
	 */
	private BigDecimal amount;
	
	/**
	 * 红包时间
	 */
	private Date createTime;
	
	/**
	 * 手机号码
	 */
	private String serialNumber;
	
	/**
	 * 状态 0：初始化 1：赠送成功
	 */
	private Integer status;
	
	/**
	 * 上账结果返回
	 */
	private String remark;

	/**
	 * 红包来源用户id
	 */
	private Long subUserId;

	private BottomMaUser bottomMaUser;

}

