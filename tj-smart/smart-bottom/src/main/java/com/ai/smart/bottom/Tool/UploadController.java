package com.ai.smart.bottom.Tool;

import com.ai.smart.bottom.helper.SmsHelper;
import com.ai.smart.common.base.GlobalResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@RequestMapping("/upload")
@RestController
@Api(value = "/upload", description = "上传接口")
public class UploadController {

    @Value("${qrcodeDomain}")
    private String qrcodeDomain;

    @Value("${imgpath}")
    private String imgpath;

    @ApiOperation(notes = "/uploadImg", value = "上传图片")
    @RequestMapping(value = "/uploadImg",method = RequestMethod.POST)
    public GlobalResponse uploadImg(MultipartFile file,String fileName){
        try {
            File targetFile = new File(imgpath);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(imgpath+fileName);
            out.write(file.getBytes());
            out.flush();
            out.close();
            return GlobalResponse.success(qrcodeDomain+"/"+fileName);
        } catch (IOException e) {
            log.error("uploadImg exception",e);
        }
        return GlobalResponse.fail("上传图片失败");
    }

    @ApiOperation(notes = "/sendSms", value = "测试短信")
    @RequestMapping(value = "/sendSms",method = RequestMethod.GET)
    public GlobalResponse sendSms(String serialNum){
        SmsHelper.sendSms(serialNum,"测试短信");
        return GlobalResponse.success(true);
    }
}
