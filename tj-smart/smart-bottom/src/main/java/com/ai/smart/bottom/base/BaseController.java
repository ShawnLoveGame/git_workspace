package com.ai.smart.bottom.base;

import com.ai.smart.bottom.filter.LoginUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取登录用户信息
     *
     * @return
     */
    public LoginUser getLoginUser() {
        LoginUser userVO = (LoginUser) getRequest().getAttribute("loginUser");
        return userVO != null ? userVO : null;
    }

    /**
     * 获取登录用户ID
     *
     * @return
     */
    public long getUserId() {
        LoginUser userVO = (LoginUser) getRequest().getAttribute("loginUser");
        return userVO != null ? userVO.getId() : null;
    }

    public String getOpenId() {
        LoginUser userVO = (LoginUser) getRequest().getAttribute("loginUser");
        return userVO != null ? userVO.getOpenId() : null;
    }

    public String getSerialNumber() {
        LoginUser userVO = (LoginUser) getRequest().getAttribute("loginUser");
        return userVO != null ? userVO.getSerialNumber() : null;
    }
}
