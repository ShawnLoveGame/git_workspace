package com.he.exam.model.pan;

import java.io.File;
import java.util.List;

public class ClipVO {

	private List<SizeVO> sizes;

	private File file;

	private String masterFileId;
	
	// file后缀
	private String ext;
	

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public List<SizeVO> getSizes() {
		return sizes;
	}

	public void setSizes(List<SizeVO> sizes) {
		this.sizes = sizes;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getMasterFileId() {
		return masterFileId;
	}

	public void setMasterFileId(String masterFileId) {
		this.masterFileId = masterFileId;
	}

}
