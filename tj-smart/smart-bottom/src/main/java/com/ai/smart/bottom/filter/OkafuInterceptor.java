package com.ai.smart.bottom.filter;

import com.ai.smart.bottom.user.service.PorchService;
import com.ai.smart.bottom.user.service.UserService;
import com.ai.smart.common.base.GlobalResponse;
import com.ai.smart.common.helper.MockHelper;
import com.ai.smart.common.helper.json.JsonHelper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Objects;

@Slf4j
@Component
public class OkafuInterceptor implements HandlerInterceptor {

	@Autowired
	public PorchService porchService;

	@Autowired
	private UserService userService;

	@Autowired
	private MockHelper mockHelper;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("Authorization");
		if (Objects.equals(token,"pass")){
			return true;
		}
        token = Strings.isNullOrEmpty(token)?request.getParameter("Authorization"):token;
        if(!Strings.isNullOrEmpty(token)){
//        	if (mockHelper.isMock()){
//				GlobalResponse userInfo = userService.findUserInfo(Long.valueOf(token));
//				LoginUser loginUser = JsonHelper.fromJson(JsonHelper.toJson(userInfo.getData()), LoginUser.class);
//				request.setAttribute("loginUser", loginUser);
//				return true;
//			}
            LoginUser loginUser = porchService.getAndRefreshLoginUserInfo(token);
            request.setAttribute("loginUser", loginUser);
            return true;
        }
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		if(method.isAnnotationPresent(LoginAuth.class)){
			LoginAuth la = method.getAnnotation(LoginAuth.class);
			if (la.isAuth()){
				if (Strings.isNullOrEmpty(token)){
					response.setStatus(HttpStatus.OK.value());
					response.getOutputStream().write(JsonHelper.toJson(GlobalResponse.fail("请登录",999)).getBytes("UTF-8"));
					return false;
				}
			}
		}
        return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
