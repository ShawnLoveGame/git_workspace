package com.he.exam.webapp.model.floorCenter;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class FloorContent implements Serializable{
	
	private Integer id; //主键ID

    private Long floorId;//模块ID

    private Short floorContentType;//模块内容类型

    private String floorContentDirectUrl;//模块内容链接URL

    private String floorContentImgUrl; //内容图片URL

    private Short floorContentSort;	//内容排序

    private Date updateTime;	//最近更新时间

    private Date createTime;	//创建时间

    private Integer isDelete;	//是否删除

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getFloorId() {
		return floorId;
	}

	public void setFloorId(Long floorId) {
		this.floorId = floorId;
	}

	public Short getFloorContentType() {
		return floorContentType;
	}

	public void setFloorContentType(Short floorContentType) {
		this.floorContentType = floorContentType;
	}

	public String getFloorContentDirectUrl() {
		return floorContentDirectUrl;
	}

	public void setFloorContentDirectUrl(String floorContentDirectUrl) {
		this.floorContentDirectUrl = floorContentDirectUrl;
	}

	public String getFloorContentImgUrl() {
		return floorContentImgUrl;
	}

	public void setFloorContentImgUrl(String floorContentImgUrl) {
		this.floorContentImgUrl = floorContentImgUrl;
	}

	public Short getFloorContentSort() {
		return floorContentSort;
	}

	public void setFloorContentSort(Short floorContentSort) {
		this.floorContentSort = floorContentSort;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

}
