package com.itdage.websocket.userList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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

import com.google.gson.Gson;
import com.itdage.constant.StatusConstant;
import com.itdage.entity.Result;
import com.itdage.entity.User;

@Service
public class UserListHandler extends AbstractWebSocketHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	public static Set<String> set = new HashSet<>();
	// 保存用户名和session的映射关系
	public static Map<String, WebSocketSession> userSessionMap = new ConcurrentHashMap<String, WebSocketSession>();
	// 其他类注入
	private WebSocketSession session;
	// 用户名
	private String username;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		this.session = session;
		// 获取拦截中的用户名
		Map<String, Object> map = session.getAttributes();
		username = (String) map.get("username");
		// 保存用户名和session的映射关系,发送消息时用
		userSessionMap.put(username, session);
		// 保存人员在线列表
		set.add(username);
		System.out.println("在线人员个数start" + set.size());
		// 广播上线通知
		try {
			// 广播上线通知
			broadcastMsg(userSessionMap, username, "上线了...");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 更新用户列表
	}
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String msg = message.getPayload();
		System.out.println("接收到客户端信息:" + message.getPayload());
		Result result = new Gson().fromJson(msg, Result.class);
		if (result.getCode() == StatusConstant.USERLIST) {
			broadcastUserList(set, (String)session.getAttributes().get("username"));
		}
		System.out.println(result);
		session.sendMessage(new TextMessage("hello websocket"));
	}
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//		set.remove(username);
//		userSessionMap.remove(session.getAttributes().get("username"));
		System.out.println("在线人员个数" + set.size());
	}
	public WebSocketSession getSession() throws Exception {
		if (!this.session.isOpen()) {
			throw new Exception("WebSocket已关闭!");
		}
		return session;
	}
	public void setSession(WebSocketSession session) {
		this.session = session;
	}
	/**
	 * @Title: broadcastMsg
	 * @Description: 广播消息
	 * @param map 在线人员的map
	 * @param username 上线的用户名
	 * @param msg 消息的内容
	 * @return: void
	 * @throws IOException
	 */
	public void broadcastMsg(Map<String, WebSocketSession> map, String username, String msg) throws IOException {
		Set<Entry<String, WebSocketSession>> entrySet = map.entrySet();
		Result result = new Result();
		WebSocketSession session = null;
		// 发送消息
		try {
			for (Entry<String, WebSocketSession> entry : entrySet) {
				session = entry.getValue();
				// 如果当前用户是自己则不推送上线消息 但推送上线列表
				if (entry.getKey().equals(username)) {
					continue;
				}
				result.setMsg(username + msg);
				// 待优化
				session.sendMessage(new TextMessage(new Gson().toJson(result)));
			}
		} catch (IOException e) {
			session.close();
			e.printStackTrace();
		}
	}
	/**
	 * @Title: broadcastUserList
	 * @Description: 广播用户列表
	 * @param set 用户列表
	 * @param username 当前用户
	 * @throws IOException
	 * @return: void
	 */
	public void broadcastUserList(Set<String> set, String username) throws IOException {
		Result result = new Result();
		result.setCode(StatusConstant.USERLIST);
		result.setObj(set);
		// 发送消息
		try {
			if(username == null){
				for (String userName : set) {
					userSessionMap.get(userName).sendMessage(new TextMessage(new Gson().toJson(result)));
				}
			}else{
				userSessionMap.get(username).sendMessage(new TextMessage(new Gson().toJson(result)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
