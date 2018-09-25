package com.itdage.websocket.chat;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Component
public class ChatWebSocketInterceptor implements HandshakeInterceptor {
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			String user_from= (String) servletRequest.getServletRequest().getSession().getAttribute("username");
			String user_to = servletRequest.getServletRequest().getParameter("user_to");
//			String msg = servletRequest.getServletRequest().getParameter("msg");
			if (!StringUtils.isEmpty(user_from) && !StringUtils.isEmpty(user_to)) {
				attributes.put("user_from", user_from);
				attributes.put("user_to", user_to);
//				attributes.put("msg", msg);
				return true;
			}
		}
		return false;
	}
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO Auto-generated method stub
	}
}
