package com.ai.smart.bottom.user.controller;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.ai.smart.bottom.base.BaseController;
import com.ai.smart.bottom.common.service.ComSectionService;
import com.ai.smart.bottom.filter.LoginUser;
import com.ai.smart.bottom.helper.RandomValidateCodeHelper;
import com.ai.smart.bottom.user.service.UserService;
import com.ai.smart.bottom.wechat.service.WeService;
import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.common.helper.redis.RedisHelper;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "/user",description = "用户接口")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private ComSectionService comSectionService;

    @Autowired
    private WeService weService;

    @Autowired
    private RandomValidateCodeHelper randomValidateCodeHelper;

    @Autowired
    private RedisHelper redisHelper;

    @ApiOperation(notes = "/checkIsAuth",value = "判断当前用户是否授权用户信息")
    @RequestMapping(value = "/checkIsAuth",method = RequestMethod.POST)
    public GlobalResponse checkIsAuth(){
        LoginUser loginUser = getLoginUser();
        if (Strings.isNullOrEmpty(loginUser.getNickName())){
            return GlobalResponse.fail("未授权用户信息");
        }else{
            return GlobalResponse.success("已授权用户信息");
        }
    }

    @ApiOperation(notes = "/findCurrentSerialNum",value = "获取当前用户绑定的手机号码")
    @RequestMapping(value = "/findCurrentSerialNum",method = RequestMethod.POST)
    public GlobalResponse findCurrentSerialNum(){
        return GlobalResponse.success(getLoginUser());
    }

    @ApiOperation(notes = "/checkSerialNum",value = "校验手机号码号段")
    @RequestMapping(value = "/checkSerialNum",method = RequestMethod.POST)
    public GlobalResponse checkSerialNum(String serialNum){
        return comSectionService.checkSectionNum(serialNum);
    }

    @ApiOperation(notes = "/sendSmsCode",value = "发送短信验证码")
    @RequestMapping(value = "/sendSmsCode",method = RequestMethod.POST)
    public GlobalResponse sendSmsCode(String serialNum,HttpServletRequest request){
        return userService.sendSmsCode(serialNum, request.getRemoteAddr());
    }

    @ApiOperation(notes = "/getVerify",value = "获取图片验证码")
    @RequestMapping(value = "/getVerify")
    public void getVerify( HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            randomValidateCodeHelper.getRandcode(response,getOpenId(),redisHelper);
        } catch (Exception e) {
            log.error("获取验证码失败>>>>   ", e);
        }
    }

    /**
     * 绑定手机号码
     * @param serialNum
     * @return
     */
    @ApiOperation(notes = "/bindSerialNum",value = "绑定手机号码")
    @RequestMapping(value = "/bindSerialNum",method = RequestMethod.POST)
    public GlobalResponse bindSerialNum(String serialNum,String imgCode,String smsCode,HttpServletRequest request){
        return userService.bindSerialNum(serialNum,imgCode,smsCode,getLoginUser(),request.getRemoteAddr());
    }

    @ApiOperation(notes = "/checkSmsVerCode",value = "检验验证码")
    @RequestMapping(value = "/checkSmsVerCode",method = RequestMethod.POST)
    public GlobalResponse checkSmsVerCode(String serialNum,String smsCode,HttpServletRequest request){
        return userService.checkSmsVerCode(serialNum,smsCode,request.getRemoteAddr());
    }

    @ApiOperation(notes = "/phone",value = "获取用户绑定手机号信息")
    @RequestMapping(value = "/phone",method = RequestMethod.POST)
    public GlobalResponse phone(String encryptedData, String iv) {
        return weService.phone(getUserId(),encryptedData,iv);
    }

    @ApiOperation(notes = "/info",value = "微信用户信息接口")
    @RequestMapping(value = "/info",method = RequestMethod.POST)
    public GlobalResponse info(String encryptedData, String iv) {
        return weService.info(encryptedData, iv,getLoginUser());
    }

    @ApiOperation(notes = "/findUserInfo",value = "获取用户信息接口")
    @RequestMapping(value = "/findUserInfo",method = RequestMethod.POST)
    public GlobalResponse findUserInfo(){
        return userService.findUserInfo(getUserId());
    }


}
