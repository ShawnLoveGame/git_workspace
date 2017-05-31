package com.he.im.interceptor;

import com.he.im.common.ConstantParam;
import com.he.im.model.profire.UserLoginVo;
import com.he.im.util.CookieUtils;
import com.he.im.util.codec.AESUtils;
import com.he.im.util.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器<br>
 *
 */
public class CheckLoginInterceptor extends HandlerInterceptorAdapter {

    private Log log = LogFactory.getLog(getClass());


    @Value("${mainDomain}")
    private String mainDomain;

    /**
     * 拦截器
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
        // 去request请求地址
        StringBuffer  url= request.getRequestURL();
        if (log.isDebugEnabled()) {
            log.debug("CheckLoginInterceptor preHandle url is : " + url);
        }
        
        if(url.toString().toUpperCase().contains("/seller/registerOne".toUpperCase())){
        	return true;
        }
        
        String cookieValue = decodeCookie(request);
        if(StringUtils.isNotBlank(cookieValue)){
            UserLoginVo user = JsonUtils.fromJson(cookieValue, UserLoginVo.class);
            request.setAttribute(ConstantParam.cookieLoginUser, user);
            request.setAttribute("backendOperatorVO", user);
            //判断用户是否在线
            return true;
        }else{
            response.sendRedirect(mainDomain + "/login/index");
            return false;
        }
    }

    /**
     * Description ： cookie解密
     *
     * @param request
     * @return
     *
     */
    public String decodeCookie(HttpServletRequest request){
        // 判断cookie中的值
        Cookie[] cookies = CookieUtils.getCookies(request);
        if(cookies == null){
            return null;
        }
        String loginuserInfoStr = null;
        String cookieValue = CookieUtils.getCookieValue(ConstantParam.cookieLoginUser, request);
        if(StringUtils.isNotEmpty(cookieValue)){
            loginuserInfoStr = AESUtils.decrypt2str(cookieValue, ConstantParam.cookieLoginPassword);
        }
        return loginuserInfoStr;
    }
}
