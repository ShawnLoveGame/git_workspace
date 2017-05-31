package com.he.im.model;

import java.io.Serializable;

public class BaseDto implements Serializable{

	
	//<code>serialVersionUID</code> 的注释
	private static final long serialVersionUID = 4829062088209392399L;

	public final static String DIRECTION_DESC = "DESC";

    public final static String DIRECTION_ASC = "ASC";

    private int start;

    private int page = 1;

    private int rows = 10;

    // 数据库字段名
    private String sort;

    // 方向
    private String dir;

    private boolean needCount;
    
    // 是否分页校验位
    private int isPage;


    private String order;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getStart() {
        start = (this.getPage() - 1) * this.getRows();
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public boolean isNeedCount() {
        return needCount;
    }

    public void setNeedCount(boolean needCount) {
        this.needCount = needCount;
    }

    public int getPage() {
        if (page < 1) {
            page = 1;
        }
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        if (rows < 0) {
            rows = 10;
        }
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

	public int getIsPage() {
		return isPage;
	}

	public void setIsPage(int isPage) {
		this.isPage = isPage;
	}

}
