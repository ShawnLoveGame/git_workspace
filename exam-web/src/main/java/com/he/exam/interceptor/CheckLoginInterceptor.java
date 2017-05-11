package com.he.exam.interceptor;

import com.he.exam.constant.AnalyzeKeyConstant;
import com.he.exam.constant.CookieNameConstant;
import com.he.exam.model.backoperator.CtxSessionBag;
import com.he.exam.model.backoperator.SessionBagImpl;
import com.he.exam.model.backoperator.dto.BackendOperatorDTO;
import com.he.exam.model.backoperator.vo.BackOperatorVO;
import com.he.exam.service.backendOperator.BackendOperatorService;
import com.he.exam.util.codec.CipherUtils;
import com.he.exam.util.codec.DesEncrypt;
import com.he.exam.util.ip.IpUtils;
import com.he.exam.util.json.JsonUtils;
import com.he.exam.util.shared.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 登录拦截器<br>
 * 
 */
public class CheckLoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private BackendOperatorService backendOperatorService;
    
//    @Value("${base}")
//    public String mainDomain;
    
    @Value("${cookDomain}")
    public String cookDomain;

    protected final Log log = LogFactory.getLog(getClass());

    private BeanCopier COPIER = BeanCopier.create(BackOperatorVO.class, BackOperatorVO.class, false);

    /**
     * 
     * Method Name : preHandle<br>
     * 
     * Description : 登录拦截器
     * 
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     * @since
     *
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断cookie中的值
        Cookie[] cookies = request.getCookies();
        // redis中的KEY值
        String redisKey = null;
        // 用户名
        String userName = null;
        if (cookies == null) {
            response.sendRedirect("/login/toLogin");
            return false;
        } else {
            for (Cookie cookie : cookies) {
                // 找到匹配的COOKIE信息
                if (CookieNameConstant.LOGIN_COOKIE.equals(cookie.getName())) {
                    // cookieValue解密
                    try {
                        redisKey = DesEncrypt.decrypt(cookie.getValue(), AnalyzeKeyConstant.LOGIN_KEY);
                    } catch (Exception e) {
                        log.error(e);
                    }
                } else if (CookieNameConstant.AUTO_LOGIN_COOKIE.equals(cookie.getName())) {
                    // 7天自动登录的用户信息
                    userName = cookie.getValue();
                }
            }
        }
        // 登录cookie为空
        if (redisKey == null) {
            if (userName != null) {
                // 自动登录操作
                BackOperatorVO backOperatorVO = autoLogin(userName, request, response);
                if (backOperatorVO == null) {
                    response.sendRedirect("/login/toLogin");
                    return false;
                } else {
                    // 用户信息存放到ThreadLocal
                    SessionBagImpl sessionBagImpl = new SessionBagImpl();
                    sessionBagImpl.setBackOperatorVO(backOperatorVO);
                    CtxSessionBag.clear();
                    CtxSessionBag.setSessionBag(sessionBagImpl);
                    request.setAttribute("backendOperatorVO", backOperatorVO);
                    return true;
                }
            } else {
                // 清除ThreadLocal中的信息
                response.sendRedirect("/login/toLogin");
                return false;
            }

        } else {
            String backendOperatorVOJson = redisUtils.get(redisKey);
            if (!StringUtils.isBlank(backendOperatorVOJson)) {
                // JSON转对象
                BackOperatorVO backOperatorVO = JsonUtils.fromJson(backendOperatorVOJson, BackOperatorVO.class);
                // 用户信息存放到ThreadLocal
                SessionBagImpl sessionBagImpl = new SessionBagImpl();
                sessionBagImpl.setBackOperatorVO(backOperatorVO);
                CtxSessionBag.clear();
                CtxSessionBag.setSessionBag(sessionBagImpl);
                request.setAttribute("backendOperatorVO", backOperatorVO);
                return true;
            } else {
                if (userName != null) {
                    // 自动登录
                    BackOperatorVO backOperatorVO = autoLogin(userName, request, response);
                    if (backOperatorVO == null) {
                        response.sendRedirect("/login/toLogin");
                        return false;
                    } else {
                        // 用户信息存放到ThreadLocal
                        SessionBagImpl sessionBagImpl = new SessionBagImpl();
                        sessionBagImpl.setBackOperatorVO(backOperatorVO);
                        CtxSessionBag.clear();
                        CtxSessionBag.setSessionBag(sessionBagImpl);
                        request.setAttribute("backendOperatorVO", backOperatorVO);
                        return true;
                    }
                } else {
                    response.sendRedirect("/login/toLogin");
                    return false;
                }
            }
        }
    }

    /**
     * 后处理（调用了Service并返回ModelAndView，但未进行页面渲染）
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }


    public boolean isAjaxRequest(HttpServletRequest request){
        String requestType = request.getHeader("X-Requested-With");

        boolean isAjax = "XMLHttpRequest".equalsIgnoreCase(requestType) ? true:false;
        return isAjax;
    }

    /**
     * 返回处理（已经渲染了页面）
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /**
         * 清除线程
         */
        CtxSessionBag.clear();
    }

    /**
     * Description ： 自动登录
     * 
     * daizy
     * 
     * @since
     *
     */
    public BackOperatorVO autoLogin(String userName, HttpServletRequest request, HttpServletResponse response) {
        // 用户查询对象 入参
        BackendOperatorDTO backendOperatorDTO = new BackendOperatorDTO();
        backendOperatorDTO.setUserName(userName);
        // 根据用户名查找用户
        BackOperatorVO backendOperatorVO = backendOperatorService.findBackendOperatorByDTO(backendOperatorDTO);
        if (backendOperatorVO == null) {
            return null;
        }
        BackOperatorVO backOperatorVO = new BackOperatorVO();
        COPIER.copy(backendOperatorVO, backOperatorVO, null);
        
        // 获取客户端IP
        String iP = IpUtils.getIpAddr(request);
        // 校验上一次登录IP是否与本次相同
        if (!StringUtils.isBlank(backOperatorVO.getLastIp()) && !StringUtils.isBlank(iP) && iP.equals(backOperatorVO.getLastIp())) {
            // 登录操作
            // 用户信息放入线程中
            SessionBagImpl sessionBagImpl = new SessionBagImpl();
            sessionBagImpl.setBackOperatorVO(backOperatorVO);
            CtxSessionBag.clear();
            CtxSessionBag.setSessionBag(sessionBagImpl);
            // 生成KEY保存在cookie中
            String uuid = UUID.randomUUID().toString();
            String redisKey = CipherUtils.MD5Encode(uuid + "|" + backOperatorVO.getId());
            String redisKeyEncryption = "";
            try {
                redisKeyEncryption = DesEncrypt.encrypt(redisKey, AnalyzeKeyConstant.LOGIN_KEY);
            } catch (Exception e) {
                log.error("登录cookie信息DES加密失败" + e);
                return null;
            }
            // 生成cookie
            Cookie cookie = new Cookie(CookieNameConstant.LOGIN_COOKIE, redisKeyEncryption);

            cookie.setPath("/");
            cookie.setDomain(cookDomain);
            // 添加cookie
            response.addCookie(cookie);
            // 存放redis缓存
            try {
                Map<String, String> map = new HashMap<String, String>();
                String backendOperatorVOJson = JsonUtils.toJson(backOperatorVO);
                map.put(redisKey, backendOperatorVOJson);
                // 去缓存中获取用户信息
                redisUtils.setex(redisKey, 60 * 60 *24 , backendOperatorVOJson);
            } catch (Exception e) {
                log.error("登录设置缓存信息异常：" + e);
                return null;
            }
            return backOperatorVO;
        }
        return null;
    }
    
}
