package com.he.exam.webapp.client;

import java.io.Serializable;

/**
 * 
 * 返回结果集
 * 
 * @author daizy
 *
 */
@SuppressWarnings("serial")
public class SoaResult implements Serializable{
	
	private boolean isSuccess;
	
	/**
	 * 结果码 0:成功 1：失败
	 */
	private Integer resultCode = 0;
	
	/**
	 * 响应消息
	 */
	private String message = null;
	
	/**
	 * 响应数据
	 */
	private Object data = null;
	
	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public SoaResult(){}
	
	public SoaResult(Integer resultCode,String message){
		this.resultCode = resultCode;
		this.message= message;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
