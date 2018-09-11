package com.itdage.websocket.userList;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.itdage.entity.Result;
import com.itdage.entity.User;

@Service
public class UserListHandler extends AbstractWebSocketHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static List<User> list = new ArrayList<User>();
	// 保存用户名和session的映射关系
	public static Map<String, WebSocketSession> userSessionMap = new ConcurrentHashMap<String, WebSocketSession>();
	
	// 其他类注入
	private WebSocketSession session;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		this.session = session;
		// 获取拦截中的用户名
		Map<String, Object> map = session.getAttributes();
		String username = (String)map.get("username");
		// 保存用户名和session的映射关系,发送消息时用
		userSessionMap.put(username, session);
		session.sendMessage(new TextMessage("test"));
		
		// 广播上线通知 + 更新用户列表
		broadcastMsg(userSessionMap, "上线了...");
	}
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		logger.info("接收到客户端信息:" + message.getPayload());
		session.sendMessage(new TextMessage("hello websocket"));
	}
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
	}
	public WebSocketSession getSession() throws Exception {
		if(! this.session.isOpen()){
			throw new Exception("WebSocket已关闭!");
		}
		return session;
	}
	public void setSession(WebSocketSession session) {
		this.session = session;
	}
	
	/**
	 * 
	 * @Title: broadcastMsg 
	 * @Description: 广播消息
	 * @param map 在线人员的map
	 * @param msg 消息的内容
	 * @return: void
	 * @throws IOException 
	 */
	public void broadcastMsg(Map<String, WebSocketSession> map, String msg) throws IOException{
		Set<Entry<String, WebSocketSession>> entrySet = map.entrySet();
		for(Entry<String, WebSocketSession> entry : entrySet){
			msg = entry.getKey() + msg;
			list.add(new User(entry.getKey(), 23));
			// 待优化
			entry.getValue().sendMessage(new TextMessage(msg));
		}
	}
	
	public void broadcastUserList(Map<String, WebSocketSession> map, String msg){
		Result result = new Result();
		Set<Entry<String, WebSocketSession>> entrySet = map.entrySet();
		for(Entry<String, WebSocketSession> entry : entrySet){
			msg = entry.getKey() + msg;
			list.add(new User(entry.getKey(), 23));
			// 待优化
			entry.getValue().sendMessage(new TextMessage(msg));
		}
	}
}
