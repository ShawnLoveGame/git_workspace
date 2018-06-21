package com.ai.smart.bottom.user.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * 
 * <br>
 * <b>功能：</b>BottomUserQr 实体类<br>
 * <b>日期：</b> 2018-06-06 13:04:27 <br>
 */
@Data
@SuppressWarnings("serial")
public class BottomUserQr implements Serializable {
	
	/**
	 * 
	 */
	private Long id;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 世界杯活动个人二维码A
	 */
	private String userQrUrl;
	

}

