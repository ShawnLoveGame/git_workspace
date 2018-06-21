package com.ai.smart.bottom.user.service;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.ai.smart.bottom.common.service.ComSectionService;
import com.ai.smart.bottom.enums.RedisBottomKeyEnum;
import com.ai.smart.bottom.filter.LoginUser;
import com.ai.smart.bottom.user.mapper.BottomMaUserMapper;
import com.ai.smart.bottom.user.model.BottomMaUser;
import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.common.helper.AssertHelper;
import com.ai.smart.common.helper.DateHelper;
import com.ai.smart.common.helper.MockHelper;
import com.ai.smart.common.helper.redis.RedisHelper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Objects;

/**
 * 用户信息服务
 */
@Slf4j
@Service
public class UserService {


    @Autowired
    private BottomMaUserMapper bottomMaUserMapper;

    @Autowired
    private ComSectionService comSectionService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private MockHelper mockHelper;

    public GlobalResponse sendSmsCode(String serialNum,String ip){
        AssertHelper.notBlank(serialNum,"手机号码必填");
        // 缓存判断是否频繁操作
        String redisJson = redisHelper.get(RedisBottomKeyEnum.BOTTOM_SMS_CODE_.name() + serialNum);
        if (!Strings.isNullOrEmpty(redisJson)){
            return GlobalResponse.fail("验证码5分钟内有效，不可频繁发送哦！");
        }
        // 校验手机号码号段
        GlobalResponse globalResponse = comSectionService.checkSectionNum(serialNum);
        if (!globalResponse.isSuccess()){
            return globalResponse;
        }
        if (!checkTenByOndate(serialNum)){
            return GlobalResponse.fail("同一个手机号码一天只能发送10次验证码哦！");
        }
        // 发送短信
        globalResponse = smsService.sendSmsVerCode(serialNum,"" ,ip);
        if (!globalResponse.isSuccess()){
            return globalResponse;
        }
        // 缓存验证码
        redisHelper.setex(RedisBottomKeyEnum.BOTTOM_SMS_CODE_.name() + serialNum,1*60,"ss");
        return GlobalResponse.success("短信验证码发送成功！");
    }


    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    public GlobalResponse findUserInfo(Long userId){
        return GlobalResponse.success(bottomMaUserMapper.selectByPrimaryKey(userId));
    }

    /**
     * 判断同一手机号每日发送频率
     * @param serialNum
     * @return
     */
    public boolean checkTenByOndate(String serialNum){
        String yyyyMMdd = DateHelper.converToStringDate(new Date(), "yyyyMMdd");
        String js = redisHelper.get(RedisBottomKeyEnum.BOTTOM_SMS_CODE_.name() + serialNum + "_" + yyyyMMdd);
        if (Strings.isNullOrEmpty(js)){
            js = "0";
        }
        if (Integer.valueOf(js) > 9){
            return false;
        }
        int i = Integer.valueOf(js) + 1;
        redisHelper.setex(RedisBottomKeyEnum.BOTTOM_SMS_CODE_.name() + serialNum + "_" + yyyyMMdd,60*60*24,i);
        return true;
    }


    /**
     * 通过openid获取用户信息
     * @param openId
     * @return
     */
    public BottomMaUser findUserInfoByOpenId(String openId){
        return bottomMaUserMapper.selectByOpenId(openId);
    }

