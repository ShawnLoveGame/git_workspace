package com.he.exam.controller.login;

import com.he.exam.constant.AnalyzeKeyConstant;
import com.he.exam.constant.CookieNameConstant;
import com.he.exam.controller.BaseController;
import com.he.exam.model.ModelResult;
import com.he.exam.model.ValidateCodeReturn;
import com.he.exam.model.backoperator.CtxSessionBag;
import com.he.exam.model.backoperator.SessionBagImpl;
import com.he.exam.model.backoperator.dto.BackendOperatorDTO;
import com.he.exam.model.backoperator.vo.BackOperatorVO;
import com.he.exam.service.backendOperator.BackendOperatorService;
import com.he.exam.service.user.UserService;
import com.he.exam.util.ValidateCodeUtils;
import com.he.exam.util.codec.CipherUtils;
import com.he.exam.util.codec.DesEncrypt;
import com.he.exam.util.ip.IpUtils;
import com.he.exam.util.json.JsonUtils;
import com.he.exam.util.shared.RedisUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 登录类
 * Created by he on 2016/12/1.
 */
@Controller
public class LoginController extends BaseController{
    private Log log = LogFactory.getLog(getClass());

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private BackendOperatorService backendOperatorService;
    private BeanCopier COPIER = BeanCopier.create(BackOperatorVO.class, BackOperatorVO.class, false);

    @Resource
    private UserService userService;

    @RequestMapping("/login/toLogin")
    public String toLogin(){
        return "login/toLogin";
    }

    /**
     * 获取验证码
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/login/getValidateCode")
    public void getValidateCode(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 获取验证码信息
        ValidateCodeReturn validateCodeReturn = ValidateCodeUtils.getValidateCode(80, 30, 4, new Color(242, 242, 242));
        // 生成redis获取验证码的KEY保存到cookie中
        String validateCodeKey = CipherUtils.MD5Encode(UUID.randomUUID().toString());
        // 保存到cookie
        Cookie cookie = new Cookie(CookieNameConstant.VALIDATE_CODE, validateCodeKey);
        cookie.setPath("/");
        resp.addCookie(cookie);
        // 保存验证码到redis，时间为1分钟
        redisUtils.setex(validateCodeKey, 10*60, validateCodeReturn.getValidateCode());
        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(validateCodeReturn.getValidateCodeImg(), "jpeg", sos);
        sos.close();
    }

    /**
     * Description ： 登录
     */
    @RequestMapping(value = "/login/login")
    @ResponseBody
    public ModelResult login(HttpServletRequest req, HttpServletResponse resp, BackendOperatorDTO backendOperatorDTO) {
        // 获取cookie
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            log.error("登录校验获取验证码COOKIE信息失败，用户名【" + backendOperatorDTO.getUserName() + "】");
            return new ModelResult(false, ModelResult.INPUT, "获取验证码失败");
        }
        // 获取cookie中的验证码KEY
        String validateCodeKey = "";
        for (Cookie cookie : cookies) {
            // 找到匹配的COOKIE信息
            if (CookieNameConstant.VALIDATE_CODE.equals(cookie.getName())) {
                validateCodeKey = cookie.getValue();
            }
        }
        if (validateCodeKey == "") {
            log.error("登录校验获取验证码COOKIE信息失败，用户名【" + backendOperatorDTO.getUserName() + "】");
            return new ModelResult(false, ModelResult.INPUT, "获取验证码失败");
        }
        // 去redis中查找验证码
        String validateCode = redisUtils.get(validateCodeKey);
        // 设置验证码
        backendOperatorDTO.setSessionValidateCode(validateCode);
        // 设置客户端IP
        backendOperatorDTO.setLastIp(IpUtils.getIpAddr(req));
        BackOperatorVO backendOperatorVO = null;
        // 登录校验
        try {
            backendOperatorVO = backendOperatorService.login(backendOperatorDTO);
        } catch (Exception e) {
            log.error("查询用户信息出错： " + e);
            return new ModelResult(false, ModelResult.INPUT, e.getMessage());
        }

