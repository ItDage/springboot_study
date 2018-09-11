package com.itdage.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.itdage.websocket.chat.ChatHandler;
import com.itdage.websocket.chat.ChatWebSocketInterceptor;
import com.itdage.websocket.userList.UserListHandler;
import com.itdage.websocket.userList.WebSocketHandShakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
	
	@Autowired
	private UserListHandler userListHandler;
	@Autowired
	private WebSocketHandShakeInterceptor interceptor;
	
	@Autowired
	private ChatHandler chatHandler;
	@Autowired
	private ChatWebSocketInterceptor chatInterceptor;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		
		registry.addHandler(userListHandler, "/userList").addInterceptors(interceptor).setAllowedOrigins("*");
		registry.addHandler(chatHandler, "/chat").addInterceptors(chatInterceptor).setAllowedOrigins("*");
		
	}
}
