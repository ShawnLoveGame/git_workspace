package com.he.exam.webapp.model.floorCenter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * 首页楼层
 * 
 * @author daizy
 *
 */
@SuppressWarnings("serial")
public class Floor implements Serializable{
	
	
	private Long id; //主键

    private String floorTitle;//楼层模块的标题

    private String floorName;//模块名

    private Short floorSort;//模块排序

    private String floorDesc;//模块描述

    private Date updateTime; //最近更新时间

    private Date createTime; //创建时间

    private Integer isDelete;	//是否删除

    private Integer floorSource;//楼层资源属于什么首页 1.来源于App
    
    private List<FloorContent> floorContentList;//楼层的内容

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFloorTitle() {
		return floorTitle;
	}

	public void setFloorTitle(String floorTitle) {
		this.floorTitle = floorTitle;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public Short getFloorSort() {
		return floorSort;
	}

	public void setFloorSort(Short floorSort) {
		this.floorSort = floorSort;
	}

	public String getFloorDesc() {
		return floorDesc;
	}

	public void setFloorDesc(String floorDesc) {
		this.floorDesc = floorDesc;
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

	public Integer getFloorSource() {
		return floorSource;
	}

	public void setFloorSource(Integer floorSource) {
		this.floorSource = floorSource;
	}

	public List<FloorContent> getFloorContentList() {
		return floorContentList;
	}

	public void setFloorContentList(List<FloorContent> floorContentList) {
		this.floorContentList = floorContentList;
	}

}
