package com.itdage.websocket.userList;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import com.google.gson.Gson;
import com.itdage.constant.StatusConstant;
import com.itdage.controller.LoginController;
import com.itdage.entity.Result;

@Service
public class UserListHandler extends AbstractWebSocketHandler {
	// 登录后保存的用户名  退出即注销, 必须要new一下 不这样写会报错:java.lang.UnsupportedOperationException: null 详见:https://blog.csdn.net/Tracycater/article/details/77592472?locationNum=2&fps=1
	public static Set<String> set = new HashSet<>(LoginController.userMap.keySet());
	// 保存用户名和session的映射关系
	public static Map<String, WebSocketSession> userSessionMap = new ConcurrentHashMap<String, WebSocketSession>();
	// 其他类注入
	private WebSocketSession session;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		this.session = session;
		System.out.println("主页websocket连接建立");
		// 获取拦截中的用户名
		String username = (String) session.getAttributes().get("username");
//		if(String)
		// 保存用户名和session的映射关系,发送消息时用
		set.add(username);
		userSessionMap.put(username, session);
		Result result = new Result();
		
		// 广播上线通知
		try {
			// 广播上线通知
			result.setCode(StatusConstant.ONLINE_NOTICE);
			result.setMsg(username + "上线了");
			broadcastMsg(set, username, false, true, result);
			// 广播用户列表(排除自己给其他人发)
			result.setCode(StatusConstant.USERLIST);
			broadcastMsg(set, username, false, true, result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 更新用户列表
	}
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String msg = message.getPayload();
		Result result = new Gson().fromJson(msg, Result.class);
		if (result.getCode() == StatusConstant.USERLIST) {
			broadcastMsg(set, (String)session.getAttributes().get("username"), true, false, result);
		}
	}
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String username = (String)session.getAttributes().get("username");
		set.remove(username);
		userSessionMap.remove(username);
		Result result = new Result();
		result.setCode(StatusConstant.USERLIST);
		broadcastMsg(set, username, false, true, result);
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
	 * @Title: broadcastUserList
	 * @Description: 广播用户列表
	 * @param set 用户列表
	 * @param username 当前用户
	 * @param isSingle 是否是单发
	 * @param excludeCurrentUser 发送信息时是否排除当前用户
	 * @param result 返回的消息包装类
	 * @throws IOException
	 * @return: void
	 */
	public void broadcastMsg(Set<String> set, String username, boolean isSingle, boolean excludeCurrentUser, Result result) throws IOException {
		result.setObj(set);
		// 发送消息
		try {
			if(isSingle){
				// 给指定人发
				userSessionMap.get(username).sendMessage(new TextMessage(new Gson().toJson(result)));
			}else{
				// 给所有人发
				for (String userName : set) {
					if(excludeCurrentUser && userName.equals(username)){
						// 排除username给其他人发
						continue;
					}
					userSessionMap.get(userName).sendMessage(new TextMessage(new Gson().toJson(result)));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