        BackOperatorVO backOperatorVO = new BackOperatorVO();
        COPIER.copy(backendOperatorVO, backOperatorVO, null);

        // 用户信息放入线程中
        SessionBagImpl sessionBagImpl = new SessionBagImpl();
        sessionBagImpl.setBackOperatorVO(backOperatorVO);
        CtxSessionBag.clear();
        CtxSessionBag.setSessionBag(sessionBagImpl);
        // 生成KEY保存在cookie中
        String uuid = UUID.randomUUID().toString();
        String redisKey = CipherUtils.MD5Encode(uuid + "|" + backOperatorVO.getId());
        String cookieValue = "";
        try {
            cookieValue = DesEncrypt.encrypt(redisKey, AnalyzeKeyConstant.LOGIN_KEY);
        } catch (Exception e) {
            log.error("登录cookie信息DES加密失败" + e);
            return new ModelResult(false, ModelResult.INPUT, "登录异常");
        }
        // 生成cookie
        Cookie cookie = new Cookie(CookieNameConstant.LOGIN_COOKIE, cookieValue);
        cookie.setPath("/");
        resp.addCookie(cookie);
        // 存放redis缓存
        Map<String, String> map = new HashMap<String, String>();
        String BackOperatorVOJson = JsonUtils.toJson(backOperatorVO);
        map.put(redisKey, BackOperatorVOJson);
        redisUtils.setex(redisKey, 60 * 60 * 24, BackOperatorVOJson);
        // 判断是否勾选7天自动登录
        if (backendOperatorDTO.getAutoLogin() != null &&
                backendOperatorDTO.getAutoLogin() == 1) {
            // 生成cookie
            Cookie autoLoginCookie = new Cookie(CookieNameConstant.AUTO_LOGIN_COOKIE, backOperatorVO.getUserName());
            // 设置cookie失效时间为7天
            autoLoginCookie.setMaxAge(7 * 24 * 60 * 60);
            autoLoginCookie.setPath("/");
            // autoLoginCookie.setDomain("121.41.128.145");

            resp.addCookie(autoLoginCookie);
        }
        return new ModelResult(true, ModelResult.SUCCESS, "");

    }

    /**
     * Description ： 退出、注销
     */
    @RequestMapping(value = "/login/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // 清除TreadLocal信息
        CtxSessionBag.clear();
        // 清除cookie信息
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (CookieNameConstant.LOGIN_COOKIE.equals(cookie.getName())) {
                    // 设置cookie的生命为0（删除）
                    cookie.setMaxAge(0);
                    // 必须设置路径，否则无效
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    // 清除redies
                    try {
                        String redisKey = DesEncrypt.decrypt(cookie.getValue(), AnalyzeKeyConstant.LOGIN_KEY);
                        redisUtils.del(redisKey);
                    } catch (Exception e) {
                        log.error("用户注销删除缓存信息失败");
                    }
                } else if (CookieNameConstant.AUTO_LOGIN_COOKIE.equals(cookie.getName())) {
                    // 清除7天自动登录的cookie信息
                    cookie.setMaxAge(0);
                    // 必须设置路径，否则无效
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
        // 清除缓存
        return "login/toLogin";
    }

    @RequestMapping("/backend/toIndex")
    public String toIndex(HttpServletRequest re) {
        return "backend/index";
    }

    @RequestMapping("/backend/headPage")
    public String headPage(HttpServletRequest re) {
        return "backend/headPage";
    }

    @RequestMapping("/backend/menuPage")
    public String menuPage(HttpServletRequest re) {
        return "backend/menu";
    }

    @RequestMapping("/backend/welcomePage")
    public String welcomePage(Model model) {
        ModelResult result = userService.findWelComeData();
        result.rendering(model);
        return "backend/welcome";
    }
}
