package com.he.im.util.file;

import org.apache.commons.codec.binary.Hex;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class FileHashUtils {

	public final static String MD5 = "MD5";
	public final static String SHA1 = "SHA-1";

	private static MessageDigest md5Digest = null;

	static {
		try {
			md5Digest = MessageDigest.getInstance(MD5);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static String getMD5(File file) {
		return getHash(file, MD5).get(MD5);
	}

	public static String getMD5(String str) {
		return new String(Hex.encodeHex(md5Digest.digest(str.getBytes())));
	}

	public static String getSHA1(File file) {
		return getHash(file, SHA1).get(SHA1);
	}

	public static Map<String, String> getHash(File file, String... hashType) {
		FileInputStream fileInputStream=null;
		try {
			fileInputStream = new FileInputStream(file);
			return getHash(fileInputStream, hashType);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
		    if(fileInputStream!=null){
		        try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
		    }
		}
		
		return new LinkedHashMap<String, String>();
	}

	/**
	 * 获取网盘上传所需的 uid
	 * 
	 * @param file
	 * @return
	 */
	public static String getPanFileUID(File file) {
		Map<String, String> hash = getHash(file, MD5, SHA1);
		return getMD5(hash.get(MD5) + hash.get(SHA1));

	}

	/**
	 * 获取输入流的多个 hash 值 不要再计算 hash 后关闭流
	 * 
	 * @param file
	 * @param hashType
	 * @return
	 */
	public static Map<String, String> getHash(InputStream file,
			String... hashType) {
		Map<String, String> result = new LinkedHashMap<String, String>();
		if (hashType.length == 0) {
			return result;
		}
		try {
			Map<String, MessageDigest> digestMap = new LinkedHashMap<String, MessageDigest>();
			for (String type : hashType) {
				digestMap.put(type, MessageDigest.getInstance(type));
			}
			byte[] buffer = new byte[1024 * 4];
			int length;
			while ((length = file.read(buffer)) != -1) {
				for (MessageDigest digest : digestMap.values()) {
					digest.update(buffer, 0, length);
				}
			}
			for (Entry<String, MessageDigest> entry : digestMap.entrySet()) {
				result.put(entry.getKey(),
						new String(Hex.encodeHex(entry.getValue().digest())));

			}
			return result;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}
}
