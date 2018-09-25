package com.itdage.websocket.chat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.runner.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.itdage.websocket.userList.UserListHandler;

@Component
public class ChatHandler extends AbstractWebSocketHandler {
	
	// 保存单线联系的关系,以这个为依据判断推送到chat页面还是index页面
	private static Map<String, String> relationMap = new ConcurrentHashMap<String, String>();
	// 保存chat页面user和session关系
	public static Map<String, WebSocketSession> chatSessionMap = new ConcurrentHashMap<String, WebSocketSession>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String user_from = (String) session.getAttributes().get("user_from");
		String user_to = (String) session.getAttributes().get("user_to");
		relationMap.put(user_from, user_to);
		chatSessionMap.put(user_from, session);
		System.out.println("chat连接成功");
	}
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		// 构造消息返回实体
		Result result = new Result();
		System.out.println("chat接收到的消息:" + message.getPayload());
		String user_from = (String) session.getAttributes().get("user_from");
		String user_to = (String) session.getAttributes().get("user_to");
		// 入口user_from和user_to已判断是否为空,此处不需再判断
		if (user_from.equals(relationMap.get(user_to))) {
			// 推送到chat.html的websocket上  此处待优化(保存分两步走的)
			chatSessionMap.get(user_to).sendMessage(new TextMessage((String)message.getPayload()));
		} else {
			// 推送到主页上
			UserListHandler.userSessionMap.get(user_to).sendMessage(new TextMessage("发送给主页消息"));
		}
		session.sendMessage(new TextMessage("chat服务端响应"));
	}
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("chat连接关闭");
		relationMap.remove(session.getAttributes().get("user_from"));
		chatSessionMap.remove(session.getAttributes().get("user_from"));
	}
}
