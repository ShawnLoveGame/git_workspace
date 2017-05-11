package com.he.exam.ueditor.upload;

import com.he.exam.ueditor.define.State;

import java.io.InputStream;

/**
 * 将文件数据写入数据库
 *
 */
public class StorageManagerDB {
	public static final int BUFFER_SIZE = 8192;

	public StorageManagerDB() {
	}
	
	public static State saveBinaryFile(byte[] data, String path) {
		
		//调用DAO来存储图片
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public static State saveFileByInputStream(InputStream is, String path,
			long maxSize) {
		
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public static State saveFileByInputStream(InputStream is, String path) {
		return null;
	}
	
	
}
