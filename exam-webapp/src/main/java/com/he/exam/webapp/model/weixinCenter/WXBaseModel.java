package com.he.exam.webapp.model.weixinCenter;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WXBaseModel implements Serializable{
	
	
	private String errcode;
	
	private String errmsg;

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
