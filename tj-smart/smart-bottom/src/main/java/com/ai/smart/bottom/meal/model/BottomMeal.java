package com.ai.smart.bottom.meal.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <br>
 * <b>功能：</b>BottomMeal 实体类<br>
 * <b>日期：</b> 2018-05-14 18:08:06 <br>
 */
@Data
@SuppressWarnings("serial")
public class BottomMeal implements Serializable {
	
	/**
	 * 
	 */
	private Long id;
	
	/**
	 * 父节点 id
	 */
	private Long parentId;
	
	/**
	 * 套餐ncode
	 */
	private String ncode;
	
	/**
	 * 套餐名称
	 */
	private String mealName;
	
	/**
	 * 套餐小字说明
	 */
	private String mealTitle;
	
	/**
	 * 套餐资费
	 */
	private String mealPrice;
	
	/**
	 * 是否有子品 1：是  0：否
	 */
	private Integer isHavSub;
	
	/**
	 * 生效 2：即时生效  3：下月生效
	 */
	private Integer offType;
	
	/**
	 * 创建日期
	 */
	private Date createTime;
	
	/**
	 * 是否主套餐 1：主  0：否
	 */
	private Integer isMain;
	
	/**
	 * 状态 ：1：有效 0：无效  
	 */
	private Integer status;
	
	/**
	 * 套餐说明
	 */
	private String mealContent;
	
	/**
	 * 套餐排序
	 */
	private Integer mealSort;
	

}

