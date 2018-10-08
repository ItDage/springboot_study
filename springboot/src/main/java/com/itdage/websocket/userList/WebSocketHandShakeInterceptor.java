package com.itdage.websocket.userList;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
public class WebSocketHandShakeInterceptor implements HandshakeInterceptor {
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			/**
			 * 集成单点登录的时候这里可以用request获取token去换取用户名
			 */
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			String username = (String) servletRequest.getServletRequest().getSession().getAttribute("username");
			if(!StringUtils.isEmpty(username)){
				attributes.put("username", username);
				return true;
			}
			System.out.println("拦截" + username);
		}
		return false;
	}
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
	}
}
