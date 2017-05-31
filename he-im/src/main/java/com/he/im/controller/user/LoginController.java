package com.he.im.controller.user;

import com.he.im.common.ConstantParam;
import com.he.im.conn.ImConnection;
import com.he.im.model.ModelResult;
import com.he.im.model.profire.UserLoginVo;
import com.he.im.service.user.UserService;
import com.he.im.util.CookieUtils;
import com.he.im.util.codec.AESUtils;
import com.he.im.util.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by he on 2017/4/24.
 */
@Controller
public class LoginController {

    private Log log = LogFactory.getLog(getClass());

    @Resource
    private UserService userService;

    @Resource private ImConnection imConnection;

    @RequestMapping("/login/index")
    public String toLoginPage(){

        return "user/loginPage";
    }



    @RequestMapping(value = "/login/toLogin", method = RequestMethod.POST)
    @ResponseBody
    public ModelResult toLogin(HttpServletRequest request , HttpServletResponse response , HttpSession httpSession) {
        String jid = request.getParameter("jid");
        String pwd = request.getParameter("pwd");
        ModelResult result = userService.toLogin(jid, pwd , response , request);
        if(result.isSuccess()){
            httpSession.setAttribute("userName", jid);
        }
        return result;
    }

    /**
     * Description ： 退出、注销
     */
    @RequestMapping(value = "/login/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ConstantParam.cookieLoginUser.equals(cookie.getName())) {
                    // 设置cookie的生命为0（删除）
                    cookie.setMaxAge(0);
                    // 必须设置路径，否则无效
                    cookie.setPath("/");
                    response.addCookie(cookie);

                    //
                    String loginuserInfoStr = null;
                    String cookieValue = CookieUtils.getCookieValue(ConstantParam.cookieLoginUser, request);
                    if(StringUtils.isNotEmpty(cookieValue)){
                        loginuserInfoStr = AESUtils.decrypt2str(cookieValue, ConstantParam.cookieLoginPassword);
                        UserLoginVo userLoginVo  = JsonUtils.fromJson(loginuserInfoStr , UserLoginVo.class);
                        //清除当前用户登录状态
                        imConnection.logout(userLoginVo.getUserName().replaceAll("@" , "+40").replaceAll(" ", "-40"));
                    }

                }
            }
        }
        return "redirect:/login/index";
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


    @RequestMapping("/backend/changePwd")
    public String changePwd(HttpServletRequest re) {
        return "backend/changePwdPage";
    }

}
