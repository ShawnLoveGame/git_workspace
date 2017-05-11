package com.he.exam.webapp.sso.controller;

import com.he.exam.webapp.client.RedisEnum;
import com.he.exam.webapp.model.userCenter.UserLoginVo;
import com.he.exam.webapp.service.userCenter.CookiesService;
import com.he.exam.webapp.service.userCenter.LoginService;
import com.he.exam.webapp.sso.constant.ConstantParam;
import com.he.exam.webapp.utils.CookieUtils;
import com.he.exam.webapp.utils.codec.AESUtils;
import com.he.exam.webapp.utils.json.JsonUtils;
import com.he.exam.webapp.utils.shared.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 
 * 基础控制器
 * 
 * @author daizy
 *
 */
public class BaseController extends SSOController {
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${cookiesDomain}")
	public String cookiesDomin;
	
	@Resource
	private LoginService loginService;
	@Resource
	private CookiesService cookiesService;
	@Resource
	private RedisUtils redisUtils;
	
	/**
	 * Description ： 清除登录状态
	 *
	 */
	public void clearLogin(HttpServletResponse response, HttpServletRequest request) {
		try {// 清除登录缓存
			String loginuserInfoStr = null;
			UserLoginVo loginUserVo = null;
			String cookieValue = CookieUtils.getCookieValue(ConstantParam.cookieLoginUser, request);
	        if(StringUtils.isNotEmpty(cookieValue)){
	        	loginuserInfoStr = AESUtils.decrypt2str(cookieValue, ConstantParam.cookieLoginUser);
				if(StringUtils.isNotEmpty(loginuserInfoStr)){
					loginUserVo = JsonUtils.fromJson(loginuserInfoStr, UserLoginVo.class);
				}
			}
	        if (null != loginUserVo) {
	        	redisUtils.del(RedisEnum.REDIS_LOGIN_USER_INFO_.getKey() + loginUserVo.getUserId());
	        }
		} catch (Exception e) {
			logger.error("redis del error", e);
		}
		cookiesService.clearCookie(ConstantParam.cookieLoginUser, cookiesDomin, response, getRequest());
	}

	/**
	 * Description ： 获取cookie 中的昵称 
	 * @return
	 */
	public String userNickName() {
		try {
			String value = CookieUtils.getCookieValue(ConstantParam.cookieUNickName, getRequest());
			if(StringUtils.isNotBlank(value)){
				return URLDecoder.decode(value,"utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

}
