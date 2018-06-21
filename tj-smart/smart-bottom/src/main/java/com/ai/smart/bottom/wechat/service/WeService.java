package com.ai.smart.bottom.wechat.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.util.http.QrCodeRequestExecutor;
import com.ai.smart.bottom.common.service.ComSectionService;
import com.ai.smart.bottom.enums.RedisBottomKeyEnum;
import com.ai.smart.bottom.enums.WxQrCodeEnum;
import com.ai.smart.bottom.filter.LoginUser;
import com.ai.smart.bottom.user.mapper.BottomMaUserMapper;
import com.ai.smart.bottom.user.model.BottomMaUser;
import com.ai.smart.bottom.user.model.BottomUserQr;
import com.ai.smart.bottom.user.service.PorchService;
import com.ai.smart.bottom.user.service.UserQrService;
import com.ai.smart.bottom.user.service.UserService;
import com.ai.smart.bottom.vo.WxMaWxcodeLimitVo;
import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.common.helper.AesHelper;
import com.ai.smart.common.helper.AssertHelper;
import com.ai.smart.common.helper.FileHelper;
import com.ai.smart.common.helper.io.HttpHelper;
import com.ai.smart.common.helper.redis.RedisHelper;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.crypto.PKCS7Encoder;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import static cn.binarywang.wx.miniapp.api.WxMaQrcodeService.GET_WXACODE_UNLIMIT_URL;

/**
 * 小程序基础服务
 */
@Slf4j
@Service
public class WeService {

    @Autowired
    private UserService userService;

    @Autowired
    private PorchService porchService;

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private UserQrService userQrService;

    @Autowired
    private ComSectionService comSectionService;

    @Autowired
    private RedisHelper redisHelper;

    @Autowired
    private BottomMaUserMapper bottomMaUserMapper;

    @Value("${uploadIp}")
    private String uploadIp;
    /**
     * 通过code获取sessionkey
     * @param code
     * @return
     */
    public GlobalResponse getSessionInfo(String code){
        AssertHelper.notBlank(code,"用户code为空");
        try {
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
            // 存储用户信息
            WxMaUserInfo userInfo = new WxMaUserInfo();
            userInfo.setOpenId(session.getOpenid());
            userInfo.setUnionId(session.getUnionid());
            BottomMaUser bottomMaUser = userService.saveUserInfoByApp(userInfo);
            LoginUser loginUser = porchService.doLoginUser(bottomMaUser);
            HashMap<Object, Object> re = Maps.newHashMap();
            re.put("token",loginUser.getToken());
            re.put("userTag",loginUser.getId().toString());
            redisHelper.setex(RedisBottomKeyEnum.BOTTOM_SESSION_KEY_+bottomMaUser.getId().toString(),5*60,session.getSessionKey());
            return GlobalResponse.success(re);
        } catch (WxErrorException e) {
            log.error("getSessionInfo exception:", e);
        }
        return GlobalResponse.fail("获取用户session异常");
    }


    /**
     * 获取用户绑定手机号信息
     * @param encryptedData
     * @param iv
     * @return
     */
    public GlobalResponse phone(Long userId,String encryptedData, String iv){
        String sessionKey = redisHelper.get(RedisBottomKeyEnum.BOTTOM_SESSION_KEY_ + userId.toString());

        if (Strings.isNullOrEmpty(sessionKey)){
            return GlobalResponse.fail("sessionKey失效",-200);
        }
        String decrypt = decrypt(sessionKey, encryptedData, iv);
        WxMaPhoneNumberInfo phoneNoInfo = WxMaPhoneNumberInfo.fromJson(decrypt);//wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);
        // 校验手机号码是否为重庆移动
        GlobalResponse globalResponse = comSectionService.checkSectionNum(phoneNoInfo.getPhoneNumber());
        if (!globalResponse.isSuccess()){
            return GlobalResponse.fail("非重庆移动号码",-100);
        }
        //移除已经绑定的手机号码
        BottomMaUser bottomMaUser1 = new BottomMaUser();
        bottomMaUser1.setSerialNum(phoneNoInfo.getPhoneNumber());
        BottomMaUser bySeaialNum = bottomMaUserMapper.findBySeaialNum(bottomMaUser1);
        if (bySeaialNum != null){
            bottomMaUserMapper.clearSerialNum(bySeaialNum);
        }
        BottomMaUser bottomMaUser = new BottomMaUser();
        bottomMaUser.setId(userId);
        bottomMaUser.setSerialNum(phoneNoInfo.getPhoneNumber());
        bottomMaUser.setBindTime(new Date());
        bottomMaUserMapper.updateByPrimaryKeySelective(bottomMaUser);
        return GlobalResponse.success(bottomMaUser.getSerialNum());
    }

