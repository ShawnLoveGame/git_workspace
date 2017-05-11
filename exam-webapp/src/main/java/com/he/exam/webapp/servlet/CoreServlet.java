package com.he.exam.webapp.servlet;

import com.he.exam.webapp.service.weixinCenter.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 */
@SuppressWarnings("serial")
@Component
public class CoreServlet extends HttpServlet{
	
	private static Logger log = LoggerFactory.getLogger(CoreServlet.class);
	
	@Resource
    private CommonService commonService;
    

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
//	 / 微信加密签名
	        String signature = request.getParameter("signature");
	        // 时间戮
	        String timestamp = request.getParameter("timestamp");
	        // 随机数
	        String nonce = request.getParameter("nonce");
	        // 随机字符串
	        String echostr = request.getParameter("echostr");
	        log.error(">>" + signature + "   ---aa>" + echostr);
	        // 通过检验 signature 对请求进行校验，若校验成功则原样返回 echostr，表示接入成功，否则接入失败
	       if(commonService.checkSignature(signature, timestamp, nonce)){
	           out.print(echostr);
	       }
	       out.close();
	       out = null;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("weinxiEvent exception :",e);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}


}
