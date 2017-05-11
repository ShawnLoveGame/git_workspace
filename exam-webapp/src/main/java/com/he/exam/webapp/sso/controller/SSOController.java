package com.he.exam.webapp.sso.controller;

import com.he.exam.webapp.model.exam.ExamUser;
import com.he.exam.webapp.model.userCenter.UserLoginVo;
import com.he.exam.webapp.sso.constant.ConstantParam;
import com.he.exam.webapp.utils.CookieUtils;
import com.he.exam.webapp.utils.codec.AESUtils;
import com.he.exam.webapp.utils.codec.EncodeUtils;
import com.he.exam.webapp.utils.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 
 * sso控制器
 * 
 * @author daizy
 *
 */
public class SSOController {
	
	private transient static final Log logger = LogFactory.getLog(SSOController.class);
	
	public static final String UTF8 = "UTF-8";
	
	@Value("${mainDomain}")
	public String mainDomain;

	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
    /**
     * Description ： 这里记述method的说明
     * 
     * @return
     *
     */
	public String getReturnURL() {
		String obj = getRequest().getParameter("returnURL");
		if (StringUtils.isBlank(obj) || (obj.equals(mainDomain) || obj.equals(mainDomain+"/"))) {
            return EncodeUtils.urlDecode("", UTF8);// 个人中心
		} else {
			return EncodeUtils.urlDecode(obj, UTF8);
		}
	}

	    /**
     * Description ： 获取登录用户信息 
     * 
     * @return
     * 
     */
	/*public UserLoginVo getLoginUser() {
		UserLoginVo obj = (UserLoginVo) getRequest().getAttribute(ConstantParam.cookieLoginUser);
		return obj != null ? obj : null;

	}*/

	/**
     * Description ： 获取用户Id 
     * 
     * @return
     * 
     */
	public Long getEndUserId() {
		UserLoginVo obj = getEndUser(getRequest());
		return obj != null ? obj.getUserId() : null;
	}

	/**
     * Description ： 获取用户
     * 
     * @return
     * 
     */
	public String getEndUserName() {
		ExamUser obj = (ExamUser) getRequest().getAttribute(ConstantParam.cookieLoginUser);
		return obj != null ? obj.getUserName() : "";
	}
	
	
	/**
     * Description ： 获取用户openid
     * 
     * @return
     * 
     */
	public String getOpenId() {
		ExamUser obj = (ExamUser) getRequest().getAttribute(ConstantParam.cookieLoginUser);
		return obj != null ? obj.getOpenid() : "";
	}
	
	/**
     * Description ： 判断是否已登录 
     * 
     * @return
     * @throws IOException
     * 
     */
	public boolean isLogin() {
		return getRequest().getAttribute(ConstantParam.cookieLoginUser) == null ? false: true;
	}

	/**
	 * Description ： cookie解密
	 *
	 * @param request
	 * @return
	 *
	 */
	public UserLoginVo getEndUser(HttpServletRequest request){
		String code = request.getParameter("code");
		UserLoginVo user = (UserLoginVo) request.getAttribute(ConstantParam.cookieLoginUser);
		if(user != null){
			return user;
		}
		// 判断cookie中的值
		Cookie cookies = CookieUtils.getCookie(ConstantParam.cookieLoginUser , request);
		if(cookies != null){
			String cookieValue = CookieUtils.getCookieValue(ConstantParam.cookieLoginUser, request);
			if(StringUtils.isNotEmpty(cookieValue)){
				cookieValue = AESUtils.decrypt2str(cookieValue, ConstantParam.cookieLoginPassword);
				user = JsonUtils.fromJson(cookieValue, UserLoginVo.class);
				return user;
			}
		}
		return null;
	}
	

	 /**
     * 把对象输出到页面
     * 
     * @param obj
     */
	public void ajaxPrintPage(Object obj, HttpServletResponse response) {
		response.setCharacterEncoding(UTF8);
		PrintWriter writer = null;
		try {
			try {
				writer = response.getWriter();
				if (null == obj) {
					writer.print("");
				} else {
					writer.print(obj.toString());
				}
			} catch (IOException e) {
				logger.error("ajax print page error:", e);
			}
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}

}
