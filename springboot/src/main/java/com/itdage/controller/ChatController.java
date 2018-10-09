package com.itdage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @ClassName: ChatController 
 * @Description: 暂时只为了跳转到chat.html使用
 * @author: scy
 * @date: 2018年9月8日 下午2:11:18
 */
@Controller
public class ChatController {
	
	
	@RequestMapping("/chatPage")
	public String forwardChat(){
		return "chat.html";
	}
	
	@RequestMapping("/chatRoom")
	public String forwardChatRoom(){
		return "chatRoom.html";
	}
}
