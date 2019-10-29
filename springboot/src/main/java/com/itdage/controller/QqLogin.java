package com.itdage.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QqLogin {
    @RequestMapping("/")
    public String forwardChat(){
        return "empty.html";
    }
}
