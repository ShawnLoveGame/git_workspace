package com.ai.smart.bottom.common.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * <br>
 * <b>功能：</b>ComSection 实体类<br>
 * <b>日期：</b> 2018-05-07 10:22:10 <br>
 */
@Data
@SuppressWarnings("serial")
public class ComSection implements Serializable {
	
	/**
	 * 号段名称
	 */
	private String sectionName;
	
	/**
	 * 号段类型1：重庆移动
	 */
	private Integer sectionType;
	
	/**
	 * 号段
	 */
	private String sectionNum;
	

}

