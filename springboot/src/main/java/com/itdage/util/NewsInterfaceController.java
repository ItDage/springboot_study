package com.itdage.util;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.itdage.entity.News;


/**
 * 
 * @ClassName: NewsInterfaceController 
 * @Description: 新闻数据接口
 * @author: scy
 * @date: 2018年10月20日 上午11:03:23
 */
@Configuration
@EnableScheduling
public class NewsInterfaceController {
	
	private final static Logger logger = LoggerFactory.getLogger(NewsInterfaceController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${news.url}")
	private String url;
	
	@Value("${news.AppKey}")
	private String appKey;
	
	public static ConcurrentHashMap<String, News> map = new ConcurrentHashMap<>();
	/**
	 * 
	 * @Title: getNews 
	 * @Description: 获取消息 -- 每日获取最大次数 100次
	 * @return
	 * @return: String
	 */
	@Scheduled(cron = "0 */30 * * * ?")
	public void getNews(){
		logger.info("定时请求新闻数据接口");
		News news2 = restTemplate.getForObject(url + "?type=top&key=" + appKey, News.class);
		map.put("news", news2);
	}
}
