package com.ai.smart.common.helper.weixin.entity;


public abstract class BaseInfoEntity extends UUIdEntity {

	protected String createDate; // 创建时间
	protected String createUserId; // 创建者ID
	protected String updateDate; // 更新时间
	protected String updateUserId; // 更新者ID
	protected String deleteFlag; // 删除标识，0：有效；1：无效
	protected String page; // 更新者ID
	protected String allCount; // 删除标识，0：有效；1：无效
	protected String pageStart; // 页数
	protected String pageSize;//每页的条数
	private String userName;
	protected String pubUserId;// 发布者ID

	public String getPubUserId() {
		return pubUserId;
	}

	public void setPubUserId(String pubUserId) {
		this.pubUserId = pubUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPageStart() {
		return pageStart;
	}

	public void setPageStart(String pageStart) {
		this.pageStart = pageStart;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getAllCount() {
		return allCount;
	}

	public void setAllCount(String allCount) {
		this.allCount = allCount;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public String getCreateUserId() {
		return createUserId;
	}
	
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	
	public String getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	public String getUpdateUserId() {
		return updateUserId;
	}
	
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	
	public String getDeleteFlag() {
		return deleteFlag;
	}
	
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
}
