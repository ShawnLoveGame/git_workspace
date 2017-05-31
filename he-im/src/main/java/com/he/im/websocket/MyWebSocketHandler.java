package com.he.im.websocket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.he.im.service.user.UserService;
import com.he.im.util.shared.RedisUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Socket处理器
 */
@Component
public class MyWebSocketHandler implements WebSocketHandler {

	private static Log log = LogFactory.getLog(MyWebSocketHandler.class);
	//用于保存HttpSession与WebSocketSession的映射关系
	public static final Map<String, WebSocketSession> userSocketSessionMap;

	@Autowired private UserService  userService;
	@Resource private RedisUtils redisUtils;
	
	static {
		userSocketSessionMap = new ConcurrentHashMap<String, WebSocketSession>();
	}
	
	/**
	 * 建立连接后,把登录用户的id写入WebSocketSession
	 */
	public void afterConnectionEstablished(final WebSocketSession session)
			throws Exception {
		String userName = (String) session.getAttributes().get("userName");
		log.error("userName: + "+userName);
		if (userSocketSessionMap.get(userName) == null) {
			userSocketSessionMap.put(userName, session);
		}
	}

	/**
	 * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
	 */
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		log.error("收消息处理：" + message.getPayload());
			if(message.getPayloadLength()==0)
				return;
			Message msg=new Gson().fromJson(message.getPayload().toString(),Message.class);
			msg.setDate(new Date());
			sendMessageToUser(msg.getFromName(), new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
	}

	/**
	 * 消息传输错误处理
	 */
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// 移除当前抛出异常用户的Socket会话
		while (it.hasNext()) {
			Entry<String, WebSocketSession> entry = it.next();
			if (entry.getValue().getId().equals(session.getId())) {
				userSocketSessionMap.remove(entry.getKey());
				System.out.println("Socket会话已经移除:用户ID" + entry.getKey());
				break;
			}
		}
	}

	/**
	 * 关闭连接后
	 */
	public void afterConnectionClosed(WebSocketSession session,CloseStatus closeStatus) throws Exception {
		System.out.println("Websocket:" + session.getId() + "已经关闭");
		Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		// 移除当前用户的Socket会话
		while (it.hasNext()) {
			Entry<String, WebSocketSession> entry = it.next();
			if (entry.getValue().getId().equals(session.getId())) {
				userSocketSessionMap.remove(entry.getKey());
				System.out.println("Socket会话已经移除:用户ID" + entry.getKey());
				break;
			}
		}
	}

	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 给所有在线用户发送消息
	 * @param message
	 * @throws IOException
	 */
	public void broadcast(final TextMessage message) throws IOException {
		Iterator<Entry<String, WebSocketSession>> it = userSocketSessionMap.entrySet().iterator();
		//多线程群发
		while (it.hasNext()) {
			final Entry<String, WebSocketSession> entry = it.next();
			if (entry.getValue().isOpen()) {
				// entry.getValue().sendMessage(message);
				new Thread(new Runnable() {

					public void run() {
						try {
							if (entry.getValue().isOpen()) {
								entry.getValue().sendMessage(message);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}).start();
			}

		}
	}

	/**
	 * 给某个用户发送消息
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sendMessageToUser(String uid, TextMessage message) throws IOException {
		WebSocketSession session = userSocketSessionMap.get(uid);
		if (session != null && session.isOpen()) {
			session.sendMessage(message);
		}
	}

	public static void sendMsgToUser(String fromName  , String toName , String text, Integer type){
		Message msg = new Message();
		msg.setFromName(fromName);
		msg.setToName(toName);
		msg.setText(text);
		msg.setType(type);
		try {
			WebSocketSession session = MyWebSocketHandler.userSocketSessionMap.get(toName);
			if (session != null && session.isOpen()) {
				session.sendMessage(new TextMessage(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().toJson(msg)));
			}
		} catch (Exception e) {
			log.error("推送失败" ,  e);
		}
	}

}