    /**
     * AES解密
     *
     * @param encryptedData 消息密文
     * @param ivStr         iv字符串
     */
    public static String decrypt(String sessionKey, String encryptedData, String ivStr) {
        try {
            AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
            params.init(new IvParameterSpec(Base64.decodeBase64(ivStr)));

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            byte[] keyByte  = Base64.decodeBase64(sessionKey);
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyByte, "AES"), params);

            return new String(PKCS7Encoder.decode(cipher.doFinal(Base64.decodeBase64(encryptedData))), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("AES解密失败", e);
        }
    }


    /**
     * 获取用户信息
     * @return
     */
    public GlobalResponse info(String encryptedData, String iv,LoginUser loginUser){
        // 处理用户信息
        String sessionKey = redisHelper.get(RedisBottomKeyEnum.BOTTOM_SESSION_KEY_ + loginUser.getId().toString());
        if (Strings.isNullOrEmpty(sessionKey)){
            return GlobalResponse.fail("sessionKey失效",-200);
        }
        //String decrypt = decrypt(sessionKey, encryptedData, iv);
        WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey,encryptedData,iv);
        BottomMaUser bottomMaUser = userService.saveUserInfoByApp(userInfo);
        return GlobalResponse.success(bottomMaUser);
    }


    /**
     * 生成小程序个人二维码
     * @param path
     * @return
     */
    public GlobalResponse getwxacodeunlimit(Long userId,String path){
        try {
            BottomUserQr byUserId = userQrService.findByUserId(userId);
            if (byUserId != null ){
                return GlobalResponse.success(byUserId.getUserQrUrl());
            }
            WxMaWxcodeLimitVo wxMaWxcodeLimit = new WxMaWxcodeLimitVo();
            wxMaWxcodeLimit.setScene(WxQrCodeEnum.A_.name()+userId);
            wxMaWxcodeLimit.setPage(path);
            wxMaWxcodeLimit.setWidth(150);
            wxMaWxcodeLimit.setAutoColor(true);
            wxMaWxcodeLimit.setLineColor(null);
            wxMaWxcodeLimit.set_hyaline(true);
            File wxCodeLimit = this.wxMaService.execute(new QrCodeRequestExecutor(this.wxMaService.getRequestHttp()),
                    GET_WXACODE_UNLIMIT_URL, wxMaWxcodeLimit);

            GlobalResponse pass = HttpHelper.upload(uploadIp+"/upload/uploadImg", wxCodeLimit, wxMaWxcodeLimit.getScene()+".png","pass");
            //存储二维码图片地址
            BottomUserQr bottomUserQr = new BottomUserQr();
            bottomUserQr.setUserId(userId);
            bottomUserQr.setUserQrUrl(pass.getData().toString());
            userQrService.saveBottomUserQr(bottomUserQr);
            return GlobalResponse.success(bottomUserQr.getUserQrUrl());
        } catch (Exception e) {
            log.error("getwxacodeunlimit exception",e);
        }
        return GlobalResponse.fail("ss");
    }

}
