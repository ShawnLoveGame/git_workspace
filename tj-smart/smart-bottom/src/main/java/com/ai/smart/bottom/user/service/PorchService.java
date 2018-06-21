package com.ai.smart.bottom.user.service;

import com.ai.smart.bottom.enums.TokenEnum;
import com.ai.smart.bottom.filter.LoginUser;
import com.ai.smart.bottom.user.model.BottomMaUser;
import com.ai.smart.common.helper.AesHelper;
import com.ai.smart.common.helper.redis.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PorchService {


    @Autowired
    private UserService userService;

    /**
     * 解析token
     * @param token
     * @return
     */
    public LoginUser getAndRefreshLoginUserInfo(String token){
        // 解密
        String decrypt = AesHelper.decrypt(token, TokenEnum.BOTTOM_U.name());
        // 获取用户信息
        BottomMaUser userInfo = userService.findUserInfoByOpenId(decrypt);
        if (userInfo == null){
            return null;
        }
        LoginUser loginUser = new LoginUser();
        loginUser.setNickName(userInfo.getNickName());
        loginUser.setOpenId(userInfo.getOpenId());
        loginUser.setSerialNumber(userInfo.getSerialNum());
        loginUser.setId(userInfo.getId());
        loginUser.setHeadImg(userInfo.getHeadImg());
        return loginUser;
    }

    public LoginUser doLoginUser(BottomMaUser userInfo){
        if (userInfo != null){
            final String token = AesHelper.encrypt(userInfo.getOpenId(), TokenEnum.BOTTOM_U.name());
            LoginUser loginUser = new LoginUser();
            loginUser.setNickName(userInfo.getNickName());
            loginUser.setOpenId(userInfo.getOpenId());
            loginUser.setSerialNumber(userInfo.getSerialNum());
            loginUser.setToken(token);
            loginUser.setId(userInfo.getId());
            loginUser.setHeadImg(userInfo.getHeadImg());
            return loginUser;
        }
        return null;
    }
}
