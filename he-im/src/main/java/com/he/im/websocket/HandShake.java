
package com.he.im.websocket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * Socket建立连接（握手）和断开
 */
public class HandShake implements HandshakeInterceptor {

	private Log log = LogFactory.getLog(getClass());

	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
		log.error("Websocket:用户[userName:" + ((ServletServerHttpRequest) request).getServletRequest().getSession(false).getAttribute("userName") + "]已经建立连接");
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest().getSession(false);
			// 标记用户
			String userName = (String) session.getAttribute("userName");
			if(userName!=null){
				attributes.put("userName", userName.replaceAll("@" , "+40").replaceAll(" " , "-40"));
			}else{
				return false;
			}
		}
		return true;
	}

	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
		log.error("after hand");
	
	}

}
