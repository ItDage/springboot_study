package com.itdage.springboot;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import com.itdage.service.TestContextService;

public class MyApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent>{

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		ConfigurableApplicationContext context = event.getApplicationContext();
		TestContextService testContextService = (TestContextService)context.getBean("testContextService");
		testContextService.test();
		System.out.println(testContextService.name);
		
	}
}
