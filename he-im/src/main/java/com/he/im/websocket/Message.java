package com.he.im.websocket;

import java.util.Date;

public class Message {

	public String fromName;

	public String toName;
	//发送的文本
	public String text;
	//发送日期
	public Date date;

	private Integer type;

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
