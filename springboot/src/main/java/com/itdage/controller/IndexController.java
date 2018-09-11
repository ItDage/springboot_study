package com.itdage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	
	@RequestMapping("/index")
	public String forwardIndex(){
		
		return "index.html";
	}
}
