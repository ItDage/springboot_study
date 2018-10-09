package com.itdage.websocket.chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
	// 缓存未接收的消息(对方chat.html没有打开, 缓存该信息)
	public static List<Result> cacheMessageList = new ArrayList<Result>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String user_from = (String) session.getAttributes().get("user_from");
		String user_to = (String) session.getAttributes().get("user_to");
		relationMap.put(user_from, user_to);
		chatSessionMap.put(user_from, session);
		System.out.println("chat连接成功");
		Iterator<Result> iterator = cacheMessageList.iterator();
		boolean isRemoveDot = false;
		while(iterator.hasNext()){
			Result result = iterator.next();
			if(user_from.equals(result.getUser_to())){
				result.setCode(StatusConstant.MESSAGE_CHAT_NOTICE);
				session.sendMessage(new TextMessage(new Gson().toJson(result)));
				iterator.remove();
				isRemoveDot = true;
			}
		}
		// 主页取消小黄点
		if(isRemoveDot){
			Result result = new Result();
			result.setCode(StatusConstant.MESSAGE_INDEX_CANCEL);
			result.setObj(user_to);
			UserListHandler.userSessionMap.get(user_from).sendMessage(new TextMessage(new Gson().toJson(result)));
		}
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		// 构造消息返回实体
		Result result = new Result();
		// 获取消息来源和目的地
		String user_from = (String) session.getAttributes().get("user_from");
		String user_to = (String) session.getAttributes().get("user_to");
		result.setUser_from(user_from);
		result.setUser_to(user_to);
		// 消息发送时间--通用
		result.setDate(DateUtil.getYMDHMSByDate(new Date()));
		//TODO 消息内容 -- 通用  --非空处理
		result.setMsg((String)message.getPayload());
		// 入口user_from和user_to已判断是否为空,此处不需再判断
		// 推送到自己页面(不管对方是否打开了chat.html页面)
		result.setCode(StatusConstant.MESSAGE_CHAT_NOTICE_ME);
		chatSessionMap.get(user_from).sendMessage(new TextMessage(new Gson().toJson(result)));
		if (user_from.equals(relationMap.get(user_to))) {
			// 推送到对方chat.html的websocket上 此处待优化(保存分两步走的)
			result.setCode(StatusConstant.MESSAGE_CHAT_NOTICE);
			chatSessionMap.get(user_to).sendMessage(new TextMessage(new Gson().toJson(result)));
		} else {
			// 推送到主页上,主页上显示小黄点
			result.setCode(StatusConstant.MESSAGE_INDEX_NOTICE);
			result.setObj(user_from);
			UserListHandler.userSessionMap.get(user_to).sendMessage(new TextMessage(new Gson().toJson(result)));
			// 此消息先保存
			cacheMessageList.add(result);
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("chat连接关闭");
		relationMap.remove(session.getAttributes().get("user_from"));
		chatSessionMap.remove(session.getAttributes().get("user_from"));
	}
}