    /**
     * 保存用户信息
     * @param userInfo
     */
    public BottomMaUser saveUserInfoByApp(WxMaUserInfo userInfo){
        AssertHelper.notNull(userInfo,"授权用户信息为空");
        // 查询用户是否已经存在
        BottomMaUser user = bottomMaUserMapper.selectByOpenId(userInfo.getOpenId());
        if (user == null){
            //新增用户
            user = new BottomMaUser();
            user.setCreateTime(new Date());
            user.setHeadImg(userInfo.getAvatarUrl());
            if (!Strings.isNullOrEmpty(userInfo.getNickName())){
                user.setNickName(URLEncoder.encode(userInfo.getNickName()));
            }
            user.setOpenId(userInfo.getOpenId());
            user.setCity(userInfo.getCity());
            user.setCountry(userInfo.getCountry());
            user.setLanguage(userInfo.getLanguage());
            user.setGender(Integer.valueOf(Strings.isNullOrEmpty(userInfo.getGender())?"2":userInfo.getGender()));
            user.setProvince(userInfo.getProvince());
            user.setUnionid(userInfo.getUnionId());
            bottomMaUserMapper.insert(user);
            return user;
        }
        //user.setCreateTime(new Date());
        user.setHeadImg(userInfo.getAvatarUrl());
        if (!Strings.isNullOrEmpty(userInfo.getNickName())){
            user.setNickName(URLEncoder.encode(userInfo.getNickName()));
        }
        user.setCity(userInfo.getCity());
        user.setCountry(userInfo.getCountry());
        user.setLanguage(userInfo.getLanguage());
        user.setGender(Integer.valueOf(Strings.isNullOrEmpty(userInfo.getGender())?"2":userInfo.getGender()));
        user.setProvince(userInfo.getProvince());
        user.setUnionid(userInfo.getUnionId());
        bottomMaUserMapper.updateByPrimaryKeySelective(user);
        return user;
    }


    /**
     * 绑定手机号码
     * @param serialNum
     * @return
     */
    public GlobalResponse bindSerialNum(String serialNum,String imgCode,String smsCode,LoginUser loginUser,String ip){
        AssertHelper.notBlank(serialNum,"手机号码必填");
        AssertHelper.notBlank(imgCode,"图片验证码必填");
        AssertHelper.notBlank(smsCode,"短信验证码必填");
        // 判断手机号码是否正常
        GlobalResponse globalResponse = comSectionService.checkSectionNum(serialNum);
        if (!globalResponse.isSuccess()){
            return globalResponse;
        }
        String redisJson = redisHelper.get(RedisBottomKeyEnum.BOTTOM_SMS_CODE_.name() + serialNum);
        if (Strings.isNullOrEmpty(redisJson)){
            return GlobalResponse.fail("短信验证码已经失效，请重新获取！");
        }
        String vjson = redisHelper.get( RedisBottomKeyEnum.BOTTOM_IMG_VER_CODE_.name() + loginUser.getOpenId());
        if (Strings.isNullOrEmpty(vjson)){
            return GlobalResponse.fail("图形验证码已经失效，请重新获取！");
        }
        if (!Objects.equals(imgCode,vjson)){
            return GlobalResponse.fail("图形验证码输入不正确！");
        }
        //验证验证码是否正确
        globalResponse = smsService.checkSmsVerCode(serialNum, smsCode, ip);
        if (!globalResponse.isSuccess()){
            return globalResponse;
        }

        //移除已经绑定的手机号码
        BottomMaUser bottomMaUser1 = new BottomMaUser();
        bottomMaUser1.setSerialNum(serialNum);
        BottomMaUser bySeaialNum = bottomMaUserMapper.findBySeaialNum(bottomMaUser1);
        if (bySeaialNum != null){
            bottomMaUserMapper.clearSerialNum(bySeaialNum);
        }
        BottomMaUser bottomMaUser = new BottomMaUser();
        bottomMaUser.setId(loginUser.getId());
        bottomMaUser.setSerialNum(serialNum);
        bottomMaUser.setBindTime(new Date());
        bottomMaUserMapper.updateByPrimaryKeySelective(bottomMaUser);
        return GlobalResponse.success(serialNum);
    }


    public GlobalResponse checkSmsVerCode(String serialNum,String smsCode,String ip){
        AssertHelper.notBlank(serialNum,"手机号码必填");
        AssertHelper.notBlank(smsCode,"短信验证码必填");
        if (mockHelper.isMock()){
            return GlobalResponse.success("短信验证码验证成功！");
        }
        // 判断手机号码是否正常
        GlobalResponse globalResponse = comSectionService.checkSectionNum(serialNum);
        if (!globalResponse.isSuccess()){
            return globalResponse;
        }
        globalResponse = smsService.checkSmsVerCode(serialNum, smsCode, ip);
        if (!globalResponse.isSuccess()){
            return globalResponse;
        }
        return globalResponse;
    }
}
