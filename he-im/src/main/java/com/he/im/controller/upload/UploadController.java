package com.he.im.controller.upload;

import com.he.im.model.ModelResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by he on 2017/3/31.
 */
@Controller
@RequestMapping("/im/upload")
public class UploadController {

    private Log log = LogFactory.getLog(getClass());
    @Value("${uploadPath}")
    private String uploadPath ;

    @Value("${img_prefix}")
    private String imgPrefix;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception{
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile imgFile = multipartRequest.getFile("file");
        String uploadFilePath = System.getProperty("java.io.tmpdir");
        File tmpFile = getFile(imgFile, uploadFilePath);
        return imgUrl(imgFile.getOriginalFilename() , tmpFile);
    }

    private String imgUrl (String fileOriginalName , File tmpFile){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//        String path = "D:/upload/";
//        String path = "/data/oss/oss/";
        String fileName = null;
        try {
            String ext = FilenameUtils.getExtension(fileOriginalName).toLowerCase();
            fileName = sdf.format(new Date())+"." + ext;
            File file = new File(uploadPath);
            if(!file.exists()){
                file.mkdirs();
            }
            FileInputStream is = new FileInputStream(tmpFile);
            int length=0;
            byte[] by=new byte[1024];

            OutputStream out = new FileOutputStream(new File(uploadPath,fileName));
            while((length=is.read(by))!=-1){
                out.write(by, 0, length);
            }
            out.close();
            String url = imgPrefix+fileName;
            return url;
        } catch (IOException e) {
            log.error("图片上传失败" ,e );
        }
        return "";
    }


    @RequestMapping("/uploadWinPic")
    public String uploadWinPic(MultipartFile myfile, Model model , HttpServletRequest request , HttpServletResponse response){
        //获取文件后缀
        ModelResult result = new ModelResult(true , ModelResult.SUCCESS);
        String fileNamePatternStr = "^.*?\\.(jpg|jpeg|png|gif|bmp)$";
        String uploadFilePath = System.getProperty("java.io.tmpdir");
        if (myfile == null ||
                myfile.getOriginalFilename() == null || "".equalsIgnoreCase(myfile.getOriginalFilename())) {
            result.setStateAndSuccess(-3, false, "the file is empty");
        } else {
            String fileName = myfile.getOriginalFilename();
            if (StringUtils.isNotBlank(fileNamePatternStr)) {
                if (!Pattern.compile(fileNamePatternStr).matcher(fileName.toLowerCase()).matches()) {
                    result.setStateAndSuccess(-4, false, "file type is error，you can select " + fileNamePatternStr + "types to send");
                }
            }
            if (result.isSuccess()) {
                File file = getFile(myfile, uploadFilePath);
                String url = imgUrl(fileName , file);
                file.delete();
                log.error("图片上传URL：" + url);
                if (StringUtils.isEmpty(url)) {
                    result.setStateAndSuccess(-8, false, "上传失败");
                } else {
                    result.addAttribute("url", url);
                }
            }
        }
        model.addAttribute("result", result);
        response.addHeader("Access-Control-Allow-Origin", "*");
        return "message/uploadPicSuccess";
    }
    
    @RequestMapping("/uploadWinPicForKy")
    @ResponseBody
    public String uploadWinPicForKy(@RequestParam(value="fileData",required=false) MultipartFile file,HttpServletRequest request , HttpServletResponse response){
    	response.addHeader("Access-Control-Allow-Origin", "*");
        //获取文件后缀
        ModelResult result = new ModelResult(true , ModelResult.SUCCESS);
        String fileNamePatternStr = "^.*?\\.(jpg|jpeg|png|gif|bmp)$";
        String uploadFilePath = System.getProperty("java.io.tmpdir");
        if (file == null ||
        		file.getOriginalFilename() == null || "".equalsIgnoreCase(file.getOriginalFilename())) {
        	return "the file is empty!";
        } else {
            String fileName = file.getOriginalFilename();
            if (StringUtils.isNotBlank(fileNamePatternStr)) {
                if (!Pattern.compile(fileNamePatternStr).matcher(fileName.toLowerCase()).matches()) {
                    result.setStateAndSuccess(-4, false, "file type is error，you can select " + fileNamePatternStr + "types to send");
                }
            }
            if (result.isSuccess()) {
                File file1 = getFile(file, uploadFilePath);
                String url = imgUrl(fileName , file1);
                file1.delete();
                log.error("图片上传URL：" + url);
                if (StringUtils.isEmpty(url)) {
                   return "image upload error!";
                } else {
                	return url;
                }
            }
        }
        return "";
    }

    private File getFile(MultipartFile imgFile, String uploadImagePath) {
        String fileName = imgFile.getOriginalFilename();
        String ext = FilenameUtils.getExtension(fileName).toLowerCase();
        StringBuilder  newFileName = new StringBuilder(UUID.randomUUID().toString());
        newFileName = newFileName.append(RandomStringUtils.randomAlphanumeric(1));
        newFileName.append('.').append(ext);
        File file = this.creatFolder(uploadImagePath, newFileName.toString());
        try {
            FileUtils.copyInputStreamToFile(imgFile.getInputStream(), file);
        } catch (IOException e) {
            log.error("UploadFileServerImpl get File is error ", e);
        }
        return file;
    }
    private File creatFolder(String uploadImagePath, String fileName) {
        File file = null;
        File firstFolder = new File(uploadImagePath); // 一级文件夹
        if (!firstFolder.exists()) { // 如果一级文件夹存在，则检测二级文件夹
            firstFolder.mkdirs();
        }
        file = new File(firstFolder, fileName);
        return file;
    }

}
