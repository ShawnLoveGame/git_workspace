package com.he.exam.webapp.service.userCenter;

import com.he.exam.webapp.model.exam.ExamUser;
import com.he.exam.webapp.model.userCenter.UserLoginVo;
import com.he.exam.webapp.service.BaseService;
import com.he.exam.webapp.sso.constant.ConstantParam;
import com.he.exam.webapp.utils.CookieUtils;
import com.he.exam.webapp.utils.codec.AESUtils;
import com.he.exam.webapp.utils.json.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service("cookiesService")
public class CookiesService extends BaseService {

    private static final Log log = LogFactory.getLog(CookiesService.class);


    /**
     * Description ： 清除登录用户信息cookie
     *
     * @param domain
     */
    public void clearCookie(String name, String domain, HttpServletResponse response, HttpServletRequest request) {
        Cookie cookie = CookieUtils.getCookie(name, request);
        String path = "/";
        CookieUtils.deleteCookie(cookie, response, domain, path);
    }


    /**
     * Description ： 登录信息添加到cookie
     *
     * @param vo
     * @param response
     * @param request
     */
    public void doCookie(ExamUser vo, HttpServletResponse response, HttpServletRequest request) {
        try {
            UserLoginVo luv = new UserLoginVo();
            luv.setUserId(vo.getId());
            luv.setUserName(vo.getUserName());
            luv.setOpenid(vo.getOpenid());
            luv.setLoginTime(System.currentTimeMillis());
            luv.setUuid(vo.getUuid());
            luv.setHeadpic(vo.getHeadPic());
            luv.setNickName(vo.getNickName());

            request.setAttribute(ConstantParam.cookieLoginUser, luv);
            // 转化为字符串
            String userInfo = JsonUtils.toJson(luv);
            String path = "/";
            Integer maxAge = Integer.valueOf(ConstantParam.cookieMaxAge);
            // 清除cookie
            clearCookie(ConstantParam.cookieLoginUser, cookiesDomain, response, request);
            // userId  UserLoginVo
            CookieUtils.addCookie(response, ConstantParam.cookieLoginUser, AESUtils.encrypt(userInfo, ConstantParam.cookieLoginPassword), cookiesDomain, maxAge, true, path);
        } catch (Exception e) {
            log.error("loginCookie error:" ,e);
        }
    }
}
