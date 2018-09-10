package com.itdage.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import com.itdage.entity.User;
import com.itdage.service.TestContextService;

public class MyApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent>{

	private Logger logger = LoggerFactory.getLogger(MyApplicationReadyEventListener.class);
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		ConfigurableApplicationContext context = event.getApplicationContext();
		User user = (User)context.getBean("user");
		System.out.println(user.getName() + user.getAge());
		logger.info("输入日志到文件");
	}
}