package com.itdage.websocket.chat;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.google.gson.Gson;
import com.itdage.constant.StatusConstant;
import com.itdage.entity.Result;
import com.itdage.util.DateUtil;
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
		// 消息发送时间--通用
		result.setDate(DateUtil.getYMDHMSByDate(new Date()));
		String user_from = (String) session.getAttributes().get("user_from");
		String user_to = (String) session.getAttributes().get("user_to");
		// 入口user_from和user_to已判断是否为空,此处不需再判断
		if (user_from.equals(relationMap.get(user_to))) {
			result.setObj(message.getPayload());
			// 推送到自己的chat.html页面(前端也可以直接推送,但是时间前端获取不到)
			result.setCode(StatusConstant.MESSAGE_CHAT_NOTICE_ME);
			chatSessionMap.get(user_from).sendMessage(new TextMessage(new Gson().toJson(result)));
			// 推送到对方chat.html的websocket上 此处待优化(保存分两步走的)
			result.setCode(StatusConstant.MESSAGE_CHAT_NOTICE);
			chatSessionMap.get(user_to).sendMessage(new TextMessage(new Gson().toJson(result)));
		} else {
			// 推送到主页上
			result.setCode(StatusConstant.MESSAGE_INDEX_NOTICE);
			result.setMsg((String)message.getPayload());
			result.setObj(user_to);
			UserListHandler.userSessionMap.get(user_to).sendMessage(new TextMessage(new Gson().toJson(result)));
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("chat连接关闭");
		relationMap.remove(session.getAttributes().get("user_from"));
		chatSessionMap.remove(session.getAttributes().get("user_from"));
	}
}
