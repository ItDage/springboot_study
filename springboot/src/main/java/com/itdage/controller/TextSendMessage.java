package com.itdage.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.TextMessage;

import com.itdage.websocket.userList.UserListHandler;

@Controller
public class TextSendMessage {
	
	@Autowired
	private UserListHandler chatHandler;
	
	@RequestMapping("/test")
	public void test(){
		try {
			chatHandler.getSession().sendMessage(new TextMessage("另外一个类的调用"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
