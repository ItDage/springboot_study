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
		return "login-new";
	}
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result login(String username, String password, HttpServletRequest request) {
		Result result = new Result();
			if (userMap.containsKey(username)) {
				if(userMap.get(username).equals(password)){
					request.getSession().setAttribute("username", username);
					result.setMsg("登录成功");
					result.setCode(200);
				}else{
					result.setCode(400);
					result.setMsg("用户名或密码不正确!");
				}
			} else {
				result.setCode(200);
				result.setMsg("注册成功");
				userMap.put(username, password);
				request.getSession().setAttribute("username", username);
			}
			return result;
		}
}
