package com.ai.smart.bottom.meal.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.util.Date;

/**
 * 
 * <br>
 * <b>功能：</b>BottomMealSo 实体类<br>
 * <b>日期：</b> 2018-05-15 10:35:48 <br>
 */
@Data
@SuppressWarnings("serial")
public class BottomMealSo implements Serializable {
	
	/**
	 * 
	 */
	private Long id;
	
	/**
	 * 套餐id
	 */
	private Long mealId;
	
	/**
	 * 套餐名称
	 */
	private String mealName;
	
	/**
	 * 
	 */
	private String mealTitle;
	
	/**
	 * 价格
	 */
	private String mealPrice;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 生效 2：即时生效  3：下月生效
	 */
	private Integer offType;
	
	/**
	 * 手机号码
	 */
	private String serialNum;
	
	/**
	 * 办理时间
	 */
	private Date createTime;
	
	/**
	 * 办理状态 0：默认  1：办理成功 2：办理失败
	 */
	private Integer status;

	/**
	 * 套餐编码
	 */
	private String ncode;

	/**
	 * 备注信息
	 */
	private String remark;
	

}

