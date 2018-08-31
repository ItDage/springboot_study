package com.itdage.springboot;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

public class MyApplicationStartingEvent implements ApplicationListener<ApplicationStartingEvent>{

	@Override
	public void onApplicationEvent(ApplicationStartingEvent event) {
		SpringApplication application = event.getSpringApplication();
		application.setBannerMode(Banner.Mode.OFF);
		System.out.println("------------执行MyApplicationSatringEventListener-----------");
		
	}
}
