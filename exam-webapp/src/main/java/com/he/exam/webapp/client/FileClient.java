package com.he.exam.webapp.client;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * File工具类
 * 
 * @author daizy
 *
 */
public class FileClient {
	
	private static Logger log = LoggerFactory.getLogger(FileClient.class);
	
	public static File getFile(MultipartFile imgFile,String patternStr) {
        String fileName = imgFile.getOriginalFilename();
        String ext = FilenameUtils.getExtension(fileName).toLowerCase();
        StringBuilder  newFileName = new StringBuilder(UUID.randomUUID().toString());
        newFileName = newFileName.append(RandomStringUtils.randomAlphanumeric(1));
        newFileName.append('.').append(ext);
        File file = creatFolder( newFileName.toString());
        try {
            FileUtils.copyInputStreamToFile(imgFile.getInputStream(), file);
        } catch (IOException e) {
            log.error("UploadFileServerImpl get File is error ", e);
        }
        return file;
    }  
    
    public static File creatFolder(String fileName) {
        File file = null;
        String uploadImagePath = System.getProperty("java.io.tmpdir");
        File firstFolder = new File(uploadImagePath); // 一级文件夹
        if (!firstFolder.exists()) { // 如果一级文件夹存在，则检测二级文件夹
            firstFolder.mkdirs();
        }
        file = new File(firstFolder, fileName);
        return file;
    }

}
