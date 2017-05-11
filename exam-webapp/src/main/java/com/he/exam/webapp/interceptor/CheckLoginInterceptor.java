package com.he.exam.webapp.interceptor;

import com.he.exam.webapp.dao.exam.ExamUserMapper;
import com.he.exam.webapp.model.exam.ExamUser;
import com.he.exam.webapp.model.userCenter.UserLoginVo;
import com.he.exam.webapp.service.userCenter.CookiesService;
import com.he.exam.webapp.service.weixinCenter.CommonService;
import com.he.exam.webapp.sso.constant.ConstantParam;
import com.he.exam.webapp.utils.CookieUtils;
import com.he.exam.webapp.utils.codec.AESUtils;
import com.he.exam.webapp.utils.json.JsonUtils;
import com.he.exam.webapp.utils.shared.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * 登录拦截器<br>
 * 
 * daizy
 * 
 */
public class CheckLoginInterceptor extends HandlerInterceptorAdapter {

	private Log log = LogFactory.getLog(getClass());
	
	@Resource
	private CookiesService cookiesService;
	@Resource
	private CommonService commonService;
	@Resource private ExamUserMapper examUserMapper;
	@Resource
	public RedisUtils redisUtils;
	
	@Value("${mainDomain}")
	private String mainDomain;
	@Value("${wxAppId}")
	private String WX_APP_ID;
	@Value("${wxAppSecret}")
	public String WX_APP_SECRET;

	/**
	 * 拦截器
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
    @SuppressWarnings("deprecation")
	@Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		// 去request请求地址
		StringBuffer  url= request.getRequestURL();
		if (log.isDebugEnabled()) {
			log.debug("CheckLoginInterceptor preHandle url is : " + url);
		}
		String returnUrl = "";
		String q = request.getQueryString();
		if(StringUtils.isNotBlank(q)){
			q = "?"+q;
			returnUrl = URLEncoder.encode(url+q);
		}else{
			returnUrl = URLEncoder.encode(url.toString());
		}
		// cookie 解密
		String cookieValue = decodeCookie(request);
		// 解密成功
		if(StringUtils.isNotBlank(cookieValue)){
			UserLoginVo user = JsonUtils.fromJson(cookieValue, UserLoginVo.class);
			// 设置cookies
			ExamUser examUser = examUserMapper.selectByPrimaryKey(user.getUserId());
			cookiesService.doCookie(examUser, response, request);
			request.setAttribute(ConstantParam.cookieLoginUser, user);
			return true;
		}else{
			// 是微信浏览器
			String ua = ((HttpServletRequest) request).getHeader("user-agent").toLowerCase();
			String code = request.getParameter("code");
			if (ua.indexOf("micromessenger") > 0) {
				if(StringUtils.isNotBlank(code)){
					commonService.getWXOauthUserInfo(request, response);
					return true;
				}else{
					String u = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+WX_APP_ID+"&redirect_uri="+returnUrl+"&response_type=code&scope=snsapi_base&state=11#wechat_redirect";
					response.sendRedirect(u);
					return false;
				}
			}else{
				return true;
			}
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
