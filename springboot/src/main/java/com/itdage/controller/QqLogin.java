package com.itdage.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QqLogin {
    @ResponseBody
    @RequestMapping("/")
    public Map<String, Object> test(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("msg", "登录成功");
        return map;
    }
}
