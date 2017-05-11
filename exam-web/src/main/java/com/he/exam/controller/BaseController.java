package com.he.exam.controller;

import com.he.exam.constant.AnalyzeKeyConstant;
import com.he.exam.constant.CookieNameConstant;
import com.he.exam.exception.AlikAssert;
import com.he.exam.model.backoperator.CtxSessionBag;
import com.he.exam.model.backoperator.vo.BackOperatorVO;
import com.he.exam.util.codec.DesEncrypt;
import com.he.exam.util.json.JsonUtils;
import com.he.exam.util.shared.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BaseController {

    @Resource
    public RedisUtils redisUtils;
    
    @Value("${redis.url}")
	public String redisUrl;
    
    protected final Log log = LogFactory.getLog(getClass());

    // 获取后台用户信息
    public BackOperatorVO getBackendOperator(HttpServletRequest req) {
    	//查看线程是否存在
        if (CtxSessionBag.getSessionBag() != null && CtxSessionBag.getSessionBag().getBackOperatorVO() != null) {
            return CtxSessionBag.getSessionBag().getBackOperatorVO();
    	}
        // 判断cookie中的值
        Cookie[] cookies = req.getCookies();
        // redis中的KEY值（加密的）
        String redisKeyEncryption = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 找到匹配的COOKIE信息
                if (CookieNameConstant.LOGIN_COOKIE.equals(cookie.getName())) {
                    // 找到匹配的COOKIE信息
                    redisKeyEncryption = cookie.getValue();
                }
            }
        }
        String redisKey = "";
        // 解密操作
        try {
            redisKey = DesEncrypt.decrypt(redisKeyEncryption, AnalyzeKeyConstant.LOGIN_KEY);
        } catch (Exception e) {
            log.error("获取用户信息，解密KEY失败:" + e);
            return null;
        }
        // 去redis中查找用户信息
        String backOperatorVOJson = redisUtils.get(redisKey);
        if (!StringUtils.isBlank(backOperatorVOJson)) {
            // JSON转对象
            BackOperatorVO backOperatorVO = JsonUtils.fromJson(backOperatorVOJson, BackOperatorVO.class);
            return backOperatorVO;
        } else {
            return null;
        }
    }

    /**
     * 
     * 异步返回信息到页面<br>
     * 
     * daizy
     * 
     * @param msg
     * @since
     * 
     */
    public void returnMessage(String msg, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        PrintWriter pWriter = null;
        try {
            pWriter = response.getWriter();
            pWriter.write(msg);
        } catch (IOException e) {
            //
        } finally {
            if (pWriter != null) {
                pWriter.flush();
                pWriter.close();
            }
        }
    }

    /**
     * Description ： 查看是否有权限<br>
     *
     */
    public void hasFunction(BackOperatorVO backOperatorVO, Long operatorAreaId) {
        if (operatorAreaId == null || backOperatorVO == null) {
            AlikAssert.isFalse(true, "您没有权限操作！");
        }
    }

    /**
     * Description ： 校验是否登陆<br>
     */
    public BackOperatorVO validateIsLogin(HttpServletRequest request) {
        // 获得操作人
        BackOperatorVO backOperatorVO = this.getBackendOperator(request);
        if (backOperatorVO == null) {
            AlikAssert.isFalse(true, "您没有登陆，请重新登陆！");
        }
        return backOperatorVO;
    }


}
