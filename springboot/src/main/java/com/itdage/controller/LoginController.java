package com.itdage.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itdage.entity.Result;

@Controller
public class LoginController {
	public static Map<String, String> userMap = new ConcurrentHashMap<String, String>();

	@RequestMapping("/loginHtml")
	public String loginHtml() {
		return "login";
	}
	
	@ResponseBody
	@RequestMapping(value = "/login", method= RequestMethod.POST)
	public Result login(String username, String password, HttpServletRequest request) {
		Result result = new Result();
		if (userMap.containsKey(username)) {
			// 用户已存在
			result.setCode(400);
			result.setMsg("用户已存在");
		} else {
			request.getSession().setAttribute("username", username);
			userMap.put(username, password);
			result.setMsg("登录成功");
			result.setCode(200);
		}
		return result;
	}
}
