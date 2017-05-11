package com.he.exam.webapp.servlet;

import com.he.exam.webapp.service.weixinCenter.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author daizy
 * 微信推送servlet
 *
 */
@SuppressWarnings("serial")
@Component
public class WxOauthServlet extends HttpServlet{
	
	private  Logger log = LoggerFactory.getLogger(WxOauthServlet.class);
	@Value("${mainDomain}")
	public String mainDomain;
	
	@Resource
    private CommonService commonService;
    
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			commonService.getWXOauthUserInfo(request, response);
			String returnUrl = request.getParameter("returnUrl");
			log.error("授权回写："+returnUrl);
			response.sendRedirect(returnUrl);
			return ;
		} catch (Exception e) {
			log.error("微信网页授权回写异常",e);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}


}
