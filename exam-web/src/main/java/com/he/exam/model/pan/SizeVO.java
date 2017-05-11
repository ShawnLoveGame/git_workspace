package com.he.exam.model.pan;

import com.he.exam.util.json.JsonUtils;

import java.util.ArrayList;

public class SizeVO {
	private int height;
	private int width;

	public SizeVO() {
	}

	public SizeVO(int width,int heigth) {
		this.height = heigth;
		this.width = width;
	}

	public SizeVO(String jsonString) {
		SizeVO sizeVO = JsonUtils.fromJson(jsonString, SizeVO.class);
		this.height = sizeVO.getHeight();
		this.width = sizeVO.getWidth();
	}

	/**
	 * 
	 * @param jsonString
	 * @return
	 */
	public static SizeVO build(String jsonString) {
		if (jsonString == null) {
			return null;
		}
		return JsonUtils.fromJson(jsonString, SizeVO.class);
	}

	/**
	 * 
	 * @param jsonString
	 * @return
	 */
	public static ArrayList<SizeVO> builds(String jsonString) {
		return JsonUtils.fromJson(jsonString, JsonUtils
				.constructParametricType(ArrayList.class, SizeVO.class));
	}

	/**
	 * 
	 * @return
	 */
	public static ArrayList<SizeVO> defaultList() {
		ArrayList<SizeVO> list = new ArrayList<SizeVO>();
		list.add(new SizeVO(40, 40));
		list.add(new SizeVO(80, 80));
		list.add(new SizeVO(120, 120));
		return list;
	}

	@Override
	public String toString() {
		return "_" + width + "x" + height;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getWidth() {
		return width;
	}
}
