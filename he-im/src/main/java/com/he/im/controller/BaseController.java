package com.he.im.controller;

import com.he.im.common.ConstantParam;
import com.he.im.model.profire.UserLoginVo;
import com.he.im.util.CookieUtils;
import com.he.im.util.codec.AESUtils;
import com.he.im.util.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by he on 2017/4/25.
 */

public class BaseController {


    // 获取后台用户信息
    public UserLoginVo getBackendOperator(HttpServletRequest req ) {

        UserLoginVo userLoginVo = (UserLoginVo) req.getAttribute(ConstantParam.cookieLoginUser);
        if(userLoginVo == null ){
            //decode cookie
            String loginuserInfoStr = null;
            String cookieValue = CookieUtils.getCookieValue(ConstantParam.cookieLoginUser, req);
            if(StringUtils.isNotEmpty(cookieValue)){
                loginuserInfoStr = AESUtils.decrypt2str(cookieValue, ConstantParam.cookieLoginPassword);
                userLoginVo  = JsonUtils.fromJson(loginuserInfoStr , UserLoginVo.class);
            }
        }
        return userLoginVo;
    }
}
