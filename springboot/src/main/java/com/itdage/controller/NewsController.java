package com.itdage.controller;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.itdage.entity.News;
import com.itdage.util.NewsInterfaceController;

@Controller
public class NewsController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${news.url}")
	private String url;
	
	@Value("${news.AppKey}")
	private String appKey;
	
	@ResponseBody
	@RequestMapping(value = "/loadNews", method = RequestMethod.POST)
	public News getNews(){
		ConcurrentHashMap<String, News> map = NewsInterfaceController.map;
		if(map.get("news") == null){
			News news2 = restTemplate.getForObject(url + "?type=top&key=" + appKey, News.class);
			map.put("news", news2);
			return news2;
		}
		System.out.println(NewsInterfaceController.map.get("news"));
		return map.get("news");
	}
}
