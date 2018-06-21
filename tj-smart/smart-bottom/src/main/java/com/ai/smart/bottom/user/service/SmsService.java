package com.ai.smart.bottom.user.service;

import com.ai.smart.bottom.helper.PostSmsHelper;
import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.common.helper.AssertHelper;
import com.ai.smart.common.helper.MockHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 短信验证服务
 */
@Slf4j
@Service
public class SmsService {


    @Autowired
    private PostSmsHelper postSmsHelper;

    @Autowired
    private MockHelper mockHelper;

    @Value("${app_name}")
    public String app_name;

    /**
     * 验证码发送
     * @param serialNumber
     * @return
     */
    public GlobalResponse sendSmsVerCode(String serialNumber,String createBy,String ip){
        try {
            AssertHelper.notBlank(serialNumber,"手机号码必填");
            if (mockHelper.isMock()){
                return GlobalResponse.success("发送成功");
            }
            return postSmsHelper.send(ip, serialNumber);
        } catch (Exception e) {
            log.error("sendSmsVerCode exception:",e);
        }
        return GlobalResponse.fail("验证码发送失败！");
    }

    /**
     * 校验短信验证码是否输入正确
     * @param serialNumber
     * @param verCode
     * @param ip
     * @return
     */
    public GlobalResponse checkSmsVerCode(String serialNumber,String verCode,String ip){
        try {
            AssertHelper.notBlank(serialNumber,"手机号码必填");
            AssertHelper.notBlank(verCode,"验证码码必填");
            if (mockHelper.isMock()){
                return GlobalResponse.success("验证码校验成功");
            }
            return postSmsHelper.checkSmsVerCode(serialNumber, verCode, ip);
        } catch (Exception e) {
            log.error("checkSmsVerCode exception:",e);
        }
        return GlobalResponse.fail("验证码验证失败！");
    }

}
