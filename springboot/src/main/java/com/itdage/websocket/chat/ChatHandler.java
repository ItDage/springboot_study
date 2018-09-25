package com.itdage.websocket.chat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
public class ChatHandler extends AbstractWebSocketHandler {

	// 保存单线联系的关系,以这个为依据判断推送到chat页面还是index页面
	private static Map<String, String> relationMap = new ConcurrentHashMap<String, String>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String user_from = (String)session.getAttributes().get("user_from");
		String user_to = (String)session.getAttributes().get("user_to");
		relationMap.put(user_from, user_to);
		System.out.println("chat连接成功");
		System.out.println(user_from + "向" + user_to + "发送信息");
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		String user_from = (String)session.getAttributes().get("user_from");
		String user_to = (String)session.getAttributes().get("user_to");
		if(relationMap.get(user_from) == "")
		// 入口已经判断是否为空了
		
		session.sendMessage(new TextMessage("chat服务端响应"));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("chat连接关闭");
		relationMap.remove(session.getAttributes().get("user_from"));
	}
}
